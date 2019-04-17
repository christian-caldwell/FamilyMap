package Filter;


import com.androidmapsextensions.GoogleMap;
import com.example.fmstest2.Helper.FilterValues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Server.ServerData;

public class FilterData {
    private final int NORMAL_MAP_VALUE = GoogleMap.MAP_TYPE_NORMAL;
    private final int HYBRID_MAP_VALUE = GoogleMap.MAP_TYPE_HYBRID;
    private final int SATELLITE_MAP_VALUE = GoogleMap.MAP_TYPE_SATELLITE;
    private final int TERRAIN_MAP_VALUE = GoogleMap.MAP_TYPE_TERRAIN;

    public List<Integer> lineColorOptions;
    public int lineColorLifeStory;
    public int lineColorTree;
    public int lineColorSpouse;

    public int mapType = GoogleMap.MAP_TYPE_NORMAL;
    public List<Integer> mapTypeList;

    private Map<String, Boolean> showEventMap;
    private boolean showEventLine;
    private boolean showSpouseLine;
    private boolean showAncestorLine;
    private boolean showFatherSide;
    private boolean showMotherSide;
    private boolean showMaleEvents;
    private boolean showFemaleEvents;

    private static FilterData singleton = null;
    private FilterValues androidValues;

    private FilterData() {
        lineColorOptions = new ArrayList<>();
        mapTypeList = new ArrayList<>();
        mapTypeList.add(this.NORMAL_MAP_VALUE);
        mapTypeList.add(this.HYBRID_MAP_VALUE);
        mapTypeList.add(this.SATELLITE_MAP_VALUE);
        mapTypeList.add(this.TERRAIN_MAP_VALUE);

        setBasicSwitchesToTrue();
        setEventSwitchesToTrue();
        androidValues = FilterValues.getInstance();

        setLineColorLifeStory(androidValues.getCOLOR_RED());
        setLineColorTree(androidValues.getCOLOR_YELLOW());
        setLineColorSpouse(androidValues.getCOLOR_GREEN());
        recieveColors();
    }

    public static FilterData getInstance() {
        if (singleton == null) {
            singleton = new FilterData();
        }
        return singleton;
    }

    public void refreshFilters() {
        setEventSwitchesToTrue();
        setBasicSwitchesToTrue();
    }

    private void setEventSwitchesToTrue() {
        showEventMap = new HashMap<>();
        for (String event: ServerData.getInstance().getEventTypes()) {
            showEventMap.put(event, true);
        }
    }

    private void setBasicSwitchesToTrue() {
        showMotherSide = true;
        showFatherSide = true;
        showMaleEvents = true;
        showFemaleEvents = true;
        showSpouseLine = true;
        showEventLine = true;
        showAncestorLine = true;
    }

    private void recieveColors(){
        this.lineColorOptions.add(this.androidValues.getCOLOR_BLUE());
        this.lineColorOptions.add(this.androidValues.getCOLOR_GRAY());
        this.lineColorOptions.add(this.androidValues.getCOLOR_GREEN());
        this.lineColorOptions.add(this.androidValues.getCOLOR_PINK());
        this.lineColorOptions.add(this.androidValues.getCOLOR_RED());
        this.lineColorOptions.add(this.androidValues.getCOLOR_YELLOW());
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

    public boolean areAllEventsShown() {
        boolean keyCheck = true;
        for (String key: showEventMap.keySet()) {
            if (showEventMap.get(key) == false) {
                keyCheck = false;
                break;
            }
        }
        return keyCheck;
    }

    public boolean bothGendersAreShown() {
        if (showMaleEvents && showFemaleEvents) {
            return true;
        }
        return false;
    }

    public int getNORMAL_MAP_VALUE() {
        return NORMAL_MAP_VALUE;
    }

    public int getHYBRID_MAP_VALUE() {
        return HYBRID_MAP_VALUE;
    }

    public int getSATELLITE_MAP_VALUE() {
        return SATELLITE_MAP_VALUE;
    }

    public int getTERRAIN_MAP_VALUE() {
        return TERRAIN_MAP_VALUE;
    }

    public List<Integer> getLineColorOptions() {
        return lineColorOptions;
    }

    public int getLineColorLifeStory() {
        return lineColorLifeStory;
    }

    public int getLineColorTree() {
        return lineColorTree;
    }

    public int getLineColorSpouse() {
        return lineColorSpouse;
    }

    public int getMapType() {
        return mapType;
    }

    public List<Integer> getMapTypeList() {
        return mapTypeList;
    }

    public Map<String, Boolean> getShowEventMap() {
        return showEventMap;
    }

    public boolean isShowEventLine() {
        return showEventLine;
    }

    public boolean isShowSpouseLine() {
        return showSpouseLine;
    }

    public boolean isShowAncestorLine() {
        return showAncestorLine;
    }

    public boolean isShowFatherSide() {
        return showFatherSide;
    }

    public boolean isShowMotherSide() {
        return showMotherSide;
    }

    public boolean isShowMaleEvents() {
        return showMaleEvents;
    }

    public boolean isShowFemaleEvents() {
        return showFemaleEvents;
    }

    public static FilterData getSingleton() {
        return singleton;
    }

    public FilterValues getAndroidValues() {
        return androidValues;
    }

    public void setLineColorOptions(List<Integer> lineColorOptions) {
        this.lineColorOptions = lineColorOptions;
    }

    public void setLineColorLifeStory(int lineColorLifeStory) {
        this.lineColorLifeStory = lineColorLifeStory;
    }

    public void setLineColorTree(int lineColorTree) {
        this.lineColorTree = lineColorTree;
    }

    public void setLineColorSpouse(int lineColorSpouse) {
        this.lineColorSpouse = lineColorSpouse;
    }

    public void setMapType(int mapType) {
        this.mapType = mapType;
    }

    public void setMapTypeList(List<Integer> mapTypeList) {
        this.mapTypeList = mapTypeList;
    }

    public void setShowEventMap(Map<String, Boolean> showEventMap) {
        this.showEventMap = showEventMap;
    }

    public void setShowEventLine(boolean showEventLine) {
        this.showEventLine = showEventLine;
    }

    public void setShowSpouseLine(boolean showSpouseLine) {
        this.showSpouseLine = showSpouseLine;
    }

    public void setShowAncestorLine(boolean showAncestorLine) {
        this.showAncestorLine = showAncestorLine;
    }

    public void setShowFatherSide(boolean showFatherSide) {
        this.showFatherSide = showFatherSide;
    }

    public void setShowMotherSide(boolean showMotherSide) {
        this.showMotherSide = showMotherSide;
    }

    public void setShowMaleEvents(boolean showMaleEvents) {
        this.showMaleEvents = showMaleEvents;
    }

    public void setShowFemaleEvents(boolean showFemaleEvents) {
        this.showFemaleEvents = showFemaleEvents;
    }

    public static void setSingleton(FilterData singleton) {
        FilterData.singleton = singleton;
    }

    public void setAndroidValues(FilterValues androidValues) {
        this.androidValues = androidValues;
    }
}
