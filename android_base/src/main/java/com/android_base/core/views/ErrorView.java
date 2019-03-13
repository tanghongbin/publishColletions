package com.android_base.core.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by tanghongbin on 2017/4/15.
 */

public class ErrorView extends ClickableView{
    private TextView textView;

    public ErrorView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        textView= new TextView(context);
        textView.setText("出错了");
        textView.setTextColor(Color.BLACK);
        addView(textView);
    }

    public ErrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    public void setNoContentClickListener(OnClickListener onClickListener) {
        textView.setOnClickListener(onClickListener);
    }
}
