package com.zmj.viewpaint.lesson11

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.zmj.viewpaint.R
import kotlinx.android.synthetic.main.act_11.*

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/5/20
 * Description :
 */
class Lesson11Act: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.act_11)

        //val text = findViewById<View>(R.id.view)
        view.performClick()
    }
}