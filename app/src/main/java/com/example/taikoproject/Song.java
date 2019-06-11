package com.example.taikoproject;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Song {

    private int tempo;
    private boolean defaultTempo;
    private int numRepeats;
    private InputStream is;
    private List<String> songData;

    public Song(InputStream is, int tempo, int numRepeats) {
        this.tempo = tempo;
        defaultTempo = false;
        this.numRepeats = numRepeats;
        this.is = is;
        songData = new ArrayList<>();
    }
    public Song(InputStream is) {
        defaultTempo = true;
        this.numRepeats = 1;
        this.is = is;
        songData = new ArrayList<>();
    }
    public boolean readSong(){
        BufferedReader reader = null;
        int lineNum = 0;

        try {
            String line = "";
            reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

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

//        for (String str : songData) {
//            System.out.println(str);
//        }
        return true;
    }

    public List<String> getSongData() {
        return songData;
    }

    public int getTempo() {
        return tempo;
    }

    public int getNumRepeats() {
        return numRepeats;
    }
}
