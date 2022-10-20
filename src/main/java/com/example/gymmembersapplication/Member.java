package com.example.gymmembersapplication;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Member extends Person {

    private LocalDate membershipDate;

    private Status memberStatus;

    private List<LocalDateTime> sessionDateTimes;

    public Member(String name, String socialSecurityNumber, LocalDate membershipDate) {
        super(name, socialSecurityNumber);
        this.membershipDate = membershipDate;
        if (membershipDate.isBefore(LocalDate.now().minusYears(1))) {
            this.memberStatus = Status.EXPIRED;
        } else {
            this.memberStatus = Status.ACTIVE;
        }
        this.sessionDateTimes = new ArrayList<>();
    }

    public LocalDate getMembershipDate() {
        return membershipDate;
    }

    public void setMembershipDate(LocalDate membershipDate) {
        this.membershipDate = membershipDate;
    }

    public Status getMemberStatus() {
        return memberStatus;
    }

    public void setActiveStatus() {
        this.memberStatus = Status.ACTIVE;
        this.membershipDate = LocalDate.now();
    }

    public List<LocalDateTime> getSessionDateTimes() {
        return sessionDateTimes;
    }

    public void addSessionDate(LocalDateTime sessionDate) {
        sessionDateTimes.add(sessionDate);
    }

}
