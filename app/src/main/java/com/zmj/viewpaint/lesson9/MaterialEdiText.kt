package com.zmj.viewpaint.lesson9

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import com.zmj.viewpaint.common.dp2px

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/5/14
 * Description :
 */
class MaterialEdiText(context: Context?, attrs: AttributeSet?) : AppCompatEditText(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val TEXT_SIZE = dp2px(12f)
    private val TEX_MARGIN = dp2px(8f)
    private val TEXT_VERTICAL_OFFSET = dp2px(20f)
    private val PATTIN_LEFT = dp2px(5f)

    private var floatingShown = false
    private var floatingFraction = 0f

    init {
        paint.textSize = TEXT_SIZE
        setPadding(paddingLeft,(paddingTop + TEXT_SIZE + TEX_MARGIN).toInt(),paddingRight,paddingBottom)
        addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (floatingShown && TextUtils.isEmpty(s)){
                    val animator = ObjectAnimator.ofFloat(this@MaterialEdiText,"floatingFraction",0f)
                    animator.start()
                    floatingShown = false
                }else if (!floatingShown && !TextUtils.isEmpty(s)){
                    val animator = ObjectAnimator.ofFloat(this@MaterialEdiText,"floatingFraction",1f)
                    animator.start()
                    floatingShown = true
                }
            }
        })

    }

    fun getFloatingFraction(): Float{
        return floatingFraction
    }
    fun setFloatingFraction(floatingFraction: Float){
        this.floatingFraction = floatingFraction
        invalidate()
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        paint.alpha = (0xff * floatingFraction).toInt()
        canvas!!.drawText(hint.toString(),PATTIN_LEFT,TEXT_VERTICAL_OFFSET,paint)


    }



}