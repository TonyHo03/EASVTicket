package dk.easv.easvticket.GUI.util;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Tooltip;

public class TooltipMaker {

    // Method with generic type parameters, since the parameters' type aren't always the same.
    public static <S, T> void addTooltipsToColumns(TableColumn<S, T> column) {

        column.setCellFactory(tc -> new TableCell<S, T>() {

            private Tooltip tooltip = new Tooltip();

            // Overriding the updateItem method inside the table column
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);

                // if the cell is empty, then the tooltip should not be displayed. otherwise display the contents of the cell.
                if (empty || item == null) {

                    setText(null);
                    setTooltip(null);

                } else {

                    setText(item.toString());
                    tooltip.setText(item.toString());
                    setTooltip(tooltip);

                }
            }
        });
    }

}
