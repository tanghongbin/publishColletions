package com.android_base.core.common.net.callbacks;

import android.support.annotation.Nullable;
import android.util.Log;

import com.android_base.core.common.eventbus.BusHelper;
import com.android_base.events.RequestEndEvent;
import com.android_base.events.RequestStartEvent;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.BaseRequest;
import com.orhanobut.logger.Logger;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by tanghongbin on 2017/3/12.
 */

public class RequestStringCallback extends StringCallback {
    RequestCallback<String> requestCallback;
    boolean isNeedSendEvent;

    public RequestStringCallback(RequestCallback<String> requestCallback, boolean isSendStartAndEndEvent) {
        this.requestCallback = requestCallback;
        this.isNeedSendEvent = isSendStartAndEndEvent;
    }

    @Override
    public void onSuccess(String s, Call call, Response response) {
        if(isNeedSendEvent){
            BusHelper.postEvent(new RequestEndEvent());
        }
        if (requestCallback == null){
            Log.d("TAG","请求requestcallback为空"+requestCallback);
        }else {
            requestCallback.onSuccess(s);
        }
    }

    @Override
    public void onError(Call call, Response response, Exception e) {
        super.onError(call, response, e);
        Logger.e("TAG","请求异常");
        if(isNeedSendEvent){
            BusHelper.postEvent(new RequestEndEvent());
        }
        if (requestCallback != null){
            String message = e == null ? "网络异常,请稍候再试":e.getMessage();
            requestCallback.onFailed(message);
        }

    }

    @Override
    public void onAfter(@Nullable String s, @Nullable Exception e) {
        super.onAfter(s, e);
        if (requestCallback  != null){
            requestCallback.onAfter();
        }
    }

    @Override
    public void onBefore(BaseRequest request) {
        super.onBefore(request);
        if (requestCallback  != null){
            requestCallback.onBefore();
        }
        if(isNeedSendEvent){
            BusHelper.postEvent(new RequestStartEvent());
        }
    }

    @Override
    public void onCacheSuccess(String s, Call call) {
        super.onCacheSuccess(s, call);
    }

    @Override
    public String convertSuccess(Response response) throws Exception {
        return super.convertSuccess(response);
    }
}
