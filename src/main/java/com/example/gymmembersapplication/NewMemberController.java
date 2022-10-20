package com.example.gymmembersapplication;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class NewMemberController {

    @FXML
    private Button addNewButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField registerNameTextField;

    @FXML
    private TextField registerSsnTextField;

    public Member getNewMember() throws IOException {
        MemberFactory memberFactory = MemberFactory.getInstance();
        Member newMember;
        if (Main.testMode) {
            newMember = memberFactory.createMember("Test Testnamn", "123456-7890");
        } else {
            String name = processInput(registerNameTextField.getText());
            String ssn = processInput(registerSsnTextField.getText());
            newMember = memberFactory.createMember(name, ssn);
        }
        return newMember;
    }

    public String processInput(String in) throws IOException {
        IOManager ioManager = new IOManager();
        if (ioManager.isValidName(in) || ioManager.isValidSocialSecurityNumber(in)) {
            return in;
        } else {
            throw new IOException("Invalid name [FIRSTNAME LASTNAME] or social security number [YYMMDD-XXXX]");
        }
    }
}
