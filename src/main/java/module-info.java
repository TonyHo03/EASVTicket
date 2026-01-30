module dk.easv.easvticket {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens dk.easv.easvticket to javafx.fxml;
    exports dk.easv.easvticket;
    exports dk.easv.easvticket.GUI;
    opens dk.easv.easvticket.GUI to javafx.fxml;
}