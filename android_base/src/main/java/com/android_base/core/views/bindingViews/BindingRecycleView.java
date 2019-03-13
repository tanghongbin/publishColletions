package com.android_base.core.views.bindingViews;

import android.content.Context;
import android.util.AttributeSet;

import com.android_base.core.views.bindingViews.adpters.XBaseRecycleAdapter;

/**
 *
 */
public class BindingRecycleView extends BindingNetView<ExtendPullToRefreshRecycleView,XBaseRecycleAdapter> {
    public BindingRecycleView(Context context) {
        super(context);
    }

    public BindingRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected ExtendPullToRefreshRecycleView generateBindView() {
        return new ExtendPullToRefreshRecycleView(mContext);
    }
}
