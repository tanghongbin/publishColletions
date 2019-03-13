package com.android_base.core.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;


import com.android_base.R;

import rx.Observable;

/**
 *
 */
public class CustomSearchEdit extends EditText {
    public CustomSearchEdit(Context context) {
        super(context);
        mContext = context;
        init(context);

    }

    private Context mContext;

    private TextChangeListener changeListener;
    MotionEvent mEvent;

    private int mBackgroundColor;

    public TextChangeListener getChangeListener() {
        return changeListener;
    }

    public void setChangeListener(TextChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    public CustomSearchEdit(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(context);
    }
    public void setBackgroundColor(int color){
        mBackgroundColor = color;
    }

    private void init(Context c) {
        setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        setSingleLine();
        setPadding(20,10,20,10);
        Drawable drawable = generateSearchDrawable();
        setCompoundDrawables(drawable,null,null,null);
        setCompoundDrawablePadding(15);
        setHint("请输入搜索内容");
        setTextSize(13);
        initEvent();
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private Drawable generateSearchDrawable() {
        Drawable drawable = getResources().getDrawable(R.mipmap.icon_ss);
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        return drawable;
    }

    @Override
    public void setOnClickListener(final OnClickListener l) {
        OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEvent == null){
                    return;
                }
                int clickArea = getMeasuredWidth() - 50;
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

                if (editable == null) {

                    if (changeListener != null) {
                        changeListener.onTextChange("");
                    }

                } else {
                    String text = editable.toString();
                    if(text.length() == 0){
                        setCompoundDrawables(generateSearchDrawable(),null,null,null);
                    }else {
                        setCompoundDrawables(null,null,generateDeleteDrawable(),null);
                    }
                    if(changeListener != null){
                        changeListener.onTextChange(editable.toString());
                    }
                }
            }
        });



        setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == KeyEvent.ACTION_DOWN || actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String text = v.getText().toString();
                    //业务代码

                    v.clearFocus();
                    InputMethodManager imm1 = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm1.hideSoftInputFromWindow(v.getWindowToken(), 0);//从控件所在的窗口中隐藏

                    if(changeListener == null){
                        return true;
                    }
                    changeListener.onSearch(text);
                }
                return true;
            }
        });
    }

    private Drawable generateDeleteDrawable() {
        Drawable drawable = getResources().getDrawable(R.mipmap.icon_delete01);
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        return drawable;
    }

    public interface TextChangeListener {
        void onTextChange(String text);

        void onSearch(String text);
    }
}
