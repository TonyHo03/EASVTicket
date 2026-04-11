package dk.easv.easvticket.GUI.Controllers;

import dk.easv.easvticket.BE.Event;
import dk.easv.easvticket.BE.Location;
import dk.easv.easvticket.GUI.Models.EventModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.awt.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CreateEventController implements Initializable {

    private Stage currentStage;
    private EventModel eventModel;

    @FXML private TextField txtFldName, txtFldVenueName, txtFldAddress, txtFldCity;
    @FXML private TextArea txtAreaDesc;
    @FXML private DatePicker dtPicker;
    @FXML private Spinner<Integer> spnTotalTickets;
    @FXML private Spinner<LocalTime> spnTime;

    public void initializeClass(Stage stage, EventModel eventModel) {
        this.currentStage = stage;
        this.eventModel = eventModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SpinnerValueFactory<Integer> integerSpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 999, 0, 1);
        spnTotalTickets.setValueFactory(integerSpinnerValueFactory);

        // "TimePicker" using javafx only. Source - https://stackoverflow.com/q/32613619.
        SpinnerValueFactory<LocalTime> localTimeSpinnerValueFactory = new SpinnerValueFactory<>() {

            {
                setConverter(new StringConverter<>() {
                    @Override
                    public String toString(LocalTime time) {
                        return (time == null) ? "" : String.format("%02d:%02d", time.getHour(), time.getMinute());
                    }

                    @Override
                    public LocalTime fromString(String string) {
                        try {
                            return LocalTime.parse(string);
                        } catch (Exception e) {
                            return getValue();
                        }
                    }
                });

                setValue(LocalTime.of(0, 0));
            }

            @Override
            public void decrement(int steps) {
                setValue(getValue().minusMinutes(steps * 15L));
            }

            @Override
            public void increment(int steps) {
                setValue(getValue().plusMinutes(steps * 15L));
            }
        };
        spnTime.setValueFactory(localTimeSpinnerValueFactory);
        spnTime.getEditor().setPromptText("e.g. '12:00'");

        txtFldName.textProperty().addListener((obs, oldVal, newVal) -> txtFldName.getStyleClass().remove("error"));
        txtAreaDesc.textProperty().addListener((obs, oldVal, newVal) -> txtAreaDesc.getStyleClass().remove("error"));
        spnTotalTickets.valueProperty().addListener((obs, oldVal, newVal) -> spnTotalTickets.getStyleClass().remove("error"));
        spnTotalTickets.getEditor().textProperty().addListener((obs, oldVal, newVal) -> spnTotalTickets.getStyleClass().remove("error"));
        txtFldAddress.textProperty().addListener((obs, oldVal, newVal) -> txtFldAddress.getStyleClass().remove("error"));
        txtFldVenueName.textProperty().addListener((obs, oldVal, newVal) -> txtFldVenueName.getStyleClass().remove("error"));
        txtFldCity.textProperty().addListener((obs, oldVal, newVal) -> txtFldCity.getStyleClass().remove("error"));
        dtPicker.valueProperty().addListener((obs, oldVal, newVal) -> dtPicker.getStyleClass().remove("error"));
    }

    public void onCreateBtnClick(ActionEvent actionEvent) {
        String eventName = txtFldName.getText().strip();
        String descr = txtAreaDesc.getText().strip();
        Integer tickets = spnTotalTickets.getValue();
        String venueName = txtFldVenueName.getText().strip();
        String address = txtFldAddress.getText().strip();
        String city = txtFldCity.getText().strip();

        clearError();
        boolean hasError = false;

        if (eventName.isBlank()) {
            txtFldName.getStyleClass().add("error");
            hasError = true;
        }

        if (descr.isBlank()) {
            txtAreaDesc.getStyleClass().add("error");
            hasError = true;
        }

        if (dtPicker.getValue() == null) {
            dtPicker.getStyleClass().add("error");
            hasError = true;
        }

        if (spnTime.getValue() == null) {
            spnTime.getStyleClass().add("error");
            hasError = true;
        }

        if (venueName.isBlank()) {
            txtFldVenueName.getStyleClass().add("error");
            hasError = true;
        }

        if (address.isBlank()) {
            txtFldAddress.getStyleClass().add("error");
            hasError = true;
        }

        if (city.isBlank()) {
            txtFldCity.getStyleClass().add("error");
            hasError = true;
        }

        if (tickets == null || tickets <= 0) {
            spnTotalTickets.getStyleClass().add("error");
            hasError = true;
        }

        if (hasError) {
            return;
        }

        LocalDateTime date = LocalDateTime.of(dtPicker.getValue(), spnTime.getValue());

        try {
            Location location = new Location(venueName, address, city);

            Event newEvent = new Event(eventName, date, location, new ArrayList<>(), tickets, tickets, descr);
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

    private void clearError() {
        txtFldName.getStyleClass().remove("error");
        txtAreaDesc.getStyleClass().remove("error");
        dtPicker.getStyleClass().remove("error");
        spnTime.getStyleClass().remove("error");
        txtFldVenueName.getStyleClass().remove("error");
        txtFldAddress.getStyleClass().remove("error");
        txtFldCity.getStyleClass().remove("error");
        spnTotalTickets.getStyleClass().remove("error");
    }
}
