package com.example.gymmembersapplication;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;

public class MainController implements Initializable {


    @FXML
    private TextField searchTextField;
    @FXML
    private Button loadFromFileButton;
    @FXML
    private Button registerMemberButton;
    @FXML
    private TableView<Member> tableView;
    @FXML
    public TableColumn<Member, String> nameColumn;
    @FXML
    private TableColumn<Member, String> socialSecurityNumberColumn;
    @FXML
    private TableColumn<Member, Status> memberStatusColumn;
    @FXML
    private TableColumn<Member, LocalDate> membershipDateColumn;

    @FXML
    private TableColumn<Member, String> buttonColumn;

    private List<Member> memberList = new ArrayList<>();
    private SearchManager searchManager;

    /**
     * Event that is triggered when the user clicks enter in the search text field.
     */
    @FXML
    public void searchEvent() {
        String input;
        input = searchTextField.getText();
        searchManager = new SearchManager(memberList);

        try {
            Member member = searchManager.search(input);
            displayProfile(member);
        } catch (IllegalArgumentException | NoSuchElementException | IOException e) {
            displayAlert(e.getMessage());
        }
    }

    /**
     * Event that is triggered when the user clicks the load from file button.
     * Method is void and not testable, but calls on methods that have unit tests.
     */
    @FXML
    public void loadFromFileEvent() {
        try {
            IOManager ioManager = new IOManager();
            Path path = ioManager.chooseFile();
            List<Member> membersFromTextFile = ioManager.loadFromTextFile(path.toString());
            memberList.addAll(membersFromTextFile);
            updateMemberTableView(memberList);
        } catch (IllegalArgumentException  | IOException e) {
            displayAlert(e.getMessage());
        } catch (NullPointerException e) {
            String errorMessage = "No file was chosen.";
            displayAlert(errorMessage);
        }
    }

    /**
     * Event that is triggered when the user clicks the register member button.
     */
    @FXML
    public void registerMemberEvent() {
        try {
            displayNewMemberDialog();
        } catch (IOException e) {
            displayAlert(e.getMessage());
        }
    }

    /**
     * Method is initialized when fxmlLoader loads the fxml file in Main class.
     * Deserializes data from file and updates the table view.
     * Sets the buttons to show profile in tableview cells.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        IOManager ioManager = new IOManager();
        try {
            memberList = ioManager.deserializeData("data.ser");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        updateMemberTableView(memberList);

        /* Setting up the button to open corresponding rows profile
           There is probably a better way to do it, but it works
         */
        buttonColumn.setCellFactory(tc -> { //lambda expression for setting up the button
                    TableCell<Member, String> cell = new TableCell<>() {
                        @Override
                        public void updateItem(String optionalText, boolean empty) {
                            super.updateItem(optionalText, empty);
                            Button button = new Button();
                            button.setId("showProfileButton"); //custom css styling

                            if (!empty) { //if row does contain values
                                String imageFileName = "file:src/main/resources/com/example/gymmembersapplication/icons/icons8_forward_16px.png";
                                button.setGraphic(new ImageView(new Image(imageFileName))); //sets button graphic to image
                                button.setCursor(Cursor.HAND);
                                button.setOnAction(event -> { //event that is triggered when button is clicked
                                    Member member = getTableView().getItems().get(getIndex()); //gets the member object from the row
                                    try {
                                        displayProfile(member);
                                    } catch (IOException e) {
                                        System.out.println("Error displaying profile");
                                    }
                                });
                            }
                            setGraphic(button); //sets the cell graphic to the button, if the row is empty the button will be empty
                        }
                    };
            return cell; //return cell to be added to the table
        });
    }

    /**
     * Updates the table view with the list of members
     * Sets color of tablecolumn values containing member status
     * Serializes data everytime the table view is updated
     * @param memberList the list of members
     */
    public void updateMemberTableView(List<Member> memberList) {
        String n = "name", ssn = "socialSecurityNumber", ms = "memberStatus", md = "membershipDate";
        nameColumn.setCellValueFactory(new PropertyValueFactory<>(n));
        socialSecurityNumberColumn.setCellValueFactory(new PropertyValueFactory<>(ssn));
        memberStatusColumn.setCellValueFactory(new PropertyValueFactory<>(ms));
        membershipDateColumn.setCellValueFactory(new PropertyValueFactory<>(md));
        ObservableList<Member> observableList = FXCollections.observableArrayList(memberList);
        tableView.setItems(observableList);

        memberStatusColumn.setCellFactory(col -> { //lambda expression for coloring the status column
            TableCell<Member, Status> cell = new TableCell<>() {
                @Override
                public void updateItem(Status status, boolean empty) {
                    super.updateItem(status, empty);
                    if (status == Status.ACTIVE) {
                        String activeId = "table-cell-active";
                        setText(status.getStatusAsString());
                        setId(activeId);
                    } else if (status == Status.EXPIRED) {
                        String expiredId = "table-cell-expired";
                        setText(status.getStatusAsString());
                        setId(expiredId);
                    }
                }
            };
            return cell;
        });

        IOManager ioManager = new IOManager();
        try {
            ioManager.serializeData("data.ser", memberList);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Initializes GUI elements to display member profile
     * @param member the member to display
     * @throws IOException if the fxml file is not found
     */
    public void displayProfile(Member member) throws IOException {
        String fxmlFileName = "profile.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
        Stage stage = new Stage();
        Scene scene = new Scene(loader.load());
        stage.setResizable(false);
        stage.setScene(scene);
        ProfileController controller = loader.getController();
        controller.initializeData(member);
        stage.show();

        stage.setOnHidden(e -> {
            updateMemberTableView(memberList);
            tableView.refresh();
        });
    }

    /**
     * Initializes GUI elements to display new member dialog
     * @throws IOException if the fxml file is not found
     * @throws IOException
     */
    public void displayNewMemberDialog() throws IOException {
        String fxmlFileName = "registerMember.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("New Member");
        dialog.setResizable(false);
        dialog.getDialogPane().setContent(loader.load());
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.getDialogPane().getStylesheets().add(
                getClass().getResource("styles.css").toExternalForm());
        //adding specific css styling to buttons
        dialog.getDialogPane().lookupButton(ButtonType.OK).getStyleClass().add("registerButton");
        dialog.getDialogPane().lookupButton(ButtonType.CANCEL).getStyleClass().add("loadButton");

        Optional<ButtonType> result = dialog.showAndWait(); //optional is the result of the dialog buttons
        if (result.isPresent() && result.get() == ButtonType.OK) {
            NewMemberController controller = loader.getController();
            Member member = controller.getNewMember();
            memberList.add(member);
            updateMemberTableView(memberList);
            tableView.refresh();
        }
    }

    /**
     * Displays an alert with the given message
     * @param message the message to display
     */
    public void displayAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        String title = "Error";
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.getDialogPane().getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        alert.getDialogPane().lookupButton(ButtonType.OK).getStyleClass().add("registerButton");
        alert.showAndWait();
    }

}