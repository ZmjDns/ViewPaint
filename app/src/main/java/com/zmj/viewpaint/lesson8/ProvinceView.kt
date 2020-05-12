package com.zmj.viewpaint.lesson8

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/5/11
 * Description :
 */
class ProvinceView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var province = "北京市"

    fun getProvince(): String{
        return province
    }

    fun setProvince(province: String){
        this.province = province
        invalidate()
    }
    init {
        paint.textSize = 60f
        paint.style = Paint.Style.FILL
        paint.textAlign = Paint.Align.CENTER
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas!!.drawText(province,width/2f,height/2f,paint)
    }
}