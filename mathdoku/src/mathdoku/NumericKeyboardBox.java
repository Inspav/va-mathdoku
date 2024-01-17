package mathdoku;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NumericKeyboardBox {
    private static int result;

    public static int  Show(String title, int maxNumber) {
        result = 0;
        // Arrange stage props
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setWidth(200);
        window.setHeight(200);
        window.setMinWidth(200);
        window.setMinHeight(200);
        window.setResizable(false);

        // Arrange layout
        VBox layout = new VBox(10);

        HBox r1 = new HBox(10);
        r1.setPadding(new Insets(10, 12, 2, 12));
        Button b1 = new Button("1");
        b1.setMinWidth(50);
        Button b2 = new Button("2");
        b2.setMinWidth(50);
        Button b3 = new Button("3");
        b3.setMinWidth(50);
        if (maxNumber < 3) b3.setDisable(true);
        r1.getChildren().addAll(b1, b2, b3);

        HBox r2 = new HBox(10);
        r2.setPadding(new Insets(2, 12, 2, 12));
        Button b4 = new Button("4");
        b4.setMinWidth(50);
        if (maxNumber < 4) b4.setDisable(true);
        Button b5 = new Button("5");
        b5.setMinWidth(50);
        if (maxNumber < 5) b4.setDisable(true);
        Button b6 = new Button("6");
        b6.setMinWidth(50);
        if (maxNumber < 6) b6.setDisable(true);
        r2.getChildren().addAll(b4, b5, b6);

        HBox r3 = new HBox(10);
        r3.setPadding(new Insets(2, 12, 2, 12));
        Button b7 = new Button("7");
        b7.setMinWidth(50);
        if (maxNumber < 7) b7.setDisable(true);
        Button b8 = new Button("8");
        b8.setMinWidth(50);
        if (maxNumber < 8) b8.setDisable(true);
        Button b9 = new Button("9");
        b9.setMinWidth(50);
        if (maxNumber < 9) b9.setDisable(true);
        r3.getChildren().addAll(b7, b8, b9);

        HBox r4 = new HBox(10);
        r4.setPadding(new Insets(2, 12, 2, 12));
        Button bOK = new Button("");
        bOK.setDisable(true);
        bOK.setMinWidth(50);
        Button bCl = new Button("Clear");
        bCl.setMinWidth(50);
        Button bEx = new Button("Exit");
        bEx.setMinWidth(50);
        r4.getChildren().addAll(bOK, bCl, bEx);

        // Set event handlers
        b1.setOnAction(e -> {
            result = 1;
            window.close();
        });
        b2.setOnAction(e -> {
            result = 2;
            window.close();
        });
        b3.setOnAction(e -> {
            result = 3;
            window.close();
        });
        b4.setOnAction(e -> {
            result = 4;
            window.close();
        });
        b5.setOnAction(e -> {
            result = 5;
            window.close();
        });
        b6.setOnAction(e -> {
            result = 6;
            window.close();
        });
        b7.setOnAction(e -> {
            result = 7;
            window.close();
        });
        b8.setOnAction(e -> {
            result = 8;
            window.close();
        });
        b9.setOnAction(e -> {
            result = 9;
            window.close();
        });
        bCl.setOnAction(e -> {
            result = 0;
            window.close();
        });
        bEx.setOnAction(e -> {
            result = -1;
            window.close();
        });

        layout.getChildren().addAll(r1, r2, r3, r4);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return result;
    }
}

