package com.zmj.viewpaint.lesson15_view_summary.likeview

import android.content.Context
import android.graphics.*
import android.support.v4.app.INotificationSideChannel
import android.util.AttributeSet
import android.view.View
import androidx.annotation.IdRes
import com.zmj.viewpaint.R

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/7/8
 * Description :
 */
class LikeImageView : View {

    constructor(context: Context?): super(context)
    constructor(context: Context?, attrs: AttributeSet?): super(context, attrs)
    constructor(context: Context?,attrs: AttributeSet?,styleDef: Int): super(context, attrs,styleDef)

    private val imagePaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var startX: Float = 0f
    private var likedBitmap: Bitmap = BitmapFactory.decodeResource(resources,R.drawable.ic_messages_like_selected)
    private var shiningBitmap: Bitmap = BitmapFactory.decodeResource(resources,R.drawable.ic_messages_like_selected_shining)
    private var unLikeBitmap: Bitmap = BitmapFactory.decodeResource(resources,R.drawable.ic_messages_like_unselected)


    private var middlePadding = 0f
    private var leftPadding = 0f
    private var centerY = 0f

    private var liked = false
    private var animProgress = 0f



    init {
        imagePaint.color = Color.parseColor("#c3c4c3")
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        startX = likedBitmap.width * 0.1f + leftPadding

        val width = (likedBitmap.width * 1.1).toInt()
        val height = likedBitmap.height + shiningBitmap.height

        setMeasuredDimension((width + leftPadding + middlePadding).toInt(),height)
    }

    fun setLike(isLike: Boolean){
        liked = isLike
        if (!liked){
            animProgress = 0f
        }
        invalidate()
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas!!)

        centerY = height / 2f
        //绘制放大圈
        drawCircle(canvas,(startX + likedBitmap.width / 2),centerY)

        //绘制点赞图片
        val likeTop = centerY - unLikeBitmap.height / 2
        drawLike(canvas,likeTop,startX)

        //绘制闪光
        drawShining(canvas,likeTop,startX)

    }

    private fun drawShining(canvas: Canvas, likeTop: Float, likeLeft: Float) {
        if (liked && animProgress > 0){
            val scale = animProgress
            val shiningTop =(likeTop - scale*shiningBitmap.height/2)
            val shiningLeft = (likeLeft + (likedBitmap.width - scale*shiningBitmap.width) / 2)

            canvas.save()
            canvas.translate(shiningLeft,shiningTop)
            canvas.scale(scale,scale)
            canvas.drawBitmap(shiningBitmap,0f,0f,imagePaint)
            canvas.restore()
        }
    }

    private fun drawLike(canvas: Canvas, likeTop: Float, likeLeft: Float) {
        var bitmap: Bitmap? = null
        var scale = 0f
        if (liked){
            //灰色一下变小
            if (animProgress > 0 && animProgress <= 0.1){
                bitmap = unLikeBitmap
                scale = -0.01f
            }
            //红色小且半透明，变正常工程中就变成了实体
            if (animProgress > 0.1 && animProgress < 0.5){
                imagePaint.alpha = (255 * (0.5 + animProgress)).toInt()
            }else{
                imagePaint.alpha = 255
            }
            //红色放大
            if (animProgress > 0.1 && animProgress < 0.9){
                bitmap = likedBitmap
                scale =(-0.01f + animProgress * 0.1).toFloat()
            }
            //一瞬间变正常
            if (animProgress > 0.9 || animProgress == 0f){
                bitmap = likedBitmap
                scale = 0f
            }
        }else{
            //红色缩小变半透明
            if (animProgress > 0 && animProgress <= 0.5){
                bitmap = likedBitmap
                imagePaint.alpha = (255 * (0.5 + animProgress)).toInt()
                scale = (-animProgress * 0.1).toFloat()
            }
            //一半的时候变灰色
            if (animProgress > 0.5 || animProgress == 0f){
                imagePaint.alpha = 255
                bitmap = unLikeBitmap
                scale = 0f
            }
        }
        canvas.save()
        if (bitmap != null){
            canvas.translate((likeLeft - bitmap.width*0.05).toFloat(),(likeTop - bitmap.height*0.05).toFloat())
            canvas.scale(1 + scale,1 + scale)
            canvas.drawBitmap(bitmap,0f,0f,imagePaint)
        }
        canvas.restore()
    }

    private fun drawCircle(canvas: Canvas, centerX: Float, centerY: Float) {
        var radius = 0f
        var alpha = 0

        if (liked && animProgress > 0){
            //透明实体
            if (animProgress > 0 && animProgress <= 0.5){
                alpha = (255 * (0.5 + animProgress)).toInt()
            }else{//实体变透明
                alpha = (255 * (1 - (animProgress - 0.5))).toInt()
            }

            radius = (0.6 + animProgress * 0.7).toFloat()
        }

        imagePaint.color = Color.parseColor("#cc775c")
        imagePaint.alpha = alpha
        imagePaint.style = Paint.Style.STROKE
        imagePaint.strokeWidth = 3f
        canvas.drawCircle(centerX,centerY,radius * likedBitmap.width/2,imagePaint)
        imagePaint.color = Color.parseColor("#c3c4c3")
        imagePaint.style = Paint.Style.FILL
    }


    @SuppressWarnings("unused")
    fun setAnimProgress(animProgress: Float){
        this.animProgress = animProgress
        invalidate()
    }

    fun setMiddlePadding(middlePadding: Float){
        this.middlePadding = middlePadding
    }

    fun setLeftPadding(leftPadding: Float){
        this.leftPadding = leftPadding
    }

    fun setUnlikeSrc(@IdRes unlikeSrc: Int){
        this.unLikeBitmap = BitmapFactory.decodeResource(resources,unlikeSrc)
    }
    fun setLikedSrc(@IdRes likedSrc: Int){
        this.likedBitmap = BitmapFactory.decodeResource(resources,likedSrc)
    }
    fun setShiningSrc(@IdRes shiningSrc: Int){
        this.shiningBitmap = BitmapFactory.decodeResource(resources,shiningSrc)
    }
}