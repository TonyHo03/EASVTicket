package dk.easv.easvticket.GUI;

import dk.easv.easvticket.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML
    private Label welcomeText;

    private Stage currentStage;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    private void onLoginBtnClick() throws IOException {
        try {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 424, 522);
        Stage stage = new Stage();

        LoginController loginController = fxmlLoader.getController();
        loginController.setStage(stage);

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
