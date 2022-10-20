package com.example.gymmembersapplication;

import java.time.LocalDate;

public class MemberFactory {

    private static MemberFactory instance;

    private MemberFactory() {}

    public static MemberFactory getInstance() {
        if (instance == null) {
            instance = new MemberFactory();
        }
        return instance;
    }

    /**
     * Overloaded methods to create new member objects.
     * @param name Name of the member.
     * @param socialSecurityNumber Social security number of the member.
     * @membershipdate Membership date of the member.
     * @return new Member object.
     */
    public Member createMember(String name, String socialSecurityNumber) {
        return new Member(name, socialSecurityNumber, LocalDate.now());
    }
    public Member createMember(String name, String socialSecurityNumber, String membershipDate) {
        return new Member(name, socialSecurityNumber, LocalDate.parse(membershipDate));
    }
}
