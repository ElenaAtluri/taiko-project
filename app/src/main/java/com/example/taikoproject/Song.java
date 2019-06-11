package com.example.taikoproject;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Song {

    private int tempo;
    private boolean defaultTempo;
    private int numRepeats;
    private File file;
    private List<String> songData;

    public Song(File file, int tempo, int numRepeats) {
        this.tempo = tempo;
        defaultTempo = false;
        this.numRepeats = numRepeats;
        this.file = file;
        songData = new ArrayList<String>();

        readSong();
    }
    public Song(File file) {
        defaultTempo = true;
        this.numRepeats = 1;
        this.file = file;
        songData = new ArrayList<String>();

        readSong();
    }
    public boolean readSong(){
        BufferedReader reader = null;
        final String DELIMITER = ",";
        int lineNum = 0;

        try {
            String line = "";
            reader = new BufferedReader(new FileReader(file));

            while ((line = reader.readLine()) != null) {
                if (lineNum == 0 && defaultTempo) {
                    tempo = Integer.parseInt(line);
                }
                else {
                    songData.add(line);

                }
                lineNum++;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        for (String str : songData) {
            System.out.println(str);
        }
        return true;
    }

    /*
    public boolean playSong() {
        TimerTask task = new TimerTask() {
            int counter = 0;
            @Override
            public void run() {
                Log.i("tag",(String)songData.get((int)counter));
                counter ++;
                if (counter >= songData.size()) {
                    cancel();
                }
            }
        };
        Timer timer = new Timer();
        long delay = 0;
        long intervalPeriod = 60/tempo * 1000;
        timer.scheduleAtFixedRate(task, delay,	intervalPeriod);
        return true;
    }
    */

    public List<String> getSongData() {
        return songData;
    }

    public int getTempo() {
        return tempo;
    }
}
