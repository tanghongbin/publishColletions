package com.ui_componet.headview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.ui_componet.R;

/**
 * 共用标题头
 */
public class HeadView extends LinearLayout {
    /**
     * 左上角返回imageView
     * */
    public ImageView mCommonBack;

    // 标题左边文字
    public TextView mHeadCommonLeftTv;

    //标题右边图片
    public ImageView head_common_right_iv;

    //标题中间文本
    public TextView mCenterTitle;
    //标题右边文字
    public TextView head_common_right_tv;

    public ImageView head_common_right_iv2;
    public TextView head_common_right_tv2;
    public View view;
    public RelativeLayout rl_back;
    public TextView head_common_left_tv_2;
    public ImageView head_common_right_iv_2;
    public ImageView head_common_right_iv_3;
    public RelativeLayout left_back_rl;

    public HeadView(Context context) {
        super(context);
    }

    public HeadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    public void setCustomBackGroud(int color){
        rl_back.setBackgroundColor(color);

    }

    public void setCenterTitleColor(int color){
        mCenterTitle.setTextColor(color);
    }

    private void initView(Context context) {
        view = View.inflate(context, R.layout.common_head_wrap_layout_copy,this);
        rl_back = (RelativeLayout)view.findViewById(R.id.head_view_backgroud);
        left_back_rl = (RelativeLayout)view.findViewById(R.id.left_back_rl);
        mCommonBack = (ImageView) view.findViewById(R.id.common_back);
        mHeadCommonLeftTv = (TextView)view.findViewById(R.id.head_common_left_tv);
        head_common_right_iv = (ImageView) view.findViewById(R.id.head_common_right_iv);
        mCenterTitle = (TextView)view.findViewById(R.id.head_common_title);
        head_common_right_tv = (TextView)view.findViewById(R.id.head_common_right_tv);
        head_common_right_iv2 = (ImageView) view.findViewById(R.id.head_common_right_iv2);
        head_common_right_tv2 = (TextView)view.findViewById(R.id.head_common_right_tv2);
        head_common_left_tv_2 = (TextView)view.findViewById(R.id.head_common_left_tv_2);
        head_common_right_iv_2 = (ImageView) view.findViewById(R.id.head_common_right_iv_2);
        head_common_right_iv_3 = (ImageView) view.findViewById(R.id.head_common_right_iv_3);
       // mCommonBack.setImageResource(R.drawable.back_button_white);
    }

    public void setAllTextColor(int color){
        mHeadCommonLeftTv.setTextColor(color);
        mCenterTitle.setTextColor(color);
        head_common_right_tv.setTextColor(color);
        head_common_right_tv2.setTextColor(color);
        head_common_left_tv_2.setTextColor(color);
    }
    public void setLeftTitle2(String title,OnClickListener onClickListener){
        head_common_left_tv_2.setText(title);
        head_common_left_tv_2.setOnClickListener(onClickListener);
        left_back_rl.setOnClickListener(onClickListener);
    }
    public void setLeftTitle2(String title){
        setLeftTitle2(title,null);
    }
    public void setLeftImageAndTitle(String title,int image,OnClickListener onClickListener){
        head_common_left_tv_2.setText(title);
        head_common_left_tv_2.setOnClickListener(onClickListener);
        setLeftImage(image,onClickListener);
    }

    //设置标题左边图片监听器
    public void setLeftListener(OnClickListener listener){
        mCommonBack.setOnClickListener(listener);
        left_back_rl.setOnClickListener(listener);
    }

    //设置标题中间文字
    public void setCenterTitle(String ms,int rightDrawable,OnClickListener onClickListener){

        if (rightDrawable == 0){
            mCenterTitle.setCompoundDrawables(null,null,null,null);
        }else {
            Drawable drawble = getResources().getDrawable(rightDrawable);
            drawble.setBounds(0,0,drawble.getMinimumWidth(),drawble.getMinimumHeight());
            mCenterTitle.setCompoundDrawablePadding(5);
            mCenterTitle.setCompoundDrawables(null,null,drawble,null);
        }
        mCenterTitle.setText(ms);
        mCenterTitle.setOnClickListener(onClickListener);
    }
    public void setCenterTitle(String ms){
        setCenterTitle(ms,0,null);
    }

    //设置标题左边文字
    public void setLeftText(String msg,OnClickListener listener){
        mHeadCommonLeftTv.setText(msg);
        mCommonBack.setVisibility(View.GONE);
        if(msg != null && listener != null){
            mHeadCommonLeftTv.setOnClickListener(listener);
            left_back_rl.setOnClickListener(listener);
        }
    }

    //设置右边文字和监听器
    public void setRightText(String msg,OnClickListener listener){
        head_common_right_tv.setText(msg);
        head_common_right_tv.setVisibility(View.VISIBLE);
        if(msg != null) {
            head_common_right_tv.setOnClickListener(listener);
        }
    }

    public void setRightTextColor(int color){
        head_common_right_tv.setTextColor(getResources().getColor(color));
    }
    //设置右边文字
    public void setRightText(String tv){
        head_common_right_tv.setText(tv);
    }
    //设置右边监听器
    public void setRightListener(OnClickListener onClickListener){
        head_common_right_tv.setOnClickListener(onClickListener);
    }
    public void setRightListener2(OnClickListener onClickListener){
        head_common_right_tv2.setOnClickListener(onClickListener);
    }

    //设置左边图片和监听器
    public void setLeftImage(int image,OnClickListener listener){
        if(image != 0){
            mCommonBack.setImageResource(image);
            mCommonBack.setOnClickListener(listener);
            left_back_rl.setOnClickListener(listener);
        }
    }

    //设置左边图片和监听器,如果为空则不显示
    public void setLeftImage(int image){
        if(image != 0){
            mCommonBack.setImageResource(image);
        }else {
            mCommonBack.setVisibility(View.GONE);
        }
    }
    public void setRightImage(int image,OnClickListener listener){
        if(image != 0){
            head_common_right_iv.setVisibility(View.VISIBLE);
            head_common_right_iv.setImageResource(image);
            head_common_right_iv.setOnClickListener(listener);
        }
    }

    public View getRightImageView(){
        return head_common_right_iv;
    }
    @Deprecated //没有使用了
    public  void setRightImage2(int image,OnClickListener listener){
        if(image != 0){
            head_common_right_iv2.setVisibility(View.VISIBLE);
            head_common_right_iv2.setImageResource(image);
            head_common_right_iv2.setOnClickListener(listener);
        }
    }
    //从左往右第二个image
    public void setImageRight_2(int image,OnClickListener listener){
        if (image == 0){
            head_common_right_iv_2.setImageDrawable(null);
        }else {
            head_common_right_iv_2.setImageResource(image);
        }
        head_common_right_iv_2.setOnClickListener(listener);
    }

    //从左往右第3个image
    public void setImageRight_3(int image,OnClickListener listener){
        if (image == 0){
            head_common_right_iv_3.setImageDrawable(null);
        }else {
            head_common_right_iv_3.setImageResource(image);
        }
        head_common_right_iv_3.setOnClickListener(listener);
    }

    /**
     * 返回头部标题
     * */
    public String getTitle(){
       return mCenterTitle.getText().toString();
    }



}
