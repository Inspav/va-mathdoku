package mathdoku.engine;

public class MathPuzzleItem {

    private int itemValue;
    private String cageTag; //TODO Cage tag in only one cell
    private int itemIndex;// relies on a filled ArrayList

    private boolean cageError = false;
    private boolean isSelected = false;

    private boolean cageLeft = false;
    private boolean cageBottom = false;
    private boolean cageRight = false;
    private boolean cageTop = false;

    public int getItemValue() {
        return itemValue;
    }

    public void setItemValue(int itemValue) {
        this.itemValue = itemValue;
    }

    public String getCageTag() {
        return cageTag;
    }

    public void setCageTag(String cageTag) {
        this.cageTag = cageTag;
    }

    public boolean isCageError() {
        return cageError;
    }

    public void setCageError(boolean cageError) {
        this.cageError = cageError;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isCageLeft() {
        return cageLeft;
    }

    public void setCageLeft(boolean cageLeft) {
        this.cageLeft = cageLeft;
    }

    public boolean isCageBottom() {
        return cageBottom;
    }

    public void setCageBottom(boolean cageBottom) {
        this.cageBottom = cageBottom;
    }

    public boolean isCageRight() {
        return cageRight;
    }

    public void setCageRight(boolean cageRight) {
        this.cageRight = cageRight;
    }

    public boolean isCageTop() {
        return cageTop;
    }

    public void setCageTop(boolean cageTop) {
        this.cageTop = cageTop;
    }

    public int getItemIndex() {
        return itemIndex;
    }

    public void setItemIndex(int itemIndex) {
        this.itemIndex = itemIndex;
    }

    public void setRightAndBottomBorders() {
        cageBottom = true;
        cageRight = true;
    }
}


