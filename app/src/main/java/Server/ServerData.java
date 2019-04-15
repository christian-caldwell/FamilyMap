package Server;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Model.AuthToken;
import Model.Event;
import Model.Person;
import Model.User;

public class ServerData {
    private static ServerData instance = null;
    private User user;
    private User newUser;
    private AuthToken token;
    private String serverPort;
    private String serverHost;
    private boolean loggedIn;
    private boolean toastThrown = false;

    private List<Person> peopleList;
    private List<Event> eventList;

    private Map<String, Person> peopleMap;  // done

    public Set<String> eventTypes; //done
    private Map<String, Set<String>> familyTree; //done
    private List<Float> colorList;
    private Map<String, Float> eventColorMap; //done
    private Map<String, Event> eventMap; //done

    private ServerData() {
        peopleList = new LinkedList<>();
        eventList = new LinkedList<>();
        colorList = new ArrayList<>();
        colorList.add(BitmapDescriptorFactory.HUE_BLUE);
        colorList.add(BitmapDescriptorFactory.HUE_RED);
        colorList.add(BitmapDescriptorFactory.HUE_GREEN);
        colorList.add(BitmapDescriptorFactory.HUE_MAGENTA);
        colorList.add(BitmapDescriptorFactory.HUE_ORANGE);
        colorList.add(BitmapDescriptorFactory.HUE_AZURE);
        colorList.add(BitmapDescriptorFactory.HUE_YELLOW);
        colorList.add(BitmapDescriptorFactory.HUE_VIOLET);
        colorList.add(BitmapDescriptorFactory.HUE_ROSE);
        loggedIn = false;
    }

    public static ServerData getInstance() {
        if (instance == null) {
            instance = new ServerData();
        }
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser() {
        this.user = this.newUser;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean isToastThrown() {
        return toastThrown;
    }

    public void setToastThrown(boolean toastThrown) {
        this.toastThrown = toastThrown;
    }

    public AuthToken getToken() {
        return token;
    }

    public void setToken(AuthToken token) {
        this.token = token;
    }

    public User getNewUser() {
        return newUser;
    }

    public void setNewUser(User newUser) {
        this.newUser = newUser;
    }


    ///////////////////////////////////

    public Set<String> getEventTypes() {
        return eventTypes;
    }

    public Map<String, Float> getEventColorMap() {
        return eventColorMap;
    }


    public Map<String, Set<String>> getFamilyTree() {
        return familyTree;
    }


    public Map<String, Person> getPeopleMap() {
        return peopleMap;
    }


    public void setEventTypes() {
        eventTypes = new HashSet<>();
        for (Event event: eventList) {
            eventTypes.add(event.getDescription());
        }
    }

    ///////////////////////////

    public void buildFamilyTree() {
        familyTree = new HashMap<>();
        for (Person person: peopleList) {
            String personId = person.getPersonID();
            if (!familyTree.containsKey(personId)) {
                Set<String> parentSet = new HashSet<>();
                parentSet.add(person.getFather());
                parentSet.add(person.getMother());
                familyTree.put(personId, parentSet);
            }
        }
    }
    public void buildEventColorMap() {
        int count = 0;
        eventColorMap = new HashMap<>();
        for (String event: eventTypes) {
            count = count % 9;
            eventColorMap.put(event, colorList.get(count));
            count++;
        }
    }

    public Person getPersonById(String personId) {
        for (Person person: getPeopleList()) {
            if (person.getPersonID().equals(personId)) {
                return person;
            }
        }
        return null;
    }

    public Event getEarliestEvent(String personId) {
        Event shortestEvent = new Event();
        shortestEvent.setYear(99999);
        for (Event event: eventList) {
            if (event.getPersonID().equals(personId)) {
                if (event.getYear() < shortestEvent.getYear()) {
                    shortestEvent = event;
                }
            }
        }
        return shortestEvent;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
        eventMap = new HashMap<>();
        for (Event event: eventList) {
            if (!eventMap.containsKey(event.getEventID())) {
                eventMap.put(event.getEventID(), event);
            }
        }
        setEventTypes();
    }

    public List<Person> getPeopleList() {
        return peopleList;
    }

    public void setPeopleList(List<Person> peopleList) {
        this.peopleList = peopleList;
        peopleMap = new HashMap<>();
        for (Person person : peopleList) {
            if (!peopleMap.containsKey(person.getPersonID())) {
                peopleMap.put(person.getPersonID(), person);
            }
        }
    }


    public List<Event> getEventList() {
        return eventList;
    }

}

