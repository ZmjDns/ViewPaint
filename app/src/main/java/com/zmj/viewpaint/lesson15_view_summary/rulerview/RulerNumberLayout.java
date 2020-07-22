package com.zmj.viewpaint.lesson15_view_summary.rulerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.IdRes;

import com.zmj.viewpaint.R;

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/7/21
 * Description :
 */
class RulerNumberLayout extends RelativeLayout implements RulerCallBack {
    private TextView tv_scale,tv_kg;
    //字体大小
    private float mScaleTextSize = 80,mKgTextSize = 40;
    //字体颜色
    private @ColorInt
    int mScaleTextColor = getResources().getColor(R.color.colorForgiven);
    private @ColorInt
    int mKgTextColor = getResources().getColor(R.color.colorForgiven);
    @IdRes
    int mTargetRuler;
    //kg单位文字
    private String mUnitText = "kg";


    public RulerNumberLayout(Context context) {
        super(context);
        init(context);
    }

    public RulerNumberLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        init(context);
    }

    public RulerNumberLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initAttrs(Context context,AttributeSet attrs){
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,R.styleable.RulerNumberLayout,0,0);
        mScaleTextSize = typedArray.getDimension(R.styleable.RulerNumberLayout_scaleTextSize,mScaleTextSize);
        mKgTextSize = typedArray.getDimension(R.styleable.RulerNumberLayout_kgTextSize,mKgTextSize);
        mScaleTextColor = typedArray.getColor(R.styleable.RulerNumberLayout_scaleTextColor,mScaleTextColor);
        mKgTextColor = typedArray.getColor(R.styleable.RulerNumberLayout_kgTextColor,mKgTextColor);
        mTargetRuler = typedArray.getResourceId(R.styleable.RulerNumberLayout_targetRuler,0);

        String text = typedArray.getString(R.styleable.RulerNumberLayout_kgUnitText);
        if (text != null){
            mUnitText = text;
        }
        typedArray.recycle();
    }

    private void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.layout_kg_number,this);
        tv_scale = findViewById(R.id.tv_scale);
        tv_kg = findViewById(R.id.tv_kg);

        tv_scale.setTextSize(TypedValue.COMPLEX_UNIT_PX,mScaleTextSize);
        tv_scale.setTextColor(mScaleTextColor);

        tv_kg.setTextSize(TypedValue.COMPLEX_UNIT_PX,mKgTextSize);
        tv_kg.setTextColor(mKgTextColor);
        tv_kg.setText(mUnitText);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mTargetRuler != 0){
            View ruler = getRootView().findViewById(mTargetRuler);
            if (ruler instanceof BooHeeRuler){
                ((BooHeeRuler) ruler).addCallBack(this);
            }
        }
    }

    public void bindRuler(BooHeeRuler booHeeRuler){
        booHeeRuler.addCallBack(this);
    }

    @Override
    public void onScaleChanging(float scale) {
        tv_scale.setText(String.valueOf(scale / 10));
    }
}
