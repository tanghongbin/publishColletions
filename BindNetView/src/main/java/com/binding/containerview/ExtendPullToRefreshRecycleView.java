package com.binding.containerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.widget.AdapterView;

import com.binding.R;
import com.binding.interfaces.BindNetAdapter;


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
        return R.layout.net_refresh_recycleview;
    }

    @Override
    public void setBindNetAdapter(BindNetAdapter adapter) {
        mrefreshView.setAdapter((RecyclerView.Adapter) adapter);
    }


    /***
     * 在adapter里边已经支持了，没必要再写，而且写的代价颇高
     * @param onItemClickListener
     */
    @Deprecated
    @Override
    public void setBindNetOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {

    }

    @Override
    protected void hookInit() {
        mrefreshView.setItemAnimator(new DefaultItemAnimator());
    }

    public void setManager(RecyclerView.LayoutManager layoutManager){
        mrefreshView.setLayoutManager(layoutManager);
    }
}
