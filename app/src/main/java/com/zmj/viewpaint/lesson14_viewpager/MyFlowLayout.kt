package com.zmj.viewpaint.lesson14_viewpager

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import com.zmj.viewpaint.R

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/3/19
 * Description :
 */
class MyFlowLayout : ViewGroup {

    //存放所有View的容器
    private val allViewLists = ArrayList<ArrayList<View>>()
    //实例一个List<View> 用于存放一行子View
    private val singleLineView = ArrayList<View>()

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0): super(context, attrs, defStyleAttr)

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context,attrs)
    }

    /**
     * 添加多个Tag
     */
    fun setTags(tags: List<String>){
        for (tag in tags){
            setTag(tag)
        }
    }

    /**
     * 添加单个tag标签
     */
    fun setTag(tagStr: String){
        val tv = TextView(context)
        val tvLP = MarginLayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT)
        tvLP.setMargins(30,30,0,0)
        tv.layoutParams = tvLP
        tv.setPadding(30,12,30,12)
        tv.background = resources.getDrawable(R.drawable.cyan_fade_corner_bg,null)
        tv.text = tagStr

        tv.setOnClickListener { tagClickListener?.onTagClick(it as TextView) }

        addView(tv)
        //requestLayout()
    }

    interface TagClick{
        fun onTagClick(tagView: TextView)
    }
    private var tagClickListener: TagClick? = null
    //设置点击监听
    fun setTagClickListener(tagClickListener: TagClick){
        this.tagClickListener = tagClickListener
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        allViewLists.clear()
        singleLineView.clear()
        //获取自身的模式和宽高
        val widthModel = MeasureSpec.getMode(widthMeasureSpec)
        val heightModel = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        //测量子view的大小
        //注意测量了所有可见View，如果view 的Visiable 为GONE则不测量
        measureChildren(widthMeasureSpec, heightMeasureSpec)

        //自身的测量宽高
        var myWidth = 0
        var myHeight = 0

        //子View的测量数据
        var cParams: MarginLayoutParams?

        //计算单行每添加一个子view的宽度
        var singleLineChildrenWidth = 0

        for (i in 0 until childCount){
            val cView = getChildAt(i)
            //此处不用判断子View是否可见，不可见的子View在measureChildren()的时候已经被忽略掉了
            cParams = cView.layoutParams as MarginLayoutParams

            //当前所有View的宽度 大于 父View的可用宽度时 换行
            if (singleLineChildrenWidth + cView.measuredWidth + cParams.marginStart + cParams.marginEnd > widthSize - paddingStart - paddingEnd){
                //获取上一行的宽度，每次换行的时候，取出上一行最大的宽度作为父容器的宽度
                myWidth = myWidth.coerceAtLeast(getSingleLineWidth(singleLineView))
                //获取上一行的高度
                val lastLineHeight = getSingleLineHeight(singleLineView)
                //自身高度叠加
                myHeight += lastLineHeight
                //将上一行的View 添加到总的容器中,注意一定要copy一份，因为下面要清空singleLineView，否则将会只看到最后一行的View
                allViewLists.add(copyLineViews(singleLineView))

                //清空上一行的容器，和记录行宽行高的值
                singleLineView.clear()
                singleLineChildrenWidth = 0
            }
            //计算当前宽度
            singleLineChildrenWidth += cView.measuredWidth + cParams.marginStart + cParams.marginEnd

            singleLineView.add(cView)
        }
        //循环结束之后要计算最后一行的宽高，并决定父容器的宽高
        myWidth = myWidth.coerceAtLeast(getSingleLineWidth(singleLineView))
        myHeight += getSingleLineHeight(singleLineView)
        //把最后一行放入总容器
        allViewLists.add(singleLineView)

        setMeasuredDimension(
            if (widthModel == MeasureSpec.EXACTLY) widthSize else myWidth,
            if (heightModel == MeasureSpec.EXACTLY) heightSize else myHeight
        )
    }

    //拷贝一行view
    private fun copyLineViews(singleLineView: ArrayList<View>): ArrayList<View> {
        val copiedViews = ArrayList<View>()
        for (i in 0 until singleLineView.size){
            copiedViews.add(singleLineView[i])
        }
        return copiedViews
    }

    //获取单行的宽度
    private fun getSingleLineWidth(singleLineView: ArrayList<View>): Int{
        var singLineWidth = 0
        for (cView in singleLineView){
            val cParams = cView.layoutParams as MarginLayoutParams
            singLineWidth += cView.measuredWidth + cParams.marginStart + cParams.marginEnd
        }

        return singLineWidth
    }
    //获取单行高度
    private fun getSingleLineHeight(singleLineView: ArrayList<View>): Int {
        var singleLineHeight = 0
        for (view in singleLineView){
            val cParams = view.layoutParams as MarginLayoutParams
            singleLineHeight = singleLineHeight.coerceAtLeast(view.measuredHeight + cParams.topMargin + cParams.bottomMargin)
        }
        return singleLineHeight
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        /* //初始的时候需要计算父亲的paddingtop
         var ct = 0 + paddingTop;
         var cb = 0
         for (i in 0 until  allViewLists.size){
             val singleLineView = allViewLists[i]
             val nowLineHeight = getSingleLineHeight(singleLineView)
             if (i == 0){
                 cb += ct + nowLineHeight
             }else{
                 val lastLineHeight = getSingleLineHeight(allViewLists[i - 1])
                 ct += lastLineHeight
                 cb = ct + nowLineHeight
             }
             //
             var cl = 0 + paddingStart;
             var cr = 0;
             for (j in 0 until singleLineView.size){
                 val cNowView = singleLineView[j]
                 val cNowParams = cNowView.layoutParams as MarginLayoutParams
                 if (j == 0){
                     cl += cNowParams.marginStart
                     cr += cl + cNowView.measuredWidth
                 }else{
                     val cLastView = singleLineView[j - 1]
                     val cLastParams = cLastView.layoutParams as MarginLayoutParams
                     cl += cLastView.measuredWidth + cLastParams.marginEnd + cNowParams.marginStart
                     cr += cl + cNowView.measuredWidth
                 }

                 cNowView.layout(cl,ct,cr,cb)
             }
         }*/


        /*//当前行之上的高度
        var historyHeight = paddingTop

        var ct = 0
        var cb = 0
        for (i in 0 until allViewLists.size){
            val thisLineViews = allViewLists[i]
            val thiLineHeight = getSingleLineHeight(thisLineViews)
            if (i > 0){
                val lastLineViews = allViewLists[i - 1]
                val lastLineHeight = getSingleLineHeight(lastLineViews)
                historyHeight += lastLineHeight
            }

            var cl = 0 + paddingStart
            var cr = 0
            for (j in 0 until thisLineViews.size){
                val thisView = thisLineViews[j]
                val thisParams = thisView.layoutParams as MarginLayoutParams
                if (j > 0){
                    val lastView = thisLineViews[j -1]
                    val cLastParams = lastView.layoutParams as MarginLayoutParams

                    cl += lastView.measuredWidth + cLastParams.marginEnd
                    cr = cl + thisParams.marginStart + thisView.measuredWidth
                    //ct += historyHeight + thisParams.topMargin
                    //cb = ct + thisParams.topMargin + thisView.measuredHeight
                }else{
                    //本行第一个View
                    cl += paddingStart +  thisParams.marginStart
                    cr += cl + thisView.measuredWidth
                    //ct += historyHeight + thisParams.topMargin
                    //cb = ct + thisParams.topMargin + thisView.measuredHeight
                }
                ct += historyHeight + thisParams.topMargin
                cb = ct + thisParams.topMargin + thisView.measuredHeight

                thisView.layout(cl,ct,cr,cb)
            }
        }*/


        //如果思路不清晰这个会比较麻烦
        //思路：
        //单个子View的left 和top是不断变化的，搞两个变量单独记录每一个子View的位置状态
        //在双层for循环中先对每一行布局，换行后记得重置left，增加top

        var left = paddingStart
        var top = paddingTop

        for (i in 0 until allViewLists.size){
            val thisLineViews = allViewLists[i]
            val thisLineHeight = getSingleLineHeight(thisLineViews)

            for (j in 0 until thisLineViews.size){
                val thisView = thisLineViews[j]
                val thisLp = thisView.layoutParams as MarginLayoutParams

                val cl = left + thisLp.marginStart
                val ct = top + thisLp.topMargin
                val cr = cl + thisView.measuredWidth
                val cb = ct + thisView.measuredHeight

                thisView.layout(cl,ct,cr,cb)

                left += thisView.measuredWidth + thisLp.marginEnd + thisLp.marginStart
            }
            //单行View循环完毕，走出来准备进入下一行之前，要重置left
            left = paddingStart
            top += thisLineHeight
        }
    }
}