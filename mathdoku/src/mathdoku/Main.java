package mathdoku;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mathdoku.engine.MathPuzzle;
import mathdoku.engine.MathPuzzleReader;
import mathdoku.engine.generator.DocuGenerator;

import java.io.File;

public class Main extends Application {

    private Stage mainWindow;
    private BorderPane masterPane;
    private MenuBar mainMenu;
    private MathDokuBoard mathDokuBoard;
    private MathPuzzle mathPuzzle;
    private MenuItem mnuUndo;
    private MenuItem mnuRedo;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Set interface props, create interface elements
        mainWindow = primaryStage;
        primaryStage.setTitle("MathDoku");
        createMenu();

        mathPuzzle = TestData.create3x3();
        // Create math doku board
        mathDokuBoard = new MathDokuBoard(mathPuzzle, this);
        // Create master pane
        masterPane = new BorderPane(mathDokuBoard, mainMenu, null, null, null);

        // Arrange scene and stage
        primaryStage.setScene(new Scene(masterPane, 500, 500));
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(500);
        primaryStage.show();
        newDoku(4);
        //mathDokuBoard.requestFocus();
    }

    private void createMenu() {
        // Creating main menu bar
        mainMenu = new MenuBar();

        // Creating File menu
        Menu fileMenu = new Menu("_Game");
        MenuItem mnuLoadFromFile = new MenuItem("Load from file");
        mnuLoadFromFile.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        MenuItem mnuLoadFromText = new MenuItem("Load from text input");
        mnuLoadFromText.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));
        MenuItem mnuNewGame = new MenuItem("New game");
        //mnuNewGame.setDisable(true);
        MenuItem mnuExit = new MenuItem("Exit");
        MenuItem mnuHint = new MenuItem("Hint");
        MenuItem mnuSolveGame = new MenuItem("Solve game");
        Menu optionsMenu = new Menu("Options");
        CheckMenuItem showMistakes = new CheckMenuItem("Show mistakes");
        showMistakes.setSelected(true);
        optionsMenu.getItems().add(showMistakes);

        fileMenu.getItems().add(mnuLoadFromFile);
        fileMenu.getItems().add(mnuLoadFromText);
        fileMenu.getItems().add(mnuNewGame);
        fileMenu.getItems().add(new SeparatorMenuItem());
        fileMenu.getItems().add(mnuHint);
        fileMenu.getItems().add(mnuSolveGame);
        fileMenu.getItems().add(new SeparatorMenuItem());
        fileMenu.getItems().add(optionsMenu);
        fileMenu.getItems().add(new SeparatorMenuItem());
        fileMenu.getItems().add(mnuExit);

        // File menu event handlers


        // Creating Actions menu
        Menu actionsMenu = new Menu("_Actions");
        mnuUndo = new MenuItem("Undo");
        mnuUndo.setDisable(true);
        mnuUndo.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
        mnuRedo = new MenuItem("Redo");
        mnuRedo.setDisable(true);
        mnuRedo.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));
        MenuItem mnuClear = new MenuItem("Clear");
        actionsMenu.getItems().add(mnuUndo);
        actionsMenu.getItems().add(mnuRedo);
        actionsMenu.getItems().add(mnuClear);
