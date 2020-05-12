package com.zmj.viewpaint.lesson8

import android.animation.*
import android.graphics.Point
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.zmj.viewpaint.R
import com.zmj.viewpaint.common.dp2px
import com.zmj.viewpaint.lesson8.evaluator.PointTypeEvaluator
import com.zmj.viewpaint.lesson8.evaluator.ProvinceEvaluator
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

        /*//多个动画化按顺序执行
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
        animatorSet.start()*/

        /*//同一个View的    多个属性    同时    做动画改变
        val bottomFlipHolder = PropertyValuesHolder.ofFloat("bottomFlip",45f)
        val rotationFlipHolder = PropertyValuesHolder.ofFloat("rotationFlip",270f)
        val topFlipHolder = PropertyValuesHolder.ofFloat("topFlip",-45f)

        val objectAnimatorHolder = ObjectAnimator.ofPropertyValuesHolder(flipPage,bottomFlipHolder,rotationFlipHolder,topFlipHolder)
        objectAnimatorHolder.startDelay = 1000
        objectAnimatorHolder.duration = 2000
        objectAnimatorHolder.start()*/

       /* //keyFrame设置关键帧
        val length = dp2px(300f)
        val keyframe1 = Keyframe.ofFloat(0f,0f)
        val keyframe2 = Keyframe.ofFloat(0.2f,1.2f * length)
        val keyframe3 = Keyframe.ofFloat(0.8f,0.6f * length)
        val keyframe4 = Keyframe.ofFloat(1f,1f * length)

        val propertyValuesHolder = PropertyValuesHolder.ofKeyframe("translationX",keyframe1,keyframe2,keyframe3,keyframe4)
        val objectAnimator = ObjectAnimator.ofPropertyValuesHolder(keyFrame,propertyValuesHolder)
        objectAnimator.startDelay = 1000
        objectAnimator.duration = 2000
        objectAnimator.start()*/

        //ObjectAnimator的使用
        val targetPoint = Point(dp2px(300f).toInt(), dp2px(300f).toInt())
        val pointViewAnim = ObjectAnimator.ofObject(pointView,"point",
            PointTypeEvaluator(),targetPoint)
        pointViewAnim.startDelay = 1000
        pointViewAnim.duration = 2000
        pointViewAnim.interpolator = AccelerateInterpolator()
        pointViewAnim.start()

        val provinceAni = ObjectAnimator.ofObject(provinceView,"province",ProvinceEvaluator(),"澳门特别行政区")
        provinceAni.startDelay = 1000
        provinceAni.interpolator = DecelerateInterpolator()
        provinceAni.duration = 5000
        provinceAni.start()
    }
}