package Person;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.example.fmstest2.PersonListEventChild;
import com.example.fmstest2.Utils;

import java.util.ArrayList;
import java.util.List;

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
                parentList.add(new PersonListEventChild(Utils.getFather(person.getFather()).getFirstName() + " " + Utils.getFather(person.getFather()).getLastName(), "FATHER", "male", person, null));
            }
            if (Utils.getMother(person.getMother()) != null) {
                parentList.add(new PersonListEventChild(Utils.getMother(person.getMother()).getFirstName() + " " + Utils.getMother(person.getMother()).getLastName() , "MOTHER", "female", person, null));
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
