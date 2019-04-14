package Person;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.ArrayList;
import java.util.List;

import com.example.fmstest2.Main.Utils;
import Model.Event;
import Model.Person;
import Server.ServerData;

public class TopEvent implements ParentObject {

    private List<Object> childrenList;
    private List<Object> parentList;
    public String title;
    private Person person;
    private ServerData fms;

    public TopEvent(Person person, String title) {
        childrenList = new ArrayList<>();
        this.person = person;
        this.title = title;
        if (title.equals("event")) {
            List<Event> personEventList = Utils.getPersonEvents(person.getPersonID());
            for (Event event : personEventList) {
                String eventString = event.toString();
                String name = person.getFirstName() + " " + person.getLastName();
                childrenList.add(new PersonListEventChild(eventString, name, "event", null, event));
            }
        }
        else {
           parentList = new ArrayList<>();
            if (Utils.getFather(person.getFather()) != null) {
                parentList.add(new PersonListEventChild(Utils.getFather(person.getFather()).getDescendant(), "FATHER", "male", person, null));
            }
            if (Utils.getMother(person.getMother()) != null) {
                parentList.add(new PersonListEventChild(Utils.getMother(person.getMother()).getDescendant(), "MOTHER", "female", person, null));
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
