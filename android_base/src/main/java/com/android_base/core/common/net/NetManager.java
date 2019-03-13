package com.android_base.core.common.net;


import android.content.Context;
import android.text.TextUtils;

import com.android_base.core.common.eventbus.BusHelper;
import com.android_base.core.common.net.callbacks.HttpCallback;
import com.android_base.core.common.net.callbacks.ProgressCallback;
import com.android_base.core.common.net.callbacks.RequestCallback;
import com.android_base.core.common.net.callbacks.RequestStringCallback;
import com.android_base.core.common.net.entry.Response;
import com.android_base.events.BaseEvent;
import com.android_base.events.DownloadBaseEvent;
import com.android_base.events.ErrorEvent;
import com.android_base.events.RequestEndEvent;
import com.android_base.events.RequestStartEvent;
import com.android_base.events.UploadMutipleFileErrorEvent;
import com.android_base.util.ClassUtil;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.request.BaseRequest;
import com.lzy.okgo.request.PostRequest;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import okhttp3.Call;
import okhttp3.ResponseBody;
import pushlish.tang.com.commonutils.others.NetworkUtils;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 此工具类在对应具体的网络业务层和
 * 网络请求框架类型做了一层桥接，如果以后进行替换，可直接将
 * OkRequestType 类型换成其他类型，只是把底层网络请求框架替换即可，
 * 对应的业务层逻辑不会做修改。所有数据请求都会统一做网络判断处理
 */

public final class NetManager {
    /**
     * @description 这里不写成接口避免以后扩展会影响很多类
     */

    private static final int TIME_OUT = 10000;
    private static final int ERROR_CODE = 404;
    private static final int SUCCESS_CODE = 200;
    private static final int UPLOAD_OR_DOWNLOAD = 60 * 1000;
    private static final String DISABLED_NET_MESSAGE = "网络已断开，请稍候再试";
    public static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

    public static Context getContext() {
        return mContext;
    }


    /***
     * post 已body方式的进行请求
     * @param request 构建的请求
     */
    public static <T extends BaseEvent> void post(BaseRequest request, final Class<T> classType) {
        request(request, classType);
    }

    /**
     * 不需要响应事件
     *
     * @param request
     * @param isOpenSendAndEndEvent
     * @param classType
     */
    public static <T extends BaseEvent> void post(BaseRequest request, boolean isOpenSendAndEndEvent, final Class<T> classType) {
        Logger.d(request.getParams());
        request(request, isOpenSendAndEndEvent, classType);
    }

    /***
     * post 已body方式的进行请求
     * @param request 构建的请求,不需要响应
     *
     */
    public static <T extends BaseEvent> void post(BaseRequest request) {
        request(request, null);
    }


    /***
     *  GET 请求,不需要响应
     */
    public static <T extends BaseEvent> void get(BaseRequest request) {
        request(request, null);
    }


    public static <T extends BaseEvent> void get(BaseRequest request,boolean isNeedSendEvent) {
        request(request, isNeedSendEvent,null);
    }

    /***
     *  GET 请求
     */
    public static <T extends BaseEvent> void get(BaseRequest request, boolean isNeedSendEvent,final Class<T> classType) {
        request(request,isNeedSendEvent, classType);
    }

