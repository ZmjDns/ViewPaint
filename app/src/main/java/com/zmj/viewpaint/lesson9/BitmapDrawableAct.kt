package com.zmj.viewpaint.lesson9

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zmj.viewpaint.R

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/5/13
 * Description :bitmap 和 Drawable
 *
 * Bitmap 完整图像的像素信息
 *
 * Drawable更像是一个view,有自己的绘制规则，操作Canvas来进行内容绘制
 * 专注于绘制，对于view的复用很方便（如 股票的蜡烛图、医疗健康的环、饼图等）
 *
 */
class BitmapDrawableAct: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_nine)
    }
}