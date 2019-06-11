package com.example.taikoproject;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class AccuracyMeterView extends View {
    public AccuracyMeterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRGB(0, 0, 225);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = (Resources.getSystem().getDisplayMetrics().widthPixels)/2;
        int height = 400;
        setMeasuredDimension(width, height);
    }
}


