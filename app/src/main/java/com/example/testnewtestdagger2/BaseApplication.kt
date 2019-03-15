package com.example.testnewtestdagger2

import android.app.Activity
import android.app.Application
import com.example.testnewtestdagger2.dagger.DaggerAppComponent
import com.yiqihudong.imageutil.ContextManager
import com.yiqihudong.imageutil.ImageSelectedHelper
import com.yiqihudong.imageutil.view.ImageSingleChooseActivity
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class BaseApplication : Application(),HasActivityInjector {

    @JvmField
    @Inject
    var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>? = null

    override fun activityInjector(): DispatchingAndroidInjector<Activity>? {
       return dispatchingActivityInjector
    }


    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder().build().inject(this)

        ContextManager.init(this)

        var prefix = ImageSingleChooseActivity.SPECIAL_CUSTOM_URL_HEADER + "com.example"
        ImageSelectedHelper.initProviderAuth(this,prefix)


    }


}
