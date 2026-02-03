package dk.easv.easvticket.GUI;

import dk.easv.easvticket.MainApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    private Stage currentStage;

    @FXML
    private Button logoutBtn, addUserBtn, editUserBtn, deleteUserBtn, addCoordinatorBtn, deleteEventBtn;

    @FXML
    private void onLogoutBtnClick() {

        Stage stage = new Stage();

        MainApplication.startMain(stage);

        currentStage.close();

    }

    @FXML
    private void onAddUserBtnClick() {

    }

    public void setStage(Stage stage) {

        this.currentStage = stage;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logoutBtn.getStyleClass().add("Purple_Buttons");

        addUserBtn.getStyleClass().add("Invisible_Buttons");
        editUserBtn.getStyleClass().add("Invisible_Buttons");
        deleteUserBtn.getStyleClass().add("Invisible_Buttons");
        addCoordinatorBtn.getStyleClass().add("Invisible_Buttons");
        deleteEventBtn.getStyleClass().add("Invisible_Buttons");

    }
}
