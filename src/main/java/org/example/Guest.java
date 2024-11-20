package org.example;

public class Guest {
    public String name = "";
    public String surname = "";
    //add other fields if necessary

    public Guest(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }
}
