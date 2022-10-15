package app;

import javafx.application.Application;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;


public class App extends Application {


    public void start(Stage stage) {


        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        final TextField rowString = new TextField();
        rowString.setPromptText("Enter row.");
        rowString.setPrefColumnCount(10);
        rowString.getText();
        GridPane.setConstraints(rowString, 0, 0);
        grid.getChildren().add(rowString);

        final TextField colString = new TextField();
        colString.setPromptText("Enter col.");
        GridPane.setConstraints(colString, 0, 1);
        grid.getChildren().add(colString);

        final TextField amountOfBombsString = new TextField();
        amountOfBombsString.setPrefColumnCount(15);
        amountOfBombsString.setPromptText("Amount of Bombs.");
        GridPane.setConstraints(amountOfBombsString, 0, 2);
        grid.getChildren().add(amountOfBombsString);

        Button submit = new Button("Submit");
        GridPane.setConstraints(submit, 1, 0);
        grid.getChildren().add(submit);



        Button saveGame = new Button("Save Game Progress");
        FileChooser fileChooser = new FileChooser();




        Button importGame = new Button("Import Game");
        GridPane.setConstraints(importGame, 2, 0);
        grid.getChildren().add(importGame);
        importGame.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(stage);
            byte[] array = new byte[0];
            try {
                array = Files.readAllBytes(selectedFile.toPath());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            ImportFromFile importer = new ImportFromFile();
            Board board;
            board = (Board) importer.importGame(array);

            stage.close();

            VBox vbox = new VBox();
            final Cell[][] cells = board.getBoard();
            SaveToFile saveGameFile = new SaveToFile();
            final Map<TextField, Cell> mappedTextFields = new HashMap<>();

            saveGame.setOnAction(b -> saveGameFile.saveGame(board));
            Controller controller = new Controller();
            final Scene scene2 = new Scene(vbox);

            vbox.getChildren().add(saveGame);
            vbox.getChildren().add(controller.getGridPane(cells, mappedTextFields , board, vbox, saveGame));



            stage.setScene(scene2);
            stage.setResizable(false);
            stage.sizeToScene();
            stage.show();
        });


        final Scene scene = new Scene(grid);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();

        submit.setOnAction(e -> {
            Board board;
            Controller validation = new Controller();
            if (!validation.isValidInput(rowString.getText(), colString.getText(), amountOfBombsString.getText()))
                return;
            if ((!rowString.getText().isEmpty() && !colString.getText().isEmpty() && !amountOfBombsString.getText().isEmpty())) {
                board = new Board(Integer.parseInt(rowString.getText()), Integer.parseInt(colString.getText()), Integer.parseInt(amountOfBombsString.getText()));

            } else {
                board = new Board();
            }

            stage.close();


            VBox vbox = new VBox();
            final Cell[][] cells = board.getBoard();
            SaveToFile saveGameFile = new SaveToFile();
            final Map<TextField, Cell> mappedTextFields = new HashMap<>();

            saveGame.setOnAction(b -> saveGameFile.saveGame(board));
            Controller controller = new Controller();
            final Scene scene2 = new Scene(vbox);

            vbox.getChildren().add(saveGame);
            vbox.getChildren().add(controller.getGridPane(cells, mappedTextFields , board, vbox, saveGame));



            stage.setScene(scene2);
            stage.setResizable(false);
            stage.sizeToScene();
            stage.show();
        });






    }


    public static void main(final String[] args) {
        launch(args);

    }
}