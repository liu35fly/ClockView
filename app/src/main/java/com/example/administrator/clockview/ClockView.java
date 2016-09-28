package com.example.administrator.clockview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import static com.example.administrator.clockview.Utils.dip2px;

/**
 * Purpose     :
 * Description :
 * Author      : FLY
 * Date        : 2016.09.12 13:43
 */

public class ClockView extends View {

    private int mWidth, mHeigh;
    private Paint mPaint;
    private Context context;
    //边距
    private int padding;
    //圆环宽度
    private int ringWidth;

    private float pointX = -1F, pointY = -1F;

    public ClockView(Context context) {
        this(context, null);
    }

    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        mHeigh = widthSize / 2;
        mWidth = widthSize;
        setMeasuredDimension(mWidth, mHeigh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initPaint();
        drawArc(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                pointX = event.getX();
//                pointY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:

                pointX = event.getX();
                pointY = event.getY();
                if (pointY > mHeigh) {
                    pointY = mHeigh;
                } else if (pointY < 0) {
                    pointY = 0;
                }
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                pointX = event.getX();
                pointY = event.getY();
                if (pointY > mHeigh) {
                    pointY = mHeigh;
                } else if (pointY < 0) {
                    pointY = 0;
                }
                postInvalidate();
                break;
        }


        return true;
    }

    /**
     * 画表盘
     *
     * @param canvas
     */
    private void drawArc(Canvas canvas) {
        ringWidth = dip2px(context, 40);
        padding = dip2px(context, 20);
        //绘制内圆
//        mPaint.setARGB(155, 167, 190, 206);
//        mPaint.setStrokeWidth(2);
//        canvas.drawCircle(mHeigh, mHeigh, mHeigh - ringWidth-padding, mPaint);

        //绘制圆环
//        mPaint.setARGB(255, 212, 225, 233);
//        mPaint.setStrokeWidth(ringWidth);
//        canvas.drawCircle(mHeigh, mHeigh, mHeigh - ringWidth + 1 + ringWidth / 2-padding, mPaint);


        //绘制外圆
//        mPaint.setARGB(155, 167, 190, 206);
//        mPaint.setStrokeWidth(2);
//        canvas.drawCircle(mHeigh, mHeigh, mHeigh-padding, mPaint);

        /* 设置渐变色 这个正方形的颜色是改变的 */
        Shader mShader = new LinearGradient(0, 0, 100, 100,
                new int[]{Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW,
                        Color.LTGRAY}, null, Shader.TileMode.REPEAT); // 一个材质,打造出一个线性梯度沿著一条线。
//        mPaint.setShader(mShader);0
        //画弧
        mPaint.setARGB(255, 212, 225, 233);
        mPaint.setStrokeWidth(ringWidth);
        RectF oval1 = new RectF(padding, padding, mHeigh * 2 - padding, mHeigh * 2 - padding);
        canvas.drawArc(oval1, 180, 60, false, mPaint);//小弧形

//        mPaint.setARGB(255, 125, 125, 233);
        mPaint.setStrokeWidth(ringWidth);
        mPaint.setShader(mShader);
        canvas.drawArc(oval1, 240, 60, false, mPaint);//小弧形

        mPaint.setARGB(255, 212, 225, 233);
        mPaint.setShader(null);
        mPaint.setStrokeWidth(ringWidth);
        canvas.drawArc(oval1, 300, 60, false, mPaint);//小弧形

        drawLines(canvas);
        drawLine(canvas);
        getAngle();

        //裁剪
//        Rect rect;
//
//        canvas.save();
//        rect=new Rect(0,0,mHeigh/2,mHeigh);
//        mPaint.setARGB(255, 212, 225, 233);
//        mPaint.setStrokeWidth(ringWidth);
//        canvas.clipRect(rect);
//        canvas.drawCircle(mHeigh, mHeigh, mHeigh - ringWidth + 1 + ringWidth / 2-padding, mPaint);
//        canvas.restore();
//
//        canvas.save();
//        rect=new Rect(mHeigh/2,0,mHeigh/2*3,mHeigh);
//        mPaint.setARGB(255, 125, 125, 233);
//        mPaint.setStrokeWidth(ringWidth);
//        canvas.clipRect(rect);
//        canvas.drawCircle(mHeigh, mHeigh, mHeigh - ringWidth + 1 + ringWidth / 2-padding, mPaint);
//        canvas.restore();
//
//        canvas.save();
//        rect=new Rect(mHeigh/2*3,0,mHeigh*2,2*mHeigh);
//        mPaint.setARGB(255, 212, 225, 233);
//        mPaint.setStrokeWidth(ringWidth);
//        canvas.clipRect(rect);
//        canvas.drawCircle(mHeigh, mHeigh, mHeigh - ringWidth + 1 + ringWidth / 2-padding, mPaint);
//        canvas.restore();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true); //消除锯齿
        mPaint.setStyle(Paint.Style.STROKE); //绘制空心圆
    }

    /**
     * 画刻度
     *
     * @param canvas
     */
    private void drawLines(Canvas canvas) {
        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(5);

        for (int i = 0; i < 19; i++) {
            //刻度的长度
            int h = mHeigh - ringWidth / 3;
            if (i % 2 == 1) {
                h = mHeigh - ringWidth / 3 * 2;
            }
            double c = Math.cos(10 * i * Math.PI / 180);
            double s = Math.sin(10 * i * Math.PI / 180);
            canvas.drawLine((float) (mHeigh * (1 + c)), (float) (mHeigh * (1 - s)),
                    (float) (mHeigh + ((h) * c)), (float) (mHeigh - ((h) * s)), linePaint);
        }
    }

    /**
     * 画指针
     *
     * @param canvas
     */
    private void drawLine(Canvas canvas) {
        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(5);
        if (pointX == -1) {
            canvas.drawLine(mHeigh, mHeigh, mHeigh, 0, linePaint);
            return;
        }
        float pointXCopy = pointX;
        if (pointX > mHeigh) {
            pointXCopy = pointX - mHeigh;
        } else {
            pointXCopy = mHeigh - pointX;
        }
        pointY = mHeigh - pointY;

        double x1 = mHeigh * pointXCopy / (Math.sqrt(pointXCopy * pointXCopy + pointY * pointY));
        double y1 = mHeigh * pointY / (Math.sqrt(pointXCopy * pointXCopy + pointY * pointY));
        if (pointX > mHeigh) {
            x1 += mHeigh;
        } else {
            x1 = mHeigh - x1;
        }
        y1 = mHeigh - y1;

        if (y1 > mHeigh) {
            y1 = mHeigh;
        }

        canvas.drawLine(mHeigh, mHeigh, (float) x1, (float) y1, linePaint);
    }

    /**
     * 获取角度
     */
    private void getAngle() {

        double angle = 0;
        if (pointX == -1) {
            return;
        }

        if (pointX < mHeigh) {
            angle = Math.asin((mHeigh - pointX) / mHeigh) * 180 / Math.PI;
            Log.e("test", "左偏 " + angle + " 度");
            return;
        }
        angle = Math.asin((pointX - mHeigh) / mHeigh) * 180 / Math.PI;
        Log.e("test", "右偏 " + angle + " 度");


    }


}
