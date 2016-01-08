package com.k_beta.exercise.customviewspractice.widget.loadingview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.k_beta.exercise.customviewspractice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dongkai on 2016/1/8.
 */
public class LoadingView58 extends View {
    private int loadingViewColor = 0;
    private Paint mPaint ;
    private static int DURATION = 1000;
    private static int rotation = 0;
    private static float transation = 1;
    private int repeatCount =0;
    private int width1 = 30;
    float circleWidth = 4;
    List<Animator> anims = new ArrayList<>();
    boolean shouldChange =true;
    private static final int SWEEP_ALL = 330;
    //default size
    private int start = 0;
    private int sweep = 60;
    private static final int DEFAULT_VIEW_SIZE =90;
    public LoadingView58(Context context) {
        super(context);
        init(null,0);
    }

    public LoadingView58(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public LoadingView58(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    public LoadingView58(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
        width1 = dp2px(30);
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
//        int radius = (getWidth()-6)/2;
        int x = dp2px(45);
        int y = (int) ( transation);
        canvas.save();
        canvas.translate(x, y);
        //canvas.scale(transation, transation);
       canvas.rotate(rotation);
        mPaint.setStyle(Paint.Style.FILL);
        switch (repeatCount%3){
            case 0:{
                mPaint.setColor(Color.BLACK);
                canvas.drawCircle(0, 0, dp2px(15), mPaint);
                break;
            }
            case 1:{
                mPaint.setColor(Color.WHITE);
                canvas.drawRect(-dp2px(15), -dp2px(15), dp2px(15), dp2px(15), mPaint);// 正方形
//                canvas.drawCircle(0, 0, dp2px(15), mPaint);
                break;
            }
            case 2:{
                mPaint.setColor(Color.BLUE);
                Path path = new Path();
                path.moveTo(0, -dp2px(15));// 此点为多边形的起点
                path.lineTo(-dp2px(13), dp2px(10));
                path.lineTo(dp2px(13), dp2px(10));
                path.close(); // 使这些点构成封闭的多边形
                canvas.drawPath(path, mPaint);
                break;
            }
        }
        canvas.restore();
    }

    protected void initAnimation() {
        ValueAnimator scaleAnim = ValueAnimator.ofFloat(dp2px(20), dp2px(180), dp2px(20));
        scaleAnim.setDuration(DURATION);
        scaleAnim.setRepeatCount(-1);//always repeat
        scaleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                transation = (float) animation.getAnimatedValue();
                if (animation.getAnimatedFraction() >= 0.5f && shouldChange) {
                    repeatCount++;
                    repeatCount = repeatCount % 3;
                    shouldChange = false;
                } else if(animation.getAnimatedFraction() < 0.5f){
                    shouldChange = true;

                }
                postInvalidate();
            }
        });

        scaleAnim.start();
        ValueAnimator rotateAnim = ValueAnimator.ofInt(0, 360);
        rotateAnim.setDuration(DURATION);
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
