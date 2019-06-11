package com.example.taikoproject;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import java.io.File;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View songView = findViewById(R.id.songView);
        File file = new File("C:\\Users\\elena\\AndroidStudioProjects\\taiko-project\\app\\src\\main\\java\\com\\example\\taikoproject\\songdata\\song1.csv");
        System.out.println(file);
        Song song = new Song(file);
        songView = new SongView(this, song);
    }
}
