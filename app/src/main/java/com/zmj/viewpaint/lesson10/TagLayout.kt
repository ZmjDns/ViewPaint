package com.zmj.viewpaint.lesson10

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/5/18
 * Description : TabLayout
 */
class TagLayout(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {

    private val childrenBounds = ArrayList<Rect>()

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context,attrs)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var widthUsed = 0
        var heightUsed = 0
        var linWidthUsed = 0
        var lineMaxHeight = 0
        val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        val specWidth = MeasureSpec.getSize(widthMeasureSpec)
        for (index in 0 until childCount){
            val child = getChildAt(index)
            measureChildWithMargins(child,widthMeasureSpec,0,heightMeasureSpec,heightUsed)
            if (widthSpecMode != MeasureSpec.UNSPECIFIED && widthUsed + child.measuredWidth > specWidth){//换行
                widthUsed = 0
                linWidthUsed = 0
                heightUsed += lineMaxHeight
                lineMaxHeight = 0
                measureChildWithMargins(child,widthMeasureSpec,0,heightMeasureSpec,heightUsed)
            }
            var childRect: Rect
            if (childrenBounds.size <= index){
                childRect = Rect()
                childrenBounds.add(childRect)
            }else{
                childRect = childrenBounds[index]
            }
            childRect.set(linWidthUsed,heightUsed,linWidthUsed + child.measuredWidth,heightUsed +child.measuredHeight)
            linWidthUsed += child.measuredWidth
            widthUsed = Math.max(widthUsed,linWidthUsed)
            lineMaxHeight = Math.max(lineMaxHeight,child.measuredHeight)

        }

        val width = widthUsed
        val height = heightUsed + lineMaxHeight

        setMeasuredDimension(width,height)
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for (index in 0 until childCount){
            val child = getChildAt(index)
            val rect = childrenBounds[index]
            child.layout(rect.left,rect.top,rect.right,rect.bottom)
        }
    }
}