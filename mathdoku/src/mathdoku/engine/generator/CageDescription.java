package mathdoku.engine.generator;

import java.util.ArrayList;

public class CageDescription {
    private int result;
    private String operator;
    private ArrayList<CageDescriptionItem> items;
    private ProbabilityGenerator probGen;

    public CageDescription() {
        items = new ArrayList<>();
        probGen = new ProbabilityGenerator();
    }

    public void addCageDescriptionItem(CageDescriptionItem item) {
        items.add(item);
    }

    public void fillOperatorAndResult() {
        if(tryDivision()) return;
        if(trySubtraction()) return;
        if(tryAddition()) return;
        setMultiplication();
    }

    private boolean tryDivision() {
        if (items.size() != 2) return false;
        if(items.get(0).getValue() > items.get(1).getValue() && items.get(0).getValue() % items.get(1).getValue() == 0) {
            result = items.get(0).getValue() / items.get(1).getValue();
            operator = "÷";
            return true;
        }
        if(items.get(1).getValue() > items.get(0).getValue() && items.get(1).getValue() % items.get(0).getValue() == 0) {
            result = items.get(1).getValue() / items.get(0).getValue();
            operator = "÷";
            return true;
        }
        return false;
    }

    private boolean trySubtraction() {
        if (probGen.get50()) return false;
        int result;
        CageDescriptionItem maxItem = items.get(0);
        for (int i = 1; i < items.size(); i++) {
            if(maxItem.getValue() < items.get(i).getValue()) maxItem = items.get(i);
        }
        result = maxItem.getValue();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) == maxItem) continue;
            result -= items.get(i).getValue();
        }
        if(result > 0) {
            this.result = result;
            operator = "-";
            return true;
        }
        return false;
    }

    private boolean tryAddition() {
        if(probGen.get50()) return false;
        for (CageDescriptionItem item: items) {
            result += item.getValue();
        }
        operator = "+";
        return true;
    }

    private void setMultiplication() {
        result = 1;
        for (CageDescriptionItem item : items) {
            result *= item.getValue();
        }
        operator = "x";
    }

    public int getItemSize() {
        return items.size();
    }

    public void print() {
        //System.out.print(String.format("result: %d, operator %s, items: ", result, operator));
        for (CageDescriptionItem item : items
        ) {
            System.out.print(String.format("%d-%d,  ", item.getIndex(), item.getValue()));
        }
        System.out.println("");
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%d%s ", result, operator));
        for (int i=0;i<items.size();i++)
            if (i == items.size()-1) sb.append(String.format("%s", items.get(i).getIndex()+1));
            else sb.append(String.format("%s,", items.get(i).getIndex()+1));
        return sb.toString();
    }
}

