package dk.easv.easvticket.GUI.util;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeFormatting {

    public static <S> void changeFormat(TableColumn<S, LocalDateTime> dateColumn) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        dateColumn.setCellFactory(column -> new TableCell<S, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime value, boolean empty) {
                super.updateItem(value, empty);
                if (empty || value == null) {
                    setText(null);
                } else {
                    setText(value.format(formatter));
                }
            }
        });

    }

}
