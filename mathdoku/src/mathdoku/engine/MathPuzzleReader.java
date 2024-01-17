package mathdoku.engine;

import javafx.scene.control.Alert;
import mathdoku.AlertBox;

import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;

public class MathPuzzleReader {
    public MathPuzzle readPuzzleFromFile(String filename) throws Exception {
        File f = new File(filename);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(f));
        } catch (FileNotFoundException e) {
            AlertBox.show("File not found", "File with name %s does not exist.", filename);
        }
        return readPuzzle(reader);
    }
    public MathPuzzle readPuzzleFromString(String text) {
        BufferedReader reader = new BufferedReader(new StringReader(text));
        return readPuzzle(reader);
    }
    private MathPuzzle readPuzzle(BufferedReader reader) {
        try {
            ArrayList<MathPuzzleBlueprintItem> blueprintArrayList = new ArrayList<>();
            int maxIndexValue = 0;
            String line = null;
            int fileLineNumber = 1;
            while ((line = reader.readLine()) != null) {
                MathPuzzleBlueprintItem blueprintItem = new MathPuzzleBlueprintItem();
                String[] tokens = line.split(" ");
                if (tokens.length != 2)
                    throw new MathPuzzleReaderException("Unexpected line structure on line " + fileLineNumber);

                if (tokens[0].contains("+")) {
                    blueprintItem.setOperator("+");
                    tokens[0] = tokens[0].replace("+", "");
                } else if (tokens[0].contains("x")) {
                    blueprintItem.setOperator("x");
                    tokens[0] = tokens[0].replace("x", "");
                } else if (tokens[0].contains("÷")) {
                    blueprintItem.setOperator("÷");
                    tokens[0] = tokens[0].replace("÷", "");
                } else if (tokens[0].contains("-")) {
                    blueprintItem.setOperator("-");
                    tokens[0] = tokens[0].replace("-", "");
                } else if (tokens[0].length() == 1) {
                    blueprintItem.setOperator(" ");
                } else
                    throw new MathPuzzleReaderException("Missing or incorrectly written operator on line " + fileLineNumber);

                try {
                    blueprintItem.setCageValue(Integer.parseInt(tokens[0]));
                } catch (NumberFormatException e) {
                    throw new MathPuzzleReaderException("Invalid number format on line " + fileLineNumber);
                }

                String[] itemIndexes = tokens[1].split(",");
                for (String itemIndex : itemIndexes) {
                    try {
                        blueprintItem.addIndex(Integer.parseInt(itemIndex));
                    } catch (NumberFormatException e) {
                        throw new MathPuzzleReaderException("Invalid number index format on line " + fileLineNumber);
                    }
                }
                blueprintArrayList.add(blueprintItem);
                maxIndexValue = Math.max(maxIndexValue, blueprintItem.getMaxIndexValue());
                fileLineNumber++;
            }
            if (Math.sqrt(maxIndexValue) - (int) Math.sqrt(maxIndexValue) != 0)
                throw new MathPuzzleReaderException("Invalid puzzle value");
            MathPuzzle result = new MathPuzzle((int) Math.sqrt(maxIndexValue));
            ArrayList<MathPuzzleCage> cages = new ArrayList<>();

            for (MathPuzzleBlueprintItem item : blueprintArrayList) {
                MathPuzzleCage cage = new MathPuzzleCage(result, item.getCageValue(), item.getOperator(), item.getIndexArrayList());
                cages.add(cage);
            }
            result.setCages(cages.toArray(new MathPuzzleCage[cages.size()]));
            return result;
        }
        catch (MathPuzzleReaderException e) {
            AlertBox.show("Incorrect file format", e.getMessage());

        } catch (IOException e) {
            AlertBox.show("IOException", e.getMessage());
        } catch (Exception e) {
            AlertBox.show("GeneralException", e.getMessage());
        }
        return null;
    }
}
