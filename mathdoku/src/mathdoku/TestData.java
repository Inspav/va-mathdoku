package mathdoku;

import mathdoku.engine.MathPuzzle;
import mathdoku.engine.MathPuzzleCage;
import mathdoku.engine.MathPuzzleItem;

public class TestData {
    public static MathPuzzle create() {
        MathPuzzle result = new MathPuzzle(6);
        MathPuzzleCage[] cages = new MathPuzzleCage[15];
        cages[0] = new MathPuzzleCage(result, 11, "+",
                new MathPuzzleItem[]{result.getItem(0, 0), result.getItem(1, 0)});
        cages[1] = new MathPuzzleCage(result, 2, "÷",
                new MathPuzzleItem[]{result.getItem(0, 1), result.getItem(0, 2)});
        cages[2] = new MathPuzzleCage(result, 20, "x",
                new MathPuzzleItem[]{result.getItem(0, 3), result.getItem(1, 3)});
        cages[3] = new MathPuzzleCage(result, 6, "x",
                new MathPuzzleItem[]{result.getItem(0, 4), result.getItem(0, 5),
                    result.getItem(1,5), result.getItem(2,5)});
        cages[4] = new MathPuzzleCage(result, 3, "-",
                new MathPuzzleItem[]{result.getItem(1, 1), result.getItem(1, 2)});
        cages[5] = new MathPuzzleCage(result, 3, "÷",
                new MathPuzzleItem[]{result.getItem(1, 4), result.getItem(2, 4)});
        cages[6] = new MathPuzzleCage(result, 240, "x",
                new MathPuzzleItem[]{result.getItem(2, 0), result.getItem(2, 1),
                        result.getItem(3,0), result.getItem(3,1)});
        cages[7] = new MathPuzzleCage(result, 6, "x",
                new MathPuzzleItem[]{result.getItem(2, 2), result.getItem(2, 3)});
        cages[8] = new MathPuzzleCage(result, 6, "x",
                new MathPuzzleItem[]{result.getItem(3, 2), result.getItem(4, 2)});
        cages[9] = new MathPuzzleCage(result, 7, "+",
                new MathPuzzleItem[]{result.getItem(3, 3), result.getItem(4, 3),
                        result.getItem(4,4)});
        cages[10] = new MathPuzzleCage(result, 30, "x",
                new MathPuzzleItem[]{result.getItem(3, 4), result.getItem(3, 5)});
        cages[11] = new MathPuzzleCage(result, 6, "x",
                new MathPuzzleItem[]{result.getItem(4, 0), result.getItem(4, 1)});
        cages[12] = new MathPuzzleCage(result, 8, "+",
                new MathPuzzleItem[]{result.getItem(5, 0), result.getItem(5, 1),
                result.getItem(5,2)});
        cages[13] = new MathPuzzleCage(result, 2, "÷",
                new MathPuzzleItem[]{result.getItem(5,3 ), result.getItem(5, 4)});
        cages[14] = new MathPuzzleCage(result, 9, "+",
                new MathPuzzleItem[]{result.getItem(4, 5), result.getItem(5, 5)});
        result.setCages(cages);
        return result;
    }
    public static MathPuzzle create3x3() {
        MathPuzzle result = new MathPuzzle(3);
        return result;
    }
}
