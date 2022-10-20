package com.example.gymmembersapplication;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class SearchManagerTest {

    private List<Member> testList = List.of(
            new Member("Anders Andersson", "123456-7890", LocalDate.of(2021, 11, 28)),
            new Member("Karl Karlsson", "987654-3210", LocalDate.of(2022, 5, 2)),
            new Member("Mira Mikaelsson",  "111111-9999", LocalDate.of(2021, 3, 21)));

    private SearchManager sm = new SearchManager(testList);

    @Test
    void searchTest() {

        assert(sm.search("Anders Andersson") == testList.get(0));
        assert(sm.search("Mira Mikaelsson") == testList.get(2));
        assert(sm.search("Mira Mikaelsson") != testList.get(1));

        assert(sm.search("123456-7890") == testList.get(0));
        assert(sm.search("987654-3210") == testList.get(1));
        assert(sm.search("987654-3210") != testList.get(0));

        Throwable e1 = assertThrows(NoSuchElementException.class, () -> {
            sm.search("Joakim Svensson");
        });

        Throwable e2 = assertThrows(NoSuchElementException.class, () -> {
            sm.search("555555-1234");
        });

    }

    @Test
    void validateInputTest() {

        assert(sm.validateInput("Anders Andersson").equals("Anders Andersson"));
        assert(sm.validateInput("   Karl Karlsson      ").equals("Karl Karlsson"));
        assert(sm.validateInput("123456-7890").equals("123456-7890"));
        assert(!sm.validateInput("111111-9999").equals("987654-3210"));

        Throwable e1 = assertThrows(IllegalArgumentException.class,
                () -> sm.validateInput("Testar33att22göra33fel"));
        Throwable e2 = assertThrows(IllegalArgumentException.class,
                () -> sm.validateInput(" "));
    }




}



/*

calling method

boolean test = true;

if (test) {
    userSearch = "";
    List<Member> testList = new ArrayList<>();
    SearchManager.search(userSearch, testList);
} else {
    userSearch = textField.getText();
    List<Member> searchList = new ArrayList<>(memberList);
    SearchManager.search(userSearch, memberList);
    }
 */
