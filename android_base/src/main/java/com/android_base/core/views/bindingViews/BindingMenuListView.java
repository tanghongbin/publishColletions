package com.android_base.core.views.bindingViews;

import android.content.Context;
import android.util.AttributeSet;

import com.android_base.core.common.adapter.CustomBaseAdapter;
import com.android_base.core.views.PullRefreshMenuListView;

/**
 *
 */
public class BindingMenuListView extends BindingNetView<ExtendPullToRefreshMenuListView,CustomBaseAdapter> {
    public BindingMenuListView(Context context) {
        super(context);
    }

    public BindingMenuListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected ExtendPullToRefreshMenuListView generateBindView() {
        return new ExtendPullToRefreshMenuListView(mContext);
    }

}
