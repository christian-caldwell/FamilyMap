package com.example.fmstest2;

import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.icu.text.SymbolTable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import Model.AuthToken;
import Model.Event;
import Model.Person;
import Model.User;
import Server.Communicator;
import Server.JsonResponseParser;
import Server.ServerData;

public class LoginFragment extends Fragment {

    private Button button;
    private Button existingUserButton;
    private RadioGroup radioGenderGroup;
    private Button btnDisplay;
    private View v;
    private Timer timer;
    private TimerTask timerTask;

    private EditText mServerHostEditText;
    private EditText mServerPortEditText;
    private EditText mUserNameEditText;
    private EditText mPasswordEditText;
    private EditText mFirstNameEditText;
    private EditText mLastNameEditText;
    private EditText mEmailEditText;
    private RadioButton mFemaleRadioButton;
    private RadioButton mMaleRadioButton;

    final Handler handler = new Handler();

    private ServerData serverData = ServerData.getInstance();

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_login, container, false);

        radioGenderGroup=(RadioGroup)v.findViewById(R.id.radio_gender);

        button = v.findViewById(R.id.signIn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!executeLogin()){
                    Toast.makeText(getActivity(), "Data Field(s) Are Empty",
                            Toast.LENGTH_SHORT).show();
                }
                else {

                }
            }
        });

        existingUserButton = (Button) v.findViewById(R.id.register);
        existingUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId=radioGenderGroup.getCheckedRadioButtonId();
                if (!executeRegister()) {
                    Toast.makeText(getActivity(), "Data Field(s) Are Empty",
                            Toast.LENGTH_SHORT).show();
                }
                else {

                }
            }
        });

        mServerHostEditText = (EditText) v.findViewById(R.id.server_host);
        mServerPortEditText = (EditText) v.findViewById(R.id.server_port);
        mUserNameEditText = (EditText) v.findViewById(R.id.username);
        mPasswordEditText = (EditText) v.findViewById(R.id.password);
        mFirstNameEditText = (EditText) v.findViewById(R.id.first_name);
        mLastNameEditText = (EditText) v.findViewById(R.id.last_name);
        mEmailEditText = (EditText) v.findViewById(R.id.email);
        mFemaleRadioButton = (RadioButton) v.findViewById(R.id.radio_female);
        mMaleRadioButton = (RadioButton) v.findViewById(R.id.radio_male);
        startTimer();
        return v;
    }

    public void startTimer() {
        //set a new Timer
        this.timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
        timer.schedule(timerTask, 500, 500); //
    }

    public void initializeTimerTask() {

        timerTask = new TimerTask() {
            public void run() {

                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    public void run() {
                        //get the current timeStamp
                        Calendar calendar = Calendar.getInstance();
                        if (checkFieldsFilled(false)){
                            button.setBackgroundColor(Color.DKGRAY);
                        }
                        else {
                            button.setBackgroundColor(Color.LTGRAY);
                        }
                        if (checkFieldsFilled(true)){
                            existingUserButton.setBackgroundColor(Color.DKGRAY);
                        }
                        else {
                            existingUserButton.setBackgroundColor(Color.LTGRAY);
                        }
                    }
                });
            }
        };
    }

    private boolean checkFieldsFilled(boolean allFieldsNeeded) {
        if (this.mServerHostEditText.getText().toString().equals("")) {
            return false;
        }
        else if (this.mServerPortEditText.getText().toString().equals("")) {
            return false;
        }
        else if (this.mUserNameEditText.getText().toString().equals("")) {
            return false;
        }
        else if (this.mPasswordEditText.getText().toString().equals("")) {
            return false;
        }
        else if (this.mFirstNameEditText.getText().toString().equals("") && allFieldsNeeded) {
            return false;
        }
        else if (this.mLastNameEditText.getText().toString().equals("") && allFieldsNeeded) {
            return false;
        }
        else if (this.mEmailEditText.getText().toString().equals("") && allFieldsNeeded) {
            return false;
        }
        else {
            return true;
        }
    }

    public boolean executeRegister() {
        if (checkFieldsFilled(true)) {
            String gender;
            if (this.mFemaleRadioButton.isChecked()) {
                gender = "f";
            }
            else {
                gender = "m";
            }

            serverData.setNewUser(new User(this.mUserNameEditText.getText().toString(),
                    this.mPasswordEditText.getText().toString(),
                    this.mEmailEditText.getText().toString(),
                    this.mFirstNameEditText.getText().toString(),
                    this.mLastNameEditText.getText().toString(),
                    gender, null));
            serverData.setServerPort(this.mServerPortEditText.getText().toString());
            serverData.setServerHost(this.mServerHostEditText.getText().toString());

            RegisterTask registerTask = new RegisterTask();
            registerTask.execute();
            return true;
        }
        else {
            return false;
        }
    }

    public boolean executeLogin() {
        if (checkFieldsFilled(false)) {
            serverData.setNewUser(new User(this.mUserNameEditText.getText().toString(),
                                        this.mPasswordEditText.getText().toString(),
                                        null, null, null,
                                null, null));
            serverData.setServerPort(this.mServerPortEditText.getText().toString());
            serverData.setServerHost(this.mServerHostEditText.getText().toString());

            LoginTask loginTask = new LoginTask();
            loginTask.execute();
            return true;
        }
        else {
            return false;
        }
    }


    public class RegisterTask extends AsyncTask<Void, Void, Integer> {
        private ServerData serverData = ServerData.getInstance();

        public RegisterTask() {
        }

        @Override
        protected Integer doInBackground(Void... params) {
            JSONObject registerResult = null;
            try {
                Communicator clientCommunicator = new Communicator();
                serverData.setToken(null);
                registerResult = clientCommunicator.sendRegisterRequest();
                if (registerResult == null) {
                    return 0;
                }
                AuthToken newToken = new AuthToken();
                System.out.println(registerResult.toString());

                if (registerResult.has("message")) {
                    return 2;
                }

                newToken.setAuthorization(registerResult.getString("authToken"));
                newToken.setUserName(registerResult.getString("userName"));
                newToken.setPersonID(registerResult.getString("personID"));
                serverData.setToken(newToken);
                serverData.setUser();
                //clientCommunicator.requestFillUser();
                List<Person> peopleList = JsonResponseParser.parsePeople(clientCommunicator.getPeople());
                serverData.setPeopleList(peopleList);
                List<Event> eventList = JsonResponseParser.parseEvents(clientCommunicator.getEvents());
                serverData.setEventList(eventList);
                return 1;
            }
            catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }

        @Override
        protected void onProgressUpdate(Void... progress) {
        }

        @Override
        protected void onPostExecute(Integer resultType) {
            if (resultType == 1) {

                Toast.makeText(getActivity(), "Registration Of" +
                                serverData.getUser().getUserName() +
                                " Is Successful! \n FirstName: " +
                                serverData.getUser().getFirstName() +
                                " \n LastName: " +
                                serverData.getUser().getLastName() +
                                " \n New AuthToken: " +
                                serverData.getToken().getAuthorization(),
                        Toast.LENGTH_LONG).show();
                serverData.setLoggedIn(true);
                    android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
                    MainActivity mainActivity = (MainActivity) getActivity();
                    fm.beginTransaction()
                            .replace(R.id.mainFrameLayout, new MainMapFragment())
                            .addToBackStack(null)
                            .commit();
                    mainActivity.setIconsVisible();

            }
            else if (resultType == 0) {
                Toast.makeText(getActivity(), "Error During Login...",
                        Toast.LENGTH_LONG).show();
            }
            else if (resultType == 2) {
                Toast.makeText(getActivity(), "User name already taken. Try a different user name",
                        Toast.LENGTH_LONG).show();
            }
        }
    }


    public class LoginTask extends AsyncTask<Void, Void, Integer> {
        private ServerData serverData = ServerData.getInstance();

        public LoginTask() {
        }

        @Override
        protected Integer doInBackground(Void... params) {
            JSONObject loginResult = null;
            try {
                Communicator clientCommunicator = new Communicator();
                serverData.setToken(null);
                loginResult = clientCommunicator.sendLoginRequest();
                if (loginResult == null) {
                    return 0;
                }
                AuthToken newToken = new AuthToken();
                System.out.println(loginResult.toString());

                if (loginResult.has("message")) {
                    return 2;
                }

                newToken.setAuthorization(loginResult.getString("authToken"));
                newToken.setUserName(loginResult.getString("userName"));
                newToken.setPersonID(loginResult.getString("personID"));
                serverData.setToken(newToken);
                serverData.setUser();
                System.out.println(clientCommunicator.getPeople());
                System.out.println(clientCommunicator.getEvents());
                List<Person> peopleList = JsonResponseParser.parsePeople(clientCommunicator.getPeople());
                serverData.setPeopleList(peopleList);
                List<Event> eventList = JsonResponseParser.parseEvents(clientCommunicator.getEvents());
                serverData.setEventList(eventList);
                return 1;
            }
            catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }

        @Override
        protected void onProgressUpdate(Void... progress) {
        }

        @Override
        protected void onPostExecute(Integer resultType) {
            if (resultType == 1) {

                Toast.makeText(getActivity(), "Login Of "  +
                            serverData.getUser().getUserName() +
                            " Is Successful! \n FirstName: " +
                            serverData.getUser().getFirstName() +
                            " \n LastName: " +
                            serverData.getUser().getLastName() +
                            " \n New AuthToken: " +
                            serverData.getToken().getAuthorization(),
                            Toast.LENGTH_LONG).show();
                serverData.setLoggedIn(true);
                android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
                MainActivity mainActivity = (MainActivity) getActivity();
                fm.beginTransaction()
                        .replace(R.id.mainFrameLayout, new MainMapFragment())
                        .addToBackStack(null)
                        .commit();
                mainActivity.setIconsVisible();
            }
            else if (resultType == 0) {
                Toast.makeText(getActivity(), "Error During Registration...",
                        Toast.LENGTH_LONG).show();
            }
            else if (resultType == 2) {
                Toast.makeText(getActivity(), "User Is Not Registered or Password Is Incorrect",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

}
