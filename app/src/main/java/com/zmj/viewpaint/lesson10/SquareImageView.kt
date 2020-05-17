package com.zmj.viewpaint.lesson10

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/5/17
 * Description : 对一个View修改尺寸
 */
class SquareImageView(context: Context?, attrs: AttributeSet?) : androidx.appcompat.widget.AppCompatImageView(context, attrs) {


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val measuredWith = measuredWidth
        val measuredHeight = measuredHeight
        val size = Math.max(measuredWith,measuredHeight)

        setMeasuredDimension(size,size)     //保存测量的值
    } 
}