package com.example.taikoproject;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import java.util.Timer;
import java.util.TimerTask;

public class SongView extends View {

    private Song song;
    private Paint linePaint;
    private int width, height;
    private int horizontalPadLeft, horizontalPadRight, verticalPadTop, verticalPadBottom;

    public SongView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SongView(Context context, AttributeSet attrs, Song song) {
        super(context, attrs);
        this.song = song;
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int numBeats = song.getTimeSig()[0];
        int cellHeight = (height-verticalPadTop-verticalPadBottom)/(numBeats*4);
        verticalPadTop += (height-verticalPadTop-verticalPadBottom)%(numBeats*4);
        int cellWidth = (width - 2*horizontalPadLeft)/4;
        horizontalPadLeft += (width - 2*horizontalPadLeft)%4;

        for (int i = 0; i < numBeats*4; i++) {
            canvas.drawRect(horizontalPadLeft, verticalPadTop + i*cellHeight, width-horizontalPadRight, verticalPadTop+(i+1)*cellHeight, linePaint);
        }
        for (int j = 0; j < 4; j++) {
           canvas.drawRect(horizontalPadLeft + j*cellWidth, verticalPadTop, horizontalPadLeft+(j+1)*cellWidth, height-verticalPadBottom, linePaint);
        }
//        canvas.drawRect(horizontalPadLeft, verticalPadTop, width-horizontalPadRight, height-verticalPadBottom, bluePaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = (Resources.getSystem().getDisplayMetrics().widthPixels)/2;
        height = Resources.getSystem().getDisplayMetrics().heightPixels - 400;
        horizontalPadLeft = 20;
        horizontalPadRight = 20;
        verticalPadTop = 40;
        verticalPadBottom = 220;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public boolean playSong() {
        TimerTask task = new TimerTask() {
            int counter = 0;
            @Override
            public void run() {
                System.out.println(song.getSongData().get(counter));
                if (++counter >= song.getSongData().size()) {
                    cancel();
                }
            }
        };

        Timer timer = new Timer();
        double delay = 60.0/song.getTempo() * 1000 * 8;
        double intervalPeriod = 60.0/song.getTempo() * 1000;
        long delayLong = (long)delay;
        long intervalPeriodLong = (long)intervalPeriod;
        timer.scheduleAtFixedRate(task, delayLong, intervalPeriodLong);

        return true;
    }

    private void init() {
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(Color.BLACK);
        linePaint.setStyle(Paint.Style.STROKE);
    }
}
