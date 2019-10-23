package com.example.testnewtestdagger2

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.binding.interfaces.BindNetAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.activity_scrolling.*
import kotlinx.android.synthetic.main.custom_fragment.*
import java.util.*

class ScrollingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(toolbar)

        mViewpager.adapter = CustomPagerAdapter(supportFragmentManager)
    }

    class CustomPagerAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {
        override fun getItem(p0: Int): Fragment {
           return classes[p0].newInstance()
        }

        override fun getCount(): Int {
            return classes.size
        }

        var classes = Arrays.asList(
            CustomFragment::class.java
        )

    }

    class CustomFragment: Fragment() {
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return inflater.inflate(R.layout.custom_fragment,null)
        }

        override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)
            mBindList.setAdapter(CustomAdapter(activity!!,android.R.layout.simple_list_item_1,
                mBindList.totalList as List<String>
            ))

            Handler().postDelayed({
                var list = Arrays.asList(
                    "4564","34534","324","123","23","4564","34534","324","123","23","4564","34534","324","123","23",
                    "4564","34534","324","123","23","4564","34534","324","123","23","4564","34534","324","123","23"
                )
                mBindList.bindList(null)
                mBindList.notifyObserverDataChanged()
            },2500)
            mBindList.setNoContentClick {
                var list = Arrays.asList(
                    "4564","34534","324","123","23","4564","34534","324","123","23","4564","34534","324","123","23",
                    "4564","34534","324","123","23","4564","34534","324","123","23","4564","34534","324","123","23"
                )
                mBindList.bindList(list)
                mBindList.notifyObserverDataChanged()
            }
        }
    }

    class CustomAdapter(context: Context,layoutId:Int,list:List<String>) :
        BaseQuickAdapter<String,BaseViewHolder>(layoutId,list) ,BindNetAdapter{
        override fun convert(helper: BaseViewHolder?, item: String?) {
            helper!!.getView<TextView>(android.R.id.text1).text = item
        }

        override fun notifyMetaDataChange() {
            notifyDataSetChanged()
        }

    }
}
