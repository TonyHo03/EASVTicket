package dk.easv.easvticket.GUI.Controllers;

import dk.easv.easvticket.BE.*;
import dk.easv.easvticket.Facade.ControllerModelFacade;
import dk.easv.easvticket.GUI.Models.EventModel;
import dk.easv.easvticket.GUI.Models.TicketModel;
import dk.easv.easvticket.GUI.util.DateTimeFormatting;
import dk.easv.easvticket.GUI.util.TooltipMaker;
import dk.easv.easvticket.MainApplication;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CoordinatorController {
    @FXML private Button addEventBtn, assignCoordBtn, editEventBtn, deleteEventBtn, createTicketBtn, printBtn, logoutBtn, backBtn;
    @FXML private Label lblHeader;
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
    private int selectedEventId;

    private ControllerModelFacade facade;

    public CoordinatorController() {}

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
            createEventController.initializeClass(stage, facade.eventModel);

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
        System.out.println(selected);

        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Please select an event to assign coordinators to.").showAndWait();
            return;
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/AssignCoordView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();

            AssignCoordController assignCoordController = fxmlLoader.getController();

            try {
                assignCoordController.initializeClass(stage, facade.eventModel, facade.userModel.getUsersWithRole(Roles.Coordinator), selected);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

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
            editEventController.initializeClass(stage, selected, facade.eventModel, () -> eventManageView.refresh());

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
        confirmation.setContentText("Are you sure you want to delete \"" + selected.getName() + "\"?");

        Optional<ButtonType> result = confirmation.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            try {
                facade.eventModel.deleteEvent(selected);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void onAddTypeBtnClick() {

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/CreateTicketTypeView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();

            CreateTicketTypeController createTicketTypeController = fxmlLoader.getController();
            createTicketTypeController.initializeClass(stage, facade.ticketModel, getSelectedEvent(selectedEventId));

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.resizableProperty().setValue(false);

            stage.setTitle("Create Ticket Type");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    @FXML
    private void onCreateTicketBtnClick() {

        Event selectedEvent = getSelectedEvent(selectedEventId);
        if (selectedEvent.getAvailableTickets() > 0) {
            try {

                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/CreateTicketView.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();

                CreateTicketController createTicketController = fxmlLoader.getController();
                createTicketController.initializeClass(stage, facade.eventModel, facade.ticketModel, selectedEvent);

                stage.initModality(Modality.APPLICATION_MODAL);
                stage.resizableProperty().setValue(false);

                stage.setTitle("Create Ticket");
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {

                e.printStackTrace();

            }
        }
        else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No available ticket left");
            alert.setContentText("There are no available tickets for this event. Please try a different event or check back later.");
            alert.showAndWait();

        }
    }

    @FXML
    private void onEditTicketBtnClick() {
        try {
            Ticket selectedTicket = ticketManageView.getSelectionModel().getSelectedItem();
            Event selectedEvent = getSelectedEvent(selectedEventId);

            if (selectedTicket != null) {

                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/EditTicketView.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();

                EditTicketController editTicketController = fxmlLoader.getController();
                editTicketController.initializeClass(stage, facade.eventModel, facade.ticketModel, selectedEvent, selectedTicket);

                stage.initModality(Modality.APPLICATION_MODAL);
                stage.resizableProperty().setValue(false);

                stage.setTitle("Edit Ticket");
                stage.setScene(scene);
                stage.show();
            }
            else {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Could not find selected ticket.");
                alert.setContentText("Please select a ticket before attempting to edit.");
                alert.showAndWait();

            }
        }
        catch (Exception e) {

            e.printStackTrace();

        }
    }

    @FXML
    private void onDeleteTicketBtnClick() {

        Ticket selectedTicket = ticketManageView.getSelectionModel().getSelectedItem();
        Event selectedEvent = getSelectedEvent(selectedEventId);


        if (selectedTicket != null) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Delete ticket");
            alert.setContentText("Are you sure you want to delete this ticket?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    facade.ticketModel.deleteTicket(selectedTicket);
                    facade.eventModel.refreshEvents();

                    for (Event event: facade.eventModel.getEvents()) {
                        if (event.getId() == selectedEvent.getId()) {
                            selectedEvent = event;
                            facade.ticketModel.refreshTickets(selectedEvent);
                            System.out.println("Found updated event, replacing old variable.");
                            break;
                        }
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Could not find selected ticket");
            alert.setContentText("Please select a ticket before attempting to delete.");
            alert.showAndWait();

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
            ticketPDFController.initializeClass(stage, facade.ticketModel, selectedTicket);

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.resizableProperty().setValue(false);

            stage.setTitle("Ticket PDF Viewer");
            stage.setScene(scene);
            stage.show();

        }
        catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No selected ticket.");
            alert.setContentText("Please select a ticket before attempting to print or send.");
            alert.showAndWait();

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
            if (search.isBlank()) return true;
            return event.getName().toLowerCase().contains(search) ||
                    event.getDate().toString().toLowerCase().contains(search) ||
                    event.getLocation().toString().toLowerCase().contains(search) ||
                    event.getCoordinators().stream().map(User::getUsername).collect(Collectors.joining(", ")).toLowerCase().contains(search);
        });
    }

    public void initializeClass(Stage currentStage, String username, ControllerModelFacade facade) {

        this.currentStage = currentStage;
        lblHeader.setText("Welcome, " + username);
        this.facade = facade;

        UISetup();

    }

    private Event getSelectedEvent(int eventId) {

        for (Event event: eventManageView.getItems()) {

            if (event.getId() == eventId) {

                return event;

            }

        }

        return null;

    }

    private void UISetup() {

        eventManageCard.setManaged(true);
        ticketManageCard.setManaged(false);
        eventManageCard.setVisible(true);
        ticketManageCard.setVisible(false);
        eventManageCard.setPrefHeight(360);
        ticketManageCard.setPrefHeight(360);

        eventManageView.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                Event selectedEvent = eventManageView.getSelectionModel().getSelectedItem();
                if (selectedEvent != null) {
                    selectedEventId = selectedEvent.getId();
                    FilteredList<Ticket> filteredList = new FilteredList<>(facade.ticketModel.getTickets());
                    filteredList.setPredicate(ticket -> ticket.getEvent().getName().equals(selectedEvent.getName()));
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

        //eventManageView.setItems(facade.eventModel.getEvents());

        TooltipMaker.addTooltipsToColumns(clmEventName);
        TooltipMaker.addTooltipsToColumns(clmDate);
        TooltipMaker.addTooltipsToColumns(clmLocation);
        TooltipMaker.addTooltipsToColumns(clmCoordinators);
        TooltipMaker.addTooltipsToColumns(clmTickets);

        DateTimeFormatting.changeFormat(clmDate);

        filteredEvents = new FilteredList<>(facade.eventModel.getEvents());
        SortedList<Event> sortedEvents = new SortedList<>(filteredEvents);
        sortedEvents.comparatorProperty().bind(eventManageView.comparatorProperty());
        eventManageView.setItems(sortedEvents);

        eventSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                filterEvents();
            } catch (IOException e) {
               e.printStackTrace();
            }
        });
    }
}