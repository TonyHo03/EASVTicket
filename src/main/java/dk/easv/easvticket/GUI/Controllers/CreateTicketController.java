package dk.easv.easvticket.GUI.Controllers;

import dk.easv.easvticket.BE.Event;
import dk.easv.easvticket.BE.Ticket;
import dk.easv.easvticket.BE.TicketTypes;
import dk.easv.easvticket.GUI.Models.EventModel;
import dk.easv.easvticket.GUI.Models.TicketModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class CreateTicketController {

    private Stage currentStage;
    private TicketModel ticketModel;
    private EventModel eventModel;
    private Event selectedEvent;

    @FXML private TextField txtFldCName, txtFldEmail;
    @FXML private ChoiceBox<Event> cbEvent;
    @FXML private ChoiceBox<TicketTypes> cbType;
    @FXML private Spinner<Double> spnPrice;

    public void initializeClass(Stage stage, EventModel eventModel, TicketModel ticketModel, Event selectedEvent) {

        this.currentStage = stage;
        this.eventModel = eventModel;
        this.ticketModel = ticketModel;
        this.selectedEvent = selectedEvent;

        UISetup();

    }

    private void UISetup() {

        cbEvent.getItems().add(selectedEvent);
        cbEvent.getSelectionModel().select(0);

        SpinnerValueFactory<Double> doubleSpinnerValueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 999.0, 0.0, 1.0);
        spnPrice.setValueFactory(doubleSpinnerValueFactory);

        try {

            ObservableList<TicketTypes> ticketTypes = FXCollections.observableArrayList(ticketModel.getTicketTypes(selectedEvent));
            cbType.setItems(ticketTypes);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onCreateBtnClick() {

        String ticketId = "TK-" + UUID.randomUUID();

        clearError();
        boolean hasError = false;

        if (txtFldCName.getText().isBlank()) {
            txtFldCName.getStyleClass().add("error");
            hasError = true;
        }

        if (txtFldEmail.getText().isBlank()) {
            txtFldEmail.getStyleClass().add("error");
            hasError = true;
        }

        if (spnPrice.getValue() == null) {
            spnPrice.getStyleClass().add("error");
            hasError = true;
        }

        if (cbType.getValue() == null) {
            cbType.getStyleClass().add("error");
            hasError = true;
        }

        if (hasError) {
            return;
        }

        try {
            ticketModel.createTicket(new Ticket(ticketId, cbEvent.getValue(), txtFldCName.getText(), txtFldEmail.getText(), spnPrice.getValue(), cbType.getValue()));
            eventModel.refreshEvents();

            for (Event event: eventModel.getEvents()) {
                if (event.getId() == selectedEvent.getId()) {
                    selectedEvent = event;
                    ticketModel.refreshTickets(selectedEvent);
                    System.out.println("Found updated event, replacing old variable.");
                    break;
                }
            }

            currentStage.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onCancelBtnClick() {
        currentStage.close();
    }

    private void clearError() {
        txtFldCName.getStyleClass().remove("error");
        txtFldEmail.getStyleClass().remove("error");
        spnPrice.getStyleClass().remove("error");
        cbType.getStyleClass().remove("error");
    }
}
