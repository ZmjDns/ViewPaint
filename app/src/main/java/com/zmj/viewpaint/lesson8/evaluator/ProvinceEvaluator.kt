package com.zmj.viewpaint.lesson8.evaluator

import android.animation.TypeEvaluator
import com.zmj.viewpaint.lesson8.Provinces

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/5/11
 * Description :
 */
class ProvinceEvaluator: TypeEvaluator<String> {
    override fun evaluate(fraction: Float, startValue: String?, endValue: String?): String {
        val startIndex = Provinces.provinces.indexOf(startValue)
        val endIndex = Provinces.provinces.indexOf(endValue)
        val index = startIndex + (endIndex - startIndex) * fraction
        return Provinces.provinces.get(index.toInt())
    }
}