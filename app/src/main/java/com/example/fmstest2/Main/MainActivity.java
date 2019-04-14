package com.example.fmstest2.Main;

import Server.ServerData;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.fmstest2.Main.LoginFragment;
import com.example.fmstest2.Map.MapFragment;
import com.example.fmstest2.R;

public class MainActivity extends AppCompatActivity {

    private LoginFragment loginFragment;
    private MapFragment mapFragment;
    private ServerData serverData = ServerData.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v4.app.FragmentManager fm = this.getSupportFragmentManager();
        loginFragment = (LoginFragment) fm.findFragmentById(R.id.login_fragment);
        mapFragment = (MapFragment) fm.findFragmentById(R.id.map);

        if (serverData.isLoggedIn()) {
            if (!serverData.isToastThrown()) {

            }
        }
        else {
            if (loginFragment == null) {
                loginFragment = LoginFragment.newInstance();
                fm.beginTransaction()
                        .add(R.id.login_fragment, loginFragment)
                        .commit();
            }
        }
    }


}
