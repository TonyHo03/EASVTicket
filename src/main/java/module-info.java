module dk.easv.easvticket {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens dk.easv.easvticket to javafx.fxml;
    exports dk.easv.easvticket;
}