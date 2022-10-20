package com.example.gymmembersapplication;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class NewMemberControllerTest {

    private NewMemberController testCtrl = new NewMemberController();


    @Test
    void getNewMemberTest() throws IOException {
        assert(testCtrl.getNewMember().getName().equals("Test Testnamn"));
        assert(testCtrl.getNewMember().getSocialSecurityNumber().equals("123456-7890"));

    }

    @Test
    void processInputTest() throws IOException {
        assert(testCtrl.processInput("Test Testnamn").equals("Test Testnamn"));
        assert(testCtrl.processInput("112233-4455").equals("112233-4455"));

        Throwable processException = assertThrows(IOException.class,
                () -> testCtrl.processInput("Ol3g Ol3gsson"));

        Throwable processException2 = assertThrows(IOException.class,
                () -> testCtrl.processInput("1234"));
    }
}