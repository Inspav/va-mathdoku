package mathdoku.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MathPuzzleCage {

    private ArrayList<MathPuzzleItem> cageItems = new ArrayList<MathPuzzleItem>();
    private MathPuzzle cagePuzzle;
    private int cageValue; // Target value after the operation on the values of individual cells
    private String cageOperation;
    private HashMap<Integer, Boolean> indexMap;

    public MathPuzzleCage(MathPuzzle cagePuzzle, int cageValue, String cageOperation, MathPuzzleItem[] items) {
        this.cagePuzzle = cagePuzzle;
        this.cageValue = cageValue;
        this.cageOperation = cageOperation;
        indexMap = new HashMap<>();

        items[0].setCageTag(cageValue + cageOperation);
        for (MathPuzzleItem item : items) {
            indexMap.put(item.getItemIndex(), true);
            cageItems.add(item);
        }
        mapCage();
    }

    public MathPuzzleCage(MathPuzzle cagePuzzle, int cageValue, String cageOperation, ArrayList<Integer> itemIndexes) {
        this.cagePuzzle = cagePuzzle;
        this.cageValue = cageValue;
        this.cageOperation = cageOperation;
        indexMap = new HashMap<>();

        ArrayList<MathPuzzleItem> items = new ArrayList<>();
        for (Integer itemIndex : itemIndexes) {
            items.add(cagePuzzle.getItemByIndex(itemIndex - 1));
        }
        items.get(0).setCageTag(cageValue + cageOperation);
        for (MathPuzzleItem item : items) {
            indexMap.put(item.getItemIndex(), true);
            cageItems.add(item);
        }
        mapCage();
    }

    public boolean cageVerification() throws Exception {
        switch (cageOperation) {
            case "+":
                return cageVerificationPlusOperator();
            case "x":
                return cageVerificationMultiplicationOperator();
            case "-":
                return cageVerificationSubtractionOperator();
            case "÷":
                return cageVerificationDivisionOperator();
            case " ":
                return cageVerificationNoneOperator();
            default:
                throw new Exception("Unknown operator type, possible operators are +, -, x, ÷");
        }
    }

    /*
    Private methods for cageVerification for each different operator
     */
    private boolean cageVerificationPlusOperator() throws Exception {
        int sumResult = 0;
        for (MathPuzzleItem item : cageItems) item.setCageError(false);
        for (MathPuzzleItem item : cageItems) {
            if (item.getItemValue() == 0) return true;
            sumResult += item.getItemValue();
        }
        if (sumResult == cageValue) return true;
        else {
            for (MathPuzzleItem item : cageItems) {
                item.setCageError(true);
            }
            return false;
        }
    }

    private boolean cageVerificationMultiplicationOperator() throws Exception {
        int multiplicationResult = 1;
        for (MathPuzzleItem item : cageItems) item.setCageError(false);
        for (MathPuzzleItem item : cageItems) {
            if (item.getItemValue() == 0) return true;
            multiplicationResult *= item.getItemValue();
        }
        if (multiplicationResult == cageValue) return true;
        else {
            for (MathPuzzleItem item : cageItems) {
                item.setCageError(true);
            }
            return false;
        }
    }

    private boolean cageVerificationSubtractionOperator() throws Exception {
        ArrayList<Integer> subtractionValues = new ArrayList<Integer>();
        for (MathPuzzleItem item : cageItems) item.setCageError(false);
        for (MathPuzzleItem item : cageItems) {
            if (item.getItemValue() == 0) return true;
            subtractionValues.add(item.getItemValue());
        }

        Collections.sort(subtractionValues, Collections.reverseOrder());
        int highestItem = subtractionValues.get(0);

        for (int i = 1; i < subtractionValues.size(); i++) {
            highestItem -= subtractionValues.get(i);
        }
        if (highestItem == cageValue) return true;
        else {
            for (MathPuzzleItem item : cageItems) {
                item.setCageError(true);
            }
            return false;
        }
    }

    private boolean cageVerificationDivisionOperator() throws Exception {
        ArrayList<Integer> divisionValues = new ArrayList<Integer>();
        for (MathPuzzleItem item : cageItems) item.setCageError(false);
        for (MathPuzzleItem item : cageItems) {
            if (item.getItemValue() == 0) return true;
            divisionValues.add(item.getItemValue());
        }

        Collections.sort(divisionValues, Collections.reverseOrder());
        int highestItem = divisionValues.get(0);

        for (int i = 1; i < divisionValues.size(); i++) {
            highestItem /= divisionValues.get(i);
        }
        if (highestItem == cageValue) return true;
        else {
            for (MathPuzzleItem item : cageItems) {
                item.setCageError(true);
            }
            return false;
        }
    }

    private boolean cageVerificationNoneOperator() throws Exception {
        for (MathPuzzleItem item : cageItems) item.setCageError(false);
        if (cageItems.get(0).getItemValue() == cageValue) return true;
        else return false;
    }

    /*
    End of cageVerification helper methods
     */

    /*
    Managing borders of individual cells in a cage
     */
    private void mapCage() {
        for (MathPuzzleItem item : cageItems) {
            item.setRightAndBottomBorders();
            for (MathPuzzleItem sibling : cageItems) {
                if (item == sibling) continue;
                if (item.getItemIndex() < cagePuzzle.getnValue()) {
                    item.setCageTop(true);
                }
                if (((item.getItemIndex() % cagePuzzle.getnValue()) == 0)) {
                    item.setCageLeft(true);
                }
                if (item.getItemIndex() == sibling.getItemIndex() - 1) {
                    item.setCageRight(false);
                } else if (item.getItemIndex() == sibling.getItemIndex() + 1) {
                    sibling.setCageRight(false);
                } else if (item.getItemIndex() == sibling.getItemIndex() - cagePuzzle.getnValue()) {
                    item.setCageBottom(false);
                } else if (item.getItemIndex() == sibling.getItemIndex() + cagePuzzle.getnValue()) {
                    sibling.setCageBottom(false);
                }
            }
        }
    }

    public boolean ownIndex(int index) {
        return indexMap.containsKey(index);
    }

    public boolean cageSolverVerification() {
        switch (cageOperation) {
            case "+":
                return cageSolverVerificationPlusOperator();
            case "x":
                return cageSolverVerificationMultiplicationOperator();
            case "-":
                return cageSolverVerificationSubtractionOperator();
            case "÷":
                return cageSolverVerificationDivisionOperator();
            case " ":
                return cageSolverVerificationNoneOperator();
        }
        return false;
    }

    /*
    Private methods for cageVerification for each different operator
     */
    private boolean cageSolverVerificationPlusOperator() {
        int sumResult = 0;
        for (Map.Entry<Integer, Boolean> item : indexMap.entrySet()) {
            if (cagePuzzle.getSolutionItem(item.getKey()) == 0) return true;
            sumResult += cagePuzzle.getSolutionItem(item.getKey());
        }
        if (sumResult == cageValue) return true;
        return false;
    }

    private boolean cageSolverVerificationMultiplicationOperator() {
        int multiplicationResult = 1;
        for (Map.Entry<Integer, Boolean> item : indexMap.entrySet()) {
            if (cagePuzzle.getSolutionItem(item.getKey()) == 0) return true;
            multiplicationResult *= cagePuzzle.getSolutionItem(item.getKey());
        }
        if (multiplicationResult == cageValue) return true;
        return false;
    }

    private boolean cageSolverVerificationSubtractionOperator() {
        ArrayList<Integer> subtractionValues = new ArrayList<Integer>();
        for (Map.Entry<Integer, Boolean> item : indexMap.entrySet()) {
            if (cagePuzzle.getSolutionItem(item.getKey()) == 0) return true;
            subtractionValues.add(cagePuzzle.getSolutionItem(item.getKey()));
        }

        Collections.sort(subtractionValues, Collections.reverseOrder());
        int highestItem = subtractionValues.get(0);

        for (int i = 1; i < subtractionValues.size(); i++) {
            highestItem -= subtractionValues.get(i);
        }
        if (highestItem == cageValue) return true;
        return false;
    }

    private boolean cageSolverVerificationDivisionOperator() {
        ArrayList<Integer> divisionValues = new ArrayList<Integer>();
        for (Map.Entry<Integer, Boolean> item : indexMap.entrySet()) {
            if (cagePuzzle.getSolutionItem(item.getKey()) == 0) return true;
            divisionValues.add(cagePuzzle.getSolutionItem(item.getKey()));
        }

        Collections.sort(divisionValues, Collections.reverseOrder());
        int highestItem = divisionValues.get(0);

        for (int i = 1; i < divisionValues.size(); i++) {
            highestItem /= divisionValues.get(i);
        }
        if (highestItem == cageValue) return true;
        return false;
    }

    private boolean cageSolverVerificationNoneOperator() {
        for (Map.Entry<Integer, Boolean> item : indexMap.entrySet()) {
            if (cagePuzzle.getSolutionItem(item.getKey()) == 0) return true;
            if (cagePuzzle.getSolutionItem(item.getKey()) == cageValue) return true;
        }
        return false;
    }
}


