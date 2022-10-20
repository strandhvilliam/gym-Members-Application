package com.example.gymmembersapplication;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class PersonTest {

    Person p = new Member("Karl Karlsson", "123456-7890", LocalDate.of(2021, 11, 28));

    @Test
    void getName() {
        assert(p.getName().equals("Karl Karlsson"));
        assert(!p.getName().equals("Karl Karlssonsson"));
    }

    @Test
    void getSocialSecurityNumber() {
        assert(p.getSocialSecurityNumber().equals("123456-7890"));
        assert(!p.getSocialSecurityNumber().equals("123456-7891"));
    }
}