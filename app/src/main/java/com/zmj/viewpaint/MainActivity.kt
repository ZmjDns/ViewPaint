package com.zmj.viewpaint

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zmj.viewpaint.lesson10.LayoutLesson10Act
import com.zmj.viewpaint.lesson11.Lesson11Act
import com.zmj.viewpaint.lesson12.Lesson12Act
import com.zmj.viewpaint.lesson6.SixAct
import com.zmj.viewpaint.lesson7.ActSeven
import com.zmj.viewpaint.lesson8.AnimatorAct
import com.zmj.viewpaint.lesson9.BitmapDrawableAct
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

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
    }
}
