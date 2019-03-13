package com.android_base.core.common.net.requestnettypes.okgorequest;

import com.lzy.okgo.adapter.CacheCall;
import com.lzy.okgo.request.BaseRequest;

import okhttp3.Response;

/**
 * Created by tanghongbin on 2017/4/9.
 */

public class CustomCacheCall<T>  extends CacheCall<T>{

    public CustomCacheCall(BaseRequest baseRequest) {
        super(baseRequest);
    }

}
