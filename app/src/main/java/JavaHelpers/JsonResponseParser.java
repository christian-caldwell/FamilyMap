package JavaHelpers;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import Model.AuthToken;
import Model.Event;
import Model.Person;
import Model.User;
import Server.ServerData;

public class JsonResponseParser {

    public static User parseUser(JSONObject jsonUser, User user) {
        try {
            AuthToken token = new AuthToken();
            token.setAuthorization(jsonUser.getString("Authorization"));
            token.setUserName(user.getUserName());
            token.setPersonID(user.getPersonId());
            ServerData.getInstance().setToken(token);
            user.setUserName(jsonUser.getString("userName"));
            user.setPersonId(jsonUser.getString("personId"));
            return user;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("JSON", "Json exception in parseUser.");
            return user;
        }
    }

    public static List<Person> parsePeople(JSONObject jsonList) {
        try {
            JSONArray data = jsonList.getJSONArray("data");
            List<Person> personList = new LinkedList<>();
            for (int i = 0; i < data.length(); i++) {
                Person person = new Person();
                JSONObject jsonPerson = (JSONObject) data.get(i);
                try {
                    person.setDescendant(jsonPerson.getString("descendant"));
                    person.setPersonID(jsonPerson.getString("personID"));
                    person.setFirstName(jsonPerson.getString("firstName"));
                    person.setLastName(jsonPerson.getString("lastName"));
                    person.setGender(jsonPerson.getString("gender"));
                    if (jsonPerson.has("spouse")) {
                        person.setSpouse(jsonPerson.getString("spouse"));
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    if (jsonPerson.has("father")) {
                        person.setFather(jsonPerson.getString("father"));
                    }
                    if (jsonPerson.has("mother")) {
                        person.setMother(jsonPerson.getString("mother"));
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                personList.add(person);
            }
            return personList;

        } catch (JSONException e) {
            Log.e("JSON", "Json exception in parsePeople.");
            e.printStackTrace();
            return null;
        }
    }

    public static List<Person> parsePeople() {

        List<Person> personList = new LinkedList<>();
        Person person = new Person();
        ServerData data = ServerData.getInstance();

        person.setDescendant(data.getUser().getUserName());
        person.setPersonID(data.getUser().getPersonId());
        person.setFirstName(data.getUser().getFirstName());
        person.setLastName(data.getUser().getLastName());
        person.setGender(data.getUser().getGender());
        person.setSpouse("");
        person.setFather("");

        personList.add(person);

        return personList;
    }

    public static List<Event> parseEvents(JSONObject jsonList) {
        try {
            JSONArray data = jsonList.getJSONArray("data");
            List<Event> eventList = new LinkedList<>();
            for (int i = 0; i < data.length(); i++) {
                Event event = new Event();
                JSONObject jsonEvent = (JSONObject) data.get(i);
                event.setEventID(jsonEvent.getString("eventID"));
                event.setPersonID(jsonEvent.getString("personID"));
                Double jsonDouble = Double.parseDouble(jsonEvent.getString("latitude"));
                event.setLatitude(jsonDouble);
                jsonDouble = Double.parseDouble(jsonEvent.getString("longitude"));
                event.setLongitude(jsonDouble);
                event.setCountry(jsonEvent.getString("country"));
                event.setCity(jsonEvent.getString("city"));
                event.setDescription(jsonEvent.getString("description").toLowerCase());
                Integer jsonInteger = Integer.parseInt(jsonEvent.getString("year"));
                event.setYear(jsonInteger);
                event.setDescendant(jsonEvent.getString("descendant"));
                eventList.add(event);
            }
            return eventList;

        } catch (JSONException e) {
            Log.e("JSON", "Json exception in parseEvent.");
            e.printStackTrace();
            return null;
        }
    }

    public static List<Event> parseEvents() {

        List<Event> eventList = new LinkedList<>();

        Event event = new Event();
        event.setEventID("testEvent");
        event.setPersonID(ServerData.getInstance().getUser().getPersonId());
        event.setLatitude(new Double(1));
        event.setLongitude(new Double(1));
        event.setCountry("testLocation");
        event.setCity("testCity");
        event.setDescription("testDescription");
        event.setYear(1);
        event.setDescendant(ServerData.getInstance().getUser().getUserName());
        eventList.add(event);

        return eventList;
    }

}
