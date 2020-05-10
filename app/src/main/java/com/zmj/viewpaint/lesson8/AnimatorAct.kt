package com.zmj.viewpaint.lesson8

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnResume
import com.zmj.viewpaint.R
import com.zmj.viewpaint.common.dp2px
import kotlinx.android.synthetic.main.act_eight.*

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/5/8
 * Description :
 */
class AnimatorAct: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.act_eight)

        initCircleView()

        flipPageAnimator()
    }

    private fun initCircleView(){
        val objectAnimator: ObjectAnimator = ObjectAnimator.ofFloat(circleView,"radius", dp2px(150f))
        objectAnimator.startDelay = 1000
        objectAnimator.start()
    }

    private fun flipPageAnimator() {
        /*val objectAnimator = ObjectAnimator.ofFloat(flipPage,"bottomFlip",135f)
        objectAnimator.duration = 1000
        objectAnimator.startDelay = 1000
        objectAnimator.start()*/

        //多个动画化按顺序执行
        val bottomFlipAnimator = ObjectAnimator.ofFloat(flipPage,"bottomFlip",45f)
        bottomFlipAnimator.duration = 1500

        val rotationAnimator = ObjectAnimator.ofFloat(flipPage,"rotationFlip",270f)
        rotationAnimator.duration = 1500
        rotationAnimator.repeatMode = ValueAnimator.RESTART
        rotationAnimator.repeatCount = 10

        val topFlipAnimator = ObjectAnimator.ofFloat(flipPage,"topFlip",-45f)
        rotationAnimator.duration = 1500
        rotationAnimator.repeatMode = ValueAnimator.RESTART
        rotationAnimator.repeatCount = 10

        //多个动画化按顺序执行
        val animatorSet = AnimatorSet()
        animatorSet.playSequentially(bottomFlipAnimator,rotationAnimator,topFlipAnimator)
        animatorSet.startDelay = 1000
        animatorSet.start()
    }
}