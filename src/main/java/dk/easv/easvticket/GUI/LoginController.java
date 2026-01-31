package dk.easv.easvticket.GUI;

import dk.easv.easvticket.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    private Stage currentStage;

    @FXML
    private void onBackBtnClick(ActionEvent actionEvent) {
        Stage stage = new Stage();

        MainApplication.startMain(stage);

        currentStage.close();
    }

    public void setStage(Stage stage) {

        this.currentStage = stage;

    }
}
