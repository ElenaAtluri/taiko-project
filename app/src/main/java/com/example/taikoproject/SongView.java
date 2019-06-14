package com.example.taikoproject;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.graphics.Color.argb;
import static android.graphics.Color.rgb;

public class SongView extends View {

    private Song song;
    private int width, height;
    private int horizontalPadLeft, horizontalPadRight, verticalPadTop, verticalPadBottom;
    private int cellHeight;
    private int currentBeat;
    private int currentRun;
    private List<HitObject> hitObjectList;

    private Paint linePaint;
    private Paint outlinePaint;
    private Paint redPaint;
    private Paint bluePaint;
    private Paint coverPaint;

    private GestureDetector detector;

    public final int BOLD = 4;

    public SongView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SongView(Context context, AttributeSet attrs, Song song) {
        super(context, attrs);
        this.song = song;
        currentBeat = -1;
        hitObjectList = new ArrayList<>();
        detector = new GestureDetector(SongView.this.getContext(), new TouchListener());
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int numBeats = song.getTimeSig()[0];
        cellHeight = (height-verticalPadTop-verticalPadBottom)/(numBeats*4);
        verticalPadTop += (height-verticalPadTop-verticalPadBottom)%(numBeats*4);
        int cellWidth = (width - 2*horizontalPadLeft)/4;
        horizontalPadLeft += (width - 2*horizontalPadLeft)%4;

        for (int i = 0; i < numBeats*4; i++) {
            canvas.drawRect(horizontalPadLeft, verticalPadTop + i*cellHeight, width-horizontalPadRight, verticalPadTop+(i+1)*cellHeight, linePaint);
        }
        for (int j = 0; j < 4; j++) {
           canvas.drawRect(horizontalPadLeft + j*cellWidth, verticalPadTop, horizontalPadLeft+(j+1)*cellWidth, height-verticalPadBottom, outlinePaint);
        }
        canvas.drawRect(horizontalPadLeft, verticalPadTop, width-horizontalPadRight, height-verticalPadBottom, outlinePaint);

        for (int k = 0; k < hitObjectList.size(); k ++) {
            HitObject hit = hitObjectList.get(k);
            if (hit.getY() > cellHeight*song.getTimeSig()[0]*3) {
                hit.setRadius((int)(hit.getRadius()*1.1));
            }
            if (hit.getY() > cellHeight*song.getTimeSig()[0]*4) {
                hit.setRadius((int)(hit.getRadius()*0.8));
            }
            if (hit.getY() <= cellHeight*song.getTimeSig()[0]*4.5) {
                switch (hit.getHitType()) {
                    case HitObject.LEFT_KA:
                        canvas.drawCircle((float) (horizontalPadLeft + cellWidth * (1 / 2.0)), hit.getY() + verticalPadTop, hit.getRadius(), createColorPaint(hit.getColor()));
                        break;
                    case HitObject.LEFT_DON:
                        canvas.drawCircle((float) (horizontalPadLeft + cellWidth * (3 / 2.0)), hit.getY() + verticalPadTop, hit.getRadius(), createColorPaint(hit.getColor()));
                        break
                    case HitObject.RIGHT_DON:
                        canvas.drawCircle((float) (horizontalPadLeft + cellWidth * (5.0 / 2)), hit.getY() + verticalPadTop, hit.getRadius(), createColorPaint(hit.getColor()));
                        break;
                    case HitObject.RIGHT_KA:
                        canvas.drawCircle((float) (horizontalPadLeft + cellWidth * (7.0 / 2)), hit.getY() + verticalPadTop, hit.getRadius(), createColorPaint(hit.getColor()));
                        break;
                    default:
                        break;
                }
            }
        }

//        canvas.drawRect(0, 0, width, verticalPadTop, coverPaint);

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

    class TouchListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return super.onSingleTapUp(e);
        }
    }

    public boolean playSong() {
        // delay 8 measure start time
        // for each num repeat, play song once
        // play song
        currentBeat = 0;
        currentRun = 1;
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // move down all created objects
                if (hitObjectList != null) {
                    for (HitObject hit : hitObjectList) {
                        hit.setY(hit.getY() + cellHeight);
                    }
                }
                if (currentBeat < song.getLineData().size()) {
                    String[] beatData = song.getLineData().get(currentBeat).split(",");
                    for (int i = 0; i < beatData.length; i++) {
                        if (Integer.parseInt(beatData[i]) == 1) {
                            HitObject hit = new HitObject(i, currentBeat);
                            System.out.println(i);
                            if (i == HitObject.LEFT_DON || i == HitObject.LEFT_KA) {
                                hit.setColor(rgb(154, 220, 223));
                            } else if (i == HitObject.RIGHT_DON || i == HitObject.RIGHT_KA) {
                                hit.setColor(rgb(252, 104, 114));
                            }
                            hit.setRadius((cellHeight/2));
                            System.out.println(hit.toString());
                            hit.setY(0);
                            hitObjectList.add(hit);
                        }
                    }
                    currentBeat ++;
                }
                postInvalidate();
                if (hitObjectList.get(hitObjectList.size()-1).getY() > cellHeight*song.getTimeSig()[0]*4.5 && currentRun >= song.getNumRepeats()) {
                    cancel();
                }
            }
        };
        long intervalPeriod = (long)(60.0/song.getTempo() * 1000);
        Timer timer = new Timer();
        timer.schedule(task, intervalPeriod, intervalPeriod);
        // delay 4 measure end time
        return true;
    }
    private void init() {
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(Color.BLACK);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(0);

        outlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outlinePaint.setColor(Color.BLACK);
        outlinePaint.setStyle(Paint.Style.STROKE);
        outlinePaint.setStrokeWidth(BOLD);

        redPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        redPaint.setColor(Color.RED);
        redPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        bluePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bluePaint.setColor(Color.BLUE);
        bluePaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    private Paint createColorPaint(int color) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        return paint;
    }
}
