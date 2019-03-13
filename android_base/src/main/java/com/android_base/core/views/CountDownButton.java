package com.android_base.core.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.android_base.R;

/**
 * CountDownButton
 */
public class CountDownButton extends Button {
    private static final int START_DOWN = 0;
    private int countDownMax = 60;
    private int countDown = countDownMax;
    private String normalText = "获取";
    // 发射时的监听
    private StartCountListener mStartListener;
    private Drawable selectDrawableId;
    private Drawable unSelectDrawableId;

    public StartCountListener getmStartListener() {
        return mStartListener;
    }

    public void setmStartListener(StartCountListener mStartListener) {
        this.mStartListener = mStartListener;
    }

    public CountDownButton(Context context) {
        super(context);
    }

    private void init(Context context, AttributeSet attrs) {
        setText(normalText);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CountDownButton);
        selectDrawableId = typedArray.getDrawable(R.styleable.CountDownButton_selectBackgroundDrawable);
        unSelectDrawableId = typedArray.getDrawable(R.styleable.CountDownButton_unSelectBackgroundDrawable);
        typedArray.recycle();
        setBackGround();
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getText().toString().equals(normalText)){
                    startCountDown();
                    start();
                    if (mStartListener != null) {
                        mStartListener.start();
                    }
                }
            }
        });
    }

    private void start() {
        if (selectDrawableId != null){
            setBackgroundDrawable(selectDrawableId);
        }
    }

    private void stop() {
        if (unSelectDrawableId != null){
            setBackgroundDrawable(unSelectDrawableId);
        }
    }

    private void setBackGround() {
        if (unSelectDrawableId != null){
            setBackgroundDrawable(unSelectDrawableId);
        }
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);
    }

    public CountDownButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CountDownButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    public String getNormalText() {
        return normalText;
    }

    public void setNormalText(String normalText) {
        this.normalText = normalText;
    }

    public int getCountDownMax() {
        return countDownMax;
    }

    public void setCountDownMax(int countDownMax) {
        this.countDownMax = countDownMax;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case START_DOWN:
                    countDown = countDown - 1;
                    if (countDown > 0) {
                        setText(countDown + "秒");
                        mHandler.sendEmptyMessageDelayed(START_DOWN, 1000);
                    } else {
                        stop();
                        setText(normalText);
                    }
                    break;

            }
        }
    };



    /***
     * 开始计时
     */
    @Deprecated
    public void startCountDown() {
        countDown = countDownMax;
        mHandler.removeMessages(START_DOWN);
        setText(countDown + "秒");
        mHandler.sendEmptyMessageDelayed(START_DOWN, 1000);
    }

    /***
     * 停止计时
     */
    @Deprecated
    public void stopCountDown() {
        mHandler.removeMessages(START_DOWN);
        setText(normalText);
        countDown = countDownMax;
    }

    public void releaseCountDown() {
        countDown = countDownMax;
        mHandler.removeMessages(START_DOWN);
    }

    public interface StartCountListener {
        void start();
    }
}