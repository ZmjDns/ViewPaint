package com.zmj.viewpaint.lesson15_view_summary.rulerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Build;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.widget.EdgeEffect;
import android.widget.OverScroller;

import androidx.annotation.VisibleForTesting;

import java.util.ArrayList;
import java.util.List;

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/7/14
 * Description :
 */
class InnerRuler extends View {
    private Context mContext;
    private final BooHeeRuler mParent;

    protected Paint mSmallScalePaint,mBigScalePaint,mTextPaint,mOutLinePaint;

    //当前刻度值
    protected float mCurrentScale = 0;
    //最大刻度值
    protected int mMaxLength = 0;
    //长度     最小滑动值     最大滑动值
    protected int mLength, mMinPosition = 0 , mMaxPosition = 0;
    //控制滑动
    protected OverScroller mOverScroller;
    //一大格刻度包含几小格刻度
    protected int mCount = 10;
    //提前刻画量
    protected int mDrawOffset;
    //速度获取
    protected VelocityTracker mVelocityTracker;
    //惯性最大最小速度
    protected int mMaximumVelocity,mMinimumVelocity;
    /**
     * 回调接口
     */
    protected List<RulerCallBack> mRulerCallBacks = new ArrayList<>();
    //边界效果
    protected EdgeEffect mStartEdgeEffect,mEndEdgeEffect;
    //边缘效应长度
    protected int mEdgeLength;
    private final int flag;
    //互动时的位置记录
    private float mLastY;
    private float mLastX;

    public InnerRuler(Context context, BooHeeRuler booHeeRuler, int flag){
        super(context);
        mParent = booHeeRuler;
        this.flag = flag;
        init(context);
    }

