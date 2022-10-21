package com.example.gymmembersapplication;

import javafx.stage.FileChooser;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class IOManager {

    /**
     * Method deserializes a file and returns a list of members
     * @param fileName string containing name of file
     * @return list with data to be serialized
     * @throws IOException if not possible to read file, throws exception
     */
    public List<Member> deserializeData(String fileName) throws IOException {
        List<Member> data;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            data = (List<Member>) in.readObject();
            return data;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new IOException("Class not found");
        }
    }

    /**
     * Method serialises a list of members to a file
     * @param fileName destination for serialized data
     * @param data list with data to be serialized
     */
    public void serializeData(String fileName, List<Member> data) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(data);
        }

    }

    /**
     * Method reads a textfile and returns a list of members
     * @param filename string containing name of file
     * @return list of read members
     * @throws IOException
     */
    public List<Member> loadFromTextFile(String filename) throws IOException {
        List<Member> textData = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line1, line2;
            while ((line1 = reader.readLine()) != null) {
                line2 = reader.readLine();
                Member member = createMemberFromTextFile(line1, line2);
                textData.add(member);
            }
        }
        return textData;
    }

    /**
     * Methods takes two strings as input and creates a member object from them.
     * Calls methods to validate the strings.
     * @param line1 first line containing name and social security number
     * @param line2 second line containing date
     * @throws IllegalArgumentException if the format of the strings are not valid
     * @return a member object
     */
    public Member createMemberFromTextFile(String line1, String line2) {
        MemberFactory memberFactory = MemberFactory.getInstance(); //get singleton instance
        String errorMessage = "Invalid format on data";
        try {
            String[] parts = line1.split(",");
            String name = parts[1].trim();
            String socialSecurityNumber = parts[0].trim();
            String date = line2.trim();

            if(isValidName(name) || isValidSocialSecurityNumber(socialSecurityNumber) || isValidDateFormat(date)) {
                //adds dash if missing from social security number
                if (socialSecurityNumber.length() == 10) {
                    socialSecurityNumber = socialSecurityNumber.substring(0, 6) + "-" + socialSecurityNumber.substring(6);
                }

                return memberFactory.createMember(name, socialSecurityNumber, date);

            } else {
                throw new IllegalArgumentException(errorMessage);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException(errorMessage);
        }

    }

    /**
     * Method takes a string as input and uses regex to check if valid name.
     * @param name string to be checked
     * @return true if valid, false if not
     */
    public boolean isValidName(String name) {
        String namePattern = "[A-Za-z-]+\\s[A-Za-z-]+";
        return Pattern.matches(namePattern, name);
    }

    /**
     * Method takes a string as input and uses regex to check if valid social security number.
     * @param ssn string to be checked
     * @return true if valid, false if not
     */
    public boolean isValidSocialSecurityNumber(String ssn) {
        String idPattern1 = "[0-9]{6}-[0-9]{4}";
        String idPattern3 = "[0-9]{10}";
        return Pattern.matches(idPattern1, ssn)
                || Pattern.matches(idPattern3, ssn);
    }

    /**
     * Method takes a string as input and uses regex to check if valid date format.
     * @param date string to be checked
     * @return true if valid, false if not
     */
    public boolean isValidDateFormat(String date) {
        String datePattern1 = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
        String datePattern2 = "[0-9]{4}-[0-9]-[0-9]";
        return Pattern.matches(datePattern1, date) || Pattern.matches(datePattern2, date);
    }

    /**
     * Method allows user to choose a file to load
     * (not testable since requires input but other crucial methods are tested)
     * @return path to selected file
     */
    public Path chooseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files", "*.txt"));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        return Paths.get(fileChooser.showOpenDialog(null).getAbsolutePath());
    }
}
