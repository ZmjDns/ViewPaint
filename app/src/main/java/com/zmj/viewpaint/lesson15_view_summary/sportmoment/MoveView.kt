package com.zmj.viewpaint.lesson15_view_summary.sportmoment

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Nullable
import com.zmj.viewpaint.R
import com.zmj.viewpaint.common.dp2px
import java.util.*

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/8/31
 * Description :
 */
class MoveView : View {
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mAnimatorSet = AnimatorSet()
    private var mDrawInner = false
    private var mDrawOut = false
    private var mDrawLoading = true
    private var mStepNum: String? = null
    private var mKmNum: String? = null
    private var mCalNum: String? = null
    private var mWatchBitmap: Bitmap? = null
    private var mRandom: Random? = null
    private var transparentWhite = 0
    private var degree = 0f
    private var maxMove = 0f
    private var centerX = 0f
    private var centerY = 0f

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, @Nullable attrs: AttributeSet?) : super(context, attrs) {
    }
    constructor(context: Context?, @Nullable attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }

    init {
        setWillNotDraw(false)
        mPaint.textAlign = Paint.Align.CENTER
        transparentWhite = Color.parseColor("#00ffffff")
        maxMove = dp2px(50f)
        mWatchBitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_watch)
        mRandom = Random()
        mStepNum = "2274"
        mKmNum = "1.5公里"
        mCalNum = "34千卡"
    }

    fun startAnimal() {
        mDrawInner = false
        mDrawOut = false
        mDrawLoading = true
        val animator0 = ObjectAnimator.ofFloat(this@MoveView, "degree", 0f, 480f)
        animator0.duration = 3000
        animator0.addListener(object : AnimatorListener() {
            override fun onAnimationEnd(animation: Animator) {
                mDrawLoading = false
            }
        })
        val animator1 =
            ObjectAnimator.ofFloat(this, "translationY", 0f, -maxMove)
        animator1.duration = 500
        animator1.addListener(object : AnimatorListener() {
            override fun onAnimationStart(animation: Animator) {
                mDrawOut = true
            }
        })
        val animator2 =
            ObjectAnimator.ofFloat(this, "translationY", -maxMove, 0f)
        animator2.duration = 500
        animator2.addListener(object : AnimatorListener() {
            override fun onAnimationEnd(animation: Animator) {
                mDrawInner = true
            }
        })
        mAnimatorSet.playSequentially(animator0, animator1, animator2)
        mAnimatorSet.start()
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
        setMeasuredDimension(measuredWidth, measuredHeight + 50) //延长内容
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        centerX = width / 2f
        centerY = height / 2f
        //绘制步数
        mPaint.style = Paint.Style.FILL
        mPaint.textSize = 90f
        mPaint.color = Color.parseColor("#ffffff")
        val stepNumRect = Rect()
        mPaint.getTextBounds(mStepNum, 0, mStepNum!!.length - 1, stepNumRect)
        val stepNumBaseY = centerY - (stepNumRect.top + stepNumRect.bottom) / 2
        canvas.drawText(mStepNum!!, centerX, stepNumBaseY, mPaint)
        //绘制km
        mPaint.textSize = 24f
        mPaint.color = Color.parseColor("#b1d6f8")
        val kmNumRect = Rect()
        mPaint.getTextBounds(mKmNum, 0, mKmNum!!.length - 1, kmNumRect)
        val kmNumBaseX = centerX - 20 - (kmNumRect.left + kmNumRect.right) / 2
        val kmNumBaseY = stepNumBaseY + 30 + kmNumRect.height()
        canvas.drawText(mKmNum!!, kmNumBaseX, kmNumBaseY, mPaint)
        //绘制卡路里
        val calNumRect = Rect()
        mPaint.getTextBounds(mCalNum, 0, mCalNum!!.length - 1, calNumRect)
        val calNumBaseX = centerX + 20 + (calNumRect.left + calNumRect.right) / 2
        val calNumBaseY = stepNumBaseY + 30 + kmNumRect.height()
        canvas.drawText(mCalNum!!, calNumBaseX, calNumBaseY, mPaint)
        //绘制中间线
        mPaint.strokeWidth = 2f
        val centerLineTop = kmNumBaseY - kmNumRect.height()
        val centerLineBottom = centerLineTop + kmNumRect.height()
        canvas.drawLine(centerX, centerLineTop, centerX, centerLineBottom, mPaint)
        //绘制最底部手表
        val watchX = centerX - mWatchBitmap!!.width / 2
        val watchY = centerLineBottom + 40
        canvas.drawBitmap(mWatchBitmap!!, watchX, watchY, mPaint)
        //绘制刚开始的加载的旋转动画
        if (mDrawLoading) {
            canvas.save()
            canvas.rotate(degree.toFloat(), centerX.toFloat(), centerY.toFloat())
            val mShader: Shader = SweepGradient(centerX, centerY, transparentWhite, Color.WHITE)
            mPaint.strokeWidth = 1f
            mPaint.shader = mShader
            mPaint.style = Paint.Style.STROKE
            val loadingRadius = (0.65 * measuredWidth / 2).toInt()
            val loadingCircle = RectF(centerX - loadingRadius, centerY - loadingRadius, centerX + loadingRadius, centerY + loadingRadius)
            val loadingPath = Path()
            val loadingLeft = loadingCircle.left
            val loadingTop = loadingCircle.top
            val loadingRight = loadingCircle.right
            val loadingBottom = loadingCircle.bottom
            for (i in 0..19) {
                val value = mRandom!!.nextInt(25)
                val sed = mRandom!!.nextInt(3)
                loadingCircle.left = loadingLeft + value + sed
                loadingCircle.top = loadingTop + value - sed
                loadingCircle.right = loadingRight - value + sed
                loadingCircle.bottom = loadingBottom - value - sed
                loadingPath.addArc(loadingCircle, 40f, 320f)
            }
            canvas.drawPath(loadingPath, mPaint)
            loadingPath.reset()
            val decorPointX = centerX + loadingRadius
            val decorPointY = centerY + 5
            var tempX: Float
            var tempY: Float
            for (i in 0..9) {
                val value0 = mRandom!!.nextInt(i + 20)
                val value = i * 2 + mRandom!!.nextInt(i + 20)
                tempX = decorPointX - value0
                tempY = decorPointY - i * 2 - value
                loadingPath.addCircle(tempX, tempY, 5f, Path.Direction.CCW)
            }
            mPaint.style = Paint.Style.FILL
            canvas.drawPath(loadingPath, mPaint)
            mPaint.shader = null
            canvas.restore()
        }
        //绘制外轮廓
        if (mDrawOut) {
            val outRadius = ((0.65 + 0.15 * getPercent()) * measuredWidth / 2).toInt()
            mPaint.strokeWidth = 25f
            mPaint.style = Paint.Style.STROKE
            canvas.drawCircle(centerX.toFloat(), centerY.toFloat(), outRadius.toFloat(), mPaint)
        }
        //绘制内轮廓
        if (mDrawInner) {
            mPaint.strokeWidth = 5f
            val innerRadius = (0.55 * measuredWidth / 2).toInt()
            val startRunAngle = -90
            val runedAngle = 270
            val startUnRunAngle = startRunAngle + runedAngle
            val unRunedAngle = 360 - runedAngle
            val innerCircle = RectF(centerX - innerRadius, centerY - innerRadius, centerX + innerRadius, centerY + innerRadius)
            val effects: PathEffect = DashPathEffect(floatArrayOf(2f, 4f), 1f)
            val unRunPath = Path()
            unRunPath.addArc(innerCircle, startUnRunAngle.toFloat(), unRunedAngle.toFloat())
            mPaint.pathEffect = effects
            canvas.drawPath(unRunPath, mPaint)
            mPaint.pathEffect = null
            mPaint.color = Color.WHITE
            val runedPath = Path()
            runedPath.addArc(innerCircle, startRunAngle.toFloat(), runedAngle.toFloat())
            canvas.drawPath(runedPath, mPaint)
        }
    }

    private fun getPercent(): Float {
        return Math.abs(translationY) / maxMove
    }

    override fun setTranslationY(translationY: Float) {
        super.setTranslationY(translationY)
        invalidate()
    }

    fun setDegree(degree: Float) {
        this.degree = degree
        invalidate()
    }

    abstract inner class AnimatorListener :Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {}
        override fun onAnimationCancel(animation: Animator) {}
        override fun onAnimationRepeat(animation: Animator) {}
        override fun onAnimationEnd(animation: Animator) {}
    }
}