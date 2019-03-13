package com.android_base.core.module;

import android.app.Activity;
import android.content.Context;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;


/**
 * Created by tanghongbin on 2017/7/3.
 */

public class WebViewHandler extends BaseWebviewHandler {
    public WebViewHandler(Context mContext, BridgeWebView mBridgeWebView) {
        super(mContext, mBridgeWebView);
    }

    @Override
    protected void init() {
        mBridgeWebView.registerHandler("appBack", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                if (mContext instanceof Activity){
                    Activity activity = (Activity) mContext;
                    if (activity != null && !activity.isFinishing()){
                        activity.setResult(Activity.RESULT_OK);
                        activity.finish();
                    }
                }
            }
        });
    }
}
