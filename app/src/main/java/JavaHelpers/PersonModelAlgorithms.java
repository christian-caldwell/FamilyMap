package JavaHelpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Model.Event;
import Model.Person;
import Server.ServerData;

/**
 * Created by crc4444 on 4/17/19.
 */

public class PersonModelAlgorithms {


    public static Person getFather(String personId) {
        for (Person person : ServerData.getInstance().getInstance().getPeopleList()) {
            if (person.getPersonID() != null && person.getPersonID().equals(personId)) {
                return person;
            }
        }
        return null;
    }

    public static Person getMother(String personId) {
        for (Person person : ServerData.getInstance().getPeopleList()) {
            if (person.getPersonID() != null && person.getPersonID().equals(personId)) {
                return person;
            }
        }
        return null;
    }
}
