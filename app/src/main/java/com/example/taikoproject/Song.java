package com.example.taikoproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Song {

    private int tempo;
    private boolean defaultTempo;
    private int numRepeats;
    private String songName;
    private int[] timeSig;
    private InputStream is;
    private List<String> lineData;

    public Song(InputStream is, int tempo, int numRepeats) {
        this.tempo = tempo;
        defaultTempo = false;
        this.numRepeats = numRepeats;
        this.is = is;
        lineData = new ArrayList<>();
        readSong();
    }
    public Song(InputStream is) {
        defaultTempo = true;
        this.numRepeats = 1;
        this.is = is;
        lineData = new ArrayList<>();
        readSong();
    }

    private boolean readSong(){
        BufferedReader reader = null;
        int lineNum = 0;

        try {
            String line;
            reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

            while ((line = reader.readLine()) != null) {
                if (lineNum == 0) {
                    songName = line;
                }
                else if (lineNum == 1 && defaultTempo) {
                    tempo = Integer.parseInt(line);
                }
                else if (lineNum == 2) {
                    String[] timeSigValues = line.split("/");
                    timeSig = new int[] {Integer.parseInt(timeSigValues[0]), Integer.parseInt(timeSigValues[1])};
                }
                else {
                    lineData.add(line);
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

//        for (String str : lineData) {
//            System.out.println(str);
//        }
        return true;
    }

    public List<String> getLineData() {
        return lineData;
    }

    public int getTempo() {
        return tempo;
    }

    public int getNumRepeats() {
        return numRepeats;
    }

    public int[] getTimeSig() {
        return timeSig;
    }
}
