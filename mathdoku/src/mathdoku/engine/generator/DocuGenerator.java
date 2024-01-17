package mathdoku.engine.generator;

import java.util.ArrayList;

public class DocuGenerator {
    private DocuMatrix values;
    private DocuMatrix cages;
    private ArrayList<CageDescription> cageDescriptions;
    private int size;

    public DocuGenerator(int size) {
        this.size = size;
        values = new DocuMatrix(size);
        cages = new DocuMatrix(size);
    }

    public void generate() {
        values.generate();
        cages.generateCages();

        int maxCage = 0;
        for (int rowIndex = 0; rowIndex < size; rowIndex++)
            for (int colIndex = 0; colIndex < size; colIndex++) {
                if (maxCage < cages.getCell(rowIndex, colIndex)) maxCage = cages.getCell(rowIndex, colIndex);
            }

        cageDescriptions = new ArrayList<>();

        for(int cage = 1; cage <= maxCage; cage++){
            CageDescription cageDescription = new CageDescription();
            for (int rowIndex = 0; rowIndex < size; rowIndex++) {
                for (int colIndex = 0; colIndex < size; colIndex++) {
                    if (cage == cages.getCell(rowIndex, colIndex)) {
                        cageDescription.addCageDescriptionItem(new CageDescriptionItem(rowIndex * size + colIndex, values.getCell(rowIndex, colIndex)));
                    }
                }
            }
            if(cageDescription.getItemSize() > 0) cageDescriptions.add(cageDescription);
        }

        for (CageDescription cageDescription :cageDescriptions
        ) {
            cageDescription.fillOperatorAndResult();
            //cageDescription.print();
        }
    }

    public void print() {
        System.out.println("Values");
        System.out.println("----------------------------------------------------");
        values.print();
        System.out.println("Cages");
        System.out.println("----------------------------------------------------");
        cages.print();
    }

    public void printCages() {
        for (CageDescription cageDescription : cageDescriptions) {
            System.out.println(cageDescription.toString());
        }
    }

    public String printCagesToString() {
        StringBuilder sb = new StringBuilder();
        for (CageDescription cageDescription : cageDescriptions) {
            sb.append(cageDescription.toString());
            sb.append("\n");
        }

        return sb.toString();
    }
}
