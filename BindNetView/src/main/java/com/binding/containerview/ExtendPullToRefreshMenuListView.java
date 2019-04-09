package com.binding.containerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.binding.R;
import com.binding.interfaces.BindNetAdapter;


/**
 * Created by tanghongbin on 2017/9/25.
 */

public class ExtendPullToRefreshMenuListView extends BasePullToRefreshView<SwipeMenuListView> {
    public ExtendPullToRefreshMenuListView(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getSmartRefreshLayoutId() {
        return R.id.swipe_menu_refresh_view;
    }

    @Override
    protected int getRefreshViewId() {
        return R.id.bind_net_swipemenu;
    }

    @Override
    protected int getRefreshLayoutId() {
        return R.layout.net_refresh_menulist;
    }

    @Override
    public void setBindNetAdapter(BindNetAdapter adapter) {
        mrefreshView.setAdapter((ListAdapter) adapter);
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
    }

}