//Font
        Menu fontMenu = new Menu("Font");
        MenuItem mnuSmall = new MenuItem("Small");
        MenuItem mnuMedium = new MenuItem("Medium");
        MenuItem mnuLarge = new MenuItem("Large");
        fontMenu.getItems().addAll(mnuSmall, mnuMedium, mnuLarge);
        // Actions menu event handlers

        mnuLoadFromFile.setOnAction(e -> {
            loadFromFile();
        });

        mnuLoadFromText.setOnAction(e -> {
                   loadFromText();
                }
        );

        mnuNewGame.setOnAction(e -> {
            newDoku(0);
        });

        mnuSolveGame.setOnAction(e -> {
            mathDokuBoard.showSolution();
        });

        mnuHint.setOnAction(e -> {
            mathDokuBoard.showHint();
        });

        showMistakes.setOnAction(e -> {
                    //System.out.println(showMistakes.isSelected());
                    mathDokuBoard.setShowErrors(showMistakes.isSelected());
                }

        );
        mnuExit.setOnAction(e -> exitDoku());

        mnuUndo.setOnAction(e -> {
            mathDokuBoard.boardUndo();
        });

        mnuRedo.setOnAction(e -> {
            mathDokuBoard.boardRedo();
        });

        mnuClear.setOnAction(e -> {
            if (ConformationBox.Show("Clear conformation", "Would you like to clear the board?"))
                mathDokuBoard.boardClear();
        });

        mnuLarge.setOnAction(e -> {
            mathDokuBoard.setLargeFont();
        });

        mnuSmall.setOnAction(e -> {
            mathDokuBoard.setSmallFont();
        });

        mnuMedium.setOnAction(e -> {
            mathDokuBoard.setMediumFont();
        });

        // Arranging main menu bar
        mainMenu.getMenus().add(fileMenu);
        mainMenu.getMenus().add(actionsMenu);
        mainMenu.getMenus().add(fontMenu);
    }

    public void newDoku(int size) {
        if (size == 0) {
            DocuDimentionsEditor.Exec();
            if (!DocuDimentionsEditor.ok) return;
            size = DocuDimentionsEditor.dimetions;
        }

        DocuGenerator generator = new DocuGenerator(size);
        generator.generate();
        String dokuDescription = generator.printCagesToString();
        //System.out.printf(dokuDescription);
        MathPuzzleReader mathPuzzleReader = new MathPuzzleReader();
        try {
            mathPuzzle = mathPuzzleReader.readPuzzleFromString(dokuDescription);
            if (mathPuzzle == null) return;
            mathDokuBoard = new MathDokuBoard(mathPuzzle, this);
            // Create master pane
            masterPane = new BorderPane(mathDokuBoard, mainMenu, null, null, null);

            // Arrange scene and stage
            mainWindow.setScene(new Scene(masterPane, 500, 500));
            WaitBox.show("Wait", mathPuzzle);
            //mathPuzzle.findSolution();
            mathDokuBoard.requestFocus();
        } catch (Exception ex) {
            ex.printStackTrace();//TODO replace stack trace
        }
    }
    public void loadFromText() {
        DokuEditorParam parameter = new DokuEditorParam("");
        DokuEditor.Exec(parameter);
        if (!parameter.isProcessDef()) return;
        MathPuzzleReader mathPuzzleReader = new MathPuzzleReader();
        try {
            MathPuzzle mathPuzzle = mathPuzzleReader.readPuzzleFromString(parameter.getDokuDef());
            if (mathPuzzle == null) return;
            mathDokuBoard = new MathDokuBoard(mathPuzzle, this);
            // Create master pane
            masterPane = new BorderPane(mathDokuBoard, mainMenu, null, null, null);

            // Arrange scene and stage
            mainWindow.setScene(new Scene(masterPane, 500, 500));
            WaitBox.show("Wait", mathPuzzle);
            //mathPuzzle.findSolution();
            mathDokuBoard.requestFocus();
        } catch (Exception ex) {
            ex.printStackTrace();//TODO replace stack trace
        }
    }
    public void loadFromFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open game file");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );
        File selectedFile = fileChooser.showOpenDialog(mainWindow);
        MathPuzzleReader mathPuzzleReader = new MathPuzzleReader();
        if (selectedFile != null) {
            try {
                mathPuzzle = mathPuzzleReader.readPuzzleFromFile(selectedFile.getAbsolutePath());
                if (mathPuzzle == null) return;
                mathDokuBoard = new MathDokuBoard(mathPuzzle, this);
                // Create master pane
                masterPane = new BorderPane(mathDokuBoard, mainMenu, null, null, null);

                // Arrange scene and stage
                mainWindow.setScene(new Scene(masterPane, 500, 500));
                WaitBox.show("Wait", mathPuzzle);
                //mathPuzzle.findSolution();
                mathDokuBoard.requestFocus();
            } catch (Exception ex) {
                ex.printStackTrace();//TODO replace stack trace
            }
        }
    }
    public void exitDoku() {
        mainWindow.close();
    }

    public void enableUndo(boolean isEnabled) {
        mnuUndo.setDisable(!isEnabled);
    }
    public void enableRedo(boolean isEnabled) {
        mnuRedo.setDisable(!isEnabled);
    }
}
