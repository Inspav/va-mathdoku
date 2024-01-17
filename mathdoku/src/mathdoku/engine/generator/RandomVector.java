package mathdoku.engine.generator;

import java.util.ArrayList;
import java.util.Random;

public class RandomVector {
    private ArrayList<Integer> vector;
    private ArrayList<Integer> numbers;
    private int size;
    private Random randomGenerator;

    public RandomVector(int size) {
        this.size = size;
        vector = new ArrayList<>(size);
        numbers = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            vector.add(0);
            numbers.add(i + 1);
        }
        randomGenerator = new Random(System.currentTimeMillis());
    }

    public void randomize() {
        int availableNumbers = size;
        for (int i = 0; i < size; i++) {
            {
                int randomNumberIndex = randomGenerator.nextInt(availableNumbers);
                vector.set(i, numbers.get(randomNumberIndex));
                numbers.remove(randomNumberIndex);
                availableNumbers--;
            }
        }
    }

    public Integer[] getValues() {
        return vector.toArray(new Integer[vector.size()]);
    }

    public Integer[] getOperationValues() {
        ArrayList<Integer> opValues = new ArrayList<>(size - 1);
        for (int i = 0; i < size; i++) if (vector.get(i) < size) opValues.add(vector.get(i));
        return opValues.toArray(new Integer[opValues.size()]);
    }

    public void print() {
        for (int i = 0; i < size; i++) System.out.print(String.format("%d ", vector.get(i)));
    }
}
