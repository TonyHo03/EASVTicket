package dk.easv.easvticket.GUI.Controllers;

import dk.easv.easvticket.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private Stage currentStage;

    private final String adminUserDemo = "admin";
    private final String adminPassDemo = "admin123";
    private final String coordUserDemo = "coordinator1";
    private final String coordPassDemo = "coord123";

    private boolean isHidden = true;


    @FXML
    private TextField txtFldUser;
    @FXML
    private PasswordField passFldPass;
    @FXML
    private TextField txtFldPass;
    @FXML
    private ImageView showImg;

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
        else if (txtFldUser.getText().equals(coordUserDemo) && txtFldPass.getText().equals(coordPassDemo)) {

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
        else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Incorrect information");
            alert.setContentText("Incorrect username or password. Please try again.");
            alert.showAndWait();

        }
    }

    @FXML
    private void onShowHideBtnClick() {

        isHidden = !isHidden;

        if (isHidden) {

            showImg.setImage(new Image(MainApplication.class.getResource("images/show.png").toExternalForm()));

            passFldPass.setManaged(true);
            passFldPass.setVisible(true);

            txtFldPass.setManaged(false);
            txtFldPass.setVisible(false);

        } else {

            showImg.setImage(new Image(MainApplication.class.getResource("images/hide.png").toExternalForm()));

            passFldPass.setManaged(false);
            passFldPass.setVisible(false);

            txtFldPass.setManaged(true);
            txtFldPass.setVisible(true);

        }

    }

    public void setStage(Stage stage) {

        this.currentStage = stage;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        passFldPass.textProperty().bindBidirectional(txtFldPass.textProperty());

        passFldPass.setPrefWidth(350);
        txtFldPass.setPrefWidth(350);

        passFldPass.setManaged(true);
        passFldPass.setVisible(true);

        txtFldPass.setManaged(false);
        txtFldPass.setVisible(false);

    }
}
