package dk.easv.easvticket.GUI.Controllers;

import dk.easv.easvticket.BE.Event;
import dk.easv.easvticket.BE.Location;
import dk.easv.easvticket.BE.User;
import dk.easv.easvticket.GUI.Models.EventModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CreateEventController implements Initializable {

    private Stage currentStage;
    private EventModel eventModel;

    @FXML private TextField txtFldName, txtFldVenueName, txtFldAddress, txtFldCity;
    @FXML private TextArea txtAreaDesc;
    @FXML private DatePicker dtPicker;
    @FXML private Spinner<Integer> spnTotalTickets;

    public void initializeClass(Stage stage, EventModel eventModel) {
        this.currentStage = stage;
        this.eventModel = eventModel;
    }

    public void onCreateBtnClick(ActionEvent actionEvent) {
        String eventName = txtFldName.getText().strip();
        String descr = txtAreaDesc.getText().strip();
        LocalDate date = dtPicker.getValue();
        Integer tickets = spnTotalTickets.getValue();
        String venueName = txtFldVenueName.getText().strip();
        String address = txtFldAddress.getText().strip();
        String city = txtFldCity.getText().strip();

        if (eventName.isEmpty() || descr.isEmpty() || date == null || tickets == null || venueName.isEmpty() || address.isEmpty() || city.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Missing fields");
            alert.setContentText("Please fill out all fields.");
            alert.showAndWait();
            return;
        }

        try {
            Location location = new Location(venueName, address, city);

            Event newEvent = new Event(eventName, Date.valueOf(date), location, new ArrayList<User>(), tickets, tickets, descr);
            eventModel.createEvent(newEvent);

            currentStage.close();

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Create failed");
            alert.setContentText("Could not create event: " + e.getMessage());
            alert.showAndWait();
        }
    }

    public void onCancelBtnClick(ActionEvent actionEvent) {
        currentStage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SpinnerValueFactory<Integer> integerSpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 999, 1, 1);
        spnTotalTickets.setValueFactory(integerSpinnerValueFactory);
    }
}
