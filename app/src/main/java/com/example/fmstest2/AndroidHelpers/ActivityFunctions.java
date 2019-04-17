package com.example.fmstest2.AndroidHelpers;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.example.fmstest2.R;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import Model.Event;
import Model.Person;
import Server.ServerData;

/**
 * This is a static class that contains usefull functions for the application.
 */
public class ActivityFunctions {

    public static Drawable getGenderIcon(Activity activity, String gender) {
        if (gender.equals("m")) {
            return new IconDrawable(activity, Iconify.IconValue.fa_male)
                    .colorRes(R.color.line_red)
                    .sizeDp(40);
        }
        else {
            return new IconDrawable(activity, Iconify.IconValue.fa_female)
                    .colorRes(R.color.pink)
                    .sizeDp(40);
        }
    }

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

    public static List<Person> getChildren(String personId) {
        List<Person> childrenList = new ArrayList<>();
        for (Person person : ServerData.getInstance().getPeopleList()) {
            if (person.getFather().equals(personId) || person.getMother().equals(personId)) {
                childrenList.add(person);
            }
        }
        return childrenList;
    }



    public static void sortEvents(List<Event> eventList) {
        Collections.sort(eventList, new Comparator<Event>() {

            public int compare(Event o1, Event o2) {
                return o2.getYear() - o1.getYear();
            }
        });
    }


}
