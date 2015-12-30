package com.k_beta.exercise.customviewspractice.widget.loadingview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.k_beta.exercise.customviewspractice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dongkai on 2015/12/29.
 */
public class ThreeDotLoadingView extends View{


    List<Animator> anims = new ArrayList<>();
    private int loadingViewColor = 0;
    private Paint mPaint ;
    private static int LOADING_ITEM_COUNT = 10;
    private static int DURATION = 700;
    private int[] delays = new int[LOADING_ITEM_COUNT];
    private float[] translateArray = new float[LOADING_ITEM_COUNT];
    //default size
    private static final int DEFAULT_VIEW_SIZE =45;
    public ThreeDotLoadingView(Context context) {
        super(context);
        init(null, 0);
    }

    public ThreeDotLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ThreeDotLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    public ThreeDotLoadingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
        mPaint.setStyle(Paint.Style.FILL);
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
        float circleSpace = 4;
        float radius = (getWidth() - circleSpace*(LOADING_ITEM_COUNT-1))/(LOADING_ITEM_COUNT*2);
        float x = (getWidth() - (radius*((LOADING_ITEM_COUNT-1)*2)+circleSpace*(LOADING_ITEM_COUNT-1)))/2;
        float y = getHeight()/2;
        for(int i = 0;i< LOADING_ITEM_COUNT;i++){
            canvas.save();
            float translateX  =x +(radius*2)*i + circleSpace*i;
            canvas.translate(translateX,y);
            canvas.scale(translateArray[i],translateArray[i]);
            canvas.drawCircle(0,0,radius,mPaint);
            canvas.restore();
        }
    }

    protected void initAnimation(){
        for(int i = 0;i< LOADING_ITEM_COUNT;i++){
            delays[i] = DURATION*i/LOADING_ITEM_COUNT;
            translateArray[i] = 1.0f;
        }
        for(int i = 0;i<LOADING_ITEM_COUNT;i++){
            ValueAnimator scaleAnim = ValueAnimator.ofFloat(1.0f,0.25f,1.0f);
            scaleAnim.setDuration(DURATION);
            scaleAnim.setStartDelay(delays[i]);
            scaleAnim.setRepeatCount(-1);//always repeat
            final int pos = i;
            scaleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    translateArray[pos] = (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            scaleAnim.start();
            anims.add(scaleAnim);
        }
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
