package com.android_base.core.views.bindingViews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.AdapterView;

import com.android_base.R;
import com.android_base.core.interfaces.BindNetAdapter;
import com.android_base.core.views.bindingViews.adpters.XBaseRecycleAdapter;

/**
 * Created by tanghongbin on 2017/9/25.
 */

public class ExtendPullToRefreshRecycleView extends BasePullToRefreshView<RecyclerView> {
    public ExtendPullToRefreshRecycleView(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getRefreshViewId() {
        return R.id.bind_net_recycleView;
    }

    @Override
    protected int getRefreshLayoutId() {
        return R.layout.base_libaray_bind_net_refresh_recycleview;
    }

    @Override
    public void setBindNetAdapter(BindNetAdapter adapter) {
        mrefreshView.setAdapter((RecyclerView.Adapter) adapter);
    }

    @Override
    public void setBindNetOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        RecyclerView.Adapter adapter = mrefreshView.getAdapter();
        if (adapter instanceof XBaseRecycleAdapter){
            XBaseRecycleAdapter baseRecycleAdapter = (XBaseRecycleAdapter) adapter;
            baseRecycleAdapter.setOnItemClickListener(onItemClickListener);
        }
    }

    @Override
    protected void hookInit() {
        mrefreshView.setLayoutManager(new LinearLayoutManager(mContext));
    }
}
