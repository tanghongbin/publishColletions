package com.android_base.core.module;

import android.os.Bundle;


import com.android_base.core.interfaces.Callback;

import java.util.List;

/**
 * Created by tanghongbin on 2017/8/3.基础列表数据加载模型
 */

public interface BaseListDataModule extends BaseDataModule {
    /**
     * 加载数据的基类方法,
     * @param bundle 传输数据的bundle,传递到参数从bundle里边取
     * @param callback
     */
    void loadDataList(Bundle bundle, int pageNo, Callback<List> callback);
}
