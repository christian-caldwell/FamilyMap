package com.example.fmstest2.Helper;

import android.graphics.Color;

import Filter.FilterData;

public class FilterValues {
    private final int COLOR_GRAY = Color.GRAY;
    private final int COLOR_RED = Color.RED;
    private final int COLOR_GREEN = Color.GREEN;
    private final int COLOR_BLUE = Color.BLUE;
    private final int COLOR_YELLOW = Color.YELLOW;
    private final int COLOR_PINK = Color.MAGENTA;

    private static FilterValues instance = null;
    private FilterData dataSingleton;

    private FilterValues() {
        this.dataSingleton = FilterData.getInstance();
    }

    public static FilterValues getInstance() {
        if (instance == null) {
            instance = new FilterValues();
        }
        return instance;
    }

    public int getCOLOR_GRAY() {
        return COLOR_GRAY;
    }

    public int getCOLOR_RED() {
        return COLOR_RED;
    }

    public int getCOLOR_GREEN() {
        return COLOR_GREEN;
    }

    public int getCOLOR_BLUE() {
        return COLOR_BLUE;
    }

    public int getCOLOR_YELLOW() {
        return COLOR_YELLOW;
    }

    public int getCOLOR_PINK() {
        return COLOR_PINK;
    }
}
