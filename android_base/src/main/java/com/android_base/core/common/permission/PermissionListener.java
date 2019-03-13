package com.android_base.core.common.permission;

import java.util.List;

/**
 *
 */
public interface PermissionListener {
    void onGranted();
    void onDenied(List<String> permissions);

}
