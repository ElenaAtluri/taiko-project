package com.example.taikoproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.InputStream;

public class GameActivity extends AppCompatActivity {

    private SongView songView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout);
        InputStream is = getResources().openRawResource(R.raw.song1);
        songView = new SongView(this, null, new Song(is));
        AccuracyMeterView accuracyMeterView = new AccuracyMeterView(this, null);
        layout.addView(songView);
        layout.addView(accuracyMeterView);
        songView.playSong();

    }

}
