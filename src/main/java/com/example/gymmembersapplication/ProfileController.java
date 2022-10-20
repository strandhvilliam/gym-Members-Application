package com.example.gymmembersapplication;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ProfileController {

    @FXML
    private Button profileAddButton;
    @FXML
    private Label profileDateLabel;
    @FXML
    private ListView<String> profileListView;
    @FXML
    private Label profileNameLabel;
    @FXML
    private Button profileRenewButton;
    @FXML
    private Label profileStatusLabel;
    @FXML
    private Label profileTotalLabel;
    @FXML
    private Label profileSsnLabel;

    private Member member;


    /**
     * Event that is triggered when the user clicks the add button.
     * Not testable, but the addSessionDate() method is tested in member class.
     */
    @FXML
    public void addClickEvent() {
        LocalDateTime newDateTime = LocalDateTime.now();
        member.addSessionDate(newDateTime);
        updateListView(member.getSessionDateTimes());
    }

    /**
     * Event that is triggered when the user clicks the renew button.
     * Not testable, but the setMembershipDate() and setActiveStatus()
     * methods are tested in member class.
     */
    @FXML
    public void renewClickEvent() {
        member.setMembershipDate(LocalDate.now());
        member.setActiveStatus();
        updateLabels();
    }

    /**
     * Method initializes the profile data.
     * @param m is the member that is displayed in the profile.
     */
    public void initializeData(Member m) {
        member = m;
        updateLabels();
        updateListView(member.getSessionDateTimes());
    }


    /**
     * Method updates the list view with the session dates and total number of sessions.
     * @param sessionDates is the list of session dates.
     */
    public void updateListView(List<LocalDateTime> sessionDates) {
        String formatPattern = "yyyy-MM-dd\t\tHH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatPattern);
        profileListView.getItems().clear();
        profileTotalLabel.setText(String.valueOf(sessionDates.size()));
        for (LocalDateTime dateTime : sessionDates) {
            profileListView.getItems().add(dateTime.format(formatter));
        }
    }

    /**
     * Method updates the labels with the current membership status,
     * date, name and social security number.
     */
    public void updateLabels() {
        profileDateLabel.setText(member.getMembershipDate().toString());
        profileNameLabel.setText(member.getName());
        profileStatusLabel.setText(member.getMemberStatus().toString());
        profileSsnLabel.setText(member.getSocialSecurityNumber());
        if(member.getMemberStatus().equals(Status.EXPIRED)) {
            String expiredId = "status-expired-label";
            profileStatusLabel.setId(expiredId);
        } else {
            String activeId = "status-active-label";
            profileStatusLabel.setId(activeId);
        }
    }

}