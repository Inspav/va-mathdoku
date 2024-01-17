package mathdoku.engine;

import java.util.ArrayList;
import java.util.Stack;

public class MathPuzzle {

    private ArrayList<MathPuzzleItem> items = new ArrayList<MathPuzzleItem>();
    private ArrayList<MathPuzzleCage> cages = new ArrayList<MathPuzzleCage>();
    private int nValue; //The value of N in the NxN matrix
    private int filledItems = 0;
    private int indexOfSelectedItem;
    private ArrayList<Boolean> rowErrors;
    private ArrayList<Boolean> columnErrors;
    private Stack<PuzzleAction> undoStack;
    private Stack<PuzzleAction> redoStack;
    private ArrayList<Integer> theSolution;
    private boolean solutionFound;

    public MathPuzzle(int nValue) {
        items = new ArrayList<>();
        rowErrors = new ArrayList<Boolean>();
        columnErrors = new ArrayList<Boolean>();
        undoStack = new Stack<PuzzleAction>();
        redoStack = new Stack<PuzzleAction>();
        theSolution = new ArrayList<>();

        for (int i = 0; i < nValue; i++) {
            rowErrors.add(false);
            columnErrors.add(false);
        }

        int index = 0;
        for (int i = 0; i < (nValue * nValue); i++) {
            MathPuzzleItem item = new MathPuzzleItem();
            theSolution.add(0);
            item.setItemIndex(index++);
            items.add(item);
        }
        this.cages = new ArrayList<>();
        this.nValue = nValue;
        this.indexOfSelectedItem = 0;
    }

    public void setCages(MathPuzzleCage[] cages) {
        for (MathPuzzleCage cage : cages) {
            this.cages.add(cage);
        }
    }

    public void addCage(MathPuzzleCage cage) {
        this.cages.add(cage);
    }

    /*
    Gets an item defined by the coordinate system.
     */
    public MathPuzzleItem getItem(int row, int column) {
        if (row < 0) throw new IllegalArgumentException("Row argument is negative, must be positive");
        if (row >= nValue) throw new IllegalArgumentException("Row argument must be less than " + nValue);

        if (column < 0) throw new IllegalArgumentException("Column argument is negative, must be positive");
        if (column >= nValue) throw new IllegalArgumentException("Column argument must be less than " + nValue);

        return items.get((nValue * row) + column);
    }

    public MathPuzzleItem getItemByIndex(int index) {
        return items.get(index);
    }

    /*
    Errors created by the user, row or columns with the same number.
     */
    private void detectPuzzleError(int row, int column) {
        rowErrors.set(row, false);
        for (int columnIndex = 0; columnIndex < nValue; columnIndex++) {
            for (int i = columnIndex + 1; i < nValue; i++) {
                if (getItem(row, columnIndex).getItemValue() == getItem(row, i).getItemValue() &&
                        getItem(row, columnIndex).getItemValue() != 0) {
                    rowErrors.set(row, true);
                }
            }
        }

        columnErrors.set(column, false);
        for (int rowIndex = 0; rowIndex < nValue; rowIndex++) {
            for (int j = rowIndex + 1; j < nValue; j++) {
                if (getItem(rowIndex, column).getItemValue() == getItem(j, column).getItemValue() &&
                        getItem(rowIndex, column).getItemValue() != 0) {
                    columnErrors.set(column, true);
                }
            }
        }
    }

    private boolean hasError(int row, int column) {
        boolean hasRowError = false;
        for (int columnIndex = 0; columnIndex < nValue; columnIndex++) {
            for (int i = columnIndex + 1; i < nValue; i++) {
                if (theSolution.get(row * nValue + columnIndex) == theSolution.get(row * nValue + i) &&
                        theSolution.get(row * nValue + columnIndex) != 0) {
                    hasRowError = true;
                }
            }
        }

        boolean hasColumnError = false;
        for (int rowIndex = 0; rowIndex < nValue; rowIndex++) {
            for (int j = rowIndex + 1; j < nValue; j++) {
                if (theSolution.get(rowIndex * nValue + column) ==theSolution.get(j * nValue + column) &&
                        theSolution.get(rowIndex * nValue + column) != 0) {
                    hasColumnError = true;
                }
            }
        }

        return (hasRowError || hasColumnError || hasCageError(row, column));
    }

    private boolean hasCageError(int row, int column) {
        int index = (row * nValue) + column;
        for (MathPuzzleCage cage : cages) {
            if (cage.ownIndex(index))
                try {
                    return !cage.cageSolverVerification();
                } catch (Exception e) {
                }
        }
        return true;
    }

    public boolean hasPuzzleError(int row, int column) {
        return (rowErrors.get(row) || columnErrors.get(column));
    }

    /*
    Is the MathDoku puzzle solved, by the specifications in the cwk
     */
    public boolean isSolved() {
        return (filledItems == (nValue * nValue));
    }


    public int getnValue() {
        return nValue;
    }

    public int moveSelectedUp() {
        indexOfSelectedItem -= nValue;
        indexOfSelectedItem += (nValue * nValue);
        indexOfSelectedItem %= (nValue * nValue);
        return indexOfSelectedItem;
    }

