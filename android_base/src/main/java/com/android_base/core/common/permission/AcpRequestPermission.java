package com.android_base.core.common.permission;

import android.content.Context;

import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

import java.util.List;

/**
 *
 */
public class AcpRequestPermission implements PermissionInterface {
    @Override
    public void requestPermission(Context context, String deniedMessage, final PermissionListener listener, String... permissons) {
        Acp.getInstance(context).request(new AcpOptions.Builder().
                        setPermissions(permissons)
                        .setDeniedMessage(deniedMessage)
                        .build()
                , new AcpListener() {
                    @Override
                    public void onGranted() {
                        listener.onGranted();
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                        listener.onDenied(permissions);
                    }
                });
    }
}
