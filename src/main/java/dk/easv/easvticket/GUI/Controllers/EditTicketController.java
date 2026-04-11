package dk.easv.easvticket.GUI.Controllers;

import dk.easv.easvticket.BE.Event;
import dk.easv.easvticket.BE.Ticket;
import dk.easv.easvticket.BE.TicketTypes;
import dk.easv.easvticket.GUI.Models.EventModel;
import dk.easv.easvticket.GUI.Models.TicketModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditTicketController {

    @FXML private TextField txtFldCName, txtFldEmail;
    @FXML private ChoiceBox<Event> cbEvent;
    @FXML private ChoiceBox<TicketTypes> cbType;
    @FXML private Spinner<Double> spnPrice;

    private Stage currentStage;
    private EventModel eventModel;
    private TicketModel ticketModel;
    private Event selectedEvent;
    private Ticket selectedTicket;

    public void initializeClass(Stage stage, EventModel eventModel, TicketModel ticketModel, Event selectedEvent, Ticket selectedTicket) {

        this.currentStage = stage;
        this.eventModel = eventModel;
        this.ticketModel = ticketModel;
        this.selectedEvent = selectedEvent;
        this.selectedTicket = selectedTicket;

        UISetup();
    }

    private void UISetup() {

        cbEvent.getItems().add(selectedEvent);
        cbEvent.getSelectionModel().select(0);

        txtFldCName.setText(selectedTicket.getCustomerName());
        txtFldEmail.setText(selectedTicket.getEmail());
        cbType.setValue(selectedTicket.getTicketType());

        SpinnerValueFactory<Double> doubleSpinnerValueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 999.0, 0.0, 1.0);
        spnPrice.setValueFactory(doubleSpinnerValueFactory);

        spnPrice.getValueFactory().setValue(selectedTicket.getPrice());

        try {

            ObservableList<TicketTypes> ticketTypes = FXCollections.observableArrayList(ticketModel.getTicketTypes());
            cbType.setItems(ticketTypes);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onSaveChangesBtnClick() {

        Ticket newTicket = new Ticket(selectedTicket.getId(), selectedTicket.getTicketId(), cbEvent.getValue(), txtFldCName.getText(), txtFldEmail.getText(), spnPrice.getValue(), cbType.getValue());
        try {
            ticketModel.updateTicket(selectedTicket, newTicket);
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
}
