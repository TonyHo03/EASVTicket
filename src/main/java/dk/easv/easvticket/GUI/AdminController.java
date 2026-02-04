package dk.easv.easvticket.GUI;

import dk.easv.easvticket.MainApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    private Stage currentStage;

    @FXML
    private Button logoutBtn, addUserBtn, editUserBtn, deleteUserBtn, assignCoordBtn, deleteEventBtn;

    @FXML
    private void onLogoutBtnClick() {

        Stage stage = new Stage();

        MainApplication.startMain(stage);

        currentStage.close();

    }

    @FXML
    private void onAddUserBtnClick() {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/AddUserView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 356, 471);
            Stage stage = new Stage();

            AddUserController addUserController = fxmlLoader.getController();
            addUserController.setStage(stage);

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.resizableProperty().setValue(false);

            stage.setTitle("Add Users");
            stage.setScene(scene);
            stage.show();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    @FXML
    private void onEditUserBtnClick() {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/EditUserView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 356, 520);
            Stage stage = new Stage();

            EditUserController editUserController = fxmlLoader.getController();
            editUserController.setStage(stage);

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.resizableProperty().setValue(false);

            stage.setTitle("Edit Users");
            stage.setScene(scene);
            stage.show();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    @FXML
    private void onAssignCoordBtnClick() {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/AssignCoordView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 356, 408);
            Stage stage = new Stage();

            AssignCoordController assignCoordController = fxmlLoader.getController();
            assignCoordController.setStage(stage);

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.resizableProperty().setValue(false);

            stage.setTitle("Assign Coordinators");
            stage.setScene(scene);
            stage.show();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    @FXML
    private void onDeleteUserBtnClick() {

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setHeaderText("Delete User");
        confirmation.setContentText("Are you sure you want to delete this user? This action cannot be undone.");

        Optional<ButtonType> result = confirmation.showAndWait();

    }

    @FXML
    private void onDeleteEventBtnClick() {

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setHeaderText("Delete Event");
        confirmation.setContentText("Are you sure you want to delete this event? This action cannot be undone.");

        Optional<ButtonType> result = confirmation.showAndWait();

    }

    public void setStage(Stage stage) {

        this.currentStage = stage;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        addUserBtn.getStyleClass().add("Invisible_Buttons");
        editUserBtn.getStyleClass().add("Invisible_Buttons");
        deleteUserBtn.getStyleClass().add("Invisible_Buttons");
        assignCoordBtn.getStyleClass().add("Invisible_Buttons");
        deleteEventBtn.getStyleClass().add("Invisible_Buttons");

    }
}
