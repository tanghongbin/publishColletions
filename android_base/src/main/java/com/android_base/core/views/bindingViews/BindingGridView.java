package com.android_base.core.views.bindingViews;

import android.content.Context;
import android.util.AttributeSet;

import com.android_base.core.common.adapter.CustomBaseAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

/**
 *
 */
public class BindingGridView extends BindingNetView<ExtendPullToRefreshGridView,CustomBaseAdapter> {
    public BindingGridView(Context context) {
        super(context);
    }

    public BindingGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected ExtendPullToRefreshGridView generateBindView() {
        return new ExtendPullToRefreshGridView(mContext);
    }
}
