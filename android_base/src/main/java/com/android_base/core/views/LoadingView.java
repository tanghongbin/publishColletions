package com.android_base.core.views;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by tanghongbin on 2017/4/15.
 */

public class LoadingView extends LinearLayout {
    public LoadingView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        TextView textView = new TextView(context);
        textView.setText("加载中.....................");
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(17);
        addView(textView);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }
}
