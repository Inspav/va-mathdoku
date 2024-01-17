package mathdoku.engine;

public class PuzzleAction {
    private int index;
    private int oldValue;
    private int newValue;

    public PuzzleAction(int index, int oldValue, int newValue) {
        this.index = index;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public int getIndex() {
        return index;
    }

    public int getOldValue() {
        return oldValue;
    }

    public int getNewValue() {
        return newValue;
    }
}
