package com.example.testnewtestdagger2

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.os.Handler
import android.widget.Toast
import com.binding.adapter.BaseRecycleAapter
import com.binding.interfaces.BindRefreshListener
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.jar.Manifest
import kotlin.collections.ArrayList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.baoyz.swipemenulistview.SwipeMenuItem
import com.baoyz.swipemenulistview.SwipeMenu
import com.baoyz.swipemenulistview.SwipeMenuCreator
import com.baoyz.swipemenulistview.SwipeMenuListView
import com.binding.interfaces.BindNetAdapter
import com.binding.interfaces.BindNetMode
import com.yiqihudong.imageutil.ImageSelectedHelper
import com.yiqihudong.imageutil.callback.SelectPicCallback
import publish.tang.common.commonutils.mediautils.MediaUtils


class MainActivity : AppCompatActivity() {

    var list = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mBindList.setAdapter(CustomAdapter(R.layout.item, mBindList.totalList as MutableList<String>?))


        var voiceList = MediaUtils.getLocalAudioFromDataBase(this,1,1,"")
        var videoList = MediaUtils.getLocalVideoFromDataBase(this,1,2,"")

        Log.d("TAG","打印视频:${videoList}\n 数量：" + (videoList?.size ?: 0) )
        Log.d("TAG","打印音乐:${voiceList}\n${+ (voiceList?.size ?: 0) }")
    }

    fun loadData(pageNo:Int){
        var list = Arrays.asList(
            "asdfsdfsdf",
            "asdfsdfsdf",
            "asdfsdfsdf",
            "asdfsdfsdf",
            "asdfsdfsdf",

            "asdfsdfsdf",
            "asdfsdfsdf",
            "asdfsdfsdf",
            "asdfsdfsdf",
            "asdfsdfsdf"
        )
        if (pageNo > 3) {
            list = null
        }
        mBindList.bindList(list)
        mBindList.notifyObserverDataChanged()
    }

    fun setupPageSize(view:View?){
        if (mBindList.pageSize == 10) {
            mBindList.pageSize = 100
        } else {
            mBindList.pageSize = 10
        }

    }


    fun both(view:View?){
        loadData(1)
    }

    fun refresh(view:View?){
        mBindList.setMode(BindNetMode.ONLY_REFREESH)
    }


    fun disabled(view:View?){
        mBindList.setMode(BindNetMode.DISABLED)
    }



    class CustomAdapter(layoutId:Int,list:MutableList<String>?): BaseRecycleAapter<String>(layoutId,list) {
        override fun convert(helper: BaseViewHolder?, item: String?) {
            helper!!.getView<TextView>(R.id.mText).text = item
        }

    }


}


fun log(any: Any) {
    Log.i("TAG", "打印数据:$any")
}