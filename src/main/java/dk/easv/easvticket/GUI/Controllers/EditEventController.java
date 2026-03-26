package dk.easv.easvticket.GUI.Controllers;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class EditEventController {

    private Stage currentStage;
    private Object ownerController;

    public void initializeClass(Stage stage, Object ownerController) {

        this.currentStage = stage;
        this.ownerController = ownerController;

    }

    public void onClickCancel(ActionEvent actionEvent) {
        currentStage.close();
    }
}
