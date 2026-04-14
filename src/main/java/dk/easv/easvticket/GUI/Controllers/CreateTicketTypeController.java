package dk.easv.easvticket.GUI.Controllers;

import dk.easv.easvticket.BE.Event;
import dk.easv.easvticket.BE.TicketTypes;
import dk.easv.easvticket.GUI.Models.TicketModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateTicketTypeController {

    @FXML private TextField txtFldType;

    private TicketModel ticketModel;
    private Stage currentStage;
    private Event selectedEvent;

    @FXML
    private void onCreateBtnClick() {

        if (!txtFldType.getText().isBlank()) {

            try {
                ticketModel.createType(new TicketTypes(txtFldType.getText(), selectedEvent.getId()));
                currentStage.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }
        else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("MISSING FIELD!");
            alert.setContentText("Please insert a name for ticket type!");

        }

    }

    @FXML
    private void onCancelBtnClick() {

        currentStage.close();

    }

    public void initializeClass(Stage currentStage, TicketModel ticketModel, Event selectedEvent) {

        this.currentStage = currentStage;
        this.ticketModel = ticketModel;
        this.selectedEvent = selectedEvent;

    }

}
