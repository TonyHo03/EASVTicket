package dk.easv.easvticket.GUI.Controllers;

import dk.easv.easvticket.BE.User;
import dk.easv.easvticket.GUI.Models.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EditUserController implements Initializable {

    @FXML private TextField usernameField;
    @FXML private TextField oldPasswordField;
    @FXML private TextField newPasswordField;
    @FXML private TextField emailField;
    @FXML private ChoiceBox<String> choiceBox;

    private Stage currentStage;
    private User selectedUser;
    private UserModel userModel;

    public void setStage(Stage stage) {
        this.currentStage = stage;
    }

    public void setModel(UserModel userModel) {
        this.userModel = userModel;
    }

    // Called from AdminController after load, to populate fields
    public void setUser(User user) {
        this.selectedUser = user;
        usernameField.setText(user.getUsername());
        emailField.setText(user.getEmail());
        choiceBox.setValue(user.getRole());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceBox.getItems().addAll("Admin", "Event Coordinator");
    }

    @FXML
    private void onClickCancel(ActionEvent actionEvent) {
        currentStage.close();
    }

    @FXML
    private void onClickSave(ActionEvent actionEvent) {
        String newUsername = usernameField.getText().trim();
        String newEmail = emailField.getText().trim();
        String newRole = choiceBox.getValue();
        String newPassword = newPasswordField.getText().trim();

        if (newUsername.isEmpty() || newEmail.isEmpty() || newRole == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Missing fields");
            alert.setContentText("Please fill in username, email and role.");
            alert.showAndWait();
            return;
        }

        selectedUser.setUsername(newUsername);
        selectedUser.setEmail(newEmail);
        selectedUser.setRole(newRole);

        if (!newPassword.isEmpty()) {
            selectedUser.setPassword(newPassword);
        }

        try {
            userModel.updateUser(selectedUser);
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Save failed");
            alert.setContentText("Could not update user: " + e.getMessage());
            alert.showAndWait();
        }
    }

    public TextField getOldPasswordField() {
        return oldPasswordField;
    }

    public void setOldPasswordField(TextField oldPasswordField) {
        this.oldPasswordField = oldPasswordField;
    }
}