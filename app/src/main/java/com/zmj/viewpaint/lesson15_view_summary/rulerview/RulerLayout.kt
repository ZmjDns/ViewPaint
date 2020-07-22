package com.zmj.viewpaint.lesson15_view_summary.rulerview

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/7/21
 * Description :
 */
class RulerLayout : RelativeLayout {
    constructor(context: Context?): super(context)
    constructor(context: Context?, attrs: AttributeSet?):super(context, attrs)
    constructor(context: Context?,attrs: AttributeSet?,styleDef: Int):super(context, attrs,styleDef)
}