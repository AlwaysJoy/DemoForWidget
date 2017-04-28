package com.example.a1311311.demoforwidget.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;

import java.util.Random;

/**
 * Created by 1311311 on 2017/4/26.╰(￣▽￣)╮
 */

public class PlanetsView extends View implements Runnable {

    private int STAR_SIZE = 30;
    private int PLANET_SIZE = 10;
    private int START_SPACING = 40;
    private float SPACING_DIFFUSE_SCALE = 1.5f;
    public static final int FLUSH_RATE = 40;
    private SparseArray<Planet> mPlanets;


    public PlanetsView(Context context) {
        super(context);
        init();
    }

    private void init() {
        setBackgroundColor(Color.BLACK);
        mPlanets = new SparseArray<>();
    }

    public PlanetsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PlanetsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        int maxDrawSize = Math.max(width, height) / 2;
        Paint paint = new Paint();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        float drawSize = 0;
        for (int i = 2; drawSize < maxDrawSize; i++) {
            drawSize = (float) (drawSize + START_SPACING * Math.pow(START_SPACING, (double)SPACING_DIFFUSE_SCALE / (double)15 * (double)i));
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.BLUE);
            paint.setStrokeWidth(3);
            canvas.drawArc(new RectF(width / 2 - drawSize, height / 2 - drawSize, width / 2 + drawSize, height / 2 + drawSize), 0, 360, false, paint);
            Planet planet = mPlanets.get(i);
            Random random = new Random();
            if (planet == null) {
                planet = new Planet();
                mPlanets.put(i, planet);
                planet.setSpeed(random.nextInt(80) + 30);
                planet.setRadius(PLANET_SIZE);
                planet.setDirection(random.nextBoolean());
                planet.setCurrentAngle(random.nextInt(360));
            }
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(planet.getColor());
            if (planet.isDirection())
                planet.setCurrentAngle(planet.getCurrentAngle() + planet.getSpeed() / (1500d * i));
            else
                planet.setCurrentAngle(planet.getCurrentAngle() - planet.getSpeed() / (1500d * i));
            int planetWidth = (int) (Math.cos(planet.getCurrentAngle()) * drawSize);
            int planetHeight = (int) (Math.sin(planet.getCurrentAngle()) * drawSize);
            canvas.drawCircle(width / 2 + planetWidth, height / 2 + planetHeight, planet.getRadius(), paint);
            postDelayed(this, FLUSH_RATE);
        }
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(Color.YELLOW);
        canvas.drawCircle(width / 2, height / 2, STAR_SIZE, paint);
    }

    @Override
    public void run() {
        removeCallbacks(this);
        invalidate();
    }

    class Planet {
        int speed = 5;
        int radius = 8;
        int color = Color.WHITE;
        boolean direction = true;
        double currentAngle = 0;

        public double getCurrentAngle() {
            return currentAngle;
        }

        public void setCurrentAngle(double currentAngle) {
            this.currentAngle = currentAngle;
        }

        public int getSpeed() {
            return speed;
        }

        public void setSpeed(int speed) {
            this.speed = speed;
        }

        public int getRadius() {
            return radius;
        }

        public void setRadius(int radius) {
            this.radius = radius;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public boolean isDirection() {
            return direction;
        }

        public void setDirection(boolean direction) {
            this.direction = direction;
        }
    }
}
