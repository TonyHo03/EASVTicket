package dk.easv.easvticket.GUI.Controllers;

import dk.easv.easvticket.BE.Event;
import dk.easv.easvticket.BE.Ticket;
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

public class CoordinatorController implements Initializable {
    private Stage currentStage;

    @FXML
    private Button addEventBtn, assignCoordBtn, editEventBtn, deleteEventBtn, buyTicketBtn, printBtn, logoutBtn;

    @FXML
    private TableView<Ticket> ticketManageView;

    @FXML
    private TableView<Event> eventManageView;

    @FXML
    private TableColumn<Event, String> clmEventName, clmLocation, clmCoordinators;

    @FXML
    private TableColumn<Event, Date> clmDate;

    @FXML
    private TableColumn<Event, Integer> clmTickets;

    @FXML
    private TableColumn<Ticket, String> clmTicketId, clmCustomer, clmEmail, clmStatus;

    @FXML
    private TableColumn<Ticket, Event> clmEvent;

    @FXML
    private TableColumn<Ticket, Integer> clmPrice;

    private ObservableList<Ticket> ticketObservableList = FXCollections.observableArrayList();

    private ObservableList<Event> eventObservableList = FXCollections.observableArrayList();

    @FXML
    private void onLogoutBtnClick() {

        Stage stage = new Stage();

        MainApplication.startMain(stage);

        currentStage.close();

    }

    @FXML
    private void onAddEventBtnClick() {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/CreateEventView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 356, 526);
            Stage stage = new Stage();

            CreateEventController createEventController = fxmlLoader.getController();
            createEventController.setStage(stage);

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.resizableProperty().setValue(false);

            stage.setTitle("Create Event");
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
    private void onEditEventBtnClick() {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/EditEventView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 356, 526);
            Stage stage = new Stage();

            EditEventController editEventController = fxmlLoader.getController();
            editEventController.setStage(stage);

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.resizableProperty().setValue(false);

            stage.setTitle("Edit Event");
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {

            e.printStackTrace();

        }

    }
    @FXML
    private void onDeleteEventBtnClick() {

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setHeaderText("Delete Event");
        confirmation.setContentText("Are you sure you want to delete this event? This action cannot be undone.");

        Optional<ButtonType> result = confirmation.showAndWait();

    }

    @FXML
    private void onBuyTicketBtnClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/BuyTicketView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 356, 526);
            Stage stage = new Stage();

            BuyTicketController buyTicketController = fxmlLoader.getController();
            buyTicketController.setStage(stage);

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.resizableProperty().setValue(false);

            stage.setTitle("Create Tickets");
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {

            e.printStackTrace();

        }
    }

    @FXML
    private void onPrintBtnClick() {

    }

    public void setStage(Stage stage) {

        this.currentStage = stage;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Styling

        addEventBtn.getStyleClass().add("Invisible_Buttons");
        assignCoordBtn.getStyleClass().add("Invisible_Buttons");
        editEventBtn.getStyleClass().add("Invisible_Buttons");
        deleteEventBtn.getStyleClass().add("Invisible_Buttons");
        buyTicketBtn.getStyleClass().add("Invisible_Buttons");
        printBtn.getStyleClass().add("Invisible_Buttons");

        // Test Data

        List<User> coordList = new ArrayList<>();
        Event newEvent = new Event("Test Event", Date.valueOf(LocalDate.now()), "Esbjerg", coordList, 100);
        coordList.add(new User("John Coordinator", "johncoord", "johncoord@gmail.com", "Event Coordinator"));
        ticketObservableList.add(new Ticket("TKT-1770376529733-5O2MEIB04", newEvent, "a", "a@a.a", 50, "Active"));

        eventObservableList.add(newEvent);

        // Ticket Table View

        clmTicketId.setCellValueFactory(new PropertyValueFactory<>("ticketId"));
        clmEvent.setCellValueFactory(new PropertyValueFactory<>("event"));
        clmCustomer.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        clmEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        clmPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        clmStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        ticketManageView.setItems(ticketObservableList);

        TooltipMaker.addTooltipsToColumns(clmTicketId);
        TooltipMaker.addTooltipsToColumns(clmEvent);
        TooltipMaker.addTooltipsToColumns(clmCustomer);
        TooltipMaker.addTooltipsToColumns(clmEmail);
        TooltipMaker.addTooltipsToColumns(clmPrice);
        TooltipMaker.addTooltipsToColumns(clmStatus);

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
