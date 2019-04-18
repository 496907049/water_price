package com.ffapp.waterprice.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

public class ScanView extends View {

    private static final String TAG = "ScanView";

    /**
     * 画圆的笔
     */
    private Paint circlePaint;

    /**
     * 画扇形渲染的笔
     */
    private Paint sectorPaint;

    /**
     * 扫描线程
     */
    private ScanThread mThread;

    /**
     * 线程进行标志
     */
    private boolean threadFlag = false;

    /**
     * 线程启动标志
     */
    private boolean start = false;

    /**
     * 扇形转动的角度
     */
    private int angle = 0;

    /**
     * 当前视图的宽高，这里相同
     */
    private int viewSize;

    /**
     * 画在view中间的图片
     */
//    private Bitmap bitmap;

    /**
     * 对图形进行处理的矩阵类
     */
    private Matrix matrix;

    public ScanView(Context context) {
        this(context, null);
    }

    public ScanView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 此处设置viewSize固定值为600
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewSize = 460;
        setMeasuredDimension(viewSize, viewSize);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint mPaint = new Paint();
//        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        Rect rectd = new Rect((viewSize / 10) * 4, (viewSize / 10) * 4, viewSize - (viewSize / 10) * 4, viewSize - (viewSize / 10) * 4);
//        canvas.drawBitmap(bitmap, rect, rectd, mPaint);

        circlePaint = new Paint();
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setAntiAlias(true);
//        circlePaint.setStrokeWidth((viewSize / 10));
        circlePaint.setStrokeWidth(2);
        int radiusPer = viewSize/14;

        circlePaint.setColor(Color.parseColor("#accbfa"));
        canvas.drawCircle(viewSize / 2, viewSize / 2, radiusPer * 1, circlePaint);
        canvas.drawCircle(viewSize / 2, viewSize / 2, radiusPer * 2, circlePaint);
        canvas.drawCircle(viewSize / 2, viewSize / 2, radiusPer * 3, circlePaint);
        canvas.drawCircle(viewSize / 2, viewSize / 2, radiusPer * 4, circlePaint);
        canvas.drawCircle(viewSize / 2, viewSize / 2, radiusPer * 5, circlePaint);
        canvas.drawCircle(viewSize / 2, viewSize / 2, radiusPer * 6, circlePaint);
//        canvas.drawCircle(viewSize / 2, viewSize / 2, radiusPer * 4f, circlePaint);

        canvas.drawLine(viewSize/2,0,viewSize/2,viewSize,circlePaint);
        canvas.drawLine(0,viewSize/2,viewSize,viewSize/2,circlePaint);
        sectorPaint = new Paint();
        sectorPaint.setAntiAlias(true);
        sectorPaint.setStyle(Paint.Style.FILL);
//        sectorPaint.setStrokeWidth((viewSize / 10) * 3);
//        Shader sectorShader = new SweepGradient(viewSize / 2, viewSize / 2, new int[]{Color.TRANSPARENT, 0xFF418BFF, 0xFFFFFF},
//                new float[]{0, 0.875f, 1f});
        Shader sectorShader = new SweepGradient(viewSize / 2, viewSize / 2, new int[]{ 0xFF418BFF,0x4D418BFF, 0xFFFFFFFF},
                new float[]{0f, 0.16f, 1f});
        sectorPaint.setShader(sectorShader);

        if (threadFlag) {
            canvas.concat(matrix);
            canvas.drawCircle(viewSize / 2, viewSize / 2, viewSize/2, sectorPaint);
        }else{
            canvas.concat(matrix);
            canvas.drawCircle(viewSize / 2, viewSize / 2, viewSize/2, sectorPaint);
        }
    }


    public void start() {
        mThread = new ScanThread(this);
        mThread.start();
        threadFlag = true;
        start = true;
    }

    public void stop() {
        if (start) {
            threadFlag = false;
            start = false;
            invalidate();
        }
    }

    public boolean isStart(){
        return  start;
    }


    class ScanThread extends Thread {

        private ScanView view;

        public ScanThread(ScanView view) {
            this.view = view;
        }

        @Override
        public void run() {
            super.run();
            while (threadFlag) {
                if (start) {
                    view.post(new Runnable() {
                        @Override
                        public void run() {
                            angle = angle + 5;
                            if (angle == 360) {
                                angle = 0;
                            }
                            matrix = new Matrix();
                            matrix.setRotate(angle, viewSize / 2, viewSize / 2);
                            view.invalidate();
                        }
                    });
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
