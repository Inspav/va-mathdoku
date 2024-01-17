package mathdoku;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.layout.*;
import mathdoku.engine.MathPuzzle;
import mathdoku.engine.MathPuzzleItem;

public class MathDokuBoard extends HBox {

    private MathPuzzle boardPuzzle;
    final GridPane gridPane = new GridPane();
    private boolean showErrors = true;
    private Main main;

    public MathDokuBoard(MathPuzzle boardPuzzle, Main main) {
        this.boardPuzzle = boardPuzzle;
        this.main = main;

        final VBox vBox = new VBox();

        vBox.alignmentProperty().set(Pos.CENTER);
        alignmentProperty().set(Pos.CENTER);


        final NumberBinding binding = Bindings.min(widthProperty(), heightProperty());

        gridPane.setMinSize(200, 200);
        vBox.prefWidthProperty().bind(binding);
        vBox.prefHeightProperty().bind(binding);
        vBox.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);

        vBox.setFillWidth(true);
        VBox.setVgrow(gridPane, Priority.ALWAYS);

        for (int i = 0; i < boardPuzzle.getnValue(); i++) {
            final ColumnConstraints columnConstraints = new ColumnConstraints(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE, Double.MAX_VALUE);
            columnConstraints.setHgrow(Priority.SOMETIMES);
            gridPane.getColumnConstraints().add(columnConstraints);

            final RowConstraints rowConstraints = new RowConstraints(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE, Double.MAX_VALUE);
            rowConstraints.setVgrow(Priority.SOMETIMES);
            gridPane.getRowConstraints().add(rowConstraints);
        }

        vBox.getChildren().add(gridPane);

        getChildren().add(vBox);

        HBox.setHgrow(this, Priority.ALWAYS);

        for (int i = 0; i < boardPuzzle.getnValue(); i++) {
            for (int j = 0; j < boardPuzzle.getnValue(); j++) {
                MathPuzzleItem puzzleItem = boardPuzzle.getItem(i, j);
                final MathDokuCell child = new MathDokuCell(puzzleItem.isCageTop(), puzzleItem.isCageLeft(), puzzleItem.isCageBottom(), puzzleItem.isCageRight(), this, (i * boardPuzzle.getnValue() + j));
                GridPane.setRowIndex(child, i);
                GridPane.setColumnIndex(child, j);
                gridPane.getChildren().add(child);
            }
        }
        renderPuzzle();
        changeSelection(0);

