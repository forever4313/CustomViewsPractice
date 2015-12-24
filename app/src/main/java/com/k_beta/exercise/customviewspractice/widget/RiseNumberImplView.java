package com.k_beta.exercise.customviewspractice.widget;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.k_beta.exercise.customviewspractice.utils.StringUtils;
import com.k_beta.exercise.customviewspractice.widget.interf.RiseNumberEndListener;

/**
 * Created by dongkai on 2015/12/23.
 */
public class RiseNumberImplView extends TextView implements  IRiseNumberBase{
    private static final int TYPE_INT = 1;
    private static final int TYPE_FLOAT = 2;
    private static final int STATUS_STOPED = 0;
    private static final int STATUS_RUNNING = 1;
    private long duration;
    private boolean flag;//是否显示逗号分隔
    private double endNumber;
    private RiseNumberEndListener endListener;
    private double fromNubmer;
    private int type =TYPE_INT;
    private int status = STATUS_STOPED;

    public RiseNumberImplView(Context context) {
        super(context);
    }

    public RiseNumberImplView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RiseNumberImplView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RiseNumberImplView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public void start() {
        if(!isRunning()){
            status = STATUS_RUNNING;
            if(type == TYPE_INT){
                runInt();
            }else{
                runFloat();
            }
        }
    }
    public boolean isRunning() {
        return (status == STATUS_RUNNING);
    }
    private void runInt(){
        ValueAnimator animator = ValueAnimator.ofInt((int)fromNubmer,(int)endNumber);
        animator.setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setText(animation.getAnimatedValue().toString());
                System.out.println(animation.getAnimatedValue().toString() + "========");
                if (animation.getAnimatedFraction() >= 1) {
                    status = STATUS_STOPED;
                    if (endListener != null) {
                        endListener.onEndFinish();
                    }
                }
            }
        });
        animator.start();
    }
    private void runFloat(){
        ValueAnimator animator = ValueAnimator.ofObject(new PointEvaluator(),fromNubmer,  endNumber);
        animator.setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                if (flag) {
                    String txt = StringUtils.formatFloat("#,###.00").format(Double.parseDouble(animation.getAnimatedValue().toString()));
                    if(animation.getAnimatedValue().toString().compareTo(String.valueOf(endNumber))>=0){
                        txt = StringUtils.formatFloat("#,###.00").format(Double.parseDouble(String.valueOf(endNumber)));
                    }
                    setText(txt);
                }else {
                    String txt = StringUtils.formatFloat("###.00").format(Double.parseDouble(animation.getAnimatedValue().toString()));
                    if(animation.getAnimatedValue().toString().compareTo(String.valueOf(endNumber))>=0){
                        txt = StringUtils.formatFloat("###.00").format(Double.parseDouble(String.valueOf(endNumber)));
                    }
                    setText(txt);
                }
                if(animation.getAnimatedFraction() >=1){
                    status = STATUS_STOPED;
                    if(endListener != null){
                        endListener.onEndFinish();
                    }
                }
            }
        });
        animator.start();

    }

    public class PointEvaluator implements TypeEvaluator {

        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            double start = (double) startValue;
            double end = (double) endValue;
            double middle = start + fraction * (end - start);
            return middle;
        }

    }

    @Override
    public IRiseNumberBase setNumbers(double fromNubmer, double endNumber) {
        this.fromNubmer = fromNubmer;
        this.endNumber = endNumber;
        this.type = TYPE_FLOAT;
        return this;
    }

    @Override
    public IRiseNumberBase setNumbers(int fromNubmer, int endNubmer) {
        this.endNumber = endNubmer;
        this.fromNubmer = fromNubmer;
        return this;
    }

    @Override
    public IRiseNumberBase setNumbers(double fromNubmer, double endNumber, boolean flag) {
        setNumbers(fromNubmer,endNumber);
        this.flag = flag;
        return this;
    }

    @Override
    public IRiseNumberBase setDuration(long duration) {
        this.duration = duration;
        return this;
    }


    @Override
    public void setOnEndListener(RiseNumberEndListener callBack) {
        this.endListener = callBack;
    }
}
