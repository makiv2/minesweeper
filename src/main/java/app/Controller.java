package app;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.Map;


public class Controller {


    private void addEventListner(Cell[][] cells, Board board, Map<TextField, Cell> mappedTextFields, VBox vbox, TextField tf, Button saveGame) {
        tf.setOnMouseClicked(mouseEvent -> {
            Cell cell = mappedTextFields.get(mouseEvent.getSource());
            if (cell.isCellVisible() == true)
                return;
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                board.leftClick(cell, board.getBoard());
            }

            if (mouseEvent.getButton() == MouseButton.SECONDARY)
                board.rightClickflag(cell);
            vbox.getChildren().clear();
            vbox.getChildren().add(saveGame);
            vbox.getChildren().add(getGridPane(cells, mappedTextFields, board, vbox, saveGame));
            System.out.println();
        });
    }

    public boolean isValidInput(String row, String col, String amountOfBombs) {
        if (row.isEmpty() || col.isEmpty() || amountOfBombs.isEmpty())
            return true;
        try {
            Integer.parseInt(row);
            Integer.parseInt(col);
            Integer.parseInt(amountOfBombs);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.out.println("Not valid input");
            return false;
        }
        if (Integer.parseInt(row) < 0 ||  Integer.parseInt(col) < 0 || Integer.parseInt(amountOfBombs) < 0)
            return false;
        return true;
    }





    private TextField getTextField(int row, int col, Cell[][] cells) {
        final Cell cell = cells[row][col];
        final TextField tf = new TextField();
        tf.setPrefHeight(50);
        tf.setPrefWidth(50);
        tf.setAlignment(Pos.CENTER);
        tf.setEditable(false);

        if (cell.isCellVisible()) {
            tf.setText(cells[row][col].getStatus().toString());
            tf.setStyle("-fx-control-inner-background: #0040ff;");
        }
        if (cell.isFlagged()) {
            tf.setStyle("-fx-control-inner-background: red;");
        }

        return tf;
    }

    public GridPane getGridPane(Cell[][] cells, Map<TextField, Cell> mappedTextFields, Board board, VBox vbox, Button saveGame) {
        GridPane gridPane = new GridPane();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                final TextField tf = getTextField(i, j, cells);
                addEventListner(cells, board, mappedTextFields, vbox, tf, saveGame);
                gridPane.setRowIndex(tf, i);
                mappedTextFields.put(tf, cells[i][j]);
                gridPane.setColumnIndex(tf, j);
                gridPane.getChildren().add(tf);


            }
        }
        return gridPane;
    }
}