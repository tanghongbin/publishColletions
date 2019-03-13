package com.yiqihudong.imageutil;

import android.os.Bundle;

import com.yalantis.ucrop.UCrop;

import java.io.Serializable;

/**
 * Created by tanghongbin on 2017/6/24.
 */

public class CropOptions extends UCrop.Options implements Serializable{
    private int aspectX;//裁剪框的比例
    private int aspectY;

    public int getAspectY() {
        return aspectY;
    }

    public void setAspectY(int aspectY) {
        this.aspectY = aspectY;
    }

    public int getAspectX() {
        return aspectX;
    }

    public void setAspectX(int aspectX) {
        this.aspectX = aspectX;
    }
}
