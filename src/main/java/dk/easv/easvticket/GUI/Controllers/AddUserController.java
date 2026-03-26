package dk.easv.easvticket.GUI.Controllers;

import dk.easv.easvticket.BE.Roles;
import dk.easv.easvticket.BE.User;
import dk.easv.easvticket.BLL.PasswordEncrypter;
import dk.easv.easvticket.GUI.Models.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddUserController implements Initializable {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private TextField emailField;
    @FXML private ChoiceBox<Roles> choiceBox;
    @FXML private Button cancelBtn;
    @FXML private Button createBtn;

    private Stage currentStage;
    private UserModel userModel;

    public void setStage(Stage stage) {
        this.currentStage = stage;
    }

    public void setModel(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceBox.getItems().addAll(Roles.values());
    }

    @FXML
    private void onClickCancel(ActionEvent actionEvent) {
        currentStage.close();
    }

    @FXML
    private void onClickSave(ActionEvent actionEvent) {
        String username = usernameField.getText().trim();
        String password = PasswordEncrypter.hashedPassword(passwordField.getText().trim());
        String email = emailField.getText().trim();
        Roles role = choiceBox.getValue();

        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || role == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Missing fields");
            alert.setContentText("Please fill in all fields and select a role.");
            alert.showAndWait();
            return;
        }

        if (password.contains(" ")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Invalid password");
            alert.setContentText("Your password can not contain a whitespace.");
            alert.showAndWait();
            return;
        }

        try {
            User newUser = new User(username, password, email, role.name());
            userModel.createUser(newUser);
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Create failed");
            alert.setContentText("Could not create user: " + e.getMessage());
            alert.showAndWait();
        }
    }
}