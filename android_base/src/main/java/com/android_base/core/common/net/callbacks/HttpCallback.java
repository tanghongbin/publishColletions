package com.android_base.core.common.net.callbacks;

import android.text.TextUtils;

import com.android_base.core.common.eventbus.BusHelper;
import com.android_base.events.BaseEvent;
import com.android_base.events.ErrorEvent;
import com.android_base.events.RequestEndEvent;
import com.android_base.events.RequestStartEvent;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okgo.request.BaseRequest;
import com.orhanobut.logger.Logger;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 请求字符串回调的基本封装，包装请求前验证网络，
 * 2.成功和错误处理
 */
public class HttpCallback<CLASS extends BaseEvent> extends com.lzy.okgo.callback.AbsCallback<String> {

    BaseRequest request;
    Class<CLASS> classType;
    boolean isNeedSendEvent = true;

    public HttpCallback(BaseRequest request,boolean isNeedSendEvent, Class<CLASS> classType) {
        this.request = request;
        this.classType = classType;
        this.isNeedSendEvent = isNeedSendEvent;
    }

    public HttpCallback(BaseRequest request, Class<CLASS> classType) {
        this.request = request;
        this.classType = classType;
    }

    @Override
    public void onBefore(BaseRequest request) {
        //在网络请求开始前发送请求开始的事件

        if(isNeedSendEvent){
            BusHelper.postEvent(new RequestStartEvent());
        }
        Logger.d("TAG","打印请求地址-->"+request.getUrl());
    }

    @Override
    public String convertSuccess(Response response) throws Exception {
        String s = StringConvert.create().convertSuccess(response);
        response.close();
        return s;
    }

    @Override
    public void onSuccess(String s, Call call, okhttp3.Response response) {
        try{
            Logger.i("TAG","打印返回结果-->"+s);
            if(classType == null) return;
            if(isNeedSendEvent){
                BusHelper.postEvent(new RequestEndEvent());
            }
            CLASS event = classType.newInstance();
            event.setData(s);
            BusHelper.postEvent(event);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void onError(Call call, okhttp3.Response response, Exception e) {
        try {
            Logger.i("TAG","请求错误-->");
            if(classType == null){
                return;
            }
            if(isNeedSendEvent){
                BusHelper.postEvent(new RequestEndEvent());
            }
            CLASS event = classType.newInstance();
            String errorMessage = response == null ? "":response.message();
            if(TextUtils.isEmpty(errorMessage)){
                errorMessage = e == null ? "" : e.getMessage();
            }
            event.setError(new ErrorEvent(errorMessage));
            BusHelper.postEvent(event);
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        }
    }
}
