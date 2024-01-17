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

public class AlertBox {

    public static void show(String title, String message, Object... args) {
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
        messageLabel.setText(String.format(message, args));
        Button btnOk = new Button("OK");
        HBox buttonLayout = new HBox(10, btnOk);
        buttonLayout.setPadding(new Insets(10, 5, 5, 10));

        btnOk.setOnAction(е -> {
            window.close();
        });

        BorderPane layout = new BorderPane(messageLabel, null, null, buttonLayout, null);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
