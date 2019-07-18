package com.binding.containerview;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import com.binding.R;
import com.binding.interfaces.BindNetMode;
import com.binding.interfaces.BindRefreshListener;
import com.binding.interfaces.NetRefreshViewInterface;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;


/**
 * Created by tanghongbin on 2017/9/25.
 */

public abstract class BasePullToRefreshView<VIEW extends View> extends FrameLayout
        implements NetRefreshViewInterface {
    protected Context mContext;
    protected SmartRefreshLayout mSmartRefreshLayout;
    protected VIEW mrefreshView;
    protected Handler mHandler;

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
        mHandler = new Handler(Looper.getMainLooper());
        LayoutInflater.from(mContext).inflate(getRefreshLayoutId(), this);
        mSmartRefreshLayout = (SmartRefreshLayout) findViewById(getSmartRefreshLayoutId());
        mSmartRefreshLayout.setEnableAutoLoadMore(true);
        mSmartRefreshLayout.setEnableFooterFollowWhenNoMoreData(true);
        mrefreshView = (VIEW) findViewById(getRefreshViewId());
        hookInit();
    }

    protected abstract int getSmartRefreshLayoutId();

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

        mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                Log.i("TAG", "加载更多:");
                if (bindRefreshListener != null) {
                    bindRefreshListener.pullUpToLoadMore();
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
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

    /***
     * Log.i("TAG", "启用加载更多和刷新");
     */
    @Override
    public void setModeBoth() {
        mSmartRefreshLayout.setEnableLoadMore(true);
        mSmartRefreshLayout.setEnableRefresh(true);
    }

    /***
     * Log.i("TAG", "禁用加载更多，启用刷新");
     */
    @Override
    public void setModePullFromUp() {
        Log.i("TAG", "禁用加载更多");
        mSmartRefreshLayout.setEnableLoadMore(false);
        mSmartRefreshLayout.setEnableRefresh(true);
    }

    /***
     * Log.i("TAG", "启用加载更多和刷新");
     */
    @Override
    public void setModePullFromDown() {
        mSmartRefreshLayout.setEnableLoadMore(true);
        mSmartRefreshLayout.setEnableRefresh(true);
    }
    /***
     * Log.i("TAG", "禁用加载更多和刷新");
     */
    @Override
    public void setModeDisabled() {
        mSmartRefreshLayout.setEnableLoadMore(false);
        mSmartRefreshLayout.setEnableRefresh(false);
    }

    @Override
    public void onBindNetRefreshComplete(boolean noMoreData) {
        RefreshState state = mSmartRefreshLayout.getState();
        if (state == RefreshState.Loading) {
            if (noMoreData) {
                mSmartRefreshLayout.finishLoadMoreWithNoMoreData();
            } else {
                mSmartRefreshLayout.finishLoadMore();
            }
        } else if (state == RefreshState.Refreshing) {
            // 这里有个300毫秒的延迟，设置没有数据要在350ms之后
            mSmartRefreshLayout.finishRefresh();
            if (noMoreData){
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSmartRefreshLayout.setNoMoreData(true);
                    }
                },400);
            } else {
                mSmartRefreshLayout.resetNoMoreData();
            }
        } else {
            if (noMoreData) {
                mSmartRefreshLayout.setNoMoreData(true);
            } else {
                mSmartRefreshLayout.setNoMoreData(false);
            }
        }
    }

    @Override
    public BindNetMode getBindNetMode() {
        return BindNetMode.BOTH;
    }

    @Deprecated
    private BindNetMode createMode() {
        BindNetMode mode = null;
//        mSmartRefreshLayout.setEnableLoadMore()
//        if (mSmartRefreshLayout.isEnableLoadmore() && mSmartRefreshLayout.refresh()) {
//            mode = BindNetMode.BOTH;
//        } else if (!mSmartRefreshLayout.isEnableLoadmore() && mSmartRefreshLayout.isEnableRefresh()) {
//            mode = BindNetMode.ONLY_LOAD_MORE;
//        } else if (mSmartRefreshLayout.isEnableLoadmore() && !mSmartRefreshLayout.isEnableRefresh()) {
//            mode = BindNetMode.PULL_FROM_DOWN;
//        } else if (!mSmartRefreshLayout.isEnableLoadmore() && !mSmartRefreshLayout.isEnableRefresh()) {
//            mode = BindNetMode.DISABLED;
//        }
        return mode;
    }
}
