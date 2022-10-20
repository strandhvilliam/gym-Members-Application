package com.example.gymmembersapplication;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    private Member m = new Member("Janne Jansson", "999999-9999", LocalDate.of(2021, 11, 28));

    @Test
    void addSessionDate() {
        m.addSessionDate(LocalDateTime.now());
        assertEquals(1, m.getSessionDateTimes().size());
    }

    @Test
    void getPayedMembershipDate() {
        assert(m.getMembershipDate().equals(LocalDate.of(2021, 11, 28)));
        assert(!m.getMembershipDate().equals(LocalDate.of(2020, 2, 14)));
    }

    @Test
    void setPayedMembershipDate() {
        assert(m.getMembershipDate().equals(LocalDate.of(2021, 11, 28)));
        m.setMembershipDate(LocalDate.of(2022, 10, 15));
        assert(m.getMembershipDate().equals(LocalDate.of(2022, 10, 15)));

    }

    @Test
    void getSessionDates() {
        m.addSessionDate(LocalDateTime.of(2021, 11, 29, 12, 0));
        assert(m.getSessionDateTimes().get(0).equals(LocalDateTime.of(2021, 11, 29, 12, 0)));
        assert(!m.getSessionDateTimes().get(0).equals(LocalDateTime.of(2021, 11, 30, 11, 1)));
    }
}