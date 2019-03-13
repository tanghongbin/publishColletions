package com.android_base.core.common.net.callbacks;

/**
 *
 */
public interface ProgressCallback {
    void onProgress(long currentSize, long totalSize, float progress);
}
