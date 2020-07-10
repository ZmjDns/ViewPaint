package com.zmj.viewpaint.lesson15_view_summary.likeview

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.animation.addListener
import com.zmj.viewpaint.R

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/7/7
 * Description :点赞view
 *
 * 1.attrs属性声明(likeNum,padding,likeSrc...)
 * 2.LikeView继承LinearLayout
 * 3.typeArray中取出属性
 * 4.初始化设置
 * 5.测量
 * 6.设置各种属性（注意设置完，刷新view）
 * 7.收尾onDetachView
 */
class LikeView : LinearLayout {

    private val likeImageView: LikeImageView
    private val likeNumView: LikeNumView
    private lateinit var animatorSet: AnimatorSet

    private var isLike = false

    private val animTime = 500L

    init {
        orientation = HORIZONTAL
        likeImageView = LikeImageView(context)
        addView(likeImageView)
        likeNumView = LikeNumView(context)
        addView(likeNumView)

        setOnClickListener {
            if (!animatorSet.isRunning){
                likeNumView.changeLike(isLike)
                isLike = !isLike
                likeImageView.setLike(isLike)
                animatorSet.start()
            }
        }
    }

    constructor(context: Context?): super(context)

    constructor(context: Context?, attrs: AttributeSet?): super(context, attrs){
        val custom = context?.obtainStyledAttributes(attrs,R.styleable.LikeView) ?: return

        val likeNum = custom.getInt(R.styleable.LikeView_likeNum,0)
        val liked = custom.getBoolean(R.styleable.LikeView_liked,false)
        val leftPadding = custom.getDimension(R.styleable.LikeView_leftPadding,0f)
        val rightPadding = custom.getDimension(R.styleable.LikeView_rightPadding,0f)
        val middlePadding = custom.getDimension(R.styleable.LikeView_middlePadding,0f)
        val likedSrc = custom.getResourceId(R.styleable.LikeView_likedSrc,R.drawable.ic_messages_like_selected)
        val unLikedSrc = custom.getResourceId(R.styleable.LikeView_unLikedSrc,R.drawable.ic_messages_like_unselected)
        val shiningSrc = custom.getResourceId(R.styleable.LikeView_shiningSrc,R.drawable.ic_messages_like_selected_shining)

        likeImageView.setShiningSrc(shiningSrc)
        likeImageView.setLikedSrc(likedSrc)
        likeImageView.setUnlikeSrc(unLikedSrc)
        likeNumView.setRightPadding(rightPadding)
        likeImageView.setLeftPadding(leftPadding)
        likeImageView.setMiddlePadding(middlePadding)
        likeNumView.setNum(likeNum)

        setLike(liked)

        custom.recycle()
    }

    constructor(context: Context?,attrs: AttributeSet?,defStyleAttr: Int): super(context, attrs, defStyleAttr)
    fun setLike(like: Boolean){
        isLike = like
        likeNumView.setLiked(like)
        likeImageView.setLike(like)
        invalidate()
    }
    fun setNum(num: Int){
        likeNumView.setNum(num)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val mMoveY = measuredHeight / 2

        val numAnimator = ObjectAnimator.ofInt(likeNumView,"translationY",0,mMoveY)
        numAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                likeNumView.init()
            }
        })
        numAnimator.duration = animTime

        val imageAnimator = ObjectAnimator.ofFloat(likeImageView,"animProgress",0f,1f)
        imageAnimator.duration = animTime
        animatorSet = AnimatorSet()
        animatorSet.playTogether(numAnimator,imageAnimator)

        setMeasuredDimension(likeImageView.measuredWidth + likeNumView.measuredWidth,measuredHeight)


    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animatorSet.end()
    }


}