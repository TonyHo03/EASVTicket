package dk.easv.easvticket.GUI.Controllers;

import dk.easv.easvticket.BE.Roles;
import dk.easv.easvticket.BE.User;
import dk.easv.easvticket.GUI.Models.UserModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.util.List;

public class AssignCoordController {

    private Stage currentStage;
    private UserModel userModel;
    private List<User> coordinators;

    @FXML
    private ChoiceBox<User> cbCoordinators;

    public void initializeClass(Stage stage, List<User> coordinators) {

        this.currentStage = stage;
        this.coordinators = coordinators;

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
