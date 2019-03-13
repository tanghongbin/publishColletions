package com.example.testnewtestdagger2

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.testnewtestdagger2.dagger.*
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(),MainView {


    @JvmField
    @Inject
    var testBean: TestBean? = null

    @JvmField
    @Inject
    var mainPresenter: MainPresenter? = null

    @JvmField
    @Inject
    var bean:HelloBean? = null

    @JvmField
    @Inject
    var homeTest:HomeTest? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



    }
}


fun log(any: Any){
    Log.i("TAG","打印数据:$any")
}