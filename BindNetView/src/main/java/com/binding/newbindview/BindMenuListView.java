package com.binding.newbindview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import com.binding.R;
import com.binding.containerview.ExtendPullToRefreshMenuListView;
import com.binding.containerview.ExtendPullToRefreshRecycleView;
import com.binding.interfaces.NetRefreshViewInterface;

public class BindMenuListView extends AbsBindContentView {


    private ExtendPullToRefreshMenuListView extendPullToRefreshMenuListView;

    public BindMenuListView(@NonNull Context context) {
        super(context);
    }

    public BindMenuListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public View getRefreshView() {
        return extendPullToRefreshMenuListView.getBindNetRefreshableView();
    }


    @Override
    protected NetRefreshViewInterface generateBindView() {
        extendPullToRefreshMenuListView = new ExtendPullToRefreshMenuListView(mContext);
        return extendPullToRefreshMenuListView;
    }

}
