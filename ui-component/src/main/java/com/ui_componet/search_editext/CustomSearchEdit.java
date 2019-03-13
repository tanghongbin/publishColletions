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
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.ui_componet.R;

/**
 * Created by tanghongbin on 2017/3/13.
 */

public class CustomSearchEdit extends AppCompatEditText {


    private Context mContext;
    private Drawable mDeleteDrawable;
    private Drawable mSearchDrawable;

    private TextChangeListener changeListener;
    MotionEvent mEvent;

    private int mBackgroundColor;

    public TextChangeListener getChangeListener() {
        return changeListener;
    }

    public void setChangeListener(TextChangeListener changeListener) {
        this.changeListener = changeListener;
    }


    public CustomSearchEdit(Context context) {
        super(context);
        init(context, null);

    }

    public CustomSearchEdit(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }
    public void setBackgroundColor(int color){
        mBackgroundColor = color;
    }


    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        initAttrResources(attrs);
        setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        setSingleLine();
        setPadding(20,10,20,10);
        Drawable drawable = generateSearchDrawable();
        setCompoundDrawables(drawable,null,null,null);
        setCompoundDrawablePadding(15);
        if(getHint() == null){
            setHint("请输入搜索内容");
        }
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

        TypedArray typedArray = mContext.obtainStyledAttributes(attrs,R.styleable.CustomSearchEdit);
        mDeleteDrawable = typedArray.getDrawable(R.styleable.CustomSearchEdit_deleteDrawable);
        mSearchDrawable = typedArray.getDrawable(R.styleable.CustomSearchEdit_searchDrawable);
        typedArray.recycle();

    }



    private Drawable generateSearchDrawable() {
        Drawable drawable;
        if (mSearchDrawable == null) {
            drawable = getResources().getDrawable(R.drawable.icon_ss);
        }else {
            drawable = mSearchDrawable;
        }
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        return drawable;
    }

    private Drawable generateDeleteDrawable() {
        Drawable drawable;
        if (mDeleteDrawable == null) {
            drawable = getResources().getDrawable(R.drawable.icon_delete01);
        }else {
            drawable = mDeleteDrawable;
        }
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
                int clickArea = getMeasuredWidth() - dip2px(mContext,40);
                if(mEvent.getX() > clickArea){
                    Drawable [] drawables = getCompoundDrawables();
                    if(drawables[2] != null){
                        setText("");
                        if(changeListener != null){
                            changeListener.clearText();
                        }
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
                        return false;
                    }
                    changeListener.onSearch(text);
                }
                return true;
            }
        });
    }


    public interface TextChangeListener {
        void onTextChange(String text);

        void onSearch(String text);

        void clearText();
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
