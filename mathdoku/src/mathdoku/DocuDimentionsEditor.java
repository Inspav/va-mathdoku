package mathdoku;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DocuDimentionsEditor {

    public static boolean ok;
    public static int dimetions;

    public static void Exec() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("New docu");
        window.setWidth(300);
        window.setHeight(120);
        window.setMinWidth(300);
        window.setMinHeight(120);

        TextField editor = new TextField();
        Label label = new Label();
        label.setText("Enter docu size: ");
        Button btnSave = new Button("Go");
        Button btnCancel = new Button("Cancel");

        HBox editorsLayout = new HBox(10, label, editor);
        editorsLayout.setPadding(new Insets(10, 5, 0, 5));
        HBox buttonLayout = new HBox(10, btnSave, btnCancel);
        buttonLayout.setPadding(new Insets(10, 5, 5, 10));

        btnSave.setOnAction(е -> {

            try {
                dimetions = Integer.parseInt(editor.getText());
                if (dimetions < 2 || dimetions > 8) throw new NumberFormatException();
            }
            catch (NumberFormatException ex) {
                AlertBox.show("Wrong number format", "Please enter an integer from 2 to 8");
                return;
            }

            ok = true;
            window.close();
        });
        btnCancel.setOnAction(е -> {
            ok = false;
            window.close();
        });

        BorderPane layout = new BorderPane(editorsLayout, null, null, buttonLayout, null);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
