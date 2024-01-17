package mathdoku;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DokuEditor {

    public static void Exec(DokuEditorParam param) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Math Doku editor");
        window.setWidth(300);
        window.setHeight(500);
        window.setMinWidth(300);
        window.setMinHeight(500);

        TextArea editor = new TextArea();
        editor.setText(param.getDokuDef());
        Button btnSave = new Button("Load");
        Button btnCancel = new Button("Cancel");
        HBox buttonLayout = new HBox(10, btnSave, btnCancel);
        buttonLayout.setPadding(new Insets(10, 5, 5, 10));

        btnSave.setOnAction(е -> {
            param.setDokuDef(editor.getText());
            param.setProcessDef(true);
            window.close();
        });
        btnCancel.setOnAction(е -> {
            param.setProcessDef(false);
            window.close();
        });

        BorderPane layout = new BorderPane(editor, null, null, buttonLayout, null);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}