    private void init(Context context) {
        mContext = context;

        mMaxLength = mParent.getMaxScale() - mParent.getMinScale();
        mCurrentScale = mParent.getCurrentScale();
        mCount = mParent.getCount();
        mDrawOffset = mCount * mParent.getInterval() /2;

        initPaints();

        mOverScroller = new OverScroller(context);

        //配置速度
        mVelocityTracker = VelocityTracker.obtain();
        mMaximumVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
        mMinimumVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();

        initEdgeEffects();

        //第一次进入跳转到指定刻度
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                scroll2Scale(mCurrentScale);
            }
        });

        checkAPILevel();
    }

    private void initPaints() {
        mSmallScalePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSmallScalePaint.setStrokeWidth(mParent.getSmallScaleWidth());
        mSmallScalePaint.setColor(mParent.getScaleColor());
        mSmallScalePaint.setStrokeCap(Paint.Cap.ROUND);

        mBigScalePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBigScalePaint.setColor(mParent.getScaleColor());
        mBigScalePaint.setStrokeWidth(mParent.getBigScaleWidth());
        mBigScalePaint.setStrokeCap(Paint.Cap.ROUND);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mParent.getTextColor());
        mTextPaint.setTextSize(mParent.getTextSize());
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mOutLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mOutLinePaint.setStrokeWidth(0);
        mOutLinePaint.setColor(mParent.getScaleColor());
    }
    /**
     * 边缘效应
     */
    public void initEdgeEffects() {
        if (mParent.canEdgeEffect()){
            if (mStartEdgeEffect == null || mEndEdgeEffect == null){
                mStartEdgeEffect = new EdgeEffect(mContext);
                mEndEdgeEffect = new EdgeEffect(mContext);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    mStartEdgeEffect.setColor(mParent.getEdgeColor());
                    mEndEdgeEffect.setColor(mParent.getEdgeColor());
                }
                mEdgeLength = mParent.getCursorHeight() + mParent.getInterval() * mParent.getCount();
            }
        }
    }



    private void checkAPILevel(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2){
            setLayerType(LAYER_TYPE_NONE,null);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mRulerCallBacks = null;
    }


    /**
     * 设置尺子当前刻度
     */
    public void setCurrentScale(float currentScale){
        this.mCurrentScale = currentScale;
        scroll2Scale(mCurrentScale);
    }
    public void addRulerCallBack(RulerCallBack rulerCallBack) {
        mRulerCallBacks.add(rulerCallBack);
    }

    public float getCurrentScale(){
        return mCurrentScale;
    }

    /////////////绘制逻辑//////////////////
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawRulerBody(canvas,flag);
    }

    private void drawRulerBody(Canvas canvas, int flag) {
        float location = 0,length;
        if (isVerticalRuler(flag)){
            //尺子竖直，刻度是水平的，Y是固定的
            location = getScrollY();
            length = canvas.getHeight();
        }else {
            //尺子水平，刻度是竖直得的，X是固定的
            location = getScrollX();
            length = canvas.getWidth();
        }

        int start = (int) ((location - mDrawOffset)/mParent.getInterval() + mParent.getMinScale());
        int end = (int) ((location + length + mDrawOffset)/mParent.getInterval() + mParent.getMinScale());

        for (int i = start; i <= end; i++){
            if (i >= mParent.getMinScale() && i <= mParent.getMaxScale()){
                if (i % mCount == 0) {
                    PointF[] line = getLine(mParent,flag,mParent.getBigScaleLength(),i,canvas.getHeight(),canvas.getWidth());
                    canvas.drawLine(line[0].x,line[0].y,line[1].x,line[1].y,mBigScalePaint);

                    PointF textEndPoint = getTextPoint(mParent,flag,i,canvas.getHeight(),canvas.getWidth());
                    canvas.drawText(String.valueOf(i/10),textEndPoint.x,textEndPoint.y,mTextPaint);
                }else {
                    PointF[] line = getLine(mParent,flag,mParent.getSmallScaleLength(),i,canvas.getHeight(),canvas.getWidth());
                    canvas.drawLine(line[0].x,line[0].y,line[1].x,line[1].y,mSmallScalePaint);
                }
            }
        }
        //画轮廓线
        PointF[] outLine = getOutLine(this,flag,canvas.getHeight(),canvas.getWidth());
        canvas.drawLine(outLine[0].x,outLine[0].y,outLine[1].x,outLine[1].y,mOutLinePaint);
        PointF[] outLine2 = getOutLine(this,getNegativeFlag(flag),canvas.getHeight(),canvas.getWidth());
        canvas.drawLine(outLine2[0].x,outLine2[0].y,outLine2[1].x,outLine2[1].y,mOutLinePaint);
        PointF[] outLine3 = getOutLeftOrRightLine(this,flag,true,canvas.getHeight(),canvas.getWidth());
        canvas.drawLine(outLine3[0].x,outLine3[0].y,outLine3[1].x,outLine3[1].y,mOutLinePaint);
        PointF[] outLine4 = getOutLeftOrRightLine(this,flag,false,canvas.getHeight(),canvas.getWidth());
        canvas.drawLine(outLine4[0].x,outLine4[0].y,outLine4[1].x,outLine4[1].y,mOutLinePaint);
    }

    protected static boolean isVerticalRuler(int flag){
        return flag == 0 || flag == 2;
    }
    private static int getNegativeFlag(int flag){
        return (flag + 2)%4;
    }
    private static PointF getTextPoint(BooHeeRuler mParent, int flag, int location, int height, int width) {
        PointF pointF = new PointF();
        float locationY = 0 ,locationX = 0;
        if (isVerticalRuler(flag)){
            //尺子是竖直的，刻度是水平的 Y不变
            locationY = (location - mParent.getMinScale()) * mParent.getInterval();
        }else {
            //尺子是水平的，刻度是竖直的  X不变
            locationX = (location - mParent.getMinScale()) * mParent.getInterval();
        }
        switch (flag){
            case BooHeeRuler.LEFT_LAYOUT:
                pointF.x = mParent.getTextMarginHead();
                pointF.y = locationY + mParent.getTextSize()/2f;
                break;
            case BooHeeRuler.TOP_LAYOUT:
                pointF.x = locationX;
                pointF.y = mParent.getTextMarginHead();
                break;
            case BooHeeRuler.RIGHT_LAYOUT:
                pointF.x = width - mParent.getTextMarginHead();
                pointF.y = locationY + mParent.getTextSize()/2f;
                break;
            case BooHeeRuler.BOTTOM_LAYOUT:
                pointF.x = locationX;
                pointF.y = height - mParent.getTextMarginHead();
                break;
        }
        return pointF;
    }



    /**
     * 画线，
     * 由于在画图时需要频繁的调用，直接在类加载的时候就把方法编译，方便频繁调用？
     * 个人观点，其实不写静态的也没事，因为对象实例之后，方法自动就被加载了
     */
    private static PointF[] getLine(BooHeeRuler mParent, int flag, int length, int location, int height, int width) {
        PointF startPoint = new PointF();
        PointF stopPoint = new PointF();
        float locationY = 0,locationX = 0;
        if (isVerticalRuler(flag)){
            //尺子是竖直的，刻度是水平的，Y是固定的
            locationY = (location - mParent.getMinScale()) * mParent.getInterval();
        }else {
            //尺子是你水平的，刻度是竖直的，X是固定的
            locationX = (location - mParent.getMinScale()) * mParent.getInterval();
        }
        switch (flag){
            case BooHeeRuler.LEFT_LAYOUT:       //尺子竖直
                startPoint.y = locationY;
                stopPoint.x =length;
                stopPoint.y = locationY;
                break;
            case BooHeeRuler.TOP_LAYOUT:        //尺子水平
                startPoint.x = locationX;
                stopPoint.x = locationX;
                stopPoint.y = length;
                break;
            case BooHeeRuler.RIGHT_LAYOUT:      //尺子竖直
                startPoint.x = width - length;
                startPoint.y = locationY;
                stopPoint.x = width;
                stopPoint.y = locationY;
                break;
            case BooHeeRuler.BOTTOM_LAYOUT:     //尺子水平
                startPoint.x = locationX;
                startPoint.y = height - length;
                stopPoint.x = locationX;
                stopPoint.y = height;
                break;
            default:
        }

        return new PointF[]{startPoint,stopPoint};
    }



    private static PointF[] getOutLine(View contentView, int flag, int height, int width) {
        PointF startPoint = new PointF();
        PointF stopPoint = new PointF();
        float locationY = 0, locationX = 0;
        if (isVerticalRuler(flag)){
            //边界线是竖直的  竖直滚动的起始位置
            locationY = contentView.getScrollY();
        }else {
            //边界线是水平的   水平滚动的起始位置
            locationX = contentView.getScrollX();
        }
        switch (flag){
            case BooHeeRuler.LEFT_LAYOUT:
                startPoint.y = locationY;
                stopPoint.y = locationY + height;
                break;
            case BooHeeRuler.TOP_LAYOUT:
                startPoint.x = locationX;
                stopPoint.x = locationX + width;
                break;
            case BooHeeRuler.RIGHT_LAYOUT:
                startPoint.y = locationY;
                stopPoint.y = locationY + height;
                startPoint.x = width - 0.1f;
                stopPoint.x = width - 0.1f;
                break;
            case BooHeeRuler.BOTTOM_LAYOUT:
                startPoint.x = locationX;
                startPoint.y = height - 0.1f;
                stopPoint.x = locationX + width;
                stopPoint.y = height - 0.1f;
                break;
            default:
        }

        return new PointF[]{startPoint,stopPoint};
    }

    private static PointF[] getOutLeftOrRightLine(InnerRuler convertView, int flag, boolean isLeft, int height, int width) {
        PointF startPoint = new PointF();
        PointF stopPoint = new PointF();
        float locationY = 0, locationX = 0;
        if (isVerticalRuler(flag)){
            //边界线是竖直的    竖直滚动的起始位置
            locationY = convertView.getScrollY();
        }else {
            //边界线是水平的  水平滚动的起始位置
            locationX = convertView.getScrollX();
        }
        switch (flag){
            case BooHeeRuler.LEFT_LAYOUT:
                if (isLeft){
                    startPoint.y = locationY;
                    stopPoint.y = locationY;
                    stopPoint.x = width;
                }else {
                    startPoint.y = locationY + height - 0.1f;
                    stopPoint.y = locationY + height - 0.1f;
                    stopPoint.x = width;
                }
                break;
            case BooHeeRuler.TOP_LAYOUT:
                if (isLeft){
                    startPoint.x = locationX;
                    stopPoint.x = locationX;
                    stopPoint.y = height;
                }else {
                    startPoint.x = locationX + width - 0.1f;
                    stopPoint.x = locationX + width - 0.1F;
                    stopPoint.y = height;
                }
                break;
            case BooHeeRuler.RIGHT_LAYOUT:
                if (isLeft){
                    startPoint.y = locationY;
                    stopPoint.y = locationY;
                    startPoint.x = width - 0.1f;
                }else {
                    startPoint.y = locationY + height - 0.1F;
                    stopPoint.y = locationY + height - 0.1F;
                    startPoint.x = width - 0.1F;
                }
                break;
            case BooHeeRuler.BOTTOM_LAYOUT:
                if (isLeft){
                    startPoint.x = locationX;
                    startPoint.y = height - 0.1f;
                    stopPoint.x = locationX;
                }else {
                    startPoint.x = locationX + width - 0.1F;
                    startPoint.y = height - 0.1F;
                    stopPoint.x = locationX + width - 0.1F;
                }
                break;
        }

        return new PointF[]{startPoint,stopPoint};
    }

    //============滑动事件逻辑============
    //获取控件宽高，设置相应信息
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mLength = (mParent.getMaxScale() - mParent.getMinScale()) * mParent.getInterval();
        int mHalf = (isVerticalRuler(flag) ? h : w) / 2;
        mMinPosition = -mHalf;
        mMaxPosition = mLength - mHalf;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float currentY = event.getY();
        float currentX = event.getX();
        if (mVelocityTracker == null){
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (!mOverScroller.isFinished()){
                    mOverScroller.abortAnimation();
                }
                mLastY = currentY;
                mLastX = currentX;
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = isVerticalRuler(flag) ? 0 : mLastX - currentX;
                float moveY = isVerticalRuler(flag) ? mLastY - currentX : 0;
                scrollBy((int) moveX,(int) moveY);
                mLastY = currentY;
                mLastX = currentX;
                break;
            case MotionEvent.ACTION_UP:
                mVelocityTracker.computeCurrentVelocity(1000,mMaximumVelocity);
                int velocityY = 0, velocityX = 0;
                boolean startScroll = false;
                if (isVerticalRuler(flag)){
                    velocityY = (int) mVelocityTracker.getYVelocity();
                    startScroll = Math.abs(velocityY) > mMinimumVelocity;
                }else {
                    velocityX = (int) mVelocityTracker.getXVelocity();
                    startScroll = Math.abs(velocityX) > mMinimumVelocity;
                }

                if (startScroll){
                    fling(isVerticalRuler(flag),-velocityX,-velocityY);
                }else {
                    scrollBackToCurrentScale(isVerticalRuler(flag));
                }

                //VelocityTracker
                if (mVelocityTracker != null){
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                if (!mOverScroller.isFinished()){
                    mOverScroller.abortAnimation();
                }
                scrollBackToCurrentScale(isVerticalRuler(flag));
                //VelocityTracker回收
                if (mVelocityTracker != null){
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }
                break;
        }
        return true;
    }


    private void fling(boolean isVertical,int vx,int vy){
        int startX = 0,startY = 0,velocityX = 0,velocityY = 0,minX = 0,minY = 0,maxX = 0,maxY = 0;
        startX = isVertical ? 0 : getScrollX();
        startY = isVertical ? getScrollY() : 0;
        velocityX = isVertical ? 0 : vx;
        velocityY = isVertical ? vy : 0;

        int min = mMinPosition - mEdgeLength;
        int max = mMaxPosition + mEdgeLength;
        minX = isVertical ? 0 : min;
        minY = isVertical ? 0 : max;
        maxX = isVertical ? min : 0;
        maxY = isVertical? max : 0;

        mOverScroller.fling(startX,startY,velocityX,velocityY,minX,minY,maxX,maxY);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mOverScroller.computeScrollOffset()){
            scrollTo(mOverScroller.getCurrX(),mOverScroller.getCurrY());
            if (!mOverScroller.computeScrollOffset() && mCurrentScale != Math.round(mCurrentScale)){
                scrollBackToCurrentScale(isVerticalRuler(flag));
            }
            invalidate();
        }
    }

    private void scrollBackToCurrentScale(boolean isVertical) {
        int startX = 0,startY = 0,destX = 0,destY = 0;
        startX = isVertical ? 0 : getScrollX();
        startY = isVertical ? getScrollY() : 0;

        destX = isVertical ? 0 : scale2ScrollXY(Math.round(mCurrentScale)) - startX;
        destY = isVertical ? scale2ScrollXY(Math.round(mCurrentScale)) -startY : 0;
        mOverScroller.startScroll(startX,startY,destX,destY,1000);
        invalidate();
    }

    //将移动过程中经过的刻度显示出来
    protected void scroll2Scale(float scale){
        mCurrentScale = Math.round(scale);
        scrollTo(0,scale2ScrollXY(mCurrentScale));
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
        if (isVerticalRuler(flag)){
            if (y < mMinPosition || y > mMaxPosition){
                y = mMinPosition;
            }
            if (y != getScrollY()){
                super.scrollTo(x,y);
            }
            mCurrentScale = scrollXY2Scale(y);
        }else {
            if (x < mMinPosition || x > mMaxPosition){
                x = mMinPosition;
            }
            if (x != getScrollX()){
                super.scrollTo(x,y);
            }
            mCurrentScale = scrollXY2Scale(x);
        }
        if (mRulerCallBacks != null){
            for (RulerCallBack item : mRulerCallBacks){
                item.onScaleChanging(Math.round(mCurrentScale));
            }
        }
    }

    private float scrollXY2Scale(int scrollXY) {

        return ((float) (scrollXY - mMinPosition)/mLength) * mMaxLength + mParent.getMinScale();
    }
    //将刻度转化为移动的位置

    private int scale2ScrollXY(float scale){
        return (int) ((scale - mParent.getMinScale())/mMaxLength*mLength + mMinPosition);
    }
}
