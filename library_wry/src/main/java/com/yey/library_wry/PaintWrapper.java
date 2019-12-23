package com.yey.library_wry;

import android.graphics.Paint;

public class PaintWrapper {
    //圆心
    private int mCircleX;
    private int mCircleY;
    //半径,画笔得宽度也是从半径得来
    private int mCircleRadius;
    //画笔
    private Paint mPaint;

    public int getmCircleX() {
        return mCircleX;
    }

    public void setmCircleX(int mCircleX) {
        this.mCircleX = mCircleX;
    }

    public int getmCircleY() {
        return mCircleY;
    }

    public void setmCircleY(int mCircleY) {
        this.mCircleY = mCircleY;
    }

    public int getmCircleRadius() {
        return mCircleRadius;
    }

    public void setmCircleRadius(int mCircleRadius) {
        this.mCircleRadius = mCircleRadius;
    }

    public Paint getmPaint() {
        return mPaint;
    }

    public void setmPaint(Paint mPaint) {
        this.mPaint = mPaint;
    }
}
