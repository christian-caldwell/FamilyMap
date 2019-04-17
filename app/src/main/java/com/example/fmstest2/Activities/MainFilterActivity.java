package com.example.fmstest2.Activities;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.example.fmstest2.FilterViewAdapter;
import com.example.fmstest2.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import Filter.FilterData;
import Filter.RowData;
import Server.ServerData;

public class MainFilterActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Switch fatherSideSwitch;
    private Switch motherSideSwitch;
    private Switch maleEventSwitch;
    private Switch femaleEventSwitch;
    private FilterData filterData;
    private Parcelable recyclerViewState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        filterData = filterData.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Filter");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.filter_recycler_view);
        fatherSideSwitch = (Switch) findViewById(R.id.father_switch);
        motherSideSwitch = (Switch) findViewById(R.id.mother_switch);
        maleEventSwitch = (Switch) findViewById(R.id.male_switch);
        femaleEventSwitch = (Switch) findViewById(R.id.female_switch);

        fatherSideSwitch.setChecked(filterData.isShowFatherSide());
        motherSideSwitch.setChecked(filterData.isShowMotherSide());
        maleEventSwitch.setChecked(filterData.isShowMaleEvents());
        femaleEventSwitch.setChecked(filterData.isShowFemaleEvents());

        fatherSideSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                filterData.setShowFatherSide(isChecked);
            }
        });
        motherSideSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                filterData.setShowMotherSide(isChecked);
            }
        });
        maleEventSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                filterData.setShowMaleEvents(isChecked);
            }
        });
        femaleEventSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                filterData.setShowFemaleEvents(isChecked);
            }
        });

        List<RowData> list = new ArrayList<>();
        for (String event: ServerData.getInstance().getEventTypes()) {
            RowData rowData = new RowData(event);
            list.add(rowData);
        }
        FilterViewAdapter adapter = new FilterViewAdapter(list, getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        fatherSideSwitch.setChecked(filterData.isShowFatherSide());
        motherSideSwitch.setChecked(filterData.isShowMotherSide());
        maleEventSwitch.setChecked(filterData.isShowMaleEvents());
        femaleEventSwitch.setChecked(filterData.isShowFemaleEvents());


        List<RowData> list = new ArrayList<>();
        for (String event: ServerData.getInstance().getEventTypes()) {
            RowData rowData = new RowData(event);
            list.add(rowData);
        }
        FilterViewAdapter adapter = new FilterViewAdapter(list, getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.invalidate();
    }

}



