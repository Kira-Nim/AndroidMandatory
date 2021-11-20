package com.example.android_mandatory.Model;

import java.util.ArrayList;

public class Symptom {

    private String name;
    private String id;
    private ArrayList<Registration> registrations = new ArrayList<Registration>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Registration> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(ArrayList<Registration> registrations) {
        this.registrations = registrations;
    }
}
