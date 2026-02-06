module dk.easv.easvticket {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires javafx.base;


    opens dk.easv.easvticket to javafx.fxml;
    exports dk.easv.easvticket;
    opens dk.easv.easvticket.BE to javafx.base;
    exports dk.easv.easvticket.GUI;
    opens dk.easv.easvticket.GUI to javafx.fxml;
}