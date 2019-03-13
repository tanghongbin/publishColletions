package com.android_base.core.common.permission;

import android.content.Context;



/**
 *
 */
public interface PermissionInterface {
    void requestPermission(Context context, String deniedMessage, PermissionListener listener, String... permissons);
}
