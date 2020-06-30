package com.zmj.viewpaint.lesson15_drag_nestedScroll.dragview

import android.content.Context
import android.util.AttributeSet
import android.view.DragEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import javax.sql.RowSet

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/6/29
 * Description :
 */
class DragListenerGridView(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {

    private val COLUMNS = 2
    private val ROWS = 3
    private val viewConfiguration = ViewConfiguration.get(context)
    private val myDragListener = MyDragListener()
    private lateinit var draggedView: View
    private val orderedChildren = ArrayList<View>()

    init {
        isChildrenDrawingOrderEnabled = true
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        for (index in 0 until childCount){
            val child = getChildAt(index)
            orderedChildren.add(child)      //初始化位置

            child.setOnLongClickListener {
                draggedView = it
                it.startDrag(null, DragShadowBuilder(it),it,0)
                return@setOnLongClickListener false
            }

            child.setOnDragListener(myDragListener)
        }
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val specWidth = MeasureSpec.getSize(widthMeasureSpec)
        val specHeight = MeasureSpec.getSize(heightMeasureSpec)
        val childWith = specWidth / COLUMNS
        val childHeight = specHeight / ROWS

        measureChildren(MeasureSpec.makeMeasureSpec(childWith,MeasureSpec.EXACTLY),MeasureSpec.makeMeasureSpec(childHeight,MeasureSpec.EXACTLY))

        setMeasuredDimension(specWidth,specHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var childLeft = 0
        var childTop = 0
        val childWidth = measuredWidth / COLUMNS
        val childHeight = measuredHeight / ROWS

        for (index in 0 until childCount){
            val chilidView = getChildAt(index)
            childLeft = index % 2 * childWidth
            childTop = index / 2 * childHeight

            chilidView.layout(0,0,childWidth,childHeight)

            chilidView.translationX = childLeft.toFloat()
            chilidView.translationY = childTop.toFloat()
        }
    }


    inner class MyDragListener: OnDragListener{
        override fun onDrag(v: View?, event: DragEvent?): Boolean {
            when(event?.action){
                DragEvent.ACTION_DRAG_STARTED -> {//开始
                    if (event.localState == v){
                        v?.visibility = View.INVISIBLE  //隐藏底部原来的View（正常只会拖拽起一个半透明的View）
                    }
                }
                DragEvent.ACTION_DRAG_ENTERED -> {//拖拽至进入某个区域
                    if (event.localState != v){//进入别的View区域
                        //执行排序操作
                        sortView(v!!)
                    }
                }
                DragEvent.ACTION_DRAG_EXITED -> {//退出
                }
                DragEvent.ACTION_DRAG_ENDED -> {//结束
                    v?.visibility = View.VISIBLE
                }
            }
            return true
        }

    }

    private fun sortView(targetView: View) {
        var draggedIndex = -1
        var targetIndex = -1

        for (i in 0 until childCount){
            val child = orderedChildren[i]
            if (targetView == child){
                targetIndex = i
            }else if (child == draggedView){
                draggedIndex = i
            }
        }

        if (targetIndex < draggedIndex){
            orderedChildren.removeAt(draggedIndex)
            orderedChildren.add(targetIndex,draggedView)
        }else if (targetIndex < draggedIndex){
            orderedChildren.removeAt(draggedIndex)
            orderedChildren.add(targetIndex,draggedView)
        }

        var childLeft = 0
        var childTop = 0
        val childWidth = width / COLUMNS
        val childHeight = height / ROWS

        for (i in 0 until orderedChildren.size){
            val child = orderedChildren[i]
            childLeft = i%2 * childWidth
            childTop = i/2 * childHeight

            child.animate()
                .translationX(childLeft.toFloat())
                .translationY(childTop.toFloat()).duration = 150
        }

    }
}