package dk.easv.easvticket.GUI.Controllers;

import dk.easv.easvticket.BE.Event;
import dk.easv.easvticket.BE.Location;
import dk.easv.easvticket.GUI.Models.EventModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class EditEventController implements Initializable {

    private Stage currentStage;
    private Event selectedEvent;
    private EventModel eventModel;

    @FXML private TextField txtFldName, txtFldVenueName, txtFldAddress, txtFldCity;
    @FXML private TextArea txtAreaDesc;
    @FXML private DatePicker dtPicker;
    @FXML private Spinner<Integer> spnTotalTickets;
    @FXML private Spinner<LocalTime> spnTime;

    public void setStage(Stage stage) {
        this.currentStage = stage;
    }

    public void setModel(EventModel eventModel) {
        this.eventModel = eventModel;
    }

    // FIX 2: store the event parameter into selectedEvent before reading from it
    public void setEvent(Event event) {
        this.selectedEvent = event;
        txtFldName.setText(selectedEvent.getName());
        txtAreaDesc.setText(selectedEvent.getDescription());
        dtPicker.setValue(selectedEvent.getDate().toLocalDate());
        spnTime.getValueFactory().setValue(selectedEvent.getDate().toLocalTime());
        txtFldVenueName.setText(selectedEvent.getLocation().getVenueName());
        txtFldAddress.setText(selectedEvent.getLocation().getAddress());
        txtFldCity.setText(selectedEvent.getLocation().getCity());
        spnTotalTickets.getValueFactory().setValue(selectedEvent.getTotalTickets());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        SpinnerValueFactory<Integer> integerSpinnerValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 999, 0, 1);
        spnTotalTickets.setValueFactory(integerSpinnerValueFactory);

        SpinnerValueFactory<LocalTime> localTimeSpinnerValueFactory = new SpinnerValueFactory<>() {
            {
                setConverter(new StringConverter<>() {
                    @Override
                    public String toString(LocalTime time) {
                        return (time == null) ? "" : String.format("%02d:%02d", time.getHour(), time.getMinute());
                    }
                    @Override
                    public LocalTime fromString(String string) {
                        try { return LocalTime.parse(string); } catch (Exception e) { return getValue(); }
                    }
                });
                setValue(LocalTime.of(0, 0));
            }
            @Override public void decrement(int steps) { setValue(getValue().minusMinutes(steps * 15L)); }
            @Override public void increment(int steps) { setValue(getValue().plusMinutes(steps * 15L)); }
        };

        spnTime.setValueFactory(localTimeSpinnerValueFactory);
        spnTime.getEditor().setPromptText("e.g. '12:00'");

        txtFldName.textProperty().addListener((obs, o, n) -> txtFldName.getStyleClass().remove("error"));
        txtAreaDesc.textProperty().addListener((obs, o, n) -> txtAreaDesc.getStyleClass().remove("error"));
        spnTotalTickets.valueProperty().addListener((obs, o, n) -> spnTotalTickets.getStyleClass().remove("error"));
        spnTotalTickets.getEditor().textProperty().addListener((obs, o, n) -> spnTotalTickets.getStyleClass().remove("error"));
        txtFldAddress.textProperty().addListener((obs, o, n) -> txtFldAddress.getStyleClass().remove("error"));
        txtFldVenueName.textProperty().addListener((obs, o, n) -> txtFldVenueName.getStyleClass().remove("error"));
        txtFldCity.textProperty().addListener((obs, o, n) -> txtFldCity.getStyleClass().remove("error"));
        dtPicker.valueProperty().addListener((obs, o, n) -> dtPicker.getStyleClass().remove("error"));
    }

    @FXML
    private void onClickSave(ActionEvent actionEvent) {

        String eventName = txtFldName.getText();
        String descr = txtAreaDesc.getText();
        Integer tickets = spnTotalTickets.getValue();
        String venueName = txtFldVenueName.getText();
        String address = txtFldAddress.getText();
        String city = txtFldCity.getText();

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

            int newTotal = tickets;
            int available = selectedEvent.getAvailableTickets();
            if (available > newTotal) available = newTotal;

            Event updatedEvent = new Event(selectedEvent.getId(), eventName, date, location, selectedEvent.getCoordinators(), newTotal, available, descr);

            eventModel.updateEvent(updatedEvent);

            currentStage.close();

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Update failed");
            alert.setContentText("Could not update event: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void onClickCancel(ActionEvent actionEvent) {
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