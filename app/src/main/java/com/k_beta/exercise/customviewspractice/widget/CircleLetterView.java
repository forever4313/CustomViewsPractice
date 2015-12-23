package com.k_beta.exercise.customviewspractice.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.k_beta.exercise.customviewspractice.R;

/**
 * Created by dongkai on 2015/12/2.
 */
public class CircleLetterView extends View {
    private static int DEFAULT_TITLE_COLOR = Color.WHITE;
    private static int DEFAULT_BACKGROUND_COLOR = Color.CYAN;
    private static final int DEFAULT_VIEW_SIZE = 96;
    private static float DEFAULT_TITLE_SIZE = 25f;
    private static String DEFAULT_TITLE = "A";

    private float letterTxtSize = DEFAULT_TITLE_SIZE;
    private int letterTxtColor = DEFAULT_TITLE_COLOR;
    private int letterBackgroundColor = DEFAULT_BACKGROUND_COLOR;
    private String letterContent = DEFAULT_TITLE;
    private Paint mLetterPaint,mBgPaint;
    private Typeface mFont = Typeface.defaultFromStyle(Typeface.NORMAL);
    private int mViewSize;

    public CircleLetterView(Context context) {
        super(context);
        initView(null,0);
    }

    public CircleLetterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs,0);
    }

    public CircleLetterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs,defStyleAttr);
    }

    private void initView(AttributeSet attrs, int defStyleAttr){
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CircleLetterView,defStyleAttr,0);
        if(array.hasValue(R.styleable.CircleLetterView_letter_txt_size)){
            letterTxtSize = array.getDimension(R.styleable.CircleLetterView_letter_txt_size,DEFAULT_TITLE_SIZE);
        }
        if(array.hasValue(R.styleable.CircleLetterView_letter_txt_color)){
            letterTxtColor = array.getInt(R.styleable.CircleLetterView_letter_txt_color, DEFAULT_TITLE_COLOR);
        }
        if(array.hasValue(R.styleable.CircleLetterView_letter_background_color)){
            letterBackgroundColor = array.getInt(R.styleable.CircleLetterView_letter_background_color, DEFAULT_BACKGROUND_COLOR);
        }
        if(array.hasValue(R.styleable.CircleLetterView_letter_txt)){
            letterContent = array.getString(R.styleable.CircleLetterView_letter_txt);
        }
        array.recycle();
        mLetterPaint = new Paint();
        mLetterPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mLetterPaint.setTypeface(mFont);
        mLetterPaint.setColor(letterTxtColor);
        mLetterPaint.setTextAlign(Paint.Align.CENTER);
        mLetterPaint.setLinearText(true);
        mLetterPaint.setTextSize(letterTxtSize);

        mBgPaint = new Paint();
        mBgPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setStyle(Paint.Style.FILL);
        mBgPaint.setColor(letterBackgroundColor);

    }

    private void invalidateTxtPaint(){
        mLetterPaint.setColor(letterTxtColor);
        mLetterPaint.setTextSize(letterTxtSize);
        mLetterPaint.setTypeface(mFont);
    }

    private void invalidateBgPaint(){
        mBgPaint.setColor(letterBackgroundColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = resolveSize(DEFAULT_VIEW_SIZE, widthMeasureSpec);
        int height = resolveSize(DEFAULT_VIEW_SIZE, heightMeasureSpec);
        mViewSize = Math.min(width - getPaddingLeft() - getPaddingRight(), height - getPaddingTop() - getPaddingBottom());

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF mInnerRectF = new RectF();
        mInnerRectF.set(0, 0,mViewSize, mViewSize);
        mInnerRectF.offset(getPaddingLeft(),getPaddingTop());

        float centerX = mInnerRectF.centerX();
        float centerY = mInnerRectF.centerY();

        int xPos = (int) centerX;
        int yPos = (int) (centerY - (mLetterPaint.descent() + mLetterPaint.ascent()) / 2);

        canvas.drawOval(mInnerRectF, mBgPaint);

        canvas.drawText(letterContent,
                xPos,
                yPos,
                mLetterPaint);
    }
    public void setTitleText(String title) {
        letterContent = title;
        invalidate();
    }

    public int getBackgroundColor() {
        return letterBackgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        letterBackgroundColor = backgroundColor;
        invalidateBgPaint();
    }

    public void setTitleSize(float titleSize) {
        letterTxtSize = titleSize;
        invalidateTxtPaint();
    }

    public void setTextTypeface(Typeface font){
        this.mFont = font;
        invalidateTxtPaint();
    }
}
