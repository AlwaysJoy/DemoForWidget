package com.example.a1311311.demoforwidget.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by zhang hao on 2017/5/2.
 * ..--,       .--,
 * ( (  \.---./  ) )
 * .'.__/o   o\__.'
 * ....{=  ^  =}
 * ... .>  -  <
 * ..../       \
 * ...//       \\
 * ..//|       |\\
 * .."'\       /'"_.-~^`'-.
 * .....\  _  /--'
 * ...___)( )(___
 * ..(((__) (__)))
 */

public class TurnplateView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder mHolder;
    private Canvas mCanvas;
    private RectF mRectF;
    private int mMin;
    private Paint mPaint;
    private Thread mThread;
    private int mRadius;
    private int mPadding;
    private int[] mColors = new int[]{0XFFFFC0CB, 0XFFFF7F24};
    private String[] mResultStrs = new String[]{"AudioFlinger", "AudioALSA", "AudioPolicy", "Sensors", "StepState", "Network"};
    private float mVOffset = 100;

    public TurnplateView(Context context) {
        this(context, null);
    }

    public TurnplateView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TurnplateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mHolder = getHolder();
        mHolder.addCallback(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mMin = Math.min(getMeasuredWidth(), getMeasuredHeight());
        mPadding = getPaddingLeft();
        mRadius = mMin / 2 - mPadding;
        mRectF = new RectF(mPadding, mPadding, mMin - mPadding, mMin - mPadding);
        setMeasuredDimension(mMin, mMin);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mThread = new Thread(this);
        mThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void run() {
        mCanvas = mHolder.lockCanvas();
        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        mPaint.setColor(0xFFAA0000);
        mCanvas.drawCircle(mMin / 2, mMin / 2, mRadius, mPaint);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(5);
        for (int i = 0; i < mResultStrs.length; i++) {
            mPaint.setColor(mColors[i % mColors.length]);
            int angleOffset = 360 / mResultStrs.length;
            mCanvas.drawArc(mRectF, angleOffset * i, angleOffset, true, mPaint);
            Path path = new Path();
            path.arcTo(mRectF, angleOffset * i, angleOffset);
            mPaint.setColor(Color.BLACK);
            mPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20, getResources().getDisplayMetrics()));
            float textWidth = mPaint.measureText(mResultStrs[i]);
            float hOffset = (float) ((2 * Math.PI * (mRadius - mVOffset/2) / mResultStrs.length - textWidth) / 2);
            mCanvas.drawTextOnPath(mResultStrs[i], path, hOffset, mVOffset, mPaint);
        }

        mHolder.unlockCanvasAndPost(mCanvas);
    }
}
