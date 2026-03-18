package dk.easv.easvticket.GUI.Controllers;

import javafx.stage.Stage;

public class EditUserController {

    private Stage currentStage;
    private AdminController adminController;

    public void initializeClass(Stage stage, AdminController adminController) {

        this.currentStage = stage;
        this.adminController = adminController;

    }

}
