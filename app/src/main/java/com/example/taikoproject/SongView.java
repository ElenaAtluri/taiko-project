package com.example.taikoproject;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class SongView extends View {

    private Song song;

    public SongView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SongView(Context context, AttributeSet attrs, Song song) {
        super(context, attrs);
        this.song = song;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRGB(225, 0, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = (Resources.getSystem().getDisplayMetrics().widthPixels)/2;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels - 400;
        setMeasuredDimension(width, height);
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


}
