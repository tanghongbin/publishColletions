package com.android_base.core.common.net;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.BaseRequest;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pushlish.tang.com.commonutils.others.FileUtils;

/**
 * 构造请求的抽象工厂
 */
public abstract class AbsRequestFactory {
    /**
     * get请求
     * @param url
     * @return
     */
    public abstract GetRequest getRequest(String url);

    /**
     * post请求
     * @param url
     * @return
     */
    public abstract PostRequest postRequest(String url);
    /**
     * 单文件上传请求
     * @param url
     * @return
     */
    public abstract PostRequest uploadRequest(String url);

    /**
     * 下载文件请求
     * @param url
     * @return
     */
    public abstract GetRequest downloadRequest(String url);


    /**
     * 多文件上传请求
     * @param objs path路径集合或者File对象集合
     * @return
     */
    public abstract Map<String,PostRequest> mutipleFileReuqst(List<Object> objs);
}
