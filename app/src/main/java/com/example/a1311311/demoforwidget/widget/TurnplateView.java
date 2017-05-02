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
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

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
    private int mCenterRadius = 80;
    private int mPadding;
    private int[] mColors = new int[]{0XFFFFC0CB, 0XFFFF7F24};
    private String[] mResultStrs = new String[]{"AudioFlinger", "AudioALSA", "AudioPolicy", "Sensors", "StepState", "Network"};
    private float mVOffset = 100;
    private float mSpeed = 30;
    private boolean isRunning;
    private boolean isPrepareToStop;
    private float mCurrentAngle;
    private String mCenterText = "开始";
    private int mAngleOffset = 360 / mResultStrs.length;

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
        try {
            boolean isChooseResult = false;
            draw(isChooseResult);
            if (isRunning) {
                while (true) {
                    long startTime = System.currentTimeMillis();
                    if (isPrepareToStop) {
                        mSpeed = mSpeed - 0.2f;
                        if (mSpeed <= 0) {
                            mSpeed = 0.8f;
                            isChooseResult = true;
                        }
                    }
                    boolean isFinish = isChooseResult && mCurrentAngle % (270 % mAngleOffset + mAngleOffset / 2) <= 0;
                    if (isFinish) {
                        isRunning = false;
                        mCenterText = "开始";
                        post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), "恭喜!" + mResultStrs[(int) ((mCurrentAngle + 90) / mAngleOffset)], Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    draw(isChooseResult);
                    if (isFinish) {
                        break;
                    }
                    long endTime = System.currentTimeMillis();
                    if ((endTime - startTime) < 30) {
                        try {
                            Thread.sleep(30 + startTime - endTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void draw(boolean isChooseResult) {
        mCanvas = mHolder.lockCanvas();
        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        mPaint.setColor(0xFFAA0000);
        mCanvas.drawCircle(mMin / 2, mMin / 2, mRadius, mPaint);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(5);
        float startAngle = 0;
        float endAngle = 0;
        if (isChooseResult) {
            startAngle = mCurrentAngle % (270 % mAngleOffset + mAngleOffset / 2);
        }
        mCurrentAngle += mSpeed;
        if (isChooseResult) {
            endAngle = mCurrentAngle % (270 % mAngleOffset + mAngleOffset / 2);
        }
        if (isChooseResult && endAngle < startAngle) {
            mCurrentAngle -= mSpeed;
            mCurrentAngle = (int) mCurrentAngle + 1 + (int) (mCurrentAngle + 1) % (270 % mAngleOffset + mAngleOffset / 2);
        }
        if (mCurrentAngle >= 360) {
            mCurrentAngle %= 360;
        }
        for (int i = 0; i < mResultStrs.length; i++) {
            mPaint.setColor(mColors[i % mColors.length]);
            mCanvas.drawArc(mRectF, mAngleOffset * i + mCurrentAngle, mAngleOffset, true, mPaint);
            Path path = new Path();
            path.arcTo(mRectF, mAngleOffset * i + mCurrentAngle, mAngleOffset);
            mPaint.setColor(Color.BLACK);
            mPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20, getResources().getDisplayMetrics()));
            float textWidth = mPaint.measureText(mResultStrs[i]);
            float hOffset = (float) ((2 * Math.PI * (mRadius - mVOffset / 2) / mResultStrs.length - textWidth) / 2);
            mCanvas.drawTextOnPath(mResultStrs[i], path, hOffset, mVOffset, mPaint);
        }
        mPaint.setColor(0xFFAA0000);
        mCanvas.drawCircle(mMin / 2, mMin / 2, mCenterRadius, mPaint);
        Path path = new Path();
        path.moveTo(mMin / 2 - 20, mMin / 2);
        path.rLineTo(40, 0);
        path.lineTo(mMin / 2, mMin / 2 - mRadius / 2);
        mCanvas.drawPath(path, mPaint);
        mPaint.setColor(0xFFFFFFFF);
        mPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15, getResources().getDisplayMetrics()));
        float centerTextWidth = mPaint.measureText(mCenterText);
        mCanvas.drawText(mCenterText, mMin / 2 - centerTextWidth / 2, mMin / 2 + centerTextWidth / 6, mPaint);
        mHolder.unlockCanvasAndPost(mCanvas);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int left = mPadding + mRadius - mCenterRadius;
                int top = left;
                int right = left + 2 * mCenterRadius;
                int bottom = right;
                float x = event.getX();
                float y = event.getY();
                if (x > left && x < right && y > top && y < bottom) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!isRunning) {
                    mSpeed = 30;
                    isPrepareToStop = false;
                    isRunning = true;
                    mCenterText = "停止";
                    mThread = new Thread(this);
                    mThread.start();
                } else {
                    isPrepareToStop = true;
                    mCenterText = "";
                }
                break;
        }
        return super.dispatchTouchEvent(event);
    }
}
