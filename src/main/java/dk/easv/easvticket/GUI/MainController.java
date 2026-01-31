package dk.easv.easvticket.GUI;

import dk.easv.easvticket.MainApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private Stage currentStage;

    @FXML
    private ScrollPane scrollingPane;
    @FXML
    private VBox contentBox1, contentBox2;

    private boolean placementBoolean = false;

    @FXML
    private void onLoginBtnClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/LoginView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 424, 522);
            Stage stage = new Stage();

            LoginController loginController = fxmlLoader.getController();
            loginController.setStage(stage);

            stage.resizableProperty().setValue(false);

            stage.setTitle("Login");
            stage.setScene(scene);
            stage.show();

            currentStage.close();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }


    @FXML
    private void onTestBtnClick() {

        addNewEvent();

    }

    private void addNewEvent() {

        Pane pane = new Pane();
        VBox.setMargin(pane, new Insets(10, 0, 10, 0));
        pane.getStyleClass().add("event-box");

        if (!placementBoolean) {

            contentBox1.getChildren().add(pane);

        } else {

            contentBox2.getChildren().add(pane);

        }

        placementBoolean = !placementBoolean;

    }

    public void setStage(Stage stage) {

        this.currentStage = stage;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
