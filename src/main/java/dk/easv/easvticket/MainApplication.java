package dk.easv.easvticket;

import dk.easv.easvticket.GUI.Controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) {

        MainApplication.startMain(stage);

    }

    public static void startMain(Stage stage) {

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/LoginView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            LoginController loginController = fxmlLoader.getController();
            loginController.setStage(stage);

            stage.resizableProperty().setValue(false);

            stage.setTitle("EASV Tickets");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
}
