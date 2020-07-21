package com.zmj.viewpaint.lesson15_view_summary.likeview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.zmj.viewpaint.common.dp2px
import kotlin.math.max

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/7/7
 * Description :
 */
class LikeNumView : View {

    constructor(context: Context?): super(context)
    constructor(context: Context?, attrs: AttributeSet?): super(context, attrs)
    constructor(context: Context?,attrs: AttributeSet?,styleDef: Int): super(context, attrs,styleDef)


    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textSize = dp2px(12f)

    private var mCurNum = 0
    private var newNum = 0

    private var rightPadding = 0f
    private var mMoveY: Int = 0

    private var liked = false

    private var centerY: Int = 0

    private var translationY: Int = 0

    init {
        paint.textSize = textSize
        paint.color = Color.parseColor("#c3c4c3")
    }

    fun setNum(num: Int){
        mCurNum = num
        newNum = num

        invalidate()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //保证足够长
        val curNum = ((mCurNum + 1) * 10).toString()
        val rect = Rect()
        val textBounds = paint.getTextBounds(curNum,0,curNum.length,rect)
        var width = (rect.width() + rightPadding).toInt()
        val height = rect.height() * 4
        val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)
        width = resolveSize(width,widthSpecSize)
        setMeasuredDimension(width,height)
        mMoveY = height / 2
    }

    fun changeLike(isLike: Boolean){
        if (isLike){
            if (mCurNum != 0){
                newNum = mCurNum - 1
            }
        }else{
            newNum = mCurNum + 1
        }
    }

    fun init() {
        mCurNum = newNum
    }
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas!!)
        centerY = height / 2
        val leftX = 0
        var rect = Rect()
        paint.getTextBounds("0",0,1,rect)

        drawAnimNum(canvas,leftX, centerY - (rect.top + rect.bottom)/2,mCurNum,newNum)
    }

    private fun drawAnimNum(canvas: Canvas, leftX: Int, baseTxtY: Int, mCurNum: Int, newNum: Int) {
        val curNumStr = mCurNum.toString()
        val newNumStr  = newNum.toString()
        val len = max(curNumStr.length,newNumStr.length)
        val charLen = paint.measureText("0")
        var sumLeft = leftX
        var curCharTxt: String
        var newCharTxt: String
        for (i in 0 until len){
            if (i > (curNumStr.length - 1)){
                curCharTxt = ""
            }else{
                curCharTxt = curNumStr.substring(i,(i + 1))
            }
            if (i > (newNumStr.length - 1)){
                newCharTxt = ""
            }else{
                newCharTxt = newNumStr.substring(i,(i + 1))
            }

            optDrawNum(canvas,sumLeft,baseTxtY,curCharTxt,newCharTxt,newNum > mCurNum)
            sumLeft += charLen.toInt()
        }
    }

    private fun optDrawNum(canvas: Canvas, leftX: Int, baseTxtY: Int, curNum: String, newNum: String, upOrDown: Boolean) {
        if (curNum == newNum){
            paint.alpha = 255
            canvas.drawText(curNum,leftX.toFloat(),baseTxtY.toFloat(),paint)
            return
        }
        val alpha = ((1 - 1.0 * translationY/mMoveY) * 255).toInt()
        val newBaseY: Int
        val transY: Int
        if (upOrDown){  //up
            transY = - translationY
            newBaseY = baseTxtY + mMoveY
        }else{  //down
            transY = translationY
            newBaseY = baseTxtY - mMoveY
        }
        paint.alpha = alpha
        canvas.drawText(curNum,leftX.toFloat(),baseTxtY + transY.toFloat(),paint)
        paint.alpha = 255 - alpha
        canvas.drawText(newNum,leftX.toFloat(),newBaseY + transY.toFloat(),paint)
        paint.alpha = 255
    }

    fun setTranslationY(translation: Int){
        this.translationY = translation
        invalidate()
    }

    fun setLiked(liked: Boolean){
        this.liked = liked
    }

    fun setRightPadding(rightPadding: Float){
        this.rightPadding = rightPadding
    }
}