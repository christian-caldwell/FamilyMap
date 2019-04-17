package com.example.fmstest2.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fmstest2.Activities.PersonActivity;
import com.example.fmstest2.AndroidHelpers.ActivityFunctions;
import com.example.fmstest2.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.Polyline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Filter.FilterData;
import JavaHelpers.EventModelAlgorithms;
import Model.Event;
import Model.Person;
import Server.ServerData;


public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {


    private static final String ARG_EVENT_ID = "event_id";

    private ServerData serverData;
    private FilterData filterData;
    private GoogleMap map;
    private Marker curMarker;
    private Map<Marker, Event> eventMarkerMap;

    private TextView currentPersonText;
    private TextView currentEventText;
    private ImageView genderImage;
    private LinearLayout bottomLayout;

    private Set<Marker> maleMarkers;
    private Set<Marker> femaleMarkers;
    private List<Polyline> polyLineList;

    private String selectedEventId;
    private int mapRecurseCount = 10;

    public MapFragment() {
        // Required empty public constructor
    }

    public  static MapFragment newInstance(String eventId) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_EVENT_ID, eventId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        filterData = filterData.getInstance();
        try {
            if (getArguments() != null) {
                selectedEventId = getArguments().getString(ARG_EVENT_ID);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.serverData = serverData.getInstance();
        this.serverData.buildFamilyTree();
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        eventMarkerMap = new HashMap<Marker, Event>();


        currentPersonText = (TextView) v.findViewById(R.id.currentPersonText);
        currentEventText = (TextView) v.findViewById(R.id.currentEventText);
        genderImage = (ImageView) v.findViewById(R.id.genderImage);


        bottomLayout = (LinearLayout) v.findViewById(R.id.bottomMapLayout);
        bottomLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person person = ServerData.getInstance().getPersonById(eventMarkerMap.get(curMarker).getPersonID());
                onButtonClicked(person);
            }
        });

        return v;
    }

    public void onButtonClicked(Person person) {
        Intent intent = new Intent(getActivity(), PersonActivity.class);
        intent.putExtra("person", person);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(FilterData.getInstance().mapType);
        polyLineList = new ArrayList<>();

        this.serverData.buildEventColorMap();

        for (Event event : this.serverData.getEventList()) {

            Person person = this.serverData.getPersonById(event.getPersonID());

            LatLng eventLatLng =
                    new LatLng(event.getLatitude(), event.getLongitude());

            if (person != null) {
                curMarker = map.addMarker(new MarkerOptions()
                        .position(eventLatLng)
                        .title(person.getFirstName() + " " + person.getLastName())
                        .icon(BitmapDescriptorFactory.defaultMarker(this.serverData.getEventColorMap().get(event.getDescription()))));

                eventMarkerMap.put(curMarker, event);
            }
        }
        currentPersonText.setText("Pick a person to see info");
        map.setOnMarkerClickListener(this);
        curMarker = null;
        if (selectedEventId != null) {
            for (Marker marker: eventMarkerMap.keySet()) {
                if (eventMarkerMap.get(marker).getEventID().equals(selectedEventId)) {
                    onMarkerClick(marker);
                    break;
                }
            }
        }
        else {
            filterData.refreshFilters();
        }



        maleMarkers = new HashSet<>();
        femaleMarkers = new HashSet<>();
        for (Marker m: eventMarkerMap.keySet()) {
            if (this.serverData.getPeopleMap().get(eventMarkerMap.get(m).getPersonID()).getGender().equals("m")) {
                maleMarkers.add(m);
            }
            else {
                femaleMarkers.add(m);
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Event event = eventMarkerMap.get(marker);
        marker.showInfoWindow();
        genderImage.setImageDrawable(ActivityFunctions.getGenderIcon(getActivity(), this.serverData.getPersonById(event.getPersonID()).getGender()));
        currentPersonText.setText(this.serverData.getPersonById(event.getPersonID()).getFirstName() + " " + this.serverData.getPersonById(event.getPersonID()).getLastName());
        currentEventText.setText(event.toString());
        curMarker = marker;
        drawLines();
        return true;
    }

    public void onMarkerClickWithEvent(Event event) {
        for (Marker marker: eventMarkerMap.keySet()) {
            if (eventMarkerMap.get(marker).getEventID().equals(event.getEventID())) {
                onMarkerClick(marker);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (map != null) {
            redrawMarkers();
            try {
                map.setMapType(FilterData.getInstance().mapType);
                drawLines();
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(curMarker.getPosition(), 1));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void redrawMarkers() {
        List<String> hiddenList = FilterData.getInstance().calcHiddenMarkers();
        for (Marker marker: eventMarkerMap.keySet()) {
            for (String hidden: hiddenList){
                if (eventMarkerMap.get(marker).getDescription().equals(hidden)) {
                    marker.setVisible(false);
                    break;
                }
                else {
                    marker.setVisible(true);
                }
            }
            if (marker.isVisible() == true) {
                for (String hidden : hiddenList) {
                    if (hidden.equals("male") && this.serverData.getPeopleMap().get(eventMarkerMap.get(marker).getPersonID()).getGender().equals("m")) {
                        marker.setVisible(false);
                    } else if (hidden.equals("female") && this.serverData.getPeopleMap().get(eventMarkerMap.get(marker).getPersonID()).getGender().equals("f")) {
                        marker.setVisible(false);
                    }
                }
            }
            if (hiddenList.size() == 0) {
                marker.setVisible(true);
            }
        }
    }

    public void ancestorRecurse(String personId, boolean visible) {
        Set<String> parentSet = this.serverData.getFamilyTree().get(personId);
            for (Marker marker: eventMarkerMap.keySet()) {
                if (eventMarkerMap.get(marker).getPersonID().equals(personId)) {
                    if (visible) {
                        marker.setVisible(true);
                    }
                    else {
                        marker.setVisible(false);
                    }
                }
            }
        for (String parent: parentSet) {
            if (parent != null) {
                ancestorRecurse(parent, visible);
            }
        }
    }


    public void parentOnResumeCalled() {
        if (map != null) {
            map.setMapType(FilterData.getInstance().mapType);
        }
    }

    public void drawLines() {
        try {
            for (com.google.android.gms.maps.model.Polyline line : polyLineList) {
                line.remove();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if (filterData.areAllEventsShown() && filterData.isShowEventLine()) {
            drawEventLines();
        }
        if (filterData.bothGendersAreShown() && filterData.isShowSpouseLine()) {
            drawSpouseLine();
        }
        if (filterData.isShowAncestorLine()) {
            drawAncestorLines();
        }
    }

    public void drawEventLines() {
        Event event = eventMarkerMap.get(curMarker);
        System.out.println(eventMarkerMap.toString());
        List<Event> eventList = EventModelAlgorithms.getPersonEvents(event.getPersonID());
        EventModelAlgorithms.sortEvents(eventList);
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.add(new LatLng(eventList.get(0).getLatitude(), eventList.get(0).getLongitude()));
        for (int i = 1; i < eventList.size(); i++) {
            if (eventList.get(i) != null && !eventList.get(i).equals("")) {
                polylineOptions.add(new LatLng(eventList.get(i).getLatitude(), eventList.get(i).getLongitude()));
            }
        }
        polylineOptions.color(filterData.lineColorLifeStory).width(10);
        Polyline finalPolyLine = map.addPolyline(polylineOptions);
        polyLineList.add(finalPolyLine);
    }

    public void drawSpouseLine() {
        Event event = eventMarkerMap.get(curMarker);
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.add(new LatLng(event.getLatitude(), event.getLongitude()));

        Person person = ServerData.getInstance().getPeopleMap().get(event.getPersonID());
        String spouseId = person.getSpouse();
        if (spouseId != null && !spouseId.equals("")) {
            for (Marker marker: eventMarkerMap.keySet()) {
                Event spouseEvent = this.serverData.getEarliestEvent(spouseId);
                if (eventMarkerMap.get(marker).getEventID().equals(spouseEvent.getEventID())) {
                    polylineOptions.add(new LatLng(spouseEvent.getLatitude(), spouseEvent.getLongitude()));
                }
            }
        }
        polylineOptions.color(filterData.lineColorSpouse).width(10);
        Polyline finalLine = map.addPolyline(polylineOptions);
        polyLineList.add(finalLine);
    }

    public void drawAncestorLines() {
        Event event = eventMarkerMap.get(curMarker);
        String personId = event.getPersonID();
        familyTreeRecurse(personId);
        Set<String> parentSet = this.serverData.getFamilyTree().get(personId);
        for (String parent: parentSet) {
            if (parent != null) {
                Event parentEvent = this.serverData.getEarliestEvent(parent);
                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.add(new LatLng(event.getLatitude(), event.getLongitude()));
                polylineOptions.add(new LatLng(parentEvent.getLatitude(), parentEvent.getLongitude()));
                polylineOptions.color(filterData.lineColorTree).width(10);
                Polyline finalLine = map.addPolyline(polylineOptions);
                polyLineList.add(finalLine);
                familyTreeRecurse(parent);
            }
        }
    }



    public void familyTreeRecurse(String personId) {
        Set<String> parentSet = this.serverData.getFamilyTree().get(personId);
        for (String parent: parentSet) {
            if (parent != null) {
                Event parentEvent = this.serverData.getEarliestEvent(parent);
                Event personEvent = this.serverData.getEarliestEvent(personId);
                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.add(new LatLng(personEvent.getLatitude(), personEvent.getLongitude()));
                polylineOptions.add(new LatLng(parentEvent.getLatitude(), parentEvent.getLongitude()));
                mapRecurseCount = mapRecurseCount-3;
                if (mapRecurseCount < 1) {
                    mapRecurseCount = 1;
                }
                polylineOptions.color(filterData.lineColorTree).width(mapRecurseCount);
                Polyline finalLine = map.addPolyline(polylineOptions);
                polyLineList.add(finalLine);
                familyTreeRecurse(parent);
                mapRecurseCount = mapRecurseCount+3;

            }
        }
        if (Collections.frequency(parentSet, null) == parentSet.size()) {
        }
    }

}