package Server;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
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
    private Map<String, Event> eventMap; //done
    private Map<String, Set<Event>> allEventMap;
    public Set<String> eventTypes; //done
    private Map<String, Float> eventColorMap; //done
    private Map<String, Set<String>> familyTree; //done
    private Set<String> momAncestors;
    private Set<String> dadAncestors;

    private List<Float> colorList;

    private ServerData() {
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



    public Map<String, Person> getPeopleMap() {
        return peopleMap;
    }

    public void setPeopleMap(Map<String, Person> peopleMap) {
        this.peopleMap = peopleMap;
    }

    public Map<String, Event> getEventMap() {
        return eventMap;
    }

    public void setEventMap(Map<String, Event> eventMap) {
        this.eventMap = eventMap;
    }

    public Set<String> getEventTypes() {
        return eventTypes;
    }

    public void setEventTypes() {
        eventTypes = new HashSet<>();
        for (Event event: eventList) {
            eventTypes.add(event.getDescription());
        }
    }

    public Map<String, Float> getEventColorMap() {
        return eventColorMap;
    }

    public void setEventColorMap(Map<String, Float> eventColorMap) {
        this.eventColorMap = eventColorMap;
    }

    public Map<String, Set<String>> getFamilyTree() {
        return familyTree;
    }

    public void setFamilyTree(Map<String, Set<String>> familyTree) {
        this.familyTree = familyTree;
    }

    public Set<String> getMomAncestors() {
        return momAncestors;
    }

    public void setMomAncestors(Set<String> momAncestors) {
        this.momAncestors = momAncestors;
    }

    public Set<String> getDadAncestors() {
        return dadAncestors;
    }

    public void setDadAncestors(Set<String> dadAncestors) {
        this.dadAncestors = dadAncestors;
    }

    public List<Float> getColorList() {
        return colorList;
    }

    public void setColorList(List<Float> colorList) {
        this.colorList = colorList;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public boolean isLogedIn() {
        return loggedIn;
    }

    public void setLogedIn(boolean logedIn) {
        this.loggedIn = logedIn;
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
        for (Person person: peopleList) {
            if (!peopleMap.containsKey(person.getPersonID())) {
                peopleMap.put(person.getPersonID(), person);
            }
        }
    }

    public void sortEvents(List<Event> eventList) {
        Collections.sort(eventList, new Comparator<Event>() {

            public int compare(Event o1, Event o2) {
                return o2.getYear() - o1.getYear();
            }
        });
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

//    public void buildFamilyTree() {
//        familyTree = new HashMap<>();
//        for (Person person: peopleList) {
//            if (!familyTree.containsKey(person.getPersonId())) {
//                familyTree.put(person.getPersonId(), new HashSet<String>());
//            }
//            if (person.getFatherId() != null) {
//                familyTree.get(person.getPersonId()).add(person.getFatherId());
//            }
//            if (person.getMotherId() != null) {
//                familyTree.get(person.getPersonId()).add(person.getMotherId());
//            }
//            if (person.getSpouseId() != null) {
//                familyTree.get(person.getPersonId()).add(person.getSpouseId());
//            }
//        }
//    }

    public void buildEventColorMap() {
        int count = 0;
        eventColorMap = new HashMap<>();
        for (String event: eventTypes) {
            count = count % 9;
            eventColorMap.put(event, colorList.get(count));
            count++;
        }
    }

    public void buildAllEventMap() {
        for (Event event: eventList) {
            if (!allEventMap.containsKey(event.getDescription())) {
                allEventMap.put(event.getDescription(),new HashSet<Event>());
            }
            allEventMap.get(event.getDescription()).add(event);
        }
    }

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

    public static Person getPersonById(String personId) {
        for (Person person: ServerData.getInstance().getPeopleList()) {
            if (person.getPersonID().equals(personId)) {
                return person;
            }
        }
        return null;
    }






}

