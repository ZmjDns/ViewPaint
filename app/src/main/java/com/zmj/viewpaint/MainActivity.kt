package com.zmj.viewpaint

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.TestLooperManager
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.zmj.viewpaint.lesson10.LayoutLesson10Act
import com.zmj.viewpaint.lesson11.Lesson11Act
import com.zmj.viewpaint.lesson12.Lesson12Act
import com.zmj.viewpaint.lesson13_multi_touch.MultiTouchAct
import com.zmj.viewpaint.lesson14_viewpager.Lesson14Act
import com.zmj.viewpaint.lesson15_drag_nestedScroll.dragview.Lesson15DragAct
import com.zmj.viewpaint.lesson15_view_summary.SummaryAct
import com.zmj.viewpaint.lesson15_view_summary.likeview.LikeAct
import com.zmj.viewpaint.lesson18_rxjava.RxAndroidAct
import com.zmj.viewpaint.lesson6.SixAct
import com.zmj.viewpaint.lesson7.ActSeven
import com.zmj.viewpaint.lesson8.AnimatorAct
import com.zmj.viewpaint.lesson9.BitmapDrawableAct
import kotlinx.android.synthetic.main.activity_main.*

import com.zmj.viewpaint.utils.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            val decorView = window.decorView
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.statusBarColor = Color.TRANSPARENT
        }

//        toolBar.textAlignment = View.TEXT_ALIGNMENT_CENTER
//        setSupportActionBar(toolBar)

        initView()
    }

    /*private fun drawBadge() {
        val decorView = window.decorView as ViewGroup
        val badge = View(this)
        badge.setBackgroundColor(Color.RED)
        decorView.addView(badge,200,200)
    }*/


    private fun initView(){
        lesson6.setOnClickListener {
            startActivity(Intent(this,SixAct::class.java))
        }
        lesson7.setOnClickListener {
            startActivity(Intent(this, ActSeven::class.java))
        }
        lesson8.setOnClickListener {
            startActivity(Intent(this, AnimatorAct::class.java))
        }
        lesson9.setOnClickListener {
            startActivity(Intent(this, BitmapDrawableAct::class.java))
        }
        lesson10.setOnClickListener {
            startActivity(Intent(this, LayoutLesson10Act::class.java))
        }
        lesson11.setOnClickListener {
            startActivity(Intent(this, Lesson11Act::class.java))
        }
        lesson12.setOnClickListener {
            startActivity(Intent(this,Lesson12Act::class.java))
        }
        lesson13.setOnClickListener {
            startActivity(Intent(this,MultiTouchAct::class.java))
        }
        lesson14.setOnClickListener {
            startActivity(Intent(this,Lesson14Act::class.java))
        }
        lesson15.setOnClickListener {
            startActivity(Intent(this,Lesson15DragAct::class.java))
        }
        lesson15like.setOnClickListener {
            startActivity(Intent(this,SummaryAct::class.java))
        }
        lesson18RxJava.setOnClickListener {
            startActivity(Intent(this,RxAndroidAct::class.java))
        }
    }
}
