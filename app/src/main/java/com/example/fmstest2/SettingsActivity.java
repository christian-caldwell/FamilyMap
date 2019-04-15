package com.example.fmstest2;

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

import org.json.JSONObject;

import java.util.List;

import Model.Event;
import Model.Person;
import Server.Communicator;
import Server.JsonResponseParser;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                FilterState.getInstance().setShowEventLine(isChecked);
            }
        });
        familyTreeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                FilterState.getInstance().setShowAncestorLine(isChecked);
            }
        });
        spouseSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean c = isChecked;
                FilterState.getInstance().setShowSpouseLine(isChecked);
            }
        });

        lifeStorySwitch.setChecked(FilterState.getInstance().isShowEventLine());
        familyTreeSwitch.setChecked(FilterState.getInstance().isShowAncestorLine());
        spouseSwitch.setChecked(FilterState.getInstance().isShowSpouseLine());

        lifeStorySpinner = (Spinner) findViewById(R.id.life_story_spinner);
        familyTreeSpinner = (Spinner) findViewById(R.id.family_tree_spinner);
        spouseSpinner = (Spinner) findViewById(R.id.spouse_spinner);
        mapTypeSpinner = (Spinner) findViewById(R.id.map_type_spinner);

        FilterState fs = FilterState.getInstance();
        lifeStorySpinner.setSelection(fs.lineColorOptions.indexOf(fs.lineColorLifeStory));
        familyTreeSpinner.setSelection(fs.lineColorOptions.indexOf(fs.lineColorTree));
        spouseSpinner.setSelection(fs.lineColorOptions.indexOf(fs.lineColorSpouse));
        mapTypeSpinner.setSelection(fs.mapTypeList.indexOf(fs.mapType));

        lifeStorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FilterState.getInstance().lineColorLifeStory = FilterState.getInstance().lineColorOptions.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        familyTreeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FilterState.getInstance().lineColorTree = FilterState.getInstance().lineColorOptions.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spouseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FilterState.getInstance().lineColorSpouse = FilterState.getInstance().lineColorOptions.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mapTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FilterState.getInstance().mapType = FilterState.getInstance().mapTypeList.get(position);
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
            ServerData.getInstance().setLoggedIn(true);
            Toast.makeText(getApplicationContext(), "Reloaded Data",
                    Toast.LENGTH_LONG).show();

        }
    }

}
