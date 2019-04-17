package com.example.fmstest2.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.fmstest2.MapFragment;
import com.example.fmstest2.R;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import Model.Event;

public class MapActivity extends AppCompatActivity {

    private MapFragment mapFragment;
    private android.widget.Toolbar toolbar;
    private MenuItem doubleUpArrow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        android.support.v4.app.FragmentManager fm = this.getSupportFragmentManager();
        mapFragment = (MapFragment) fm.findFragmentById(R.id.map_fragment);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.map_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Map");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        Event event = (Event) intent.getSerializableExtra("event");

        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance(event.getEventID());
            fm.beginTransaction()
                    .add(R.id.map_fragment, mapFragment)
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapFragment.parentOnResumeCalled();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_person, menu);

        doubleUpArrow = menu.findItem(R.id.double_up_arrow);

        Drawable doubleUpIcon = new IconDrawable(this, Iconify.IconValue.fa_arrow_up)
                .sizeDp(24).color(Color.WHITE);

        doubleUpArrow.setIcon(doubleUpIcon);

        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.double_up_arrow) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}
