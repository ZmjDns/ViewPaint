package com.zmj.viewpaint

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zmj.viewpaint.lesson12.Lesson12Act
import com.zmj.viewpaint.lesson6.SixAct
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

        lesson12.setOnClickListener {
            startActivity(Intent(this,Lesson12Act::class.java))
        }
    }
}
