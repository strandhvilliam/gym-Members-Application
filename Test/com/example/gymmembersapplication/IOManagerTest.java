package com.example.gymmembersapplication;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class IOManagerTest {

    private String testFileName = "Test/com/example/gymmembersapplication/test.ser";
    private IOManager ioManager = new IOManager();

    @Test
    void saveAndLoadDataTest() throws IOException {
        List<Member> saveData = new ArrayList<>();
        saveData.add(new Member("Anders Andersson", "123456789", LocalDate.of(2022, 10, 16)));
        saveData.add(new Member("Lisa Larsson", "987654321", LocalDate.of(2021, 2, 2)));
        ioManager.serializeData(testFileName, saveData);

        List<Member> loadData = ioManager.deserializeData(testFileName);
        assert(loadData.size() == 2);
        assert(loadData.get(0).getName().equals("Anders Andersson"));
        assert(loadData.get(1).getName().equals("Lisa Larsson"));

        Throwable loadException = assertThrows(IOException.class,
                () -> ioManager.deserializeData("nofile.ser"));

    }

    @Test
    void loadFromTextFileTest() throws IOException {
        List<Member> textData;
        String filename = "Test/com/example/gymmembersapplication/customersTest.txt";
        textData = ioManager.loadFromTextFile(filename);
        assert(textData.size() == 14);
        assert(textData.get(0).getName().equals("Alhambra Aromes"));
        assert(textData.get(0).getSocialSecurityNumber().equals("770302-1234"));
        assert(textData.get(0).getMembershipDate().equals(LocalDate.of(2022, 7, 1)));
        assert(textData.get(1).getName().equals("Bear Belle"));
        assert(textData.get(1).getSocialSecurityNumber().equals("820402-1234"));
        assert(textData.get(1).getMembershipDate().equals(LocalDate.of(2019, 12, 2)));

        Throwable loadException = assertThrows(IOException.class,
                () -> ioManager.loadFromTextFile("notarealfile.txt"));
    }

    @Test
    void createMemberFromTextFileTest() {
        String line1 = "123456789, Lars Larsson";
        String line2 = "2022-10-09";
        Member correctMember = new Member("Lars Larsson", "123456789", LocalDate.of(2022, 10, 9));

        assert(ioManager.createMemberFromTextFile(line1, line2).getName().equals(correctMember.getName()));
        assert(ioManager.createMemberFromTextFile(line1, line2).getSocialSecurityNumber().equals(correctMember.getSocialSecurityNumber()));
        assert(ioManager.createMemberFromTextFile(line1, line2).getMembershipDate().equals(correctMember.getMembershipDate()));

        Throwable createException = assertThrows(IllegalArgumentException.class,
                () -> ioManager.createMemberFromTextFile("55k194-1234, T0rwaldPetterson9", "201-111-111"));
    }

    @Test
    void isValidNameTest() {
        assert(ioManager.isValidName("Lars-Erik Larsson"));
        assert(ioManager.isValidName("Lars Larsson"));
        assert(!ioManager.isValidName("Testar123 med siffror22"));
        assert(!ioManager.isValidName("UtanEfternamn"));
        assert(ioManager.isValidName("Anders Andersson"));
    }

    @Test
    void isValidSocialSecurityNumberTest() {
        assert(ioManager.isValidSocialSecurityNumber("1234567890"));
        assert(ioManager.isValidSocialSecurityNumber("123456-7890"));
        assert(!ioManager.isValidSocialSecurityNumber("11123456-7890"));
        assert(!ioManager.isValidSocialSecurityNumber("111234567890"));
        assert(!ioManager.isValidSocialSecurityNumber("123456"));
        assert(!ioManager.isValidSocialSecurityNumber("12345678911111"));
        assert(!ioManager.isValidSocialSecurityNumber("1234567890-"));
        assert(!ioManager.isValidSocialSecurityNumber("12345Testf111111"));
    }

    @Test
    void isValidDateFormatTest() {
        assert(ioManager.isValidDateFormat("2022-10-09"));
        assert(ioManager.isValidDateFormat("2022-2-9"));
        assert(!ioManager.isValidDateFormat("2022-02-119"));
        assert(!ioManager.isValidDateFormat("222-1-22"));
        assert(!ioManager.isValidDateFormat("Text123123"));
    }

}
