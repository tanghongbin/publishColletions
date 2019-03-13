package com.android_base.core.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.android_base.R;
import com.android_base.core.fragments.XRBaseWebviewFragment;
import com.android_base.core.module.WebViewHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by Magicwo on 2017/9/6.
 * 传入一个URl就可以跳转到该网页了
 */

public class CommonWebViewActivity extends SupportActivity {

    public final static String URL = "url";
    protected BridgeWebView webView;

    protected String url;

    protected XRBaseWebviewFragment baseWebviewFragment;
    protected Class handlerClass;


    public static void runActivity(Context context, String url) {
        runActivity(context,url,null);
    }

    public static void runActivity(Context context, String url,Class handlerClass) {
        Intent intent = new Intent(context, CommonWebViewActivity.class);
        intent.putExtra(URL, url);
        if (handlerClass != null){
            intent.putExtra("className",handlerClass.getName());
        }
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        url = getIntent().getStringExtra(URL);
        handlerClass = getHandlerClass();
        baseWebviewFragment = XRBaseWebviewFragment.createFragment(handlerClass, url);
        loadRootFragment(R.id.container, baseWebviewFragment);
        webView = baseWebviewFragment.getBaseWebView();
    }

    private Class getHandlerClass() {
        Class classX = null;
        String className = getIntent().getStringExtra("className");
        if (TextUtils.isEmpty(className)) return WebViewHandler.class;
        try {
           classX =  Class.forName(className);
        }catch (Exception e){
            classX = WebViewHandler.class;
        }
        return classX;
    }

    @Override
    public void onBackPressedSupport() {
        if (webView == null) {
            webView = baseWebviewFragment.getBaseWebView();
        }
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            setResult(RESULT_OK);
            finish();
        }
    }
}
