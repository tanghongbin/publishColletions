package com.example.testnewtestdagger2

import android.content.Context
import android.view.View
import com.binding.ClickableView
import kotlinx.android.synthetic.main.custom_nestedscroll.view.*

class CustomErrorClass(context: Context) : ClickableView(context) {
    init {
        View.inflate(context,R.layout.custom_nestedscroll,this)
    }
    override fun setNoContentClickListener(onClickListener: OnClickListener?) {
        mButton.setOnClickListener(onClickListener)
    }

}