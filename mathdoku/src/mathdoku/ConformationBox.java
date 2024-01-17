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

public class ConformationBox {
    private static boolean result;

    public static boolean Show(String title, String message) {
        result = false;
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
        Button btnYes = new Button("Yes");
        Button btnNo = new Button("No");
        HBox buttonLayout = new HBox(10, btnYes, btnNo);
        buttonLayout.setPadding(new Insets(10, 5, 5, 10));

        btnYes.setOnAction(е -> {
            result = true;
            window.close();

        });
        btnNo.setOnAction(е -> {
            window.close();
        });

        BorderPane layout = new BorderPane(messageLabel, null, null, buttonLayout, null);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return result;
    }
}