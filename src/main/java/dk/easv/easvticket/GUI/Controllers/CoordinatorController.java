package dk.easv.easvticket.GUI.Controllers;

import dk.easv.easvticket.BE.*;
import dk.easv.easvticket.GUI.Models.EventModel;
import dk.easv.easvticket.GUI.Models.TicketModel;
import dk.easv.easvticket.GUI.util.DateTimeFormatting;
import dk.easv.easvticket.GUI.util.TooltipMaker;
import dk.easv.easvticket.MainApplication;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CoordinatorController implements Initializable {
    @FXML private Button addEventBtn, assignCoordBtn, editEventBtn, deleteEventBtn, createTicketBtn, printBtn, logoutBtn, backBtn;
    @FXML private TableView<Ticket> ticketManageView;
    @FXML private TableView<Event> eventManageView;
    @FXML private TableColumn<Event, String> clmEventName, clmLocation, clmCoordinators;
    @FXML private TableColumn<Event, LocalDateTime> clmDate;
    @FXML private TableColumn<Event, Integer> clmTickets;
    @FXML private TableColumn<Ticket, String> clmTicketId, clmCustomer, clmEmail;
    @FXML private TableColumn<Ticket, TicketTypes> clmType;
    @FXML private TableColumn<Ticket, Event> clmEvent;
    @FXML private TableColumn<Ticket, Integer> clmPrice;
    @FXML private TextField eventSearch;
    @FXML private VBox eventManageCard, ticketManageCard;

    private FilteredList<Event> filteredEvents;
    private Stage currentStage;
    private Event selectedEvent;

    private EventModel eventModel;
    private TicketModel ticketModel;

    public CoordinatorController() {

        try {
            eventModel = new EventModel();
            ticketModel  = new TicketModel();
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
    private void onAddEventBtnClick() {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/CreateEventView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();

            CreateEventController createEventController = fxmlLoader.getController();
            createEventController.initializeClass(stage, eventModel);

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
        Event selected = eventManageView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Please select an event to assign coordinators to.").showAndWait();
            return;
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/AssignCoordView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();

            AssignCoordController assignCoordController = fxmlLoader.getController();

            assignCoordController.initializeClass(stage, eventModel, selected.getCoordinators(), selected);

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
        Event selected = eventManageView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Please select an event to be edited.").showAndWait();
            return;
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/EditEventView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();

            EditEventController editEventController = fxmlLoader.getController();
            editEventController.initializeClass(stage, selected, eventModel, () -> eventManageView.refresh());

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
        Event selected = eventManageView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Please select an event to be deleted.").showAndWait();
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setHeaderText("Delete Event");
        confirmation.setContentText("Are you sure you want to archive \"" + selected.getName() + "\"?");

        Optional<ButtonType> result = confirmation.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            try {
                System.out.println("About to archive: " + selected.getName() + " with ID: " + selected.getId());

                // 1. CALL THE ARCHIVE METHOD (This runs the specific SQL for isDeleted = 1)
                eventModel.archiveEvent(selected);

                // 2. REFRESH THE FILTER (This makes it vanish from the TableView)
                filteredEvents.setPredicate(event -> !event.isDeleted());

                System.out.println("Archive completed successfully");
                new Alert(Alert.AlertType.INFORMATION, "Event archived successfully.").showAndWait();
            } catch (Exception e) {
                System.out.println("Archive FAILED: " + e.getMessage());
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to archive event: " + e.getMessage()).showAndWait();
            }
        }
    }

    @FXML
    private void onCreateTicketBtnClick() {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/CreateTicketView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();

            CreateTicketController createTicketController = fxmlLoader.getController();
            createTicketController.initializeClass(stage, eventModel, ticketModel, selectedEvent);

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

        Ticket selectedTicket = ticketManageView.getSelectionModel().getSelectedItem();

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/TicketPDFView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();

            TicketPDFController ticketPDFController = fxmlLoader.getController();
            ticketPDFController.initializeClass(stage, ticketModel, selectedTicket);

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.resizableProperty().setValue(false);

            stage.setTitle("Ticket PDF Viewer");
            stage.setScene(scene);
            stage.show();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onBackBtnClick() {

        backBtn.setVisible(false);
        eventManageCard.setManaged(true);
        ticketManageCard.setManaged(false);
        eventManageCard.setVisible(true);
        ticketManageCard.setVisible(false);

    }

    @FXML
    private void filterEvents() throws IOException {
        String search = eventSearch.getText().toLowerCase();

        filteredEvents.setPredicate(event -> {
            if (event.isDeleted()) return false;
            if (search.isBlank()) return true;
            return event.getName().toLowerCase().contains(search) ||
                    event.getDate().toString().toLowerCase().contains(search) ||
                    event.getLocation().toString().toLowerCase().contains(search) ||
                    event.getCoordinators().stream().map(User::getUsername).collect(Collectors.joining(", ")).toLowerCase().contains(search);
        });
    }

    public void setStage(Stage stage) {

        this.currentStage = stage;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        eventManageCard.setManaged(true);
        ticketManageCard.setManaged(false);
        eventManageCard.setVisible(true);
        ticketManageCard.setVisible(false);
        eventManageCard.setPrefHeight(360);
        ticketManageCard.setPrefHeight(360);

        eventManageView.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                selectedEvent = eventManageView.getSelectionModel().getSelectedItem();
                if (selectedEvent != null) {
                    FilteredList<Ticket> filteredList = new FilteredList<>(ticketModel.getTickets());
                    filteredList.setPredicate(ticket -> {

                        return ticket.getEvent().getName().equals(selectedEvent.getName());

                    });
                    ticketManageView.setItems(filteredList);
                    eventManageCard.setManaged(false);
                    ticketManageCard.setManaged(true);

                    eventManageCard.setVisible(false);
                    ticketManageCard.setVisible(true);

                    backBtn.setVisible(true);
                }
            }
        });

        // Ticket Table View

        clmTicketId.setCellValueFactory(new PropertyValueFactory<>("ticketId"));
        clmEvent.setCellValueFactory(new PropertyValueFactory<>("event"));
        clmCustomer.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        clmEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        clmPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        clmType.setCellValueFactory(new PropertyValueFactory<>("ticketType"));

        //ticketManageView.setItems(ticketModel.getTickets());

        TooltipMaker.addTooltipsToColumns(clmTicketId);
        TooltipMaker.addTooltipsToColumns(clmEvent);
        TooltipMaker.addTooltipsToColumns(clmCustomer);
        TooltipMaker.addTooltipsToColumns(clmEmail);
        TooltipMaker.addTooltipsToColumns(clmPrice);
        TooltipMaker.addTooltipsToColumns(clmType);

        // Event Table View

        clmEventName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        clmLocation.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getLocation().getVenueName()));
        clmCoordinators.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCoordinators().stream().map(User::getUsername).collect(Collectors.joining(", "))));
        clmTickets.setCellValueFactory(new PropertyValueFactory<>("availableTickets"));

        eventManageView.setItems(eventModel.getEvents());

        TooltipMaker.addTooltipsToColumns(clmEventName);
        TooltipMaker.addTooltipsToColumns(clmDate);
        TooltipMaker.addTooltipsToColumns(clmLocation);
        TooltipMaker.addTooltipsToColumns(clmCoordinators);
        TooltipMaker.addTooltipsToColumns(clmTickets);

        DateTimeFormatting.changeFormat(clmDate);

        filteredEvents = new FilteredList<>(eventModel.getEvents(), event -> !event.isDeleted());
        eventManageView.setItems(filteredEvents);

        eventSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                filterEvents();
            } catch (IOException e) {
               e.printStackTrace();
            }
        });
    }
}
