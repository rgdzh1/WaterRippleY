package com.yey.library_wry;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class WaterRippleY extends View {
    private ArrayList<PaintWrapper> mPaintWrappers = new ArrayList<>();
    private int[] mColors = new int[]{Color.CYAN, Color.GRAY, Color.YELLOW, Color.RED};
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            //当PaintWrapper集合不为空的时候,才进行绘制
            if (mPaintWrappers.size() > 0) {
                refreshPaintWrappers();
                invalidate();
                mHandler.sendEmptyMessageDelayed(0, 50);
            }
        }
    };
    private int mAlpha;
    private int mRadius;
    private float mPaintWidthRate;


    public WaterRippleY(Context context) {
        this(context, null);
    }

    public WaterRippleY(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public WaterRippleY(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParame(context, attrs, defStyleAttr);
    }


    @SuppressLint("ResourceAsColor")
    private void initParame(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WaterRippleY, defStyleAttr, 0);
        mAlpha = typedArray.getInteger(R.styleable.WaterRippleY_wry_alpha_reduce, 5);
        mRadius = typedArray.getInteger(R.styleable.WaterRippleY_wry_circle_radius_add, 5);
        mPaintWidthRate = typedArray.getFloat(R.styleable.WaterRippleY_wry_paint_width, 0.2f);

        typedArray.recycle();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制每一个圆环
        for (int i = 0; i < mPaintWrappers.size(); i++) {
            PaintWrapper paintWrapper = mPaintWrappers.get(i);
            canvas.drawCircle(paintWrapper.getmCircleX(), paintWrapper.getmCircleY(), paintWrapper.getmCircleRadius(), paintWrapper.getmPaint());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                int x = (int) event.getX();
                int y = (int) event.getY();
                addPaintWrapper(x, y);
                break;
        }
        return true;
    }

    /**
     * @param x 圆心X坐标
     * @param y 圆心Y坐标
     */
    private void addPaintWrapper(int x, int y) {
        //如果存储画笔包装类集合长度为0, 那么就代表是第一个Down事件
        //此时将画笔类加入集合, 并且开始绘制水波纹
        if (mPaintWrappers.size() == 0) {
            addList(x, y);
            //开始绘制
            mHandler.sendEmptyMessage(0);
        } else {
            //如果集合不为0, 那么取出来最后一个元素,最后一个元素所对应圆点的坐标
            //和当前手指触摸的坐标进行对比, 看位置是否符合,符合的话就加入集合中.
            PaintWrapper paintWrapper = mPaintWrappers.get(mPaintWrappers.size() - 1);
            //滑动最小距离
            int mTouchSlop = ViewConfiguration.get(this.getContext()).getScaledTouchSlop();
            int mDiffX = Math.abs(paintWrapper.getmCircleX() - x);
            int mDiffY = Math.abs(paintWrapper.getmCircleY() - y);
            if (mDiffX > mTouchSlop || mDiffY > mTouchSlop) {
                addList(x, y);
            }
        }
    }

    /**
     * 创建画笔的包装类并添加
     *
     * @param x
     * @param y
     */
    private void addList(int x, int y) {
        PaintWrapper paintWrapper = new PaintWrapper();
        paintWrapper.setmCircleX(x);
        paintWrapper.setmCircleY(y);
        Paint paint = new Paint();
        paint.setColor(mColors[(int) (Math.random() * mColors.length)]);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paintWrapper.setmPaint(paint);
        //       半径首先是0, 之后慢慢扩大
        //        paintWrapper.setmCircleRadius();
        mPaintWrappers.add(paintWrapper);
    }

    /**
     * 刷新paint参数,如透明度,画笔宽度
     */
    public void refreshPaintWrappers() {
        for (int i = 0; i < mPaintWrappers.size(); i++) {
            PaintWrapper paintWrapper = mPaintWrappers.get(i);
            //如果透明度为0,就移除该paintWrapper对象
            Paint paint = paintWrapper.getmPaint();
            int circleRadius = paintWrapper.getmCircleRadius();
            int alpha = paint.getAlpha();
            if (alpha == 0) {
                mPaintWrappers.remove(i);
                continue;
            }
            alpha -= mAlpha;
            alpha = alpha < 0 ? 0 : alpha;
            //重新设置透透明度
            paint.setAlpha(alpha);
            //圆半径扩大
            paintWrapper.setmCircleRadius(circleRadius + mRadius);
            //画笔宽度
            paint.setStrokeWidth(paintWrapper.getmCircleRadius() * mPaintWidthRate);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
    }

    /**
     * 设置水波纹的颜色
     *
     * @param mColors
     */
    public void setColors(int[] mColors) {
        this.mColors = mColors;
    }
}
