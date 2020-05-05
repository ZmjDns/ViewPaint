package com.zmj.viewpaint.common

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.TypedValue
import com.zmj.viewpaint.R

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/4/28
 * Description :
 */

fun dp2px(dp: Float): Float{
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,Resources.getSystem().displayMetrics)
}

/**
 * 获取图片Bitmap
 */
fun getAvatarBit(resources: Resources,width: Float): Bitmap{
    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true   //设置以后，在options中只会取到图片的宽高，得到宽高比之后可以计算实际需要显示的宽高
    BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, options)
    options.inJustDecodeBounds = false
    options.inDensity = options.outWidth
    options.inTargetDensity = width.toInt()
    return BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, options)
}

/**
 * Camera在Z轴的位置
 */
fun getZForCamera(): Float{
    return -6 * Resources.getSystem().displayMetrics.density
}