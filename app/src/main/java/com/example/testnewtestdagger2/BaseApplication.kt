package com.example.testnewtestdagger2

import android.app.Activity
import android.app.Application
import com.yiqihudong.imageutil.ContextManager
import com.yiqihudong.imageutil.ImageSelectedHelper
import com.yiqihudong.imageutil.view.ImageSingleChooseActivity

class BaseApplication : Application() {



    override fun onCreate() {
        super.onCreate()

//
        ContextManager.init(this)

        var prefix = ImageSingleChooseActivity.SPECIAL_CUSTOM_URL_HEADER + "com.example"
        ImageSelectedHelper.initProviderAuth(this,prefix)


    }


    override fun onTerminate() {
        super.onTerminate()

    }
}
