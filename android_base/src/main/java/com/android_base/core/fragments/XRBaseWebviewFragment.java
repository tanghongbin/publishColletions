package com.android_base.core.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;

import com.android_base.R;
import com.android_base.core.module.BaseWebviewHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import java.lang.reflect.Constructor;

import me.yokeyword.fragmentation.SupportFragment;


/**
 *
 * Description: XRBaseWebviewFragment,展示webview的公用fragment，
 */
public class XRBaseWebviewFragment extends SupportFragment {
    BridgeWebView baseWebView;
    private String url;
    protected BaseWebviewHandler mmBaseWebviewHanlder;
    protected String mModelClass;

    public BridgeWebView getBaseWebView() {
        return baseWebView;
    }
    protected View mRootView;
    /**
     * 创建fragment
     * @param classType
     * @param url
     * @return
     */
    public static XRBaseWebviewFragment createFragment(Class<? extends BaseWebviewHandler> classType, String url){
        Bundle bundle = new Bundle();
        bundle.putString("url",url);
        if (classType != null){
            bundle.putString("modelClass",classType.getName());
        }
        XRBaseWebviewFragment baseWebviewFragment = new XRBaseWebviewFragment();
        baseWebviewFragment.setArguments(bundle);
        return baseWebviewFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mRootView != null) return mRootView;
        mRootView = View.inflate(_mActivity, R.layout.base_webview,null);
        baseWebView = (BridgeWebView) mRootView.findViewById(R.id.bridge_web);
        url = getArguments().getString("url");
        mModelClass = getArguments().getString("modelClass");
        initModel();
        baseWebView.getSettings().setDomStorageEnabled(true);
        baseWebView.getSettings().setAppCacheEnabled(false);
        baseWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        baseWebView.loadUrl(url);

        return mRootView;
    }

    /**
     * 初始化model
     *
     */
    private void initModel() {
        if (TextUtils.isEmpty(mModelClass))return;
        try {
            Class modelClass =  Class.forName(mModelClass);
            Constructor construtor = modelClass.getConstructor(Context.class,BridgeWebView.class);
            mmBaseWebviewHanlder = (BaseWebviewHandler) construtor.newInstance(_mActivity,baseWebView);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        //退出前加载空webview，避免退出还会继续播放音视频
        baseWebView.loadUrl("file:///android_asset/webview/noexistcontent.html");
        super.onDestroy();
    }
}
