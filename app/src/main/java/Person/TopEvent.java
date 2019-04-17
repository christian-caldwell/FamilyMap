package Person;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.example.fmstest2.Holders.PersonListEventChild;

import java.util.ArrayList;
import java.util.List;

import JavaHelpers.EventModelAlgorithms;
import JavaHelpers.PersonModelAlgorithms;
import Model.Event;
import Model.Person;
import Server.ServerData;

public class TopEvent implements ParentObject {

    private List<Object> childrenList;
    private List<Object> parentList;
    public String title;
    private Person person;
    private ServerData serverData;

    public TopEvent(Person person, String title) {
        childrenList = new ArrayList<>();
        this.person = person;
        this.title = title;
        if (title.equals("event")) {
            List<Event> personEventList = EventModelAlgorithms.getPersonEvents(person.getPersonID());
            for (Event event : personEventList) {
                String eventString = event.toString();
                String name = person.getFirstName() + " " + person.getLastName();
                childrenList.add(new PersonListEventChild(eventString, name, "event", null, event));
            }
        }
        else {
            parentList = new ArrayList<>();
            if (PersonModelAlgorithms.getFather(person.getFather()) != null) {
                parentList.add(new PersonListEventChild(PersonModelAlgorithms.getFather(person.getFather()).getFirstName() + " " + PersonModelAlgorithms.getFather(person.getFather()).getLastName(), "FATHER", "male", person, null));
            }
            if (PersonModelAlgorithms.getMother(person.getMother()) != null) {
                parentList.add(new PersonListEventChild(PersonModelAlgorithms.getMother(person.getMother()).getFirstName() + " " + PersonModelAlgorithms.getMother(person.getMother()).getLastName() , "MOTHER", "female", person, null));
            }
        }
    }

    @Override
    public List<Object> getChildObjectList() {
        if (title.equals("event")) {
            return childrenList;
        }
        else {
            return parentList;
        }
    }

    @Override
    public void setChildObjectList(List<Object> list) {
        childrenList = list;
        parentList = list;
    }
}
