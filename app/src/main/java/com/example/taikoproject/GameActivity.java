package com.example.taikoproject;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout);
        InputStream is = getResources().openRawResource(R.raw.song1);
        Song song = new Song(is);
        song.readSong();
        SongView songView = new SongView(this, null, song);
        AccuracyMeterView accuracyMeterView = new AccuracyMeterView(this, null);
        layout.addView(songView);
        layout.addView(accuracyMeterView);

        songView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getApplicationContext();
                CharSequence text = "Element found";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
    }
    /*
    private void testSong() {
        View songView = findViewById(R.id.songView);
        InputStream is = getResources().openRawResource(R.raw.song1);
        Song song = new Song(is);

        if (song.readSong()) {
            songView = new SongView(this, song);
            ((SongView) songView).playSong();
        } else {
            System.out.println("Failed ;-;");
        }
    }
    */
}
