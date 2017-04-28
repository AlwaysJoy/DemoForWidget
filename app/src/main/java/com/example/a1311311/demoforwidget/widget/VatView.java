package com.example.a1311311.demoforwidget.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 1311311 on 2017/4/26.╰(￣▽￣)╮
 */

public class VatView extends View implements Runnable {

    private float mPresent = 0;
    private boolean isDirectionRight = true;
    private int mQuadX;
    private int mMoveSpeed = 10;

    public VatView(Context context) {
        super(context);
        init();
    }

    private void init() {

    }

    public VatView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VatView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        Paint paint = new Paint();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(0xFF66CD00);
        int cx = width / 2;
        int cy = height / 2;
        int radius = width / 4;
        canvas.drawCircle(cx, cy, radius, paint);
        float angle = (mPresent / 50 - 1) * 90;
        if (angle == 90) {
            angle = 89.99f;
        }
        float absY = (float) (Math.sin(angle * Math.PI / 180.0) * radius);
        float startY = (cy - absY);
        float absX = (float) Math.cos(angle * Math.PI / 180.0) * radius;
        float startX = cx + absX;
        paint.setColor(0xFFFF0000);
        paint.setStrokeWidth(10);
        canvas.drawPoint(startX, startY, paint);
        Path path = new Path();
        path.moveTo(startX, startY);
        path.arcTo(new RectF(cx - radius, cy - radius, cx + radius, cy + radius), 0 - angle, 180 + 2 * angle);
        refreshQuadX(2 * absX);
        path.quadTo(cx - absX + mQuadX, startY + (radius - Math.abs(absY)) / 3, startX, startY);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0x1166CD00);
        canvas.drawPath(path, paint);
        postDelayed(this, 40);
    }

    private void refreshQuadX(float absX) {
        if (isDirectionRight) {
            int newQuadX = mQuadX + mMoveSpeed;
            if (newQuadX > absX) {
                mQuadX -= mMoveSpeed;
                isDirectionRight = false;
            } else {
                mQuadX = newQuadX;
            }
        } else {
            int newQuadX = mQuadX - mMoveSpeed;
            if (newQuadX < 0) {
                mQuadX += mMoveSpeed;
                isDirectionRight = true;
            } else {
                mQuadX = newQuadX;
            }
        }
    }

    @Override
    public void run() {
        removeCallbacks(this);
        invalidate();
    }

    public void setPresent(int progress) {
        mQuadX = 0;
        mPresent = progress;
        invalidate();
    }
}
