package com.android_base.core.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by tanghongbin on 2017/2/23.无内容默认视图的父类
 */

public abstract class ClickableView extends LinearLayout {
    public ClickableView(Context context) {
        super(context);
    }

    public ClickableView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public abstract void setNoContentClickListener(OnClickListener onClickListener);
}
