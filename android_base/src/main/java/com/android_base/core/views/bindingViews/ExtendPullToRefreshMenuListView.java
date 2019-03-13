package com.android_base.core.views.bindingViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.android_base.core.interfaces.BindNetAdapter;
import com.android_base.core.interfaces.BindNetMode;
import com.android_base.core.interfaces.BindRefreshListener;
import com.android_base.core.interfaces.NetRefreshViewInterface;
import com.android_base.core.views.PullRefreshMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

/**
 * Created by tanghongbin on 2017/9/9.
 */

public class ExtendPullToRefreshMenuListView extends PullRefreshMenuListView implements NetRefreshViewInterface {
    public ExtendPullToRefreshMenuListView(Context context) {
        super(context);
    }

    public ExtendPullToRefreshMenuListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void setBindNetOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        setOnItemClickListener(onItemClickListener);
    }

    @Override
    public View getBindNetRefreshableView() {
        return getRefreshableView();
    }

    @Override
    public void setBindNetOnRefreshListener(final BindRefreshListener bindRefreshListener) {
        setOnRefreshListener(new OnRefreshListener2<SwipeMenuListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<SwipeMenuListView> refreshView) {
                if (bindRefreshListener != null){
                    bindRefreshListener.pullDownRefresh();
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<SwipeMenuListView> refreshView) {
                if (bindRefreshListener != null){
                    bindRefreshListener.pullUpToLoadMore();
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
    public void setBindNetAdapter(BindNetAdapter adapter) {
        BaseAdapter baseAdapter = (BaseAdapter) adapter;
        setAdapter(baseAdapter);
    }

    @Override
    public void setModeBoth() {
        setMode(Mode.BOTH);
    }

    @Override
    public void setModePullFromUp() {
        setMode(Mode.PULL_FROM_START);
    }

    @Override
    public void setModePullFromDown() {
        setMode(Mode.PULL_FROM_END);
    }

    @Override
    public void setModeDisabled() {
        setMode(Mode.DISABLED);
    }

    @Override
    public void onBindNetRefreshComplete() {
        onRefreshComplete();
    }

    @Override
    public BindNetMode getBindNetMode() {
        return createMode();
    }

    private BindNetMode createMode() {
        BindNetMode mode = null;
        switch (getMode()){
            case BOTH:
                mode = BindNetMode.BOTH;
                break;
            case PULL_FROM_START:
                mode = BindNetMode.PULL_FROM_UP;
                break;
            case PULL_FROM_END:
                mode = BindNetMode.PULL_FROM_DOWN;
                break;
            case DISABLED:
                mode = BindNetMode.DISABLED;
                break;
        }
        return mode;
    }
}
