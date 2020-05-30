package com.zmj.viewpaint.lesson6

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.zmj.viewpaint.R
import com.zmj.viewpaint.common.dp2px
import com.zmj.viewpaint.common.getAvatarBit


/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/4/29
 * Description :
 */
class AvatarView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap: Bitmap

    private val WIDTH = dp2px(300f)
    private val PADDING = dp2px(50f)
    private val EDG_WIDTH = dp2px(10f)

    private val saveArea = RectF()
    private val xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)


    init {
        bitmap = getAvatarBit(resources,WIDTH)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        saveArea.set(PADDING,PADDING,PADDING + WIDTH,PADDING +WIDTH)
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawOval(PADDING,PADDING,PADDING + WIDTH,PADDING + WIDTH,paint)
        val saved = canvas?.saveLayer(saveArea,paint)   //离屏缓冲
        canvas?.drawOval(PADDING + EDG_WIDTH,PADDING + EDG_WIDTH,PADDING + WIDTH - EDG_WIDTH,PADDING + WIDTH - EDG_WIDTH,paint)
        paint.setXfermode(xfermode)
        canvas?.drawBitmap(bitmap,PADDING,PADDING,paint)
        paint.setXfermode(null)
        canvas?.restoreToCount(saved!!)

        /*canvas?.drawOval(PADDING,PADDING,PADDING + WIDTH,PADDING + WIDTH,paint)
        val save = canvas?.saveLayer(saveArea,paint)
        canvas?.drawOval(PADDING - EDG_WIDTH,PADDING - EDG_WIDTH,PADDING + EDG_WIDTH,PADDING + EDG_WIDTH,paint)
        paint.xfermode = xfermode
        canvas?.drawBitmap(bitmap,PADDING,PADDING,paint)
        canvas?.restoreToCount(save!!)*/
}

    private fun getAvatar(width: Int): Bitmap{

        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true   //设置以后，在options中只会取到图片的宽高，得到宽高比之后可以计算实际需要显示的宽高
        BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, options)

        /*val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher,options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width

        return BitmapFactory.decodeResource(resources,R.drawable.avatar_rengwuxian,options)*/
    }
}