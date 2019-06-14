package com.example.taikoproject;

import android.graphics.Color;

public class HitObject {

    public static final int LEFT_KA = 0;
    public static final int LEFT_DON = 1;
    public static final int RIGHT_DON = 2;
    public static final int RIGHT_KA = 3;

    private int hitType;
    private int hitTime;
    private int y;
    private int radius;
    private int color;

    public HitObject(int value, int hitTime) {
        this.hitType = value;
        this.hitTime = hitTime;
        y = -1;
    }

    public void draw() {
        switch (hitType) {
            case LEFT_KA:
                // circle
                return;
            case LEFT_DON:
                // circle
                return;
            case RIGHT_DON:
                // circle
                return;
            case RIGHT_KA:
                //circle
                return;
            default:
                return;
        }
    }

    public int getHitType() {
        return hitType;
    }

    public int getHitTime() {
        return hitTime;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String toString() {
        String hitTypeStr = "";
        switch (hitType) {
            case LEFT_KA:
                hitTypeStr = "L Ka";
                break;
            case LEFT_DON:
                hitTypeStr = "L Don";
                break;
            case RIGHT_DON:
                hitTypeStr = "R Don";
                break;
            case RIGHT_KA:
                hitTypeStr = "R Ka";
                break;
            default:
                break;
        }
        return "hit: " + hitTypeStr + " hitTime: " + hitTime + " color: " + color;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
