package dk.easv.easvticket.GUI.Controllers;

import dk.easv.easvticket.BE.Event;
import dk.easv.easvticket.BE.Roles;
import dk.easv.easvticket.BE.User;
import dk.easv.easvticket.GUI.Models.EventModel;
import dk.easv.easvticket.GUI.Models.UserModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class AssignCoordController {

    private Stage currentStage;
    private EventModel eventModel;
    private Event selectedEvent;
    private List<User> coordinators;

    @FXML private VBox vbAssignedCoord;
    @FXML private ChoiceBox<User> cbCoordinators;

    public void initializeClass(Stage stage, EventModel eventModel, List<User> coordinators, Event selectedEvent) {

        this.currentStage = stage;
        this.eventModel = eventModel;
        this.coordinators = coordinators;
        this.selectedEvent = selectedEvent;

        choiceboxSetup();
        updateAssignedList();
    }

    @FXML
    private void onAssignBtnClick() { //
        User cbCoord = cbCoordinators.getValue();

        if (cbCoord == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Missing field");
            alert.setContentText("Please assign a Coordinator.");
            alert.showAndWait();
            return;
        }

        List<User> assignedCoordinators = selectedEvent.getCoordinators();
        if (assignedCoordinators != null) {
            boolean alreadyAssigned = assignedCoordinators.stream().anyMatch(coord -> coord.getId() == cbCoord.getId());

            if (alreadyAssigned) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Already Assigned");
                alert.setContentText(cbCoord.getUsername() + " is already assigned to this event.");
                alert.showAndWait();
                return;
            }
        }

        else {
            try {
                eventModel.assignCoordinatorToEvent(cbCoordinators.getValue(), selectedEvent);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        currentStage.close();

    }

    @FXML
    private void onCancelBtnClick() {
        currentStage.close();
    }

    public void choiceboxSetup() {
        try {
            ObservableList<User> coordinatorList = FXCollections.observableList(coordinators);
            cbCoordinators.setItems(coordinatorList);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateAssignedList() {
        vbAssignedCoord.getChildren().clear();

        List<User> assigned = selectedEvent.getCoordinators();

        if (assigned != null) {
            for (User user : assigned) {
                Label label = new Label("   " + user.getUsername());
                vbAssignedCoord.getChildren().add(label);
            }
        }
    }
}
