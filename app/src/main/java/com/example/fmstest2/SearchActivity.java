package com.example.fmstest2;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.util.LinkedList;
import java.util.List;

import Model.Event;
import Model.Person;
import Search.SearchRowData;
import Server.ServerData;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText searchTextView;
    private List<SearchRowData> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new LinkedList<>();
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_search);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Search");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.search_recycle_view);
        searchTextView = (EditText) findViewById(R.id.search_bar);

        SearchViewAdapter adapter = new SearchViewAdapter(list, getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        searchTextView.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    list = search(searchTextView.getText().toString());
                    SearchViewAdapter adapter = new SearchViewAdapter(list, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_more, menu);


        return super.onCreateOptionsMenu(menu);
    }

    public List<SearchRowData> search(String search) {
        ServerData fms = ServerData.getInstance();
        List<SearchRowData> resultList = new LinkedList<>();
        String term = search.toLowerCase();

        for (Person person: fms.getPeopleList()) {
            String firstName = person.getFirstName().toLowerCase();
            String lastName = person.getLastName().toLowerCase();
            if (firstName.contains(term) || lastName.contains(term)) {
                resultList.add(new SearchRowData(person));
            }
        }

        for (Event event: fms.getEventList()) {
            String countries = event.getCountry();
            String cities = event.getCity();
            String description = event.getDescription();
            Integer eventYear = new Integer(event.getYear());
            String year = eventYear.toString();
            if (countries.contains(term) || cities.contains(term)
                    || description.contains(term) || year.contains(term)) {
                resultList.add(new SearchRowData(event));
            }
        }
        return resultList;
    }

}
