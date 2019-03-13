package com.android_base.core.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;


import com.android_base.R;
import com.android_base.core.common.eventbus.BusHelper;
import com.android_base.core.common.json.JsonHelper;
import com.android_base.core.common.loadingdialog.LoadingDialogInterface;
import com.android_base.core.common.loadingdialog.loadingdialogtypes.CustomProgressDialog;
import com.android_base.core.common.permission.PermissionHelper;
import com.android_base.core.views.HeadView;
import com.android_base.events.ProgressDialogEvent;


import me.yokeyword.fragmentation.SupportActivity;
import pushlish.tang.com.commonutils.others.KeyboardUtils;


/**
 * 基类，默认使用数据绑定进行解析，也可以不使用，
 * 1.注册和销毁事件库（Rxbus）;
 * 2.提供标题头操作（可直接利用headview.setCenterTItle(‘’)设置标题头）;
 * 3.进度加载框
 * 4.（可自定义加载框）;
 * 在initView()里面进行赋值初始化操作
 * loaddata（）中加载数据
 */
public abstract class BaseActivity extends SupportActivity implements View.OnClickListener {


    public LoadingDialogInterface dialogInterface;

    public abstract int setContentLayout();

    public HeadView headView;
    protected Context mContext;

    //自行进行数据加载或者布局初始化
    public abstract void initView();

    public abstract void loadData();

    /**
     * oncreate中的钩子方法
     */
    protected void hook() {

    }

    /**
     * view初始化之前
     */
    protected void beforeInitView(){

    }

    protected void afterInitView(){

    }

    public ProgressDialogEvent dialogEvent;

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        dialogInterface = new CustomProgressDialog(mContext);
        dialogEvent = new ProgressDialogEvent(this);
        //如果有软键盘弹出，则隐藏
        BusHelper.registe(this);
        BusHelper.registe(dialogEvent);
        beforeInitView();
        localViewInit();
        afterInitView();
    }

    /**
     * 局部初始化，可重写此方法
     */
    protected void localViewInit() {
        setContentView(setContentLayout());
        headView = (HeadView) findViewById(R.id.common_head_view);
        if (headView != null) {
            headView.setLeftImage(R.mipmap.icon_back1_p_copy, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    KeyboardUtils.hideSoftInput(BaseActivity.this);
                    finish();
                }
            });
        }
        hook();
        initView();
        loadData();
    }


    public void showDialog() {
        if (!isFinishing()) {
            dialogInterface.showDialog();
        }
    }

    public void showDialog(String text) {
        if (!isFinishing()) {
            dialogInterface.showDialog(text);
        }
    }

    public void dismissDialog() {
        dialogInterface.dismissXDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusHelper.unRegiste(this);
        BusHelper.unRegiste(dialogEvent);
    }

    public void onClick(View view) {

    }

}
