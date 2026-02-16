package dk.easv.easvticket.GUI.util;

import dk.easv.easvticket.BE.Event;
import dk.easv.easvticket.GUI.Controllers.PurchaseTicketController;
import dk.easv.easvticket.MainApplication;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class EventboxMaker {

    public static void addNewEvent(VBox owner, Event eventObject) {

        VBox pane = new VBox();
        VBox.setMargin(pane, new Insets(10, 0, 10, 0));
        pane.setAlignment(Pos.TOP_CENTER);
        pane.getStyleClass().add("event-box");

        makeNewLayer(pane, eventObject.getName(), new Font("Arial Bold", 18), "BLACK", 50);
        makeNewLayer(pane, "PLACEHOLDER", new Font("Arial", 14), "GREY", 10);
        makeNewLayer(pane, "", new Font(0), "BLACK", 25);
        makeNewLayer(pane, eventObject.getDate().toString(), new Font(12), "GREY", 5, "images/calender.gif");
        makeNewLayer(pane, eventObject.getLocation(), new Font(12), "GREY", 5, "images/location.gif");
        makeNewLayer(pane, eventObject.getAvailableTickets() + " tickets available", new Font(12), "GREY", 5, "images/smallticket.png");

        HBox buttonBox = new HBox();
        buttonBox.setPrefHeight(50);
        buttonBox.setAlignment(Pos.CENTER_LEFT);
        pane.getChildren().add(buttonBox);

        Region buttonRegion = new Region();
        buttonRegion.setPrefWidth(150);
        buttonBox.getChildren().add(buttonRegion);

        Button ticketButton = new Button("Buy Tickets");
        ticketButton.getStyleClass().add("ticket-buttons");
        ticketButton.setOnAction(event -> onPurchaseBtnClick());
        buttonBox.getChildren().add(ticketButton);

        owner.getChildren().add(pane);

    }

    private static void makeNewLayer(VBox owner, String text, Font textDesign, String textColor, int boxHeight, String imagePath) {

        HBox headerBox = new HBox();
        headerBox.setPrefHeight(boxHeight);
        headerBox.setAlignment(Pos.CENTER_LEFT);
        //headerBox.setStyle("-fx-background-color: RED");
        owner.getChildren().add(headerBox);

        Region headerRegion = new Region();
        headerRegion.setPrefWidth(10);
        headerBox.getChildren().add(headerRegion);

        ImageView headerImage = new ImageView(MainApplication.class.getResource(imagePath).toExternalForm());
        headerImage.setFitWidth(15);
        headerImage.setFitHeight(15);
        headerBox.getChildren().add(headerImage);

        Region headerRegion2 = new Region();
        headerRegion2.setPrefWidth(5);
        headerBox.getChildren().add(headerRegion2);

        Label headerText = new Label(text);
        headerText.setFont(textDesign);
        headerText.setTextFill(Paint.valueOf(textColor));
        headerBox.getChildren().add(headerText);

    }

    private static void makeNewLayer(VBox owner, String text, Font textDesign, String textColor, int boxHeight) {

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

    private static void onPurchaseBtnClick() {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/PurchaseTicketView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 356, 421);
            Stage stage = new Stage();

            PurchaseTicketController purchaseTicketController = fxmlLoader.getController();
            purchaseTicketController.setStage(stage);

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.resizableProperty().setValue(false);

            stage.setTitle("Login");
            stage.setScene(scene);
            stage.show();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

}
