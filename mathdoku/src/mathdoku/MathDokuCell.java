package mathdoku;

import javafx.animation.RotateTransition;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class MathDokuCell extends Pane {

    public enum CellFontSize {SMALL, MEDIUM, LARGE}

    ;
    private Label topLabel;
    private Line dtBorder;
    private Line dlBorder;
    private Line dbBorder;
    private Line drBorder;
    private int dbOffSet = 0;
    private MathDokuBoard cellBoard;
    int cellIndex;
    private Circle circle;
    private Label centerLabel;


    private boolean hasMistake;

    public MathDokuCell(boolean hasCageTop, boolean hasCageLeft, boolean hasCageBottom, boolean hasCageRight, MathDokuBoard cellBoard, int cellIndex) {

        // Creating selection cycle
        circle = new Circle(10);
        circle.radiusProperty().bind(Bindings.divide(widthProperty(), 4));

        circle.setFill(Color.WHITE);

        /*circle.setOpacity(0.1);*/
        circle.centerXProperty().bind(widthProperty().divide(2));
        circle.centerYProperty().bind(widthProperty().divide(2));


        // Creating cage label
        topLabel = new Label();
        topLabel.setFont(new Font(topLabel.getFont().getName(), 10));
        topLabel.layoutXProperty().setValue(5);
        topLabel.layoutYProperty().setValue(3);
        topLabel.setVisible(false);

        // Creating main label
        centerLabel = new Label();
        centerLabel.setFont(new Font(centerLabel.getFont().getName(), 10));
        centerLabel.setText("");
        centerLabel.layoutXProperty().bind(widthProperty().subtract(centerLabel.widthProperty()).divide(2));
        centerLabel.layoutYProperty().bind(heightProperty().subtract(centerLabel.heightProperty()).divide(2));

        // Creating borders
        Line topBorder = new Line();
        /*
        topBorder.startXProperty().setValue(0);
        topBorder.startYProperty().setValue(0);
        topBorder.endYProperty().setValue(0);
        topBorder.endXProperty().bind(widthProperty());

         */
        dtBorder = new Line();
        dtBorder.startXProperty().setValue(0);
        dtBorder.startYProperty().setValue(dbOffSet);
        dtBorder.endYProperty().setValue(dbOffSet);
        dtBorder.endXProperty().bind(widthProperty());

        Line leftBorder = new Line();
        /*
        leftBorder.startXProperty().setValue(0);
        leftBorder.startYProperty().setValue(0);
        leftBorder.endXProperty().setValue(0);
        leftBorder.endYProperty().bind(heightProperty());

         */
        dlBorder = new Line();
        dlBorder.startXProperty().setValue(dbOffSet);
        dlBorder.startYProperty().setValue(0);
        dlBorder.endXProperty().setValue(dbOffSet);
        dlBorder.endYProperty().bind(heightProperty());

        Line rightBorder = new Line();
        /*
        rightBorder.startXProperty().bind(widthProperty());
        rightBorder.startYProperty().setValue(0);
        rightBorder.endXProperty().bind(widthProperty());
        rightBorder.endYProperty().bind(heightProperty());

         */
        drBorder = new Line();
        drBorder.startXProperty().bind(widthProperty().subtract(dbOffSet));
        drBorder.startYProperty().setValue(0);
        drBorder.endXProperty().bind(widthProperty().subtract(dbOffSet));
        drBorder.endYProperty().bind(heightProperty());

        Line bottomBorder = new Line();
        /*
        bottomBorder.startXProperty().setValue(0);
        bottomBorder.startYProperty().bind(heightProperty());
        bottomBorder.endXProperty().bind(widthProperty());
        bottomBorder.endYProperty().bind(heightProperty());

         */
        dbBorder = new Line();
        dbBorder.startXProperty().setValue(0);
        dbBorder.startYProperty().bind(heightProperty().subtract(dbOffSet));
        dbBorder.endXProperty().bind(widthProperty());
        dbBorder.endYProperty().bind(heightProperty().subtract(dbOffSet));

        dtBorder.setVisible(hasCageTop);
        dlBorder.setVisible(hasCageLeft);
        dbBorder.setVisible(hasCageBottom);
        drBorder.setVisible(hasCageRight);

        // Putting all together
        getChildren().add(circle);
        getChildren().add(topBorder);
        getChildren().add(leftBorder);
        getChildren().add(rightBorder);
        getChildren().add(bottomBorder);
        getChildren().add(topLabel);
        getChildren().add(centerLabel);
        getChildren().add(dtBorder);
        getChildren().add(dlBorder);
        getChildren().add(dbBorder);
        getChildren().add(drBorder);

        this.cellBoard = cellBoard;
        this.cellIndex = cellIndex;

        setFontsSize(CellFontSize.MEDIUM);

        setOnMouseClicked(e -> {
            if (e.getClickCount() == 1) {
                cellBoard.cellClicked(cellIndex);
            } else if (e.getClickCount() == 2) {
                cellBoard.cellClicked(cellIndex);
                int result = NumericKeyboardBox.Show("Select Number", cellBoard.getBoardPuzzle().getnValue());
                if (result >= 0) {
                    cellBoard.getBoardPuzzle().setSelectedItemValue(result);
                    cellBoard.renderPuzzle();
                }
            }
        });
    }

    public void setCageLabel(String cageLabel) {
        topLabel.setVisible(true);
        topLabel.setText(cageLabel);
    }

    public void toggleSelectionMark(boolean isVisible) {
        if (isVisible) circle.setFill(Color.web("#abc8e4"));
        else circle.setFill(Color.WHITE);
    }

    public void setCellText(String text) {
        if (text.equals("0")) {
            centerLabel.setText("");
        } else {
            centerLabel.setText(text);
        }
    }

    public boolean isHasMistake() {
        return hasMistake;
    }

    public void setHasMistake(boolean hasMistake) {
        this.hasMistake = hasMistake;
        if (hasMistake && cellBoard.isShowErrors()) {
            this.setStyle("-fx-background-color: #fc2008;");
        } else {
            this.setStyle("-fx-background-color: #F4F4F4;");
        }
    }

    public void setIsSolved(boolean isSolved) {
        if (isSolved) {
            this.setStyle("-fx-background-color: #00ff00;");
        }
    }

    public void setFontsSize(CellFontSize fontSize) {
        switch (fontSize) {
            case SMALL:
                centerLabel.setFont(new Font(centerLabel.getFont().getName(), 20));
                topLabel.setFont(new Font(topLabel.getFont().getName(), 10));
                break;
            case MEDIUM:
                centerLabel.setFont(new Font(centerLabel.getFont().getName(), 30));
                topLabel.setFont(new Font(topLabel.getFont().getName(), 12));
                break;
            case LARGE:
                centerLabel.setFont(new Font(centerLabel.getFont().getName(), 40));
                topLabel.setFont(new Font(topLabel.getFont().getName(), 14));
                break;
            default:
                break;
        }

    }

    public void winAnimation() {
        RotateTransition rotateLabel = new RotateTransition(Duration.seconds(3), centerLabel);
        rotateLabel.setByAngle(360);
        rotateLabel.setAutoReverse(true);
        rotateLabel.setCycleCount(4);

        rotateLabel.play();
    }
}

