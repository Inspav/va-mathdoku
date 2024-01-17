package mathdoku.engine.generator;

import java.util.ArrayList;

public class CageCollection {

    ArrayList<Cage> cages;

    public CageCollection() {
        cages = new ArrayList<>();
    }

    public int newCage() {
        Cage cage = new Cage();
        cage.addCell();
        cages.add(cage);
        return cages.size();
    }

    public Cage getCage(int Index) {
        return cages.get(Index-1);
    }

    public int getCageSize(int cageIndex) {
        return cages.get(cageIndex-1).getCageSize();
    }

    public void print() {
        for(int i = 0;i<cages.size();i++) System.out.println(String.format("Cage %d, cage size %d", i+1, cages.get(i).getCageSize()));
    }
}
