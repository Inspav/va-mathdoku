package mathdoku.engine;

import java.util.ArrayList;

public class MathPuzzleBlueprintItem {
    private String operator;
    private int cageValue;
    private ArrayList<Integer> indexArrayList;

    private int maxIndexValue;

    public MathPuzzleBlueprintItem() {
        indexArrayList = new ArrayList<Integer>();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public int getCageValue() {
        return cageValue;
    }

    public void setCageValue(int cageValue) {
        this.cageValue = cageValue;
    }

    public void addIndex(int indexValue) {
        indexArrayList.add(indexValue);
        maxIndexValue = Math.max(maxIndexValue, indexValue);
    }

    public int getMaxIndexValue() {
        return maxIndexValue;
    }

    public ArrayList<Integer> getIndexArrayList() {
        return indexArrayList;
    }
}
