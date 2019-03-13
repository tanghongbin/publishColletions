package com.android_base.core.common.net.callbacks;

/**
 * Created by tanghongbin on 2017/3/12.
 */

public abstract class RequestCallback<PARAM> {
    public abstract void onSuccess(PARAM param);

    public abstract void onFailed(String message);

    public void onBefore(){

    };

    public void onAfter(){

    };
}