        setOnKeyPressed(e -> {
            int oldIndex = boardPuzzle.getIndexOfSelectedItem();
            Node boardNode = getNodeByRowColumnIndex(oldIndex / boardPuzzle.getnValue(), oldIndex % boardPuzzle.getnValue(), gridPane);
            ((MathDokuCell) boardNode).toggleSelectionMark(false);
            //TODO int oldIndex used in two separate methods.
            switch (e.getCode().getName()) {
                case "Up":
                    boardPuzzle.moveSelectedUp();
                    break;
                case "Left":
                    boardPuzzle.moveSelectedLeft();
                    break;
                case "Down":
                    boardPuzzle.moveSelectedDown();
                    break;
                case "Right":
                    boardPuzzle.moveSelectedRight();
                    break;
                case "1":
                case "2":
                case "3":
                case "4":
                case "5":
                case "6":
                case "7":
                case "8": {
                    int digit = Integer.parseInt(e.getCode().getName());
                    if (boardPuzzle.getnValue() < digit) break;
                    boardPuzzle.setSelectedItemValue(digit);
                    renderPuzzle();
                    main.enableUndo(true);
                }
                break;
                case "Backspace":
                    boardPuzzle.setSelectedItemValue(0);
                    renderPuzzle();
                    break;
                default:
                    System.out.println(e.getCode().getName());
                    return;
            }
            changeSelection(boardPuzzle.getIndexOfSelectedItem());
        });
    }


    //TODO 3 is stuck to the border, add padding
    public void renderPuzzle() {
        int mistakeCount = 0;
        for (int i = 0; i < boardPuzzle.getnValue(); i++) {
            for (int j = 0; j < boardPuzzle.getnValue(); j++) {

                Node boardNode = getNodeByRowColumnIndex(i, j, gridPane);
                MathPuzzleItem puzzleItem = boardPuzzle.getItem(i, j);
                ((MathDokuCell) boardNode).setCageLabel(puzzleItem.getCageTag());
                ((MathDokuCell) boardNode).setCellText(Integer.toString(puzzleItem.getItemValue()));
                ((MathDokuCell) boardNode).setHasMistake(boardPuzzle.hasPuzzleError(i, j) || puzzleItem.isCageError());
                mistakeCount += (boardPuzzle.hasPuzzleError(i, j) || puzzleItem.isCageError()) ? 1 : 0;
                if (boardPuzzle.isSolved() && mistakeCount == 0) {
                    ((MathDokuCell) boardNode).setIsSolved(boardPuzzle.isSolved() && mistakeCount == 0);
                }
            }
        }
        if (boardPuzzle.isSolved() && mistakeCount == 0) {
            dokuWinAnumation();
            WinBox.Show("Congratulations", "You have solved the puzzle");
            switch (WinBox.result) {
                case "Load file":
                    main.loadFromFile();
                    break;
                case "Load text":
                    main.loadFromText();
                    break;
                case "New Puzzle":
                    main.newDoku(0);
                    break;
                case "Exit Game":
                    main.exitDoku();
                    break;
                default:
                    break;
            }
        }
    }


    private Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {
            if (gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }

    public void cellClicked(int cellIndex) {
        changeSelection(cellIndex);
    }

    private void changeSelection(int cellIndex) {
        int oldIndex = boardPuzzle.getIndexOfSelectedItem();
        Node boardNode = getNodeByRowColumnIndex(oldIndex / boardPuzzle.getnValue(), oldIndex % boardPuzzle.getnValue(), gridPane);
        ((MathDokuCell) boardNode).toggleSelectionMark(false);
        boardPuzzle.setIndexOfSelectedItem(cellIndex);
        boardNode = getNodeByRowColumnIndex(cellIndex / boardPuzzle.getnValue(), cellIndex % boardPuzzle.getnValue(), gridPane);
        ((MathDokuCell) boardNode).toggleSelectionMark(true);
    }

    /*
    Getter for the board's puzzle
     */
    public MathPuzzle getBoardPuzzle() {
        return boardPuzzle;
    }

    public void boardUndo() {
        boardPuzzle.undo();
        int i = boardPuzzle.getUndoIndex();
        if (i >= 0) changeSelection(i);
        renderPuzzle();
        main.enableUndo(boardPuzzle.canUndo());
        main.enableRedo(true);
    }

    public void boardRedo() {
        int i = boardPuzzle.getRedoIndex();
        changeSelection(i);
        boardPuzzle.redo();
        renderPuzzle();
        main.enableRedo(boardPuzzle.canRedo());
    }

    public void boardClear() {
        for (int i = 0; i < (boardPuzzle.getnValue() * boardPuzzle.getnValue()); i++) {
            boardPuzzle.setIndexOfSelectedItem(i);
            boardPuzzle.setSelectedItemValue(0);
        }
        renderPuzzle();
    }

    public void setLargeFont() {
        for (int i = 0; i < boardPuzzle.getnValue(); i++) {
            for (int j = 0; j < boardPuzzle.getnValue(); j++) {
                Node boardNode = getNodeByRowColumnIndex(i, j, gridPane);
                ((MathDokuCell) boardNode).setFontsSize(MathDokuCell.CellFontSize.LARGE);
            }
        }
        renderPuzzle();
    }

    public void setSmallFont() {
        for (int i = 0; i < boardPuzzle.getnValue(); i++) {
            for (int j = 0; j < boardPuzzle.getnValue(); j++) {
                Node boardNode = getNodeByRowColumnIndex(i, j, gridPane);
                ((MathDokuCell) boardNode).setFontsSize(MathDokuCell.CellFontSize.SMALL);
            }
        }
        renderPuzzle();
    }

    public void setMediumFont() {
        for (int i = 0; i < boardPuzzle.getnValue(); i++) {
            for (int j = 0; j < boardPuzzle.getnValue(); j++) {
                Node boardNode = getNodeByRowColumnIndex(i, j, gridPane);
                ((MathDokuCell) boardNode).setFontsSize(MathDokuCell.CellFontSize.MEDIUM);
            }
        }
        renderPuzzle();
    }

    public void dokuWinAnumation() {
        for (int i = 0; i < boardPuzzle.getnValue(); i++) {
            for (int j = 0; j < boardPuzzle.getnValue(); j++) {
                Node boardNode = getNodeByRowColumnIndex(i, j, gridPane);
                ((MathDokuCell) boardNode).winAnimation();
            }
        }
    }

    public boolean isShowErrors() {
        return showErrors;
    }

    public void setShowErrors(boolean showErrors) {
        this.showErrors = showErrors;
        renderPuzzle();
    }

    public void showSolution() {
        if (!boardPuzzle.isSolutionFound()) {
            AlertBox.show("Solve", "This doku can't be solved.");
            return;
        }

        new Thread(() -> {
            for (int i = 0; i < (boardPuzzle.getnValue() * boardPuzzle.getnValue()); i++) {
                boardPuzzle.setIndexOfSelectedItem(i);
                boardPuzzle.setSelectedItemValue(boardPuzzle.getSolutionItem(i));
                Platform.runLater(new Runnable() {
                    public void run() {
                        renderPuzzle();
                    }
                });
                try {
                    // imitating work
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
/*
        for(int i = 0; i < (boardPuzzle.getnValue() * boardPuzzle.getnValue()); i ++) {
            boardPuzzle.setIndexOfSelectedItem(i);
            boardPuzzle.setSelectedItemValue(boardPuzzle.getSolutionItem(i));
        }
        renderPuzzle();
 */
    }

    public void showHint() {
        if (!boardPuzzle.isSolutionFound()) {
            AlertBox.show("Solve", "This doku can't be solved.");
            return;
        }

        boardPuzzle.setSelectedItemValue(boardPuzzle.getSolutionItem(boardPuzzle.getIndexOfSelectedItem()));
        renderPuzzle();
    }
}

