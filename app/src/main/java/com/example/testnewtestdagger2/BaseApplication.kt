package com.example.testnewtestdagger2

import android.app.Activity
import android.app.Application
import com.example.testnewtestdagger2.dagger.DaggerAppComponent
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

    }


}
