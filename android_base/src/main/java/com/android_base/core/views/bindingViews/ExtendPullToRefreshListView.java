package com.android_base.core.views.bindingViews;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.android_base.R;
import com.android_base.core.interfaces.BindNetAdapter;
import com.android_base.core.interfaces.BindNetMode;
import com.android_base.core.interfaces.BindRefreshListener;
import com.android_base.core.interfaces.NetRefreshViewInterface;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

/**
 * Created by tanghongbin on 2017/9/9.
 */

public class ExtendPullToRefreshListView extends BasePullToRefreshView<ListView> {


    public ExtendPullToRefreshListView(Context mContext) {
        super(mContext);
    }

    @Override
    protected int getRefreshViewId() {
        return R.id.bind_net_listview;
    }

    @Override
    protected int getRefreshLayoutId() {
        return R.layout.bind_net_refresh_listview;
    }

    @Override
    public void setBindNetOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        mrefreshView.setOnItemClickListener(onItemClickListener);
    }

    @Override
    public void setBindNetAdapter(BindNetAdapter adapter) {
        mrefreshView.setAdapter((ListAdapter) adapter);
    }


}
