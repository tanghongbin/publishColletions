package com.android_base.events;

import com.lzy.okgo.request.PostRequest;

import java.io.File;
import java.util.List;

/**
 * 上传多文件失败发送的事件
 */
public class UploadMutipleFileErrorEvent extends ErrorEvent{


    public UploadMutipleFileErrorEvent(List<String> fileList) {
        this.fileList = fileList;
    }

    private List<String> fileList;

    public List<String> getFileList() {
        return fileList;
    }

    public void setFileList(List<String> fileList) {
        this.fileList = fileList;
    }
}
