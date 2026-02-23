package dk.easv.easvticket.GUI.Controllers;

import dk.easv.easvticket.BE.Event;
import dk.easv.easvticket.BE.User;
import dk.easv.easvticket.GUI.util.TooltipMaker;
import dk.easv.easvticket.MainApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
    private TableColumn<User, String> clmName, clmUsername, clmEmail, clmRole;

    private ObservableList<User> userObservableList = FXCollections.observableArrayList();

    private ObservableList<Event> eventObservableList = FXCollections.observableArrayList();




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

        // Test Data

        userObservableList.add(new User("Ismail", "ismahm01", "ismahm01@easv365.dk", "Admin"));

        List<User> coordList = new ArrayList<>();
        coordList.add(new User("John Coordinator", "johncoord", "johncoord@gmail.com", "Event Coordinator"));

        eventObservableList.add(new Event("Test Event", Date.valueOf(LocalDate.now()), "Esbjerg", coordList, 100));

        // User Table View

        clmName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        clmEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        clmRole.setCellValueFactory(new PropertyValueFactory<>("role"));

        userManageView.setItems(userObservableList);

        TooltipMaker.addTooltipsToColumns(clmName);
        TooltipMaker.addTooltipsToColumns(clmUsername);
        TooltipMaker.addTooltipsToColumns(clmEmail);
        TooltipMaker.addTooltipsToColumns(clmRole);

        // Event Table View

        clmEventName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        clmLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        clmCoordinators.setCellValueFactory(new PropertyValueFactory<>("coordinators"));
        clmTickets.setCellValueFactory(new PropertyValueFactory<>("availableTickets"));

        eventManageView.setItems(eventObservableList);

        TooltipMaker.addTooltipsToColumns(clmEventName);
        TooltipMaker.addTooltipsToColumns(clmDate);
        TooltipMaker.addTooltipsToColumns(clmLocation);
        TooltipMaker.addTooltipsToColumns(clmCoordinators);
        TooltipMaker.addTooltipsToColumns(clmTickets);

    }
}
