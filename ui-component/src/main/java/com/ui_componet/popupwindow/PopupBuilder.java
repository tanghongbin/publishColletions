package com.ui_componet.popupwindow;

import android.graphics.drawable.Drawable;

/**
 * Created by tanghongbin on 2017/3/11.
 */

public class PopupBuilder {
    private int textSize;
    private int textColor;
    private int checkDrawable;
    private int checkTextColor;
    private Drawable backgroundDrawable;
    private Drawable checkBackgroundDrawable;




    public Drawable getCheckBackgroundDrawable() {
        return checkBackgroundDrawable;
    }

    public void setCheckBackgroundDrawable(Drawable checkBackgroundDrawable) {
        this.checkBackgroundDrawable = checkBackgroundDrawable;
    }

    public Drawable getBackgroundDrawable() {
        return backgroundDrawable;
    }

    public void setBackgroundDrawable(Drawable backgroundDrawable) {
        this.backgroundDrawable = backgroundDrawable;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getCheckDrawable() {
        return checkDrawable;
    }

    public void setCheckDrawable(int checkDrawable) {
        this.checkDrawable = checkDrawable;
    }

    public int getCheckTextColor() {
        return checkTextColor;
    }

    public void setCheckTextColor(int checkTextColor) {
        this.checkTextColor = checkTextColor;
    }

    /**
     * 字体大小
     * @param size
     * @return
     */
    public PopupBuilder size(int size){
        this.textSize = size;
        return this;
    }

    /**字体颜色
     *
     * @param textColor
     * @return
     */
    public PopupBuilder color(int textColor){
        this.textColor = textColor;
        return this;
    }

    /**
     * 选中字体颜色
     * @param checkTextColor
     * @return
     */
    public PopupBuilder checkedColor(int checkTextColor){
        this.checkTextColor = checkTextColor;
        return this;
    }

    /**
     * 选中时的图像
     * @param drawble
     * @return
     */
    public PopupBuilder checkedDrawble(int drawble){
        this.checkDrawable = drawble;
        return this;
    }

    /**
     * 未选中时的背景
     * @param drawble
     * @return
     */
    public PopupBuilder background(Drawable drawble){
        this.backgroundDrawable = drawble;
        return this;
    }

    /**
     * 选中时的背景
     * @param drawble
     * @return
     */
    public PopupBuilder checkedBackground(Drawable drawble){
        this.checkBackgroundDrawable = drawble;
        return this;
    }



}
