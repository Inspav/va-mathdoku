package mathdoku;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class WinBox {
    public static String result;

    public static String Show(String title, String message) {
        result = "";
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setWidth(300);
        window.setHeight(200);
        window.setMinWidth(300);
        window.setMinHeight(200);

        Label messageLabel = new Label();
        messageLabel.setWrapText(true);
        messageLabel.setTextAlignment(TextAlignment.CENTER);
        messageLabel.setText(message);

        Button btnLoadFromFile = new Button("Load file");
        Button btnLoadFromText = new Button("Load text");
        Button btnLoadNewPuzzle = new Button("New puzzle");
        Button btnExitGame = new Button("Exit");
        HBox buttonLayout = new HBox(10, btnLoadFromFile, btnLoadFromText, btnLoadNewPuzzle, btnExitGame);
        buttonLayout.setPadding(new Insets(10, 5, 5, 10));

        btnLoadFromFile.setOnAction(е -> {
            result = "Load file";
            window.close();

        });
        btnLoadFromText.setOnAction(е -> {
            result = "Load text";
            window.close();
        });

        btnLoadNewPuzzle.setOnAction(е -> {
            result = "New Puzzle";
            window.close();
        });

        btnExitGame.setOnAction(е -> {
            result = "Exit Game";
            window.close();
        });

        BorderPane layout = new BorderPane(messageLabel, null, null, buttonLayout, null);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return result;
    }
}
