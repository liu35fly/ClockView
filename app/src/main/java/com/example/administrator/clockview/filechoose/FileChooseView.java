package com.example.administrator.clockview.filechoose;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ConfigurationHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import com.example.administrator.clockview.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Purpose     :
 * Description :
 * Author      : FLY
 * Date        : 2016.09.26 11:51
 */

public class FileChooseView extends View {

    private String TAG = FileChooseView.class.getSimpleName();

    private float lineHeigh = 50F;
    private float lineWidth = 30F;
    private float screenWidth = 0F;
    private float screenHeigh = 1800F;
    private LinkedList<FileModule> fileList = new LinkedList<>();
    private int widthMeasureSpec, heightMeasureSpec;

    private int floor = 0;
    private int lineNumber;
    private Context context;

    public FileChooseView(Context context) {
        this(context, null);
    }

    public FileChooseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public FileChooseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.widthMeasureSpec = widthMeasureSpec;
        this.heightMeasureSpec = heightMeasureSpec;
        screenWidth = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), (int) screenHeigh);
    }

    private void init() {
        lineHeigh = Utils.dip2px(context, lineHeigh);
        lineWidth = Utils.dip2px(context, lineWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e(TAG, "THIS IS ONDRAW");
        emptyData(canvas);
        Log.e(TAG, "THIS IS ONDRAW");
    }

    /**
     * 获取文件目录
     *
     * @param rootFilePath
     */
    public void setFileList(@NonNull String rootFilePath) {
        File file = new File(rootFilePath);
        String[] list = file.list();
        if (list == null || list.length == 0) {
            Log.e(TAG, "文件夹为空");
            return;
        }
        FileModule module = null;
        if (fileList.isEmpty()) {
            for (String name : list) {
                module = new FileModule(floor, name, rootFilePath);
                fileList.add(module);
            }
        } else {
            if (fileList.get(lineNumber).isOpen()) {
                for (int i = 0; i < list.length; i++) {
                    fileList.remove(lineNumber + 1);
                }
                fileList.get(lineNumber).setOpen(false);
                screenHeigh = lineHeigh * fileList.size() + Utils.dip2px(context, 50);
                /*
                会导致调用measure()过程 和 layout()过程 。
                说明：只是对View树重新布局layout过程包括measure()和layout()过程，不会调用draw()过程，
                但不会重新绘制任何视图包括该调用者本身。
                 */
                requestLayout();
                /*
                直接调用invalidate()方法，请求重新draw()，但只会绘制调用者本身
                 */
                invalidate();
                return;
            }
//            for (int j = 0; j < fileList.size(); j++) {
//                if (fileList.get(j).getName().equals(list[0])) {
//                    return;
//                }
//            }
            fileList.get(lineNumber).setOpen(true);
            for (int i = 0; i < list.length; i++) {
                module = new FileModule(floor, list[i], rootFilePath);
                fileList.add(lineNumber + 1 + i, module);
            }
        }
        screenHeigh = lineHeigh * fileList.size() + Utils.dip2px(context, 50);
        /*
        会导致调用measure()过程 和 layout()过程 。
        说明：只是对View树重新布局layout过程包括measure()和layout()过程，不会调用draw()过程，
        但不会重新绘制任何视图包括该调用者本身。
         */
        requestLayout();
        /*
        直接调用invalidate()方法，请求重新draw()，但只会绘制调用者本身
         */
        invalidate();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                clikItem(event.getX(), event.getY());
                break;
        }
        return true;
    }


    /**
     * 填入数据
     *
     * @param canvas
     */
    private void emptyData(Canvas canvas) {
        if (fileList.size() == 0) {
            return;
        }

        Paint paint = new Paint();
        paint.setTextSize(45);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);

        for (int i = 0; i < fileList.size(); i++) {
            canvas.drawText(fileList.get(i).getName(), fileList.get(i).getFloor() * lineWidth, i * lineHeigh + Utils.dip2px(context, 50), paint);
            canvas.drawRect(fileList.get(i).getFloor() * lineWidth, i * lineHeigh + Utils.dip2px(context, 60), screenWidth, i * lineHeigh + Utils.dip2px(context, 62), paint);

        }
    }

    private void clikItem(float x, float y) {
        if (y - Utils.dip2px(context, 50) < 0) {
            lineNumber = 0;
        } else {
            lineNumber = (int) ((y - Utils.dip2px(context, 50)) / lineHeigh) + 1;
        }
        Log.e(TAG, "==" + lineNumber);
        floor = fileList.get(lineNumber).getFloor();
        floor++;
        StringBuffer buffer = new StringBuffer();
        buffer.append(fileList.get(lineNumber).getFatherNname()).append("/").append(fileList.get(lineNumber).getName());
        setFileList(buffer.toString());
        Log.e(TAG, "file path is " + buffer.toString());
    }

    public int getFlieListSize() {
        return fileList.size();
    }

}
