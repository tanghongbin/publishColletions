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


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mBindList.setOnRefresshListener(object : BindRefreshListener {
            override fun pullUpToLoadMore() {
                requestData()
            }

            override fun pullDownRefresh() {
                mBindList.resetPage()
                requestData()
            }

        })
        val creator = SwipeMenuCreator { menu ->
            // create "open" item
            val openItem = SwipeMenuItem(
                applicationContext
            )
            // set item background
            openItem.background = ColorDrawable(
                Color.rgb(
                    0xC9, 0xC9,
                    0xCE
                )
            )
            // set item width
            openItem.width = 90
            // set item title
            openItem.title = "Open"
            // set item title fontsize
            openItem.titleSize = 15
            // set item title font color
            openItem.titleColor = Color.WHITE
            // add to menu
            menu.addMenuItem(openItem)

            // create "delete" item
            val deleteItem = SwipeMenuItem(
                applicationContext
            )
            // set item background
            deleteItem.background = ColorDrawable(
                Color.rgb(
                    0xF9,
                    0x3F, 0x25
                )
            )
            // set item width
            deleteItem.width = 90
            // set a icon
            // add to menu
            menu.addMenuItem(deleteItem)
        }

// set creator
        var menuList = mBindList.refreshView as SwipeMenuListView
        menuList.setMenuCreator(creator)
        menuList.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT)
        menuList.setOnMenuItemClickListener(object : SwipeMenuListView.OnMenuItemClickListener{
            override fun onMenuItemClick(position: Int, menu: SwipeMenu?, index: Int): Boolean {
                Toast.makeText(this@MainActivity,"hello",Toast.LENGTH_SHORT).show()
                return false
            }

        })
        var adapter = CustomAdapter(this,R.layout.item, mBindList.totalList as MutableList<TestBean>)
        mBindList.setAdapter(adapter)
        mBindList.setNoContentClick {
            requestData()
        }

        Handler().postDelayed({
            requestData()
        }, 3000)


    }


    class CustomAdapter(var context:Context,layoutID: Int,var list: MutableList<TestBean>)
        : BaseAdapter(),BindNetAdapter {
        override fun notifyMetaDataChange() {
            notifyDataSetChanged()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var view = View.inflate(context,R.layout.item,null)
            var iamge = view!!.findViewById<TextView>(R.id.mText)
            iamge.text = list[position].url
            return view
        }

        override fun getItem(position: Int): Any? {
            return null
        }

        override fun getItemId(position: Int): Long {
            return 1L
        }

        override fun getCount(): Int {3
            return list.size
        }

    }

    var mList = Arrays.asList(
        "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=935292084,2640874667&fm=27&gp=0.jpg"
        , "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3496345838,732839400&fm=27&gp=0.jpg"
        , "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3496345838,732839400&fm=27&gp=0.jpg"
        , "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3496345838,732839400&fm=27&gp=0.jpg"
    )

    var mListOne =
        Arrays.asList("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=935292084,2640874667&fm=27&gp=0.jpg")

    var mEmptyList = ArrayList<String>()

    private fun requestData() {
        var page = mBindList.currntPage
        var url = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=935292084,2640874667&fm=27&gp=0.jpg"
        if (page > 100) {
            var list = Arrays.asList(TestBean(url, mListOne))
            mBindList.bindList(list)
            mBindList.notifyObserverDataChanged()
        } else {
            var list = Arrays.asList(
                TestBean(url, mList),
                TestBean(url, mList),
                TestBean(url, mEmptyList),
                TestBean(url, mList),
                TestBean(url, mEmptyList),
                TestBean(url, mList),
                TestBean(url, mEmptyList),
                TestBean(url, mList),
                TestBean(url, mEmptyList),
                TestBean(url, mEmptyList),
                TestBean(url, mList)
            )
            mBindList.bindList(list)
            mBindList.notifyObserverDataChanged()
        }


    }
}


fun log(any: Any) {
    Log.i("TAG", "打印数据:$any")
}