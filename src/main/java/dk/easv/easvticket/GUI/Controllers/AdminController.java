package dk.easv.easvticket.GUI.Controllers;

import dk.easv.easvticket.BE.Event;
import dk.easv.easvticket.BE.Roles;
import dk.easv.easvticket.BE.User;
import dk.easv.easvticket.GUI.Models.UserModel;
import dk.easv.easvticket.GUI.Models.EventModel;
import dk.easv.easvticket.GUI.util.TooltipMaker;
import dk.easv.easvticket.MainApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    private Stage currentStage;

    @FXML
    private Button logoutBtn, addUserBtn, editUserBtn, deleteUserBtn, assignCoordBtn, deleteEventBtn;

    @FXML
    private TableView<User> userManageView;

    @FXML
    private TableView<Event> eventManageView;

    @FXML
    private TableColumn<Event, String> clmEventName, clmLocation, clmCoordinators;

    @FXML
    private TableColumn<Event, Date> clmDate;

    @FXML
    private TableColumn<Event, Integer> clmTickets;

    @FXML
    private TableColumn<User, String> clmUsername, clmEmail, clmRole;

    private UserModel userModel;
    private EventModel eventModel;

    public AdminController() {

        try {
            userModel = new UserModel();
            eventModel = new EventModel();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }


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
            addUserController.setModel(userModel);

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
        User selectedUser = userManageView.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No user selected");
            alert.setContentText("Please select a user to edit.");
            alert.showAndWait();
            return;
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/EditUserView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 356, 520);
            Stage stage = new Stage();

            EditUserController editUserController = fxmlLoader.getController();
            editUserController.setStage(stage);
            editUserController.setModel(userModel); // share the model
            editUserController.setUser(selectedUser); // populate fields

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.resizableProperty().setValue(false);
            stage.setTitle("Edit User");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onAssignCoordBtnClick() {

        Event selectedEvent = eventManageView.getSelectionModel().getSelectedItem();

        if (selectedEvent != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/AssignCoordView.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 356, 408);
                Stage stage = new Stage();

                AssignCoordController assignCoordController = fxmlLoader.getController();
                try {
                    assignCoordController.initializeClass(stage, eventModel, userModel.getUsersWithRole(Roles.COORDINATOR), selectedEvent);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                stage.initModality(Modality.APPLICATION_MODAL);

                stage.resizableProperty().setValue(false);

                stage.setTitle("Assign Coordinators");
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void onDeleteUserBtnClick() {
        User selectedUser = userManageView.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No user selected");
            alert.setContentText("Please select a user to delete.");
            alert.showAndWait();
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setHeaderText("Delete User");
        confirmation.setContentText("Are you sure you want to delete \"" + selectedUser.getUsername() + "\"? This action cannot be undone.");

        Optional<ButtonType> result = confirmation.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                userModel.deleteUser(selectedUser);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Deleting User");
                alert.setHeaderText("Could not delete user");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
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


        // User Table View
        userManageView.setItems(userModel.getUsers());
        clmUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        clmEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        clmRole.setCellValueFactory(new PropertyValueFactory<>("role"));

        TooltipMaker.addTooltipsToColumns(clmUsername);
        TooltipMaker.addTooltipsToColumns(clmEmail);
        TooltipMaker.addTooltipsToColumns(clmRole);

        // Event Table View
        eventManageView.setItems(eventModel.getEvents());

        clmEventName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        clmLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        clmCoordinators.setCellValueFactory(new PropertyValueFactory<>("coordinators"));
        clmTickets.setCellValueFactory(new PropertyValueFactory<>("availableTickets"));

        TooltipMaker.addTooltipsToColumns(clmEventName);
        TooltipMaker.addTooltipsToColumns(clmDate);
        TooltipMaker.addTooltipsToColumns(clmLocation);
        TooltipMaker.addTooltipsToColumns(clmCoordinators);
        TooltipMaker.addTooltipsToColumns(clmTickets);

    }
}
