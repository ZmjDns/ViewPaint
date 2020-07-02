package com.zmj.viewpaint.lesson15_drag_nestedScroll.dragview

import android.content.ClipData
import android.content.Context
import android.util.AttributeSet
import android.view.DragEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.ViewCompat
import com.zmj.viewpaint.R

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/7/2
 * Description :
 */
class DragToCollectLayout(context: Context?, attrs: AttributeSet?) : RelativeLayout(context, attrs) {

    private lateinit var avatar: ImageView
    private lateinit var logo: ImageView
    private lateinit var ll_colllect: LinearLayout
    private val collectDragListener = CollectDragListener()

    private val dragStarter = object :OnLongClickListener{
        override fun onLongClick(v: View?): Boolean {
            val imageData = ClipData.newPlainText("name",v?.contentDescription)

            //此方法比较重，能实现开进程传递数据，（第二个参数）
            return ViewCompat.startDragAndDrop(v!!,imageData,DragShadowBuilder(v),null,0)
        }
    }
    override fun onFinishInflate() {
        super.onFinishInflate()

        avatar = findViewById(R.id.avatar)
        logo = findViewById(R.id.logo)
        ll_colllect = findViewById(R.id.ll_collect)

        avatar.setOnLongClickListener(dragStarter) //在长按监听中实现拖拽
        logo.setOnLongClickListener(dragStarter)
        ll_colllect.setOnDragListener(collectDragListener)
    }


    inner class CollectDragListener: OnDragListener{
        override fun onDrag(v: View?, event: DragEvent?): Boolean {
            when(event?.action){
                DragEvent.ACTION_DROP -> {  //接收ACTION_DROP时传来的数据
                    if (v is LinearLayout){
                        val textView = TextView(context)
                        textView.textSize = 16f
                        textView.text = event.clipData.getItemAt(0).text
                        v.addView(textView)
                    }
                }
            }
            return true
        }

    }





}