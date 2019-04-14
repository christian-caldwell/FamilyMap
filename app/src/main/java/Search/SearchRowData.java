package Search;

import com.appdevelopers.seanwade.familymap.FMS;
import com.appdevelopers.seanwade.familymap.model.Event;
import com.appdevelopers.seanwade.familymap.model.Person;

/**
 * Created by seanwade on 3/28/16.
 */
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
        mainText = person.getName();
        secondaryText = "";
        if (person.getGender().equals("m")) {
            type = "male";
        }
        else {
            type = "female";
        }
    }

    public SearchRowData(Event event) {
        FMS fms = FMS.getInstance();
        mainText = event.toString();
        secondaryText = fms.getPeopleMap().get(event.getPersonId()).getName();
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
