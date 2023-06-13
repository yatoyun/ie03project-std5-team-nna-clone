package ie03.phase1.task1.generator;

import java.util.Random;

public class GenerateQuery {
    private final int NUM_PURCHASES;

    GenerateQuery(int NUM_PURCHASES) {
        this.NUM_PURCHASES = NUM_PURCHASES;
    }

    String QueryGenerator() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();

        final int NUM_QUERIES = rand.nextInt(10) + 1;
        sb.append(NUM_QUERIES).append("\n");
        for (int i = 0; i < NUM_QUERIES; i++) {
            int a = rand.nextInt(NUM_PURCHASES - 1) + 1;
            int b = rand.nextInt(NUM_PURCHASES - a) + 1 + a;//Ensure that a < b

            sb.append(a).append(" ").append(b).append("\n");
        }

        return sb.toString();
    }
}
