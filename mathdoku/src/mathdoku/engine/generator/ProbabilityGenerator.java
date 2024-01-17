package mathdoku.engine.generator;

import java.util.Random;

public class ProbabilityGenerator {

    private Random randomGenerator;

    public ProbabilityGenerator() {
        randomGenerator = new Random(System.currentTimeMillis());
    }

    public boolean get50() {
        return randomGenerator.nextInt(200) % 2 == 0;
    }

    public boolean get25() {
        return randomGenerator.nextInt(200) % 4 == 0;
    }
}
