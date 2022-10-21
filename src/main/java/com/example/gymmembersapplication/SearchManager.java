package com.example.gymmembersapplication;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class SearchManager {
    private List<Member> memberList;

    public SearchManager() {
        this.memberList = new ArrayList<>();
    }

    public SearchManager(List<Member> memberList) {
        this.memberList = new ArrayList<>(memberList);
    }

    /**
     * Method trims input and checks if valid.
     * Calls on method from ioManager to check if valid search
     * @param input String to be validated
     * @return trimmed and valid String
     */
    public String validateInput(String input) {
        int minLength = 3;
        String validInput = input.trim();
        String errorMessage;
        IOManager ioManager = new IOManager();

        if (validInput.length() < minLength || validInput.isBlank()) {
            errorMessage = "Input must be at least " + minLength + " characters long";
            throw new IllegalArgumentException(errorMessage);

        } else if(!ioManager.isValidName(validInput) && !ioManager.isValidSocialSecurityNumber(validInput)) {
            errorMessage = "Input must be either a name [FIRSTNAME LASTNAME], or a social security number [YYMMDD-XXXX]";
            throw new IllegalArgumentException(errorMessage);
        }

        return validInput;
    }

    /**
     * Searches for a member in the memberList
     * @param input is the input to search for
     * @return the member that matches the input or null if no match is found
     */
    public Member search(String input) {
        Member validMember = null;
        String validInput = validateInput(input);
        for (Member member : memberList) {
            if (member.getName().equalsIgnoreCase(validInput) || member.getSocialSecurityNumber().equalsIgnoreCase(validInput)) {
                validMember = member;
            }
        }
        if (validMember == null) {
            throw new NoSuchElementException("No member found with that name or social security number");
        }
        return validMember;
    }

}
