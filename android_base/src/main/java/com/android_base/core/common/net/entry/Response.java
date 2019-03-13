package com.android_base.core.common.net.entry;

/**
 *
 *
 * 网络请求结果封装
 * statuCode： 请求状态码   response: 请求结果
 */
public class Response {
    //网络请求状态码
    int statuCode;
    //网络返回的数据
    String data;


    public Response(int statuCode, String data) {
        this.statuCode = statuCode;
        this.data = data;
    }

    public int getStatuCode() {
        return statuCode;
    }

    public void setStatuCode(int statuCode) {
        this.statuCode = statuCode;
    }


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
