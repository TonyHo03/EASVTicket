package dk.easv.easvticket.GUI.Controllers;

import dk.easv.easvticket.BE.Event;
import dk.easv.easvticket.BE.Roles;
import dk.easv.easvticket.BE.User;
import dk.easv.easvticket.GUI.Models.EventModel;
import dk.easv.easvticket.GUI.Models.UserModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.util.List;

public class AssignCoordController {

    private Stage currentStage;
    private EventModel eventModel;
    private Event selectedEvent;
    private List<User> coordinators;

    @FXML
    private ChoiceBox<User> cbCoordinators;

    @FXML
    private void onAssignBtnClick() {

        try {
            eventModel.assignCoordinatorToEvent(cbCoordinators.getValue(), selectedEvent);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        currentStage.close();

    }

    @FXML
    private void onCancelBtnClick() {

    }

    public void initializeClass(Stage stage, EventModel eventModel, List<User> coordinators, Event selectedEvent) {

        this.currentStage = stage;
        this.eventModel = eventModel;
        this.coordinators = coordinators;
        this.selectedEvent = selectedEvent;

        choiceboxSetup();

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
}
