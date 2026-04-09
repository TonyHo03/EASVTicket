package dk.easv.easvticket.GUI.Controllers;

import dk.easv.easvticket.BE.Event;
import dk.easv.easvticket.GUI.Models.EventModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

public class EditEventController {

    // Add FXML fields for your form controls
    @FXML private TextField nameField;
    @FXML private TextField locationField;
    @FXML private DatePicker dateField;
    // Add other fields as needed

    private Stage currentStage;
    private Event event;
    private EventModel eventModel;
    private Runnable onSaveCallback;

    public void initializeClass(Stage stage, Event event, EventModel eventModel, Runnable onSaveCallback) {
        this.currentStage = stage;
        this.event = event;
        this.eventModel = eventModel;
        this.onSaveCallback = onSaveCallback;

        // Populate form fields with event data
        populateFields();
    }

    private void populateFields() {
        if (event != null) {
            // Example - adjust based on your actual FXML fields:
            // nameField.setText(event.getName());
            // locationField.setText(event.getLocation().getVenueName());
            // dateField.setValue(event.getDate().toLocalDate());
        }
    }

    @FXML
    private void onClickSave(ActionEvent actionEvent) {
        try {
            // Update event object with form values
            // event.setName(nameField.getText());
            // etc.

            // Save to database via model
            // eventModel.updateEvent(event);

            // Refresh parent table
            if (onSaveCallback != null) {
                onSaveCallback.run();
            }

            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
            // Show error alert
        }
    }

    public void onClickCancel(ActionEvent actionEvent) {
        currentStage.close();
    }
}