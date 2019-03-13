package com.ui_componet.popupwindow;

import java.io.Serializable;

/**
 * Created by tanghongbin on 2017/3/11.
 */

public class BasePopupObj implements Serializable{
    private String popTitle;
    private boolean checeked;

    public BasePopupObj() {
    }

    public BasePopupObj(String popTitle) {
        this.popTitle = popTitle;
    }

    public String getPopTitle() {
        return popTitle;
    }

    public void setPopTitle(String popTitle) {
        this.popTitle = popTitle;
    }

    public boolean isCheceked() {
        return checeked;
    }

    public void setCheceked(boolean checeked) {
        this.checeked = checeked;
    }
}
