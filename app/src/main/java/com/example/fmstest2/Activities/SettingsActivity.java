package com.example.fmstest2.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.fmstest2.R;

import org.json.JSONObject;

import java.util.List;

import Filter.FilterData;
import Model.Event;
import Model.Person;
import Server.Communicator;
import JavaHelpers.JsonResponseParser;
import Server.ServerData;

public class SettingsActivity extends AppCompatActivity {

    private Switch lifeStorySwitch;
    private Switch familyTreeSwitch;
    private Switch spouseSwitch;

    private Spinner lifeStorySpinner;
    private Spinner familyTreeSpinner;
    private Spinner spouseSpinner;
    private Spinner mapTypeSpinner;

    private LinearLayout resyncDataView;
    private LinearLayout logoutView;
    public static Context activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = this;
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Settings");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mapTypeSpinner = (Spinner) findViewById(R.id.map_type_spinner);
        resyncDataView = (LinearLayout) findViewById(R.id.resync_data_view);
        logoutView = (LinearLayout) findViewById(R.id.logout_view);
    }

    @Override
    protected void onResume() {
        super.onResume();

        lifeStorySwitch = (Switch) findViewById(R.id.life_story_switch);
        familyTreeSwitch = (Switch) findViewById(R.id.family_tree_switch);
        spouseSwitch = (Switch) findViewById(R.id.spouse_switch);

        lifeStorySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                FilterData.getInstance().setShowEventLine(isChecked);
            }
        });
        familyTreeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                FilterData.getInstance().setShowAncestorLine(isChecked);
            }
        });
        spouseSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean c = isChecked;
                FilterData.getInstance().setShowSpouseLine(isChecked);
            }
        });

        lifeStorySwitch.setChecked(FilterData.getInstance().isShowEventLine());
        familyTreeSwitch.setChecked(FilterData.getInstance().isShowAncestorLine());
        spouseSwitch.setChecked(FilterData.getInstance().isShowSpouseLine());

        lifeStorySpinner = (Spinner) findViewById(R.id.life_story_spinner);
        familyTreeSpinner = (Spinner) findViewById(R.id.family_tree_spinner);
        spouseSpinner = (Spinner) findViewById(R.id.spouse_spinner);
        mapTypeSpinner = (Spinner) findViewById(R.id.map_type_spinner);

        FilterData fs = FilterData.getInstance();
        lifeStorySpinner.setSelection(fs.lineColorOptions.indexOf(fs.lineColorLifeStory));
        familyTreeSpinner.setSelection(fs.lineColorOptions.indexOf(fs.lineColorTree));
        spouseSpinner.setSelection(fs.lineColorOptions.indexOf(fs.lineColorSpouse));
        mapTypeSpinner.setSelection(fs.mapTypeList.indexOf(fs.mapType));

        lifeStorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FilterData.getInstance().lineColorLifeStory = FilterData.getInstance().lineColorOptions.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        familyTreeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FilterData.getInstance().lineColorTree = FilterData.getInstance().lineColorOptions.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spouseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FilterData.getInstance().lineColorSpouse = FilterData.getInstance().lineColorOptions.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mapTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FilterData.getInstance().mapType = FilterData.getInstance().mapTypeList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        logoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServerData.getInstance().setLoggedIn(false);
                Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName() );
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        resyncDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PeopleTask peopleTask = new PeopleTask();
                peopleTask.execute();
                ServerData.getInstance().setRelog(true);

        }
        });

    }

    public class PeopleTask extends AsyncTask<Void, Void, Integer> {

        private ServerData serverData;

        public PeopleTask() {
            serverData = ServerData.getInstance();
        }

        @Override
        protected Integer doInBackground(Void... v) {
            try {
                Communicator communicator = new Communicator();
                JSONObject peopleResult = communicator.getPeople();
                List<Person> peopleList = JsonResponseParser.parsePeople(peopleResult);
                serverData.setPeopleList(peopleList);
                return 0;
            }
            catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Resync Failed!",
                        Toast.LENGTH_LONG).show();
            }
            return 1;
        }

        @Override
        protected void onProgressUpdate(Void... progress) {
        }

        @Override
        protected void onPostExecute(Integer id) {
            EventTask eventTask = new EventTask();
            eventTask.execute();

        }
    }

    public class EventTask extends AsyncTask<Void, Void, Integer> {

        private ServerData serverData;

        public EventTask() {
            serverData = serverData.getInstance();
        }

        @Override
        protected Integer doInBackground(Void... v) {
            try {
                Communicator communicator = new Communicator();
                JSONObject eventResult = communicator.getEvents();
                List<Event> eventList = JsonResponseParser.parseEvents(eventResult);
                serverData.setEventList(eventList);
                return 0;
            }
            catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Resync Failed!",
                        Toast.LENGTH_LONG).show();
            }
            return 1;
        }

        @Override
        protected void onProgressUpdate(Void... progress) {
        }

        @Override
        protected void onPostExecute(Integer id) {
            Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName() );
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            Toast.makeText(getApplicationContext(), "Reloaded Data",
                    Toast.LENGTH_LONG).show();
        }
    }

}
