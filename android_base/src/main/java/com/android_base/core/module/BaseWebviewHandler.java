package com.android_base.core.module;

import android.content.Context;
import android.os.Handler;

import com.github.lzyzsd.jsbridge.BridgeWebView;

/**
 * Created by tanghongbin on 2017/7/3.
 */

public abstract class BaseWebviewHandler {
    protected BridgeWebView mBridgeWebView;
    protected Context mContext;
    protected abstract void init();
    protected Handler mHandler;

    public BaseWebviewHandler(Context mContext,BridgeWebView mBridgeWebView) {
        this.mBridgeWebView = mBridgeWebView;
        this.mContext = mContext;
        mHandler = new Handler();
        init();
    }
}
