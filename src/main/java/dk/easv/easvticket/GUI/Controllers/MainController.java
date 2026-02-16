package dk.easv.easvticket.GUI.Controllers;

import dk.easv.easvticket.BE.Event;
import dk.easv.easvticket.BE.User;
import dk.easv.easvticket.GUI.util.EventboxMaker;
import dk.easv.easvticket.MainApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private Stage currentStage;

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

        List<User> users = new ArrayList<>();

        users.add(new User("John Coordinator", "johncoord", "johncoord123@easv365.dk", "Event Coordinator"));

        if (!placementBoolean) {

            EventboxMaker.addNewEvent(contentBox1, new Event("EASV Bar", Date.valueOf(LocalDate.now()), "Esbjerg", users, 100));

        } else {

            EventboxMaker.addNewEvent(contentBox2, new Event("EASV Bar", Date.valueOf(LocalDate.now()), "Esbjerg", users, 100));

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
