package com.android_base.core.views.bindingViews;

import android.content.Context;
import android.util.AttributeSet;

import com.android_base.core.common.adapter.CustomBaseAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 *
 */
public class BindingListView extends BindingNetView<ExtendPullToRefreshListView,CustomBaseAdapter> {
    public BindingListView(Context context) {
        super(context);
    }

    public BindingListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected ExtendPullToRefreshListView generateBindView() {
        return new ExtendPullToRefreshListView(mContext);
    }
}
