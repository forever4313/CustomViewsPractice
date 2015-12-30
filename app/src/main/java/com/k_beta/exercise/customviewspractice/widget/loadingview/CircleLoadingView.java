package com.k_beta.exercise.customviewspractice.widget.loadingview;

import android.animation.Animator;
import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.k_beta.exercise.customviewspractice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dongkai on 2015/12/29.
 */
public class CircleLoadingView extends View{
    private int loadingViewColor = 0;
    private Paint mPaint ;
    private static int DURATION = 1000;
    private static int rotation = 0;
    private static float scale = 1.0f;
    float circleWidth = 4;
    List<Animator> anims = new ArrayList<>();
    //default size
    private static final int DEFAULT_VIEW_SIZE =45;
    public CircleLoadingView(Context context) {
        super(context);
        init(null,0);
    }

    public CircleLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CircleLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    public CircleLoadingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, defStyleAttr);
    }

    public void setLoadingViewColor(int loadingViewColor) {
        this.loadingViewColor = loadingViewColor;
    }

    private void init(AttributeSet attrs, int defStyleRes) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.loadingViewStyle);
        loadingViewColor = a.getColor(R.styleable.loadingViewStyle_loadingColor, Color.WHITE);
        a.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(loadingViewColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(circleWidth);
        initAnimation();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = resolveSize(dp2px(DEFAULT_VIEW_SIZE), widthMeasureSpec);
        int height = resolveSize(dp2px(DEFAULT_VIEW_SIZE), heightMeasureSpec);
        setMeasuredDimension(width, height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int radius = (getWidth()-6)/2;
        int x = radius+3;
        int y = radius+3;
        canvas.save();
        canvas.translate(x, y);
        canvas.scale(scale, scale);
        canvas.rotate(rotation);
        RectF rec = new RectF(-radius,-radius, radius, radius);
        canvas.drawArc(rec,0,320,false,mPaint);
        canvas.restore();
    }

    protected void initAnimation() {
        ValueAnimator scaleAnim = ValueAnimator.ofFloat(1.0f, 0.25f,1.0f);
        scaleAnim.setDuration(DURATION);
        scaleAnim.setRepeatCount(-1);//always repeat
        scaleAnim.setInterpolator(new LinearInterpolator());
        scaleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                scale = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        scaleAnim.start();
        ValueAnimator rotateAnim = ValueAnimator.ofInt(0,360);
        rotateAnim.setDuration(DURATION+100);
        rotateAnim.setRepeatCount(-1);//always repeat
        rotateAnim.setInterpolator(new LinearInterpolator());
        rotateAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                rotation = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        rotateAnim.start();
        anims.add(scaleAnim);
        anims.add(rotateAnim);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        for(Animator ani:anims){
            if(ani.isRunning()){
                ani.end();
            }
        }
        anims.clear();
    }

    private int dp2px(int dpValue) {
        return (int) getContext().getResources().getDisplayMetrics().density * dpValue;
    }



}
