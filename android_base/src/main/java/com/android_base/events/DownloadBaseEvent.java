package com.android_base.events;

import java.io.File;

/**
 *
 */
public class DownloadBaseEvent<ERROR extends ErrorEvent> extends JudgeResultEvent{
    private File file;
    private ERROR error;

    public DownloadBaseEvent() {
    }

    public DownloadBaseEvent(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public ERROR getError() {
        return error;
    }

    public void setError(ERROR error) {
        this.error = error;
    }

    @Override
    public boolean isSuccess() {
        return error == null;
    }
}
