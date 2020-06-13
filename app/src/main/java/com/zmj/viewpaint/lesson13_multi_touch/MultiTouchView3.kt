package com.zmj.viewpaint.lesson13_multi_touch

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.SparseArray
import android.view.MotionEvent
import android.view.MotionEvent.*
import android.view.View
import androidx.core.util.size
import com.zmj.viewpaint.common.dp2px

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/6/9
 * Description :互不干扰
 */
class MultiTouchView3(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paths = SparseArray<Path>()

    init {
        paint.style = Paint.Style.STROKE
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeWidth = dp2px(4f)
        paint.color = Color.RED
        paint.strokeJoin = Paint.Join.ROUND
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when(event!!.actionMasked){
            ACTION_DOWN, ACTION_POINTER_DOWN-> {
                val pointerIndex = event.actionIndex      //pointIndex值不可靠会被复用   pointId不会
                val pointerId = event.getPointerId(pointerIndex)
                val path = Path()
                path.moveTo(event.getX(pointerIndex),event.getY(pointerIndex))
                paths.append(pointerId,path)
            }
            ACTION_MOVE -> {
                for (i in 0 until event.pointerCount){
                    val pointerId = event.getPointerId(i)
                    val path = paths[pointerId]
                    path.lineTo(event.getX(i),event.getY(i))
                }
                invalidate()
            }
            ACTION_UP, ACTION_POINTER_UP -> {
                val pointerIndex = event.actionIndex
                val pointerId = event.getPointerId(pointerIndex)
                val path = paths[pointerId]
                path.reset()
                invalidate()
            }
        }
        return true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas!!)
        for (i in 0 until paths.size()){
            canvas.drawPath(paths[i], paint)
        }
    }

}
