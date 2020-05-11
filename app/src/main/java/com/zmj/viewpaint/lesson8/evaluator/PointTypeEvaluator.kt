package com.zmj.viewpaint.lesson8.evaluator

import android.animation.TypeEvaluator
import android.graphics.Point
import android.graphics.Xfermode

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/5/11
 * Description :
 */
class PointTypeEvaluator: TypeEvaluator<Point> {
    override fun evaluate(fraction: Float, startValue: Point?, endValue: Point?): Point {
        //fraction  完成程度
        val x = startValue!!.x + (endValue!!.x - startValue.x) * fraction
        val y = startValue.y + (endValue.y - startValue.y) * fraction


        return Point(x.toInt(),y.toInt())
    }
}