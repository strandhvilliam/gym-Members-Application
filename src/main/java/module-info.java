module com.example.gymmembersapplication {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.gymmembersapplication to javafx.fxml;
    exports com.example.gymmembersapplication;
}