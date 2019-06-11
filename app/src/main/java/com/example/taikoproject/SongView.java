package com.example.taikoproject;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SongView extends View {

    private Song song;

    public SongView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SongView(Context context, Song song) {
        super(context);
        this.song = song;
    }


    public boolean playSong() {
        TimerTask task = new TimerTask() {
            int counter = 0;
            @Override
            public void run() {
                System.out.println(song.getSongData().get(counter));
                if (counter >= song.getSongData().size()) {
                    cancel();
                }
            }
        };

        Timer timer = new Timer();
        System.out.println(song.getSongData().toString() + " " + song.getTempo());
        long delay = 60/song.getTempo() * 1000 * 8;
        long intevalPeriod = 60/song.getTempo() * 1000;
        timer.scheduleAtFixedRate(task, delay, intevalPeriod);

        return true;
    }


}
