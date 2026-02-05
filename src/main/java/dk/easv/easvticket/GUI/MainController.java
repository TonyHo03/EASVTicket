package dk.easv.easvticket.GUI;

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
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
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

        VBox pane = new VBox();
        VBox.setMargin(pane, new Insets(10, 0, 10, 0));
        pane.setAlignment(Pos.TOP_CENTER);
        pane.getStyleClass().add("event-box");

        makeNewLayer(pane, "Event Name", new Font("Arial Bold", 18), "BLACK", 50);
        makeNewLayer(pane, "Description", new Font("Arial", 14), "GREY", 10);
        makeNewLayer(pane, "", new Font(0), "BLACK", 25);
        makeNewLayer(pane, "DD/MM/YY", new Font(12), "GREY", 5, "images/calender.gif");
        makeNewLayer(pane, "Location", new Font(12), "GREY", 5, "images/location.gif");
        makeNewLayer(pane, "X tickets available", new Font(12), "GREY", 5, "images/smallticket.png");

        HBox buttonBox = new HBox();
        buttonBox.setPrefHeight(50);
        buttonBox.setAlignment(Pos.CENTER_LEFT);
        pane.getChildren().add(buttonBox);

        Region buttonRegion = new Region();
        buttonRegion.setPrefWidth(150);
        buttonBox.getChildren().add(buttonRegion);

        Button ticketButton = new Button("Buy Tickets");
        ticketButton.getStyleClass().add("ticket-buttons");
        buttonBox.getChildren().add(ticketButton);


        if (!placementBoolean) {

            contentBox1.getChildren().add(pane);

        }
        else {

            contentBox2.getChildren().add(pane);

        }

        placementBoolean = !placementBoolean;

    }

    public void setStage(Stage stage) {

        this.currentStage = stage;

    }

    public void makeNewLayer(VBox owner, String text, Font textDesign, String textColor, int boxHeight, String imagePath) {

        HBox headerBox = new HBox();
        headerBox.setPrefHeight(boxHeight);
        headerBox.setAlignment(Pos.CENTER_LEFT);
        //headerBox.setStyle("-fx-background-color: RED");
        owner.getChildren().add(headerBox);

        Region headerRegion = new Region();
        headerRegion.setPrefWidth(10);
        headerBox.getChildren().add(headerRegion);

        ImageView headerImage = new ImageView(MainApplication.class.getResource(imagePath).toExternalForm());
        headerImage.setFitWidth(10);
        headerImage.setFitHeight(10);
        headerBox.getChildren().add(headerImage);

        Region headerRegion2 = new Region();
        headerRegion2.setPrefWidth(5);
        headerBox.getChildren().add(headerRegion2);

        Label headerText = new Label(text);
        headerText.setFont(textDesign);
        headerText.setTextFill(Paint.valueOf(textColor));
        headerBox.getChildren().add(headerText);

    }

    public void makeNewLayer(VBox owner, String text, Font textDesign, String textColor, int boxHeight) {

        HBox headerBox = new HBox();
        headerBox.setPrefHeight(boxHeight);
        headerBox.setAlignment(Pos.CENTER_LEFT);
        //headerBox.setStyle("-fx-background-color: RED");
        owner.getChildren().add(headerBox);

        Region headerRegion = new Region();
        headerRegion.setPrefWidth(10);
        headerBox.getChildren().add(headerRegion);

        Label headerText = new Label(text);
        headerText.setFont(textDesign);
        headerText.setTextFill(Paint.valueOf(textColor));
        headerBox.getChildren().add(headerText);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        System.out.println();

    }
}
