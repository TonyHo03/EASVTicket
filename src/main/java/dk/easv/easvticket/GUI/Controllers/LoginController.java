package dk.easv.easvticket.GUI.Controllers;

import dk.easv.easvticket.DAL.DAO.DBConnector;
import dk.easv.easvticket.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private Stage currentStage;
    private boolean isHidden = true;
    private DBConnector dbConnector = new DBConnector();

    @FXML private TextField txtFldUser, txtFldPass;
    @FXML private PasswordField passFldPass;
    @FXML private Label lblSignIn;
    @FXML private ImageView showImg;

    public LoginController() throws IOException {
    }

    @FXML
    private void onBackBtnClick(ActionEvent actionEvent) {
        Stage stage = new Stage();

        MainApplication.startMain(stage);

        currentStage.close();
    }

    // Admin            Username: admin     Password: admin123
    // Coordinator      Username: coord     Password: coord123
    @FXML
    private void onSignInBtnClick() throws Exception {
        String sql = "SELECT * FROM [User] WHERE Username = ? AND Password = ?";

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, txtFldUser.getText());
            ps.setString(2, txtFldPass.getText());

            ResultSet rs = ps.executeQuery();

            if (txtFldUser.getText().isEmpty() || txtFldPass.getText().isEmpty()) {
                lblSignIn.setText("Please fill out all fields.");
                lblSignIn.getStyleClass().remove("error-label"); // prevents stacking
                lblSignIn.getStyleClass().add("error-label");
                return;
            }

            if (!rs.next()) {
                /*Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Incorrect information");
                alert.setContentText("Incorrect username or password. Please try again.");
                alert.showAndWait();*/
                lblSignIn.setText("Incorrect username or password. Please try again.");
                lblSignIn.getStyleClass().remove("error-label"); // prevents stacking
                lblSignIn.getStyleClass().add("error-label");
                return;
            }

            String role = rs.getString("Role");

            if (role.equalsIgnoreCase("Admin")) {
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/AdminView.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();

                AdminController adminController = fxmlLoader.getController();
                adminController.setStage(stage);

                stage.resizableProperty().setValue(false);

                stage.setTitle("Admin Dashboard");
                stage.setScene(scene);
                stage.show();

                currentStage.close();
            }
            else if (role.equalsIgnoreCase("Coordinator")) {
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/CoordinatorView.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();

                CoordinatorController coordinatorController = fxmlLoader.getController();
                coordinatorController.setStage(stage);

                stage.resizableProperty().setValue(false);

                stage.setTitle("Admin Dashboard");
                stage.setScene(scene);
                stage.show();

                currentStage.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("Something went wrong. Please try again.");
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

        } else {git

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

        txtFldUser.textProperty().addListener((obs, oldVal, newVal) -> resetLabel());
        passFldPass.textProperty().addListener((obs, oldVal, newVal) -> resetLabel());

        passFldPass.textProperty().bindBidirectional(txtFldPass.textProperty());

        passFldPass.setPrefWidth(350);
        txtFldPass.setPrefWidth(350);

        passFldPass.setManaged(true);
        passFldPass.setVisible(true);

        txtFldPass.setManaged(false);
        txtFldPass.setVisible(false);

    }

    private void resetLabel() {
        lblSignIn.setText("Sign in to manage events and tickets");
        lblSignIn.getStyleClass().remove("error-label");
    }

    public void onEnterClick(KeyEvent keyEvent) throws Exception {
        if (keyEvent.getCode() == javafx.scene.input.KeyCode.ENTER) {
            onSignInBtnClick();
        }
    }
}
