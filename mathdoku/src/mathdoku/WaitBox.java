package mathdoku;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mathdoku.engine.MathPuzzle;

public class WaitBox {

    public static Stage window;

    public static void show(String title, MathPuzzle mathPuzzle) {
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setWidth(300);
        window.setHeight(200);
        window.setMinWidth(300);
        window.setMinHeight(200);

        ProgressIndicator progress = new ProgressIndicator();

        Label messageLabel = new Label();
        messageLabel.setWrapText(true);
        messageLabel.setTextAlignment(TextAlignment.CENTER);
        messageLabel.setText("Loading sudoku, please wait...");
        messageLabel.setPadding(new Insets(10, 5, 5, 10));
        messageLabel.setFont(new Font(messageLabel.getFont().getName(), 30));

        BorderPane layout = new BorderPane(progress, messageLabel, null, null, null);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.initStyle(StageStyle.UNDECORATED);
        new Thread(() -> {
            /*
            try {
                // imitating work
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
             */
            mathPuzzle.findSolution();
            Platform.runLater(new Runnable() {
                public void run() {
                    closeWindow();
                }
            });
        }).start();
        window.showAndWait();
    }

    public static void closeWindow() {
        window.close();
    }
}
