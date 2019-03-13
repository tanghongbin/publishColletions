package com.ui_componet.search_editext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.ui_componet.R;

/**
 * Created by tanghongbin on 2017/3/13.
 */

public class CustomClearEdit extends AppCompatEditText {

    private Context mContext;
    private Drawable mClearDrawable;
    public CustomClearEdit(Context context) {
        super(context);
        init(context, null);
    }

    MotionEvent mEvent;

    private int mBackgroundColor;



    public CustomClearEdit(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }


    public void setBackgroundColor(int color){
        mBackgroundColor = color;
    }


    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        initAttrResources(attrs);
        setSingleLine();
        setPadding(20,10,20,10);
        setCompoundDrawablePadding(15);
        setTextSize(13);
        initEvent();
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    /**
     * 解析布局资源
     * @param attrs
     */
    private void initAttrResources(AttributeSet attrs) {
        if (attrs == null) return;

        TypedArray typedArray = mContext.obtainStyledAttributes(attrs,R.styleable.CustomClearEdit);
        mClearDrawable = typedArray.getDrawable(R.styleable.CustomClearEdit_clearDrawable);
        typedArray.recycle();

    }

    @Override
    public void setOnClickListener(final OnClickListener l) {
        OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEvent == null){
                    return;
                }
                int clickArea = getMeasuredWidth() - dip2px(mContext,40);
                if(mEvent.getX() > clickArea){
                    Drawable [] drawables = getCompoundDrawables();
                    if(drawables[2] != null){
                        setText("");
                    }
                }
                l.onClick(v);
            }
        };
        super.setOnClickListener(listener);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ShapeDrawable shapeDrawable = new ShapeDrawable();
        shapeDrawable.setShape(new Shape() {
            @Override
            public void draw(Canvas canvas, Paint paint) {
                int color = mBackgroundColor == 0 ? Color.parseColor("#f1f2ec"):mBackgroundColor;
                paint.setColor(color);
                RectF rectF = new RectF(0,0,getMeasuredWidth(),getMeasuredHeight());
                canvas.drawRoundRect(rectF,getMeasuredHeight()/2,getMeasuredHeight()/2,paint);
            }
        });
        setBackgroundDrawable(shapeDrawable);
    }

    /**
     * 重写onTouchEvent事件记录点击事事件的位置，如果是右边并且有内容时，则清空数据
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mEvent = event;
        return super.onTouchEvent(event);
    }

    private void initEvent() {
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable != null) {
                    String text = editable.toString();
                    if(text.length() == 0){
                        setCompoundDrawables(null,null,null,null);
                    }else {
                        setCompoundDrawables(null,null,generateDeleteDrawable(),null);
                    }
                }
            }
        });

    }

    private Drawable generateDeleteDrawable() {
        Drawable drawable;

        if (mClearDrawable == null) {
            drawable = getResources().getDrawable(R.drawable.icon_delete01);
        }else {
            drawable = mClearDrawable;
        }
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        return drawable;
    }

    /**
     * 根据手机分辨率从DP转成PX
     * @param context
     * @param dpValue
     * @return
     */
    private   int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
