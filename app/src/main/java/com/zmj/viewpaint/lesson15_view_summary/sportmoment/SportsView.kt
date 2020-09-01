package com.zmj.viewpaint.lesson15_view_summary

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.zmj.viewpaint.R
import com.zmj.viewpaint.common.dp2px
import java.util.*
import kotlin.math.abs

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/8/28
 * Description :
 */
class SportsView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {


    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var centerX = 0f
    private var centerY = 0f
    private var mStepNum = "2274"
    private var mKmNum = "1.5公里"
    private var mCalNum = "34千卡"
    private var mWatchBitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_watch)

    private var degree = 0f
    private val transparentWhite = Color.parseColor("#00ffffff")
    private val mRandom = Random()

    private var mDrawLoading: Boolean = true
    private var mDrawOut: Boolean = false
    private var mDrawInner: Boolean = false
    private val maxMove = dp2px(50f)

    private val stepNumRect = Rect()
    private val kmNumRect = Rect()
    private val calNumRect = Rect()

    private val mAnimatorSet = AnimatorSet()

    private var targetStep = 8000
        set(value) {
            field = value
        }

    init {
        setWillNotDraw(false)
        mPaint.textAlign = Paint.Align.CENTER
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mAnimatorSet.end()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measuredWidth,measuredHeight + 50)    //延长内容
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {

    }
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas!!)
        centerX = width / 2f
        centerY = height / 2f
        //绘制步数
        mPaint.style = Paint.Style.FILL
        mPaint.color = Color.parseColor("#FFFFFF")
        mPaint.textSize = 90f
        mPaint.getTextBounds(mStepNum,0,mStepNum.length - 1,stepNumRect)
        val stepNumBaseY = centerY - (stepNumRect.top + stepNumRect.bottom) / 2
        canvas.drawText(mStepNum,centerX,stepNumBaseY,mPaint)   //（x，y）为text的左下角

        //绘制km
        mPaint.textSize = 24f
        mPaint.color = Color.parseColor("#b1d6f8")
        mPaint.getTextBounds(mKmNum,0,mKmNum.length - 1,kmNumRect)
        val kmBaseX = centerX - 20 - (kmNumRect.left + kmNumRect.right) / 2
        val kmBaseY = stepNumBaseY + 30 + kmNumRect.height()
        canvas.drawText(mKmNum,kmBaseX,kmBaseY,mPaint)

        //绘制卡路里
        mPaint.getTextBounds(mCalNum,0,mCalNum.length - 1,calNumRect)
        val calBaseX = centerX + 20 + (calNumRect.left + calNumRect.right) / 2
        val calBaseY = stepNumBaseY + 30 + calNumRect.height()
        canvas.drawText(mCalNum,calBaseX,calBaseY,mPaint)

        //绘制中间线
        mPaint.strokeWidth = 2f
        val centerLineTop = kmBaseY - kmNumRect.height()
        val centerLineBottom = centerLineTop + kmNumRect.height()
        canvas.drawLine(centerX,centerLineTop,centerX,centerLineBottom,mPaint)

        //绘制手表
        val watchX = centerX - mWatchBitmap.width / 2
        val watchY = centerLineBottom + 40
        canvas.drawBitmap(mWatchBitmap,watchX,watchY,mPaint)

        //h绘制刚开始的加载旋转动画
        if(mDrawLoading){
            canvas.save()
            canvas.rotate(degree,centerX,centerY)
            val shader = SweepGradient(centerX,centerY,transparentWhite,Color.WHITE)
            mPaint.strokeWidth = 1f
            mPaint.shader = shader
            mPaint.style = Paint.Style.STROKE
            val loadingRadius = (0.65 * width / 2).toInt()
            val loadingCircle = RectF(centerX - loadingRadius,centerY - loadingRadius,centerX + loadingRadius,centerY + loadingRadius)
            val loadingPath = Path()
            val loadingLeft = loadingCircle.left
            val loadingTop = loadingCircle.top
            val loadingRight = loadingCircle.right
            val loadingBottom = loadingCircle.bottom
            for (i in 0..19){
                val value = mRandom.nextInt(25)
                val seed = mRandom.nextInt(3)
                loadingCircle.left = loadingLeft + value + seed
                loadingCircle.top = loadingTop + value - seed
                loadingCircle.right = loadingRight - value + seed
                loadingCircle.bottom = loadingBottom - value - seed
                loadingPath.addArc(loadingCircle,40f,320f)
            }
            canvas.drawPath(loadingPath,mPaint)

            loadingPath.reset()
            val decorPointX = centerX + loadingRadius
            val decorPointY = centerY + 5
            var tempX = 0f
            var tempY = 0f
            for (i in 0..9){
                val value0 = mRandom.nextInt(i + 20)
                val  value = i * 2 + mRandom.nextInt(i + 20)
                tempX = decorPointX - value0
                tempY = decorPointY - i * 2 - value
                loadingPath.addCircle(tempX,tempY,5f,Path.Direction.CCW)
            }
            mPaint.style = Paint.Style.FILL
            canvas.drawPath(loadingPath,mPaint)
            mPaint.shader = null
            canvas.restore()
        }

        //绘制外轮廓
        if(mDrawOut){
            val outRadius = ((0.65 + 0.15 * precent()) * measuredWidth / 2).toFloat()
            mPaint.strokeWidth = 25f
            mPaint.style = Paint.Style.STROKE
            canvas.drawCircle(centerX,centerY,outRadius,mPaint)
        }

        //绘制内部轮廓
        if (mDrawInner){
            mPaint.strokeWidth = 5f
            val innerRadius = (0.55 * measuredWidth / 2).toFloat()
            val startAngle = -90f
            //val runedAngle = 270f
            val runedAngle = (mStepNum.toInt() * 360f / targetStep)
            Log.d("SportView","runedAngle= $runedAngle")
            val startUnRunedAngle = startAngle + runedAngle
            val unRunedAngle = 360 - runedAngle
            val innerCircle = RectF(centerX - innerRadius,centerY - innerRadius,centerX + innerRadius,centerY + innerRadius)

            val effects = DashPathEffect(floatArrayOf(2f, 4f), 1f)
            val unRunPath = Path()
            unRunPath.addArc(innerCircle,startUnRunedAngle,unRunedAngle)
            mPaint.pathEffect = effects
            canvas.drawPath(unRunPath,mPaint)
            mPaint.pathEffect = null

            mPaint.color = Color.WHITE
            val runedPath = Path()
            runedPath.addArc(innerCircle,startAngle,runedAngle)
            canvas.drawPath(runedPath,mPaint)
        }
    }

    private fun precent(): Float {
        return abs(translationY) / maxMove
    }


    @SuppressWarnings("unused")
    fun setDegree(degree: Float){
        this.degree = degree
        invalidate()
    }

    override fun setTranslationY(translationY: Float) {
        super.setTranslationY(translationY)
        invalidate()
    }

    fun startAnimal(){
        mDrawInner = false
        mDrawOut = false
        mDrawLoading = true

        //val animSet = AnimatorSet()
        val objAnimator0 = ObjectAnimator.ofFloat(this,"degree",0f,480f)
        objAnimator0.duration = 3000
        objAnimator0.addListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {
            }
            override fun onAnimationEnd(animation: Animator?) {
                mDrawLoading = false
            }
            override fun onAnimationCancel(animation: Animator?) {
            }
            override fun onAnimationStart(animation: Animator?) {
            }
        })
        val objAnimator1 = ObjectAnimator.ofFloat(this,"translationY",0f,-maxMove)
        objAnimator1.duration = 500
        objAnimator1.addListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {
            }
            override fun onAnimationEnd(animation: Animator?) {
            }
            override fun onAnimationCancel(animation: Animator?) {
            }
            override fun onAnimationStart(animation: Animator?) {
                mDrawOut = true
            }
        })
        val objAnimator2 = ObjectAnimator.ofFloat(this,"translationY",-maxMove,0f)
        objAnimator2.duration = 500
        objAnimator2.addListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {
            }
            override fun onAnimationEnd(animation: Animator?) {
                mDrawInner = true
            }
            override fun onAnimationCancel(animation: Animator?) {
            }
            override fun onAnimationStart(animation: Animator?) {
            }
        })
        mAnimatorSet.playSequentially(objAnimator0,objAnimator1,objAnimator2)
        mAnimatorSet.start()
    }
}