    /**
     * 单个文件上传
     */
    public static <T extends BaseEvent> void upLoad(BaseRequest request, final Class<T> classType,
                                                    final ProgressCallback progressListener) {
        if (!judgeNet()) {
            postErrorEvent(null, classType, new RuntimeException("网络已断开，请稍后再试"));
            return;
        }
        request.execute(new HttpCallback(request, classType) {
            @Override
            public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                if (progressListener != null) {
                    progressListener.onProgress(currentSize, totalSize, progress);
                }
            }
        });
    }

    /***
     * 此上传在后台同步进行,
     * 利用rx进行多个文件的顺序上传，并在每个文件
     * 上传成功后返回上传结果(返回的结果在UI线程处理),或者上传失败后重新进行上传(此失败不包换-网络异常，网络中断，服务器异常等，
     * 适用于文件损坏，地址找不到)，
     * 如果再次尝试上传失败后，则进行下一张上传,最后会在errorList里边存储上传失败的文件的地址，
     * 会在全部上传完成之后发送UploadMutipleFileErrorEvent事件
     */

    public static <T extends BaseEvent<UploadMutipleFileErrorEvent>> void upLoadMutipleFile(Map<String, PostRequest> requestList, final Class<T> classType) {
        if (!judgeNet()) {
            postErrorEvent(null, classType, new RuntimeException("网络已断开，请稍后再试"));
            return;
        }
        if (requestList == null || requestList.size() == 0) {
            return;
        }
        final List<String> errorList = new ArrayList<>();

        Observable.from(requestList.entrySet())
                .map(new Func1<Map.Entry<String, PostRequest>, Response>() {
                    @Override
                    public Response call(Map.Entry<String, PostRequest> entry) {
                        Response response = upLoadSync(entry.getValue());
                        if (response.getStatuCode() == ERROR_CODE) {
                            errorList.add(entry.getKey());
                        }
                        return response;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        BusHelper.postEvent(new RequestStartEvent());
                    }
                })
                .subscribe(new Action1<Response>() {
                    @Override
                    public void call(Response response) {
                        T event = ClassUtil.getInstance(classType);
                        if (event == null) {
                            return;
                        }

                        if (response != null && response.getStatuCode() == SUCCESS_CODE) {
                            event.setData(response.getData());
                        }
                        BusHelper.postEvent(event);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        BusHelper.postEvent(new RequestEndEvent());
                        postErrorEvent(null, classType, new RuntimeException(throwable.getMessage()));
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        T event = ClassUtil.getInstance(classType);
                        BusHelper.postEvent(new RequestEndEvent());
                        event.setError(new UploadMutipleFileErrorEvent(errorList));
                        BusHelper.postEvent(event);
                    }
                });
    }


    /**
     * 同步上传文件,需要参数添加到builder里面去
     *
     * @return 上传结果的返回值
     */
    private static Response upLoadSync(BaseRequest request) {
        try {
            request.connTimeOut(UPLOAD_OR_DOWNLOAD);
            okhttp3.Response response = request.execute();
            ResponseBody body = response.body();
            String message = body.string();
            return new Response(response.code(), message);
        } catch (Exception e) {
            Logger.i("同步上传文件出错");
            e.printStackTrace();
            return new Response(ERROR_CODE, "");
        }
    }


    /**
     * 下载文件
     */
    public static <T extends DownloadBaseEvent> void downLoad(BaseRequest request, String saveedPath, final Class<T> classType,
                                                              final ProgressCallback progressListener) {
        if (!judgeNet()) {
            try {
                T event = classType.newInstance();
                event.setError(new ErrorEvent("网络已断开，请稍后再试"));
                BusHelper.postEvent(event);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        request.connTimeOut(UPLOAD_OR_DOWNLOAD);
        request.execute(new FileCallback(saveedPath) {


            @Override
            public void onBefore(BaseRequest request) {
                BusHelper.postEvent(new RequestStartEvent());
            }

            @Override
            public void onSuccess(File file, Call call, okhttp3.Response response) {
                BusHelper.postEvent(new RequestEndEvent());
                T event = null;
                try {
                    event = classType.newInstance();
                    event.setFile(file);
                    BusHelper.postEvent(event);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                progressListener.onProgress(currentSize, totalSize, progress);
            }

            @Override
            public void onError(Call call, okhttp3.Response response, Exception e) {
                BusHelper.postEvent(new RequestEndEvent());
                try {
                    T event = classType.newInstance();
                    String message = response == null ? "" : response.message();
                    if (TextUtils.isEmpty(message)) {
                        message = e == null ? "" : e.getMessage();
                    }
                    event.setError(new ErrorEvent(message));
                    BusHelper.postEvent(event);
                } catch (InstantiationException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                }

            }
        });
    }

    /**
     * 所有请求配置https协议支持以及超时时长
     *
     * @param config
     */
    private static void conectionConfig(BaseRequest config) {
        config.connTimeOut(TIME_OUT);
        config.setCertificates();
    }

    /**
     * 在成功发送事件给调用方
     */
    private static <T extends BaseEvent> void postSuccessEvent(String s, Class<T> classType) {
        T event = null;
        try {
            event = classType.newInstance();
            event.setData(s);
            BusHelper.postEvent(event);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static boolean judgeNet() {
        //网络请求钱检查网络是否连接
        if (NetManager.getContext() == null) {
            throw new NullPointerException("你必须先调用Netmanager的init方法进行上下文环境对象的初始化");
        }
        if (!NetworkUtils.isConnected(mContext)) {
            return false;
        }
        return true;
    }

    /**
     * 在抛出异常或者失败时，发送事件
     *
     * @param response
     * @param classType
     * @param <T>
     */
    private static <T extends BaseEvent> void postErrorEvent(okhttp3.Response response, Class<T> classType, Exception e) {
        try {
            T event = classType.newInstance();
            String message = response == null ? "" : response.message();
            if (TextUtils.isEmpty(message)) {
                message = e == null ? "" : e.getMessage();
            }
            ErrorEvent errorEvent = new ErrorEvent(message);
            event.setError(errorEvent);
            BusHelper.postEvent(event);
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * get，post普通请求的一致封装
     *
     * @param request
     * @param classType
     * @param <T>
     */
    private static <T extends BaseEvent> void request(BaseRequest request, Class<T> classType) {
        request(request, true, classType);
    }

    private static <T extends BaseEvent> void request(BaseRequest request, boolean isNeedSendEvent, Class<T> classType) {
        if (!judgeNet()) {
            postErrorEvent(null, classType, new RuntimeException("网络已断开，请稍后再试"));
            return;
        }
        conectionConfig(request);
        HttpCallback callback = null;
        if (isNeedSendEvent) {
            callback = new HttpCallback(request, classType);
        } else {
            callback = new HttpCallback(request, isNeedSendEvent, classType);
        }

        request.execute(callback);
    }


    /**
     * 用回调方式进行请求
     *
     * @param request
     * @param callback
     */
    public static void getWithCallback(BaseRequest request,
                                       RequestCallback<String> callback) {
        getWithCallback(request, true, callback);
    }
    public static void getWithCallback(BaseRequest request,
                                      boolean isSendStartAndEndEvent, RequestCallback<String> callback) {
        requestWithCallback(request, isSendStartAndEndEvent,callback);
    }

    private static void requestWithCallback(BaseRequest request, boolean isSendStartAndEndEvent, RequestCallback<String> callback) {
        if (!judgeNet()) {
            if (callback != null) {
                callback.onFailed(DISABLED_NET_MESSAGE);
            }
            return;
        }
        conectionConfig(request);
        request.execute(new RequestStringCallback(callback,isSendStartAndEndEvent));
    }

    /**
     * 用回调方式进行请求
     * @param request
     * @param callback
     */
    public static void postWithCallback(BaseRequest request,
                                        RequestCallback<String> callback) {
        postWithCallback(request, true, callback);
    }
    public static void postWithCallback(BaseRequest request,
                                        boolean isSendStartAndEndEvent,RequestCallback<String> callback) {
        requestWithCallback(request, isSendStartAndEndEvent, callback);
    }


}
