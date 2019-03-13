package com.android_base.core.common.loadingdialog;

import android.app.Dialog;

/**
 * 进度框接口
 */
public interface LoadingDialogInterface<T extends Dialog> {
    void showDialog();
    void showDialog(String text);
    void dismissXDialog();
    T getDialog();
}
