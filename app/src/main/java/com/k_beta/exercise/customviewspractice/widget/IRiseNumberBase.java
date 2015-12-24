package com.k_beta.exercise.customviewspractice.widget;

import com.k_beta.exercise.customviewspractice.widget.interf.RiseNumberEndListener;

/**
 * Created by dongkai on 2015/12/23.
 */
public interface IRiseNumberBase {

    void start();
    IRiseNumberBase setNumbers(double number,double endNumber);
    IRiseNumberBase setNumbers(int number,int endNubmer);
    IRiseNumberBase setNumbers(double number,double endNumber,boolean flag);
    IRiseNumberBase setDuration(long duration);
    void setOnEndListener(RiseNumberEndListener callBack);

}
