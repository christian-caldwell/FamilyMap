package com.example.fmstest2;

import android.graphics.Color;

import com.androidmapsextensions.GoogleMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Server.ServerData;

/**
 * This singlton class contains the state of all filters and settings in the application.
 */
public class FilterState {

    private static FilterState instance = null;

    private boolean showFatherSide;
    private boolean showMotherSide;
    private boolean showMaleEvents;
    private boolean showFemaleEvents;
    private Map<String, Boolean> showEventMap;

    //Settings
    public List<Integer> lineColorOptions;
    public int lineColorLifeStory;
    public int lineColorTree;
    public int lineColorSpouse;

    private boolean showSpouseLine;
    private boolean showAncestorLine;
    private boolean showEventLine;

    public int mapType;
    public List<Integer> mapTypeList;

    private FilterState() {
        showEventMap = new HashMap<>();
        for (String event: ServerData.getInstance().getEventTypes()) {
            showEventMap.put(event, true);
        }
        showMotherSide = true;
        showFatherSide = true;
        showMaleEvents = true;
        showFemaleEvents = true;


        // Init the options for line colors
        lineColorOptions = new ArrayList<>();
        lineColorOptions.add(Color.RED);
        lineColorOptions.add(Color.BLUE);
        lineColorOptions.add(Color.YELLOW);
        lineColorOptions.add(Color.GREEN);
        lineColorOptions.add(Color.MAGENTA);
        lineColorOptions.add(Color.GRAY);

        // Set default line colors
        lineColorLifeStory = Color.RED;
        lineColorTree = Color.YELLOW;
        lineColorSpouse = Color.GREEN;

        showSpouseLine = true;
        showEventLine = true;
        showAncestorLine = true;

        mapType = GoogleMap.MAP_TYPE_NORMAL;

        mapTypeList = new ArrayList<>();
        mapTypeList.add(GoogleMap.MAP_TYPE_NORMAL);
        mapTypeList.add(GoogleMap.MAP_TYPE_HYBRID);
        mapTypeList.add(GoogleMap.MAP_TYPE_SATELLITE);
        mapTypeList.add(GoogleMap.MAP_TYPE_TERRAIN);
    }

    public static FilterState getInstance() {
        if (instance == null) {
            instance = new FilterState();
        }
        return instance;
    }

    public void refreshFilters() {
        showEventMap = new HashMap<>();
        for (String event: ServerData.getInstance().getEventTypes()) {
            showEventMap.put(event, true);
        }
        showMotherSide = true;
        showFatherSide = true;
        showMaleEvents = true;
        showFemaleEvents = true;
        showSpouseLine = true;
        showEventLine = true;
        showAncestorLine = true;
    }

    public void toggleMapBool(String key, boolean value) {
        //Boolean oldVal = showEventMap.get(key);
        showEventMap.remove(key);
        showEventMap.put(key, value);
    }

    public List<String> calcHiddenMarkers() {
        List<String> returnList = new ArrayList<>();
        if (showFatherSide == false) {
            returnList.add("father");
        }
        if (showMotherSide == false) {
            returnList.add("mother");
        }
        if (showMaleEvents == false) {
            returnList.add("male");
        }
        if (showFemaleEvents == false) {
            returnList.add("female");
        }
        for (String key: showEventMap.keySet()) {
            if (showEventMap.get(key).equals(false)) {
                returnList.add(key);
            }
        }

        return returnList;
    }

    public boolean isShowFatherSide() {
        return showFatherSide;
    }

    public void setShowFatherSide(boolean showFatherSide) {
        this.showFatherSide = showFatherSide;
    }

    public boolean isShowMotherSide() {
        return showMotherSide;
    }

    public void setShowMotherSide(boolean showMotherSide) {
        this.showMotherSide = showMotherSide;
    }

    public boolean isShowMaleEvents() {
        return showMaleEvents;
    }

    public void setShowMaleEvents(boolean showMaleEvents) {
        this.showMaleEvents = showMaleEvents;
    }

    public boolean isShowFemaleEvents() {
        return showFemaleEvents;
    }

    public void setShowFemaleEvents(boolean showFemaleEvents) {
        this.showFemaleEvents = showFemaleEvents;
    }

    public Map<String, Boolean> getShowEventMap() {
        return showEventMap;
    }

    public void setShowEventMap(Map<String, Boolean> showEventMap) {
        this.showEventMap = showEventMap;
    }

    public boolean isShowSpouseLine() {
        return showSpouseLine;
    }

    public void setShowSpouseLine(boolean showSpouseLine) {
        this.showSpouseLine = showSpouseLine;
    }

    public boolean isShowAncestorLine() {
        return showAncestorLine;
    }

    public void setShowAncestorLine(boolean showAncestorLine) {
        this.showAncestorLine = showAncestorLine;
    }

    public boolean isShowEventLine() {
        return showEventLine;
    }

    public void setShowEventLine(boolean showEventLine) {
        this.showEventLine = showEventLine;
    }

    public boolean areAllEventsShown() {
        for (String key: showEventMap.keySet()) {
            if (showEventMap.get(key) == false) {
                return false;
            }
        }
        return true;
    }

    public boolean bothGendersAreShown() {
        if (showMaleEvents == false || showFemaleEvents == false) {
            return false;
        }
        return true;
    }
}
