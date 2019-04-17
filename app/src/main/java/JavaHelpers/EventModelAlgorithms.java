package JavaHelpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import Model.Event;
import Server.ServerData;

/**
 * Created by crc4444 on 4/17/19.
 */

public class EventModelAlgorithms {
    public static List<Event> getPersonEvents(String personId) {
        List<Event> personEventList = new ArrayList<>();
        for (Event event : ServerData.getInstance().getInstance().getEventList()) {
            if (event.getPersonID().equals(personId)) {
                personEventList.add(event);
            }
        }
        sortEvents(personEventList);
        Collections.reverse(personEventList);
        return personEventList;
    }

    public static void sortEvents(List<Event> eventList) {
        Collections.sort(eventList, new Comparator<Event>() {

            public int compare(Event o1, Event o2) {
                return o2.getYear() - o1.getYear();
            }
        });
    }
}
