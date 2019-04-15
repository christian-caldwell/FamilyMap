package com.example.fmstest2;

import android.graphics.drawable.Drawable;

import Model.Event;
import Model.Person;

public class PersonListEventChild {

    private String eventString;
    private String personName;
    private String type;
    public Person person;
    public Event event;

    public PersonListEventChild(String eventString, String personName, String type, Person person, Event event) {
        this.eventString = eventString;
        this.personName = personName;
        this.type = type;
        this.event = event;
        this.person = person;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEventString() {
        return eventString;
    }

    public void setEventString(String eventString) {
        this.eventString = eventString;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }
}
