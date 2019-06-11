package com.example.taikoproject;

import java.io.File;

public class TestMain {
    public static void main(String args[]) {
        File file = new File("C:\\Users\\elena\\AndroidStudioProjects\\taiko-project\\app\\src\\main\\java\\com\\example\\taikoproject\\songdata\\song1.csv");
        Song song = new Song(file);
        song.readSong();
    }
}
