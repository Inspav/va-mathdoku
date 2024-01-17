package mathdoku.engine.generator;

public class Cage {
    int cageSize;

    public void addCell() {
        cageSize++;
    }

    public int getCageSize() {
        return cageSize;
    }
}