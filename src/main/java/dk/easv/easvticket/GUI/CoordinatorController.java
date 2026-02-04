package dk.easv.easvticket.GUI;

import dk.easv.easvticket.MainApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CoordinatorController implements Initializable {
    private Stage currentStage;

    @FXML
    private Button addEventBtn, assignCoordBtn, editEventBtn, deleteEventBtn, buyTicketBtn, printBtn, logoutBtn;

    @FXML
    private void onLogoutBtnClick() {

        Stage stage = new Stage();

        MainApplication.startMain(stage);

        currentStage.close();

    }

    @FXML
    private void onAddEventBtnClick() {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/CreateEventView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 356, 526);
            Stage stage = new Stage();

            CreateEventController createEventController = fxmlLoader.getController();
            createEventController.setStage(stage);

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.resizableProperty().setValue(false);

            stage.setTitle("Create Event");
            stage.setScene(scene);
            stage.show();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    @FXML
    private void onAssignCoordBtnClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/AssignCoordView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 356, 408);
            Stage stage = new Stage();

            AssignCoordController assignCoordController = fxmlLoader.getController();
            assignCoordController.setStage(stage);

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.resizableProperty().setValue(false);

            stage.setTitle("Assign Coordinators");
            stage.setScene(scene);
            stage.show();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    private void onEditEventBtnClick() {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/EditEventView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 356, 526);
            Stage stage = new Stage();

            EditEventController editEventController = fxmlLoader.getController();
            editEventController.setStage(stage);

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.resizableProperty().setValue(false);

            stage.setTitle("Edit Event");
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {

            e.printStackTrace();

        }

    }
    @FXML
    private void onDeleteEventBtnClick() {

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setHeaderText("Delete Event");
        confirmation.setContentText("Are you sure you want to delete this event? This action cannot be undone.");

        Optional<ButtonType> result = confirmation.showAndWait();

    }

    @FXML
    private void onBuyTicketBtnClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/BuyTicketView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 356, 526);
            Stage stage = new Stage();

            BuyTicketController buyTicketController = fxmlLoader.getController();
            buyTicketController.setStage(stage);

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.resizableProperty().setValue(false);

            stage.setTitle("Create Tickets");
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {

            e.printStackTrace();

        }
    }

    @FXML
    private void onPrintBtnClick() {

    }

    public void setStage(Stage stage) {

        this.currentStage = stage;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        addEventBtn.getStyleClass().add("Invisible_Buttons");
        assignCoordBtn.getStyleClass().add("Invisible_Buttons");
        editEventBtn.getStyleClass().add("Invisible_Buttons");
        deleteEventBtn.getStyleClass().add("Invisible_Buttons");
        buyTicketBtn.getStyleClass().add("Invisible_Buttons");
        printBtn.getStyleClass().add("Invisible_Buttons");

    }
}
