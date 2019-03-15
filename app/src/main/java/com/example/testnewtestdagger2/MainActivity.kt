package com.example.testnewtestdagger2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.binding.adapter.BaseRecycleAapter
import com.binding.interfaces.BindRefreshListener
import com.chad.library.adapter.base.BaseViewHolder
import com.example.testnewtestdagger2.dagger.*
import com.mylhyl.acp.Acp
import com.mylhyl.acp.AcpListener
import com.mylhyl.acp.AcpOptions
import com.yiqihudong.imageutil.ImageSelectedHelper
import com.yiqihudong.imageutil.callback.SelectPicCallback
import com.yiqihudong.imageutil.utils.ImageLoaderUtil
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.jar.Manifest
import javax.inject.Inject
import kotlin.collections.ArrayList

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

        mBindList.setOnRefresshListener(object : BindRefreshListener{
            override fun pullUpToLoadMore() {
                requestData(mBindList.currntPage)
            }

            override fun pullDownRefresh() {
                mBindList.resetPage()
                requestData(mBindList.currntPage)
            }

        })
        var adapter = CustomAdapter(R.layout.item, mBindList.totalList as MutableList<String>)
        mBindList.setAdapter(adapter)
        adapter.setOnItemClickListener { adapter, view, position ->
            Toast.makeText(this@MainActivity,"点击",Toast.LENGTH_SHORT).show()
            operate(position)

        }
        mBindList.setNoContentClick {
            requestData(mBindList.currntPage)
        }

        Handler().postDelayed({
            requestData(1)
        },3000)


    }

    private fun operate(position: Int) {
        if (position % 2 == 0) {
            var list = Arrays.asList(
                "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=935292084,2640874667&fm=27&gp=0.jpg"
                , "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3496345838,732839400&fm=27&gp=0.jpg"
                , "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3496345838,732839400&fm=27&gp=0.jpg"
                , "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3496345838,732839400&fm=27&gp=0.jpg"
                , "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3496345838,732839400&fm=27&gp=0.jpg"
                , "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3496345838,732839400&fm=27&gp=0.jpg"
                , "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3496345838,732839400&fm=27&gp=0.jpg"
                , "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3496345838,732839400&fm=27&gp=0.jpg"
                , "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3496345838,732839400&fm=27&gp=0.jpg"
                , "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3496345838,732839400&fm=27&gp=0.jpg"
            )
            var arrayList = ArrayList<String>()
            arrayList.addAll(list)

            ImageSelectedHelper.photoWall(this@MainActivity, arrayList)
        } else {


            ImageSelectedHelper.selectMutiplePic(this@MainActivity, 9)
            { images -> Log.i("TAg", "打印这些照片：$images") }
        }
    }


    class CustomAdapter(layoutID:Int,list:MutableList<String>): BaseRecycleAapter<String>(layoutID,list) {
        override fun convert(helper: BaseViewHolder?, item: String?) {
            var iamge = helper!!.getView<ImageView>(R.id.mText)
            ImageLoaderUtil.displayImage(item,iamge)
        }

    }

    private fun requestData(i: Int) {
        if (i >= 3) {
            mBindList.bindList(null)
        } else {
            var list = Arrays.asList("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=935292084,2640874667&fm=27&gp=0.jpg"
                ,"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3496345838,732839400&fm=27&gp=0.jpg"
                ,"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3496345838,732839400&fm=27&gp=0.jpg"
                ,"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3496345838,732839400&fm=27&gp=0.jpg"
                ,"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3496345838,732839400&fm=27&gp=0.jpg"
                ,"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3496345838,732839400&fm=27&gp=0.jpg"
                ,"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3496345838,732839400&fm=27&gp=0.jpg"
                ,"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3496345838,732839400&fm=27&gp=0.jpg"
                ,"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3496345838,732839400&fm=27&gp=0.jpg"
                ,"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3496345838,732839400&fm=27&gp=0.jpg")
            mBindList.bindList(list)
        }

        mBindList.notifyObserverDataChanged()
    }
}


fun log(any: Any){
    Log.i("TAG","打印数据:$any")
}