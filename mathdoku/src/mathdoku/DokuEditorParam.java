package mathdoku;

public class DokuEditorParam {
    private String dokuDef;
    private boolean processDef;

    public DokuEditorParam(String dokuDef) {
        if (dokuDef == null) dokuDef = "";
        this.dokuDef = dokuDef;
        this.processDef = false;
    }

    public String getDokuDef() {
        return dokuDef;
    }

    public void setDokuDef(String dokuDef) {
        this.dokuDef = dokuDef;
    }

    public boolean isProcessDef() {
        return processDef;
    }

    public void setProcessDef(boolean processDef) {
        this.processDef = processDef;
    }
}
