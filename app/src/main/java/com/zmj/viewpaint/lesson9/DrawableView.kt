package com.zmj.viewpaint.lesson9

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import com.zmj.viewpaint.common.dp2px

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/5/13
 * Description :
 * Drawable更像是一个view,有自己的绘制规则，操作Canvas来进行内容绘制
 */
class DrawableView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val drawable = ColorDrawable(Color.YELLOW)


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        drawable.setBounds(dp2px(20f).toInt(), dp2px(20f).toInt(),width,height)
        drawable.draw(canvas!!)


    }
}