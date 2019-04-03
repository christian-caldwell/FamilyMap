package com.example.fmstest2;

import Server.ServerData;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private LoginFragment loginFragment;
    private ServerData serverData = ServerData.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v4.app.FragmentManager fm = this.getSupportFragmentManager();
        loginFragment = (LoginFragment) fm.findFragmentById(R.id.mainFrameLayout);

        if (serverData.isLoggedIn()) {
            if (!serverData.isToastThrown()) {

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


}
