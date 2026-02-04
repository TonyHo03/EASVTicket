package dk.easv.easvticket.GUI;

import dk.easv.easvticket.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    private Stage currentStage;

    private final String adminUserDemo = "admin";
    private final String adminPassDemo = "admin123";
    private final String coordUserDemo = "coordinator1";
    private final String coordPassDemo = "coord123";


    @FXML
    private TextField txtFldUser;
    @FXML
    private PasswordField txtFldPass;

    @FXML
    private void onBackBtnClick(ActionEvent actionEvent) {
        Stage stage = new Stage();

        MainApplication.startMain(stage);

        currentStage.close();
    }

    @FXML
    private void onSignInBtnClick() {

        if (txtFldUser.getText().equals(adminUserDemo) && txtFldPass.getText().equals(adminPassDemo)) {

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/AdminView.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 600, 427);
                Stage stage = new Stage();

                AdminController adminController = fxmlLoader.getController();
                adminController.setStage(stage);

                stage.resizableProperty().setValue(false);

                stage.setTitle("Admin Dashboard");
                stage.setScene(scene);
                stage.show();

                currentStage.close();

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }

        if (txtFldUser.getText().equals(coordUserDemo) && txtFldPass.getText().equals(coordPassDemo)) {

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/CoordinatorView.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 600, 553);
                Stage stage = new Stage();

                CoordinatorController coordinatorController = fxmlLoader.getController();
                coordinatorController.setStage(stage);

                stage.resizableProperty().setValue(false);

                stage.setTitle("Admin Dashboard");
                stage.setScene(scene);
                stage.show();

                currentStage.close();

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
    }

    public void setStage(Stage stage) {

        this.currentStage = stage;

    }
}
