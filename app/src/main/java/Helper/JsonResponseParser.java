package Helper;

import android.util.Log;

import com.appdevelopers.seanwade.familymap.model.Event;
import com.appdevelopers.seanwade.familymap.model.Person;
import com.appdevelopers.seanwade.familymap.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import Model.User;

public class JsonResponseParser {

    public static User parseUser(JSONObject jsonUser, User user) {
        try {
            user.setAuthorizeCode(jsonUser.getString("Authorization"));
            user.setUserName(jsonUser.getString("userName"));
            user.setPersonId(jsonUser.getString("personId"));
            return user;
        } catch (JSONException e) {
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
                    person.setPersonId(jsonPerson.getString("personID"));
                    person.setFirstName(jsonPerson.getString("firstName"));
                    person.setLastName(jsonPerson.getString("lastName"));
                    person.setGender(jsonPerson.getString("gender"));
                    person.setSpouseId(jsonPerson.getString("spouse"));
                }
                catch (JSONException e) {

                }
                try {
                    person.setFatherId(jsonPerson.getString("father"));
                    person.setMotherId(jsonPerson.getString("mother"));
                }
                catch (JSONException e) {

                }
                personList.add(person);
            }
            return personList;

        } catch (JSONException e) {
            Log.e("JSON", "Json exception in parsePeople.");
            return null;
        }
    }

    public static List<Event> parseEvents(JSONObject jsonList) {
        try {
            JSONArray data = jsonList.getJSONArray("data");
            List<Event> eventList = new LinkedList<>();
            for (int i = 0; i < data.length(); i++) {
                Event event = new Event();
                JSONObject jsonEvent = (JSONObject) data.get(i);
                event.setEventId(jsonEvent.getString("eventID"));
                event.setPersonId(jsonEvent.getString("personID"));
                event.setLatitude(jsonEvent.getString("latitude"));
                event.setLongitude(jsonEvent.getString("longitude"));
                event.setCountry(jsonEvent.getString("country"));
                event.setCity(jsonEvent.getString("city"));
                event.setDescription(jsonEvent.getString("description").toLowerCase());
                event.setYear(jsonEvent.getString("year"));
                event.setDescendant(jsonEvent.getString("descendant"));
                eventList.add(event);
            }
            return eventList;

        } catch (JSONException e) {
            Log.e("JSON", "Json exception in parseEvent.");
            return null;
        }
    }
}
