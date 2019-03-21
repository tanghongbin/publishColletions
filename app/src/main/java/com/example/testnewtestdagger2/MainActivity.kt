package com.example.testnewtestdagger2

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
import com.mylhyl.acp.Acp
import com.mylhyl.acp.AcpListener
import com.mylhyl.acp.AcpOptions
import com.ui_componet.list.NestedGridView
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

        mBindList.setOnRefresshListener(object : BindRefreshListener{
            override fun pullUpToLoadMore() {
                requestData()
            }

            override fun pullDownRefresh() {
                mBindList.resetPage()
                requestData()
            }

        })
        var adapter = CustomAdapter(R.layout.item, mBindList.totalList as MutableList<TestBean>)
        mBindList.setAdapter(adapter)
        adapter.setOnItemClickListener { adapter, view, position ->

        }
        mBindList.setNoContentClick {
            requestData()
        }

        Handler().postDelayed({
            requestData()
        },3000)


    }




    class CustomAdapter(layoutID:Int,list:MutableList<TestBean>): BaseRecycleAapter<TestBean>(layoutID,list) {
        override fun convert(helper: BaseViewHolder?, item: TestBean?) {
            var iamge = helper!!.getView<ImageView>(R.id.mText)
            ImageLoaderUtil.displayImage(item!!.url,iamge)
            var mImageGrid = helper!!.getView<NestedGridView>(R.id.mImageGrid)
            mImageGrid.adapter = ImageAdapter(mContext,item.image)
        }
    }

    var mList = Arrays.asList("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=935292084,2640874667&fm=27&gp=0.jpg"
        ,"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3496345838,732839400&fm=27&gp=0.jpg"
        ,"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3496345838,732839400&fm=27&gp=0.jpg"
        ,"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3496345838,732839400&fm=27&gp=0.jpg")

    var mListOne = Arrays.asList("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=935292084,2640874667&fm=27&gp=0.jpg")

    var mEmptyList = ArrayList<String>()

    private fun requestData() {
        var page = mBindList.currntPage
        var url = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=935292084,2640874667&fm=27&gp=0.jpg"
        if (page == 1) {
            var list = Arrays.asList(TestBean(url,mListOne))
            mBindList.bindList(list)
            mBindList.notifyObserverDataChanged()
        } else {
            var list = Arrays.asList(TestBean(url,mList),
            TestBean(url,mList),
            TestBean(url,mEmptyList),
            TestBean(url,mList),
            TestBean(url,mEmptyList),
            TestBean(url,mList),
            TestBean(url,mEmptyList),
            TestBean(url,mList),
            TestBean(url,mEmptyList),
            TestBean(url,mEmptyList),
            TestBean(url,mList))
            mBindList.bindList(list)
            mBindList.notifyObserverDataChanged()
        }


    }
}


fun log(any: Any){
    Log.i("TAG","打印数据:$any")
}