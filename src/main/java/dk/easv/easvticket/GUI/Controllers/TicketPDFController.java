package dk.easv.easvticket.GUI.Controllers;

import dk.easv.easvticket.BE.Ticket;
import dk.easv.easvticket.BLL.util.QRGenerator;
import dk.easv.easvticket.BLL.util.TicketPayload;
import dk.easv.easvticket.GUI.Models.TicketModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

public class TicketPDFController {

    private Stage currentStage;
    private TicketModel ticketModel;
    private Ticket selectedTicket;

    @FXML private Label lblEvent, lblTicketType, lblDateTime, lblLocation, lblCustomer, lblEmail, lblPrice, lblTicketId;
    @FXML private ImageView imgQR;

    @FXML
    private void onSaveBtnClick() {

        try {
            ticketModel.printTicket(selectedTicket);
            currentStage.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onSendBtnClick() {

        ticketModel.sendTicket(selectedTicket);
        currentStage.close();

    }

    public void initializeClass(Stage currentStage, TicketModel ticketModel, Ticket selectedTicket) {

        this.currentStage = currentStage;
        this.ticketModel = ticketModel;
        this.selectedTicket = selectedTicket;

        windowSetup();

    }

    private void windowSetup() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy  HH:mm");

        lblEvent.setText(selectedTicket.getEvent().getName());
        lblTicketType.setText(selectedTicket.getTicketType().getTicketType());
        lblDateTime.setText(selectedTicket.getEvent().getDate().format(formatter));
        lblCustomer.setText(selectedTicket.getCustomerName());
        lblLocation.setText(selectedTicket.getEvent().getLocation().toString());
        lblEmail.setText(selectedTicket.getEmail());
        lblPrice.setText(selectedTicket.getPrice() + " DKK");
        lblTicketId.setText(selectedTicket.getTicketId());

        try {

            BufferedImage qrImage = QRGenerator.generate(TicketPayload.generate(selectedTicket));
            ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
            ImageIO.write(qrImage, "png", baos);

            Image fxImage = new Image(new ByteArrayInputStream(baos.toByteArray()));

            imgQR.setImage(fxImage);


        }
        catch (Exception e) {

            e.printStackTrace();

        }

    }

}
