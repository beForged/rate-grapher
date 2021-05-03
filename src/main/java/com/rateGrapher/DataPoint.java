package com.rateGrapher;


public class DataPoint {

    private int time;
    private int value;

    public DataPoint(int time, int value){
        this.time = time;
        this.value = value;
    }

    public int getTime() {
        return time;
    }

    public int getValue() {
        return value;
    }
}
