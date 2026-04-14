package dk.easv.easvticket.GUI.Controllers;

import dk.easv.easvticket.BE.Roles;
import dk.easv.easvticket.BE.User;
import dk.easv.easvticket.BLL.PasswordEncrypter;
import dk.easv.easvticket.DAL.Interfaces.IPasswordEncrypter;
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

public class CreateUserController implements Initializable {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private TextField emailField;
    @FXML private ChoiceBox<Roles> choiceBox;
    @FXML private Button cancelBtn;
    @FXML private Button createBtn;

    private Stage currentStage;
    private UserModel userModel;
    private IPasswordEncrypter encrypter = new PasswordEncrypter();

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
        String password = encrypter.hashedPassword(passwordField.getText());
        String email = emailField.getText().trim();
        Roles role = choiceBox.getValue();

        clearError();
        boolean hasError = false;

        if (username.isBlank()) {
            usernameField.getStyleClass().add("error");
            hasError = true;
        }

        if (password.isBlank()) {
            passwordField.getStyleClass().add("error");
            hasError = true;
        }

        if (email.isBlank()) {
            emailField.getStyleClass().add("error");
            hasError = true;
        }

        if (role == null) {
            choiceBox.getStyleClass().add("error");
            hasError = true;
        }

        if (hasError) {
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
    private void clearError() {
        usernameField.getStyleClass().remove("error");
        passwordField.getStyleClass().remove("error");
        emailField.getStyleClass().remove("error");
        choiceBox.getStyleClass().remove("error");
    }
}