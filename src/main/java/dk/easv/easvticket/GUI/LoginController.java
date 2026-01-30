package dk.easv.easvticket.GUI;

import dk.easv.easvticket.HelloApplication;
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
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/MainView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 477);
            Stage stage = new Stage();

            MainController mainController = fxmlLoader.getController();
            mainController.setStage(stage);

            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();

            currentStage.close();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void setStage(Stage stage) {

        this.currentStage = stage;

    }
}
