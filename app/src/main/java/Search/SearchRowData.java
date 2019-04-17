package Search;

import Model.Event;
import Model.Person;
import Server.ServerData;

public class SearchRowData {

    private String mainText;
    private String secondaryText;
    private String type;
    public Person person;
    public Event event;

    public SearchRowData() {
        this.mainText = mainText;
        this.secondaryText = secondaryText;
    }

    public SearchRowData(Person person) {
        this.person = person;
        mainText = person.getFirstName() + " " + person.getLastName();
        secondaryText = "";
        if (person.getGender().equals("m")) {
            type = "male";
        }
        else {
            type = "female";
        }
    }

    public SearchRowData(Event event) {
        ServerData fms = ServerData.getInstance();
        mainText = event.toString();
        secondaryText = fms.getPeopleMap().get(event.getPersonID()).getFirstName() + fms.getPeopleMap().get(event.getPersonID()).getLastName();
        type = "location";
        this.event = event;
    }

    public String getMainText() {
        return mainText;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public String getSecondaryText() {
        return secondaryText;
    }

    public void setSecondaryText(String secondaryText) {
        this.secondaryText = secondaryText;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
