package com.example.taikoproject;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testSong();
            }
        });
    }

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
}
