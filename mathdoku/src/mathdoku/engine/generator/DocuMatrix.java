package mathdoku.engine.generator;

import java.util.ArrayList;

public class DocuMatrix {
    private ArrayList<Integer> matrix;
    private int size;

    public DocuMatrix(int size) {
        this.size = size;
        matrix = new ArrayList<>(size * size);
        for (int i = 0; i < size * size; i++) matrix.add(0);
    }

    public void setCell(int row, int col, int value) {
        matrix.set(row * size + col, value);
    }

    public void setRow(Integer[] rowValues, int rowIndex) {
        for (int i = 0; i < size; i++) setCell(rowIndex, i, rowValues[i]);
    }

    public int getCell(int row, int col) {
        return matrix.get(row * size + col);
    }

    public void generate() {
        RandomVector vector = new RandomVector(size);
        vector.randomize();
        setRow(vector.getValues(), 0);
        Integer[] opValues = vector.getOperationValues();
        int rowIndex = 1;
        for (int i = 0; i < size - 1; i++) {
            for (int colIndex = 0; colIndex < size; colIndex++) {
                int sum = getCell(0, colIndex) + opValues[i];
                if (sum != size) sum %= size;
                setCell(rowIndex, colIndex, sum);
            }
            rowIndex++;
        }
    }

    public void generateCages() {

        CageCollection cageCollection = new CageCollection();
        ProbabilityGenerator probGen = new ProbabilityGenerator();

        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            for (int colIndex = 0; colIndex < size; colIndex++) {
                if (getCell(rowIndex, colIndex) == 0) {
                    // Клетката НЕ Е в Cage
                    setCell(rowIndex, colIndex, cageCollection.newCage());
                }

                // Клетката Е в Cage
                // Определяне накъде да продължим (надясно или надолу)
                int rowDelta = 0;
                int colDelta = 0;
                switch (cageCollection.getCageSize(getCell(rowIndex, colIndex))) {
                    case 1:
                        // 100% вероятност за добаване на нови клетки в Cage-а
                        if (probGen.get50()) rowDelta++;
                        else colDelta++;
                        break;
                    case 2:
                        // 50% вероятност за добаване на нови клетки в Cage-а
                        if (!probGen.get50()) break;
                        if (probGen.get50()) rowDelta++;
                        else colDelta++;
                        break;
                    case 3:
                        // 25% вероятност за добаване на нови клетки в Cage-а
                        if (!probGen.get25()) break;
                        if (probGen.get50()) rowDelta++;
                        else colDelta++;
                        break;
                    default:
                        // 0% вероятност за добаване на нови клетки в Cage-а
                        break;
                }

                if (colDelta != 0 && colIndex < size - 1 && getCell(rowIndex, colIndex + colDelta) > 0) continue;

                if (rowDelta != 0 || colDelta != 0) {
                    if (rowIndex < size - 1 && colIndex < size - 1) {
                        setCell(rowIndex + rowDelta, colIndex + colDelta, getCell(rowIndex, colIndex));
                        cageCollection.getCage(getCell(rowIndex, colIndex)).addCell();
                    }
                }
            }
        }

        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            for (int colIndex = 0; colIndex < size; colIndex++) {
                if (cageCollection.getCageSize(getCell(rowIndex, colIndex)) == 1) {
                    //System.out.println(String.format("Cell %d, %d is ORPHAN", rowIndex, colIndex));
                    int cageId = getSibblingCageWIthMinSize(rowIndex, colIndex, cageCollection);
                    setCell(rowIndex, colIndex, cageId);
                    cageCollection.getCage(cageId).addCell();
                }
            }
        }
    }

    private int getSibblingCageWIthMinSize(int rowIndex, int colIndex, CageCollection cageCollection) {
        int cageId = 0;
        int cageSize = 9999999;

        if (rowIndex > 0) {
            int tempCageSize = cageCollection.getCageSize(getCell(rowIndex - 1, colIndex));
            if (tempCageSize < cageSize) {
                cageId = getCell(rowIndex - 1, colIndex);
                cageSize = tempCageSize;
            }
        }
        if (rowIndex < size - 1) {
            int tempCageSize = cageCollection.getCageSize(getCell(rowIndex + 1, colIndex));
            if (tempCageSize < cageSize) {
                cageId = getCell(rowIndex + 1, colIndex);
                cageSize = tempCageSize;
            }
        }
        if (colIndex > 0) {
            int tempCageSize = cageCollection.getCageSize(getCell(rowIndex, colIndex - 1));
            if (tempCageSize < cageSize) {
                cageId = getCell(rowIndex, colIndex - 1);
                cageSize = tempCageSize;
            }
        }
        if (colIndex < size - 1) {
            int tempCageSize = cageCollection.getCageSize(getCell(rowIndex, colIndex + 1));
            if (tempCageSize < cageSize) {
                cageId = getCell(rowIndex, colIndex + 1);
                cageSize = tempCageSize;
            }
        }

        //System.out.println(String.format("Cage index %d, cage size %d", cageId, cageSize));
        return cageId;
    }

    public void print() {
        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            for (int colIndex = 0; colIndex < size; colIndex++) {
                System.out.print(String.format("%2d ", getCell(rowIndex, colIndex)));
            }
            System.out.println();
        }
    }
}
