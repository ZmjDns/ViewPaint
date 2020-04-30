package com.zmj.viewpaint.common

import android.content.res.Resources
import android.util.TypedValue

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