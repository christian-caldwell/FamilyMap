package com.example.fmstest2.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.example.fmstest2.Adapters.PersonModelMenuAdapter;
import com.example.fmstest2.R;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.util.ArrayList;

import Model.Person;
import Person.TopEvent;

public class PersonActivity extends AppCompatActivity {

    private Person person;

    private TextView textFirstName;
    private TextView textLastName;
    private TextView textGender;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;


    private MenuItem doubleUpArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        Toolbar toolbar = (Toolbar) findViewById(R.id.person_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Person Description");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        Intent intent = getIntent();
        person = (Person) intent.getSerializableExtra("person");

        textFirstName = (TextView) findViewById(R.id.personFirstName);
        textLastName = (TextView) findViewById(R.id.personLastName);
        textGender = (TextView) findViewById(R.id.personGender);

        textFirstName.setText(person.getFirstName());
        textLastName.setText(person.getLastName());
        textGender.setText(person.getGender());

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        PersonModelMenuAdapter personListAdapter = new PersonModelMenuAdapter(this, generateEvents());
        personListAdapter.setCustomParentAnimationViewId(R.id.parentEventListArrow);
        personListAdapter.setParentClickableViewAnimationDefaultDuration();
        personListAdapter.setParentAndIconExpandOnClick(true);
        recyclerView = (RecyclerView) findViewById(R.id.eventListRecyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(personListAdapter);
    }

    private ArrayList<ParentObject> generateEvents() {
        ArrayList<ParentObject> parentList = new ArrayList<>();
        parentList.add(new TopEvent(person, "event"));
        parentList.add(new TopEvent(person, "family"));
        return parentList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
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
