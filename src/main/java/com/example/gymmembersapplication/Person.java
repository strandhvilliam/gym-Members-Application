package com.example.gymmembersapplication;

import java.io.Serializable;

public abstract class Person implements Serializable {

    protected String name;
    protected String socialSecurityNumber;

    public Person(String name, String socialSecurityNumber) {
        this.name = name;
        this.socialSecurityNumber = socialSecurityNumber;
    }


    public String getName() {
        return name;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }
}
