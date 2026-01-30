package dk.easv.easvticket;

import dk.easv.easvticket.GUI.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 477);

        MainController mainController = fxmlLoader.getController();
        mainController.setStage(stage);

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}
