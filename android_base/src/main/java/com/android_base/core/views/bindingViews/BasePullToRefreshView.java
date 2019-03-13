package com.android_base.core.views.bindingViews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;

import com.android_base.R;
import com.android_base.core.interfaces.BindNetMode;
import com.android_base.core.interfaces.BindRefreshListener;
import com.android_base.core.interfaces.NetRefreshViewInterface;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

/**
 * Created by tanghongbin on 2017/9/25.
 */

public abstract class BasePullToRefreshView<VIEW extends View> extends FrameLayout implements NetRefreshViewInterface {
    protected Context mContext;
    protected SmartRefreshLayout mSmartRefreshLayout;
    protected VIEW mrefreshView;

    public BasePullToRefreshView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public BasePullToRefreshView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(@NonNull Context context) {
        mContext = context;
        LayoutInflater.from(mContext).inflate(getRefreshLayoutId(), this);
        mSmartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.bind_net_refreshLayout);
        mSmartRefreshLayout.setEnableAutoLoadmore(false);
        mrefreshView = (VIEW) findViewById(getRefreshViewId());
        hookInit();
    }

    protected void hookInit() {

    }

    protected abstract int getRefreshViewId();

    protected abstract int getRefreshLayoutId();

    @Override
    public void setBindNetOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {

    }

    @Override
    public VIEW getBindNetRefreshableView() {
        return mrefreshView;
    }

    @Override
    public void setBindNetOnRefreshListener(final BindRefreshListener bindRefreshListener) {
        mSmartRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                Log.i("TAG", "加载更多:");
                if (bindRefreshListener != null) {
                    bindRefreshListener.pullUpToLoadMore();
                }
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if (bindRefreshListener != null) {
                    bindRefreshListener.pullDownRefresh();
                }
            }
        });
    }

    @Override
    public void setBindNetLayoutParams(ViewGroup.LayoutParams params) {
        setLayoutParams(params);
    }

    @Override
    public View getBindView() {
        return this;
    }

    @Override
    public void setModeBoth() {
        mSmartRefreshLayout.setEnableLoadmore(true);
        mSmartRefreshLayout.setEnableRefresh(true);
    }

    @Override
    public void setModePullFromUp() {
        Log.i("TAG", "警用加载更多");
        mSmartRefreshLayout.setEnableLoadmore(false);
    }

    @Override
    public void setModePullFromDown() {
        mSmartRefreshLayout.setEnableRefresh(false);
    }

    @Override
    public void setModeDisabled() {
        mSmartRefreshLayout.setEnableLoadmore(false);
        mSmartRefreshLayout.setEnableRefresh(false);
    }

    @Override
    public void onBindNetRefreshComplete() {
        if (mSmartRefreshLayout.isLoading()) {
            mSmartRefreshLayout.finishLoadmore();
        } else if (mSmartRefreshLayout.isRefreshing()) {
            mSmartRefreshLayout.finishRefresh();
        }
    }
    @Override
    public BindNetMode getBindNetMode() {
        return createMode();
    }

    private BindNetMode createMode() {
        BindNetMode mode = null;
        if (mSmartRefreshLayout.isEnableLoadmore() && mSmartRefreshLayout.isEnableRefresh()) {
            mode = BindNetMode.BOTH;
        } else if (!mSmartRefreshLayout.isEnableLoadmore() && mSmartRefreshLayout.isEnableRefresh()) {
            mode = BindNetMode.PULL_FROM_UP;
        } else if (mSmartRefreshLayout.isEnableLoadmore() && !mSmartRefreshLayout.isEnableRefresh()) {
            mode = BindNetMode.PULL_FROM_DOWN;
        } else if (!mSmartRefreshLayout.isEnableLoadmore() && !mSmartRefreshLayout.isEnableRefresh()) {
            mode = BindNetMode.DISABLED;
        }
        return mode;
    }
}
