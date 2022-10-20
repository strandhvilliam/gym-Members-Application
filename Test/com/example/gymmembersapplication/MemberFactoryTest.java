package com.example.gymmembersapplication;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class MemberFactoryTest {


    private final MemberFactory memberFactory = MemberFactory.getInstance();


    @Test
    void createMemberTest() {
        Member m1 = memberFactory.createMember("Anders Andersson", "123456789");
        assert(m1.getName().equals("Anders Andersson"));
        assert(m1.getSocialSecurityNumber().equals("123456789"));
        assert(m1.getMembershipDate().equals(LocalDate.now()));
        assert(!m1.getMembershipDate().equals(LocalDate.of(2021, 11, 28)));
    }

}
