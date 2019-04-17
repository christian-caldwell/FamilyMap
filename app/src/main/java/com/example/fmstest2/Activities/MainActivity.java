package com.example.fmstest2.Activities;

import Server.ServerData;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.fmstest2.LoginFragment;
import com.example.fmstest2.MapFragment;
import com.example.fmstest2.R;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

public class MainActivity extends AppCompatActivity {

    private LoginFragment loginFragment;
    private ServerData serverData = ServerData.getInstance();
    private MapFragment mapFragment;

    private MenuItem searchIcon;
    private MenuItem filterIcon;
    private MenuItem settingsIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v4.app.FragmentManager fm = this.getSupportFragmentManager();
        loginFragment = (LoginFragment) fm.findFragmentById(R.id.mainFrameLayout);
        MapFragment mainMapFragmet = (MapFragment) fm.findFragmentById(R.id.mainFrameLayout);

        if (serverData.getRelog()) {
            if (mapFragment == null) {
                mapFragment = MapFragment.newInstance(null);
                fm.beginTransaction()
                        .add(R.id.mainFrameLayout, mapFragment)
                        .commit();
            }
        }
        else if (serverData.isLoggedIn()) {
            if (mapFragment == null) {
                mapFragment = MapFragment.newInstance(null);
                fm.beginTransaction()
                        .add(R.id.mainFrameLayout, mapFragment)
                        .commit();
            }
        }
        else {
            if (loginFragment == null) {
                loginFragment = LoginFragment.newInstance();
                fm.beginTransaction()
                        .add(R.id.mainFrameLayout, loginFragment)
                        .commit();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (ServerData.getInstance().getRelog()) {
            Toast.makeText(this, "Stutter Detected - Action Revoked",
                    Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        searchIcon = menu.findItem(R.id.toolbar_search);
        filterIcon = menu.findItem(R.id.toolbar_filter);
        settingsIcon = menu.findItem(R.id.toolbar_settings);

        Drawable searchIc = new IconDrawable(this, Iconify.IconValue.fa_search)
                .sizeDp(24).color(Color.WHITE);
        Drawable filterIc= new IconDrawable(this, Iconify.IconValue.fa_filter)
                .sizeDp(24).color(Color.WHITE);
        Drawable settingsIc = new IconDrawable(this, Iconify.IconValue.fa_gear)
                .sizeDp(24).color(Color.WHITE);

        searchIcon.setIcon(searchIc);
        filterIcon.setIcon(filterIc);
        settingsIcon.setIcon(settingsIc);

        searchIcon.setVisible(false);
        filterIcon.setVisible(false);
        settingsIcon.setVisible(false);

        if (ServerData.getInstance().getInstance().isLoggedIn() == true) {
            setIconsVisible();
        }

        return super.onCreateOptionsMenu(menu);
    }

    public void setIconsVisible() {
        searchIcon.setVisible(true);
        filterIcon.setVisible(true);
        settingsIcon.setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.toolbar_search) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.toolbar_filter) {
            Intent intent = new Intent(this, MainFilterActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.toolbar_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
