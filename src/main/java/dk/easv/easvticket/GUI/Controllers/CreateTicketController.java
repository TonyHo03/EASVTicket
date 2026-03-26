package dk.easv.easvticket.GUI.Controllers;

import dk.easv.easvticket.BE.Event;
import dk.easv.easvticket.BE.Ticket;
import dk.easv.easvticket.BE.TicketTypes;
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
    private Event selectedEvent;

    @FXML private TextField txtFldCName, txtFldEmail;
    @FXML private ChoiceBox<Event> cbEvent;
    @FXML private ChoiceBox<TicketTypes> cbType;
    @FXML private Spinner<Double> spnPrice;

    @FXML
    private void onCreateBtnClick() {

        String ticketId = "TK-" + UUID.randomUUID();

        if (txtFldCName.getText().isBlank() || txtFldEmail.getText().isBlank() || cbType.getValue() == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("MISSING FIELDS!");
            alert.setContentText("Please insert values into the missing fields!");
            alert.showAndWait();

        }
        else {
            try {
                ticketModel.createTicket(new Ticket(ticketId, cbEvent.getValue(), txtFldCName.getText(), txtFldEmail.getText(), spnPrice.getValue(), cbType.getValue()));
                currentStage.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void onCancelBtnClick() {

        currentStage.close();

    }

    public void initializeClass(Stage stage, TicketModel ticketModel, Event selectedEvent) {

        this.currentStage = stage;
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

            ObservableList<TicketTypes> ticketTypes = FXCollections.observableArrayList(ticketModel.getTicketTypes());
            cbType.setItems(ticketTypes);

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
