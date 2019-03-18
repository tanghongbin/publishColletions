package com.example.testnewtestdagger2

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.os.Handler
import android.widget.Toast
import com.mylhyl.acp.Acp
import com.mylhyl.acp.AcpListener
import com.mylhyl.acp.AcpOptions
import com.yiqihudong.imageutil.ImageSelectedHelper
import com.yiqihudong.imageutil.callback.SelectPicCallback
import com.yiqihudong.imageutil.utils.ImageLoaderUtil
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.jar.Manifest
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler().postDelayed({
            var list = ArrayList<String>()
            list.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3669430384,2722938167&fm=26&gp=0.jpg")
            list.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2052214277,1241110262&fm=26&gp=0.jpg")
            ImageSelectedHelper.photoWall(this@MainActivity,list)
        },2000)

    }

}