    public int moveSelectedLeft() {
        int firstCellIndex = indexOfSelectedItem / nValue;
        indexOfSelectedItem -= 1;
        indexOfSelectedItem += nValue;
        indexOfSelectedItem %= nValue;
        indexOfSelectedItem += firstCellIndex * nValue;
        return indexOfSelectedItem;
    }

    public int moveSelectedDown() {
        indexOfSelectedItem += nValue;
        indexOfSelectedItem %= (nValue * nValue);
        return indexOfSelectedItem;
    }

    public int moveSelectedRight() {
        int firstCellIndex = indexOfSelectedItem / nValue;
        indexOfSelectedItem += 1;
        indexOfSelectedItem %= nValue;
        indexOfSelectedItem += firstCellIndex * nValue;
        return indexOfSelectedItem;
    }

    public int getIndexOfSelectedItem() {
        return indexOfSelectedItem;
    }

    public void setIndexOfSelectedItem(int indexOfSelectedItem) {
        this.indexOfSelectedItem = indexOfSelectedItem;
    }

    public void setSelectedItemValue(int value, boolean skipUndo) {
        if (value == items.get(indexOfSelectedItem).getItemValue()) return;
        if (!skipUndo) {
            PuzzleAction undoAction = new PuzzleAction(indexOfSelectedItem, items.get(indexOfSelectedItem).getItemValue(), value);
            undoStack.push(undoAction);
        }
        if (value == 0) {
            if (items.get(indexOfSelectedItem).getItemValue() != 0) filledItems--;
            items.get(indexOfSelectedItem).setItemValue(value);
        } else {
            if (items.get(indexOfSelectedItem).getItemValue() == 0) filledItems++;
            items.get(indexOfSelectedItem).setItemValue(value);

        }
        verifyCages();
        detectPuzzleError(indexOfSelectedItem / nValue, indexOfSelectedItem % nValue);
    }

    public void setSelectedItemValue(int value) {
        setSelectedItemValue(value, false);
    }

    public void undo() {
        if (undoStack.empty()) return;
        PuzzleAction redoAction = new PuzzleAction(indexOfSelectedItem, items.get(indexOfSelectedItem).getItemValue(), 0);
        redoStack.push(redoAction);
        PuzzleAction undoAction = undoStack.pop();
        //indexOfSelectedItem = undoAction.getIndex();
        setIndexOfSelectedItem(undoAction.getIndex());
        setSelectedItemValue(undoAction.getOldValue(), true);
    }

    public int getUndoIndex() {
        if(undoStack.empty()) return -1;
        return undoStack.peek().getIndex();
    }

    public void redo() {
        if (redoStack.empty()) return;
        PuzzleAction redoAction = redoStack.pop();
        indexOfSelectedItem = redoAction.getIndex();
        setSelectedItemValue(redoAction.getOldValue());
    }

    public int getRedoIndex() {
        if(redoStack.empty()) return -1;
        return redoStack.peek().getIndex();
    }

/*
    public void clear() {
        for (MathPuzzleItem item : items) {
            item.setItemValue(0);
        }
        for (int i = 0; i < nValue; i++) {
            rowErrors.remove(i);
            columnErrors.remove(i);
            rowErrors.add(false);
            columnErrors.add(false);

        }
    }


 */
    public void verifyCages() {
        for (MathPuzzleCage cage : cages) {
            try {
                cage.cageVerification();
            } catch (Exception e) {
                // TODO - proper error handling
                e.printStackTrace();
            }
        }
    }

    public void findSolution() {
        solutionFound = false;
        solve();
        printTheSolution();
    }

    private void solve() {
        for (int rowIndex = 0; rowIndex < nValue; rowIndex++) {
            for (int columnIndex = 0; columnIndex < nValue; columnIndex++) {
                if (theSolution.get(rowIndex * nValue + columnIndex) == 0) {
                    for (int probe = 1; probe <= nValue; probe++) {
                        if (isPossible(rowIndex, columnIndex, probe)) {
                            theSolution.set(rowIndex * nValue + columnIndex, probe);
                            solve();
                            if (solutionFound) return;
                            theSolution.set(rowIndex * nValue + columnIndex, 0);
                        }
                    }
                    return;
                }
            }
        }
        solutionFound = true;
        //printTheSolution();
    }

    private boolean isPossible(int row, int column, int value){
        theSolution.set(row * nValue + column, value);
        boolean result = !hasError(row, column);
        theSolution.set(row * nValue + column, 0);
        return result;
    }

    private void printTheSolution() {
        for (int rowIndex = 0; rowIndex < nValue; rowIndex++) {
            for (int columnIndex = 0; columnIndex < nValue; columnIndex++) {
                System.out.print(String.format("%2d ", theSolution.get(rowIndex * nValue + columnIndex)));
            }
            System.out.println();
        }
    }

    public boolean isSolutionFound() {
        return solutionFound;
    }

    public int getSolutionItem(int itemIndex){
        return theSolution.get(itemIndex);
    }

    public boolean canUndo() {
        return !undoStack.empty();
    }

    public boolean canRedo() {
        return !redoStack.empty();
    }
}