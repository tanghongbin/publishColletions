package com.ui_componet.list;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by tanghongbin on 2017/2/23.无内容默认视图的父类
 */

public abstract class CouldClickView extends View {
    public CouldClickView(Context context) {
        super(context);
    }

    public CouldClickView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    protected abstract void setNoContentClickListener(OnClickListener onClickListener);
}
