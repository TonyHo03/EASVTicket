package dk.easv.easvticket.GUI;

import dk.easv.easvticket.MainApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
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

    }

    @FXML
    private void onAssignCoordBtnClick() {

    }

    @FXML
    private void onEditEventBtnClick() {
    }
    @FXML
    private void onDeleteEventBtnClick() {
    }

    @FXML
    private void onBuyTicketBtnClick() {

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
