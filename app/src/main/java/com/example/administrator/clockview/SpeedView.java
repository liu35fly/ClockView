package com.example.administrator.clockview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Purpose     :
 * Description :
 * Author      : FLY
 * Date        : 2016.09.13 15:26
 */

public class SpeedView extends View {

    private Context context;
    private int padding = 0;
    private float pointTop;

    private int[] linesTop = new int[5];
    private int[] grade = {0, 25, 50, 75, 100};

    public SpeedView(Context context) {
        this(context, null);
    }

    public SpeedView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initData();
    }

    public SpeedView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(Utils.dip2px(context, 100), Utils.dip2px(context, 120));
    }

    private void initData() {
        for (int i = 0; i < grade.length; i++) {
            linesTop[i] = Utils.dip2px(context, grade[i]);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawRect(canvas);
        drawLines(canvas);
        drawHand(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                pointTop = event.getY() - Utils.dip2px(context, 10);
                if (pointTop > Utils.dip2px(context, 100)) {
                    pointTop = Utils.dip2px(context, 100);
                } else if (pointTop < 0) {
                    pointTop = 0;
                }
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:

                pointTop = event.getY() - Utils.dip2px(context, 10);
                if (pointTop > Utils.dip2px(context, 100)) {
                    pointTop = Utils.dip2px(context, 100);
                } else if (pointTop < 0) {
                    pointTop = 0;
                }

                float min = 0;
                int num = 0;
                float[] lines = new float[linesTop.length];
                for (int i = 0; i < linesTop.length; i++) {
                    lines[i] = Math.abs(linesTop[i] - pointTop);
                }
                for (int i = 0; i < lines.length ; i++) {
                    if (i == 0) {
                        min = lines[0];
                    }
                    if (lines[i] < min) {
                        min = lines[i];
                        num = i;
                    }
                }
                pointTop =  linesTop[num];
                postInvalidate();

                break;
        }
        return true;
    }

    /**
     * 画框
     *
     * @param canvas
     */
    private void drawRect(Canvas canvas) {
        padding = Utils.dip2px(context, 5);
        Paint paint = new Paint();
        paint.setStrokeWidth(5);
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);

        canvas.drawRect(Utils.dip2px(context, 15), Utils.dip2px(context, 10), Utils.dip2px(context, 45), Utils.dip2px(context, 110), paint);
    }

    /**
     * 画刻度及数字
     *
     * @param canvas
     */
    private void drawLines(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStrokeWidth(5);
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        Paint paintText = new Paint();
        paintText.setColor(Color.BLACK);
        paintText.setAntiAlias(true);
        paintText.setTextSize(40);

        for (int i = 0; i < 5; i++) {
            canvas.drawLine(Utils.dip2px(context, 60), Utils.dip2px(context, i * 25 + 10), Utils.dip2px(context, 85), Utils.dip2px(context, i * 25 + 10), paint);
            canvas.drawText(String.valueOf(i + 1), Utils.dip2px(context, 90), Utils.dip2px(context, i * 25 + 15), paintText);
        }
    }

    /**
     * 画手柄
     *
     * @param canvas
     */
    private void drawHand(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStrokeWidth(5);
        paint.setColor(Color.GREEN);
        paint.setAntiAlias(true);
        canvas.drawRect(Utils.dip2px(context, 5), pointTop, Utils.dip2px(context, 55), pointTop + Utils.dip2px(context, 20), paint);
    }
}
