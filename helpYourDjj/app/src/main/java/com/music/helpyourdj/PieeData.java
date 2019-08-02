package com.music.helpyourdj;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;

public class PieeData extends PieData {
    ArrayList<String> xVals;
    PieDataSet dataSet;

    public PieeData(ArrayList<String> xVals,PieDataSet dataSet){
        this.xVals = xVals;
        this.dataSet = dataSet;
    }
}
