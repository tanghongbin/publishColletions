package com.android_base.util;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.widget.EditText;

import pushlish.tang.com.commonutils.ToastUtils;

/**
 *
 */
public class IpUtil {
    /**
     * 自带弹框修改ip地址
     * @param context
     */
    public static void changeIp(final Context context){
        final EditText mEditText=new EditText(context);
        mEditText.setHint(Urls.getDebugDomain(context));
        mEditText.setTextColor(Color.BLACK);
        ToastUtils.showDialog(context,  mEditText, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String url = mEditText.getText().toString();
                Urls.saveDebugDomain(context,url);
                ToastUtils.showMessage(context,"修改IP成功");
            }
        });
    }
}
