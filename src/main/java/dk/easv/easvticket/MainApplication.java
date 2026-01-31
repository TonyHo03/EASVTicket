package dk.easv.easvticket;

import dk.easv.easvticket.GUI.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) {

        MainApplication.startMain(stage);

    }

    public static void startMain(Stage stage) {

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/MainView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 477);

            MainController mainController = fxmlLoader.getController();
            mainController.setStage(stage);

            stage.resizableProperty().setValue(false);

            stage.setTitle("EASV Tickets");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
}
