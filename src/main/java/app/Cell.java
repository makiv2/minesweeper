package app;

import javafx.scene.layout.*;

import java.io.Serializable;

public class Cell extends StackPane implements Serializable {

    private Status status;
    private boolean isVisible;
    private boolean isFlagged;
    private int row;
    private int col;

    public Status getStatus() {
        return status;
    }

    public void addStatus() {
        int number = this.status.number;
        number++;
        this.status = Status.valueOf(number);

    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setCellVisible(boolean visible) {
        isVisible = visible;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public boolean isCellVisible() {
        return isVisible;
    }

    public void toggleFlag() {
        isFlagged = !isFlagged;
    }

    public Cell(Status status, int row, int col) {
        this.status = status;
        this.isVisible = false;
        this.isFlagged = false;
        this.row = row;
        this.col = col;
    }

}
