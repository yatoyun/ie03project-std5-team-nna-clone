package ie03.phase2.task4.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


class QueryExecutor {
    private final List<Map.Entry<String, Integer>> data;

    QueryExecutor(List<Map.Entry<String, Integer>> data) {
        this.data = data;
    }

    public List<String> executeInstructions(String instructions) {
        Scanner scanner = new Scanner(instructions);
        int numInstructions = Integer.parseInt(scanner.nextLine());

        List<String> results = new ArrayList<>();
        for (int i = 0; i < numInstructions; i++) {
            String line = scanner.nextLine();
            String[] splitLine = line.split(" ");

            int start = Integer.parseInt(splitLine[0]) - 1; // 0-indexed
            int end = Integer.parseInt(splitLine[1]); // make it inclusive

            StringBuilder result = new StringBuilder();
            for (int j = start; j < end; j++) {
                result.append(data.get(j).getValue()).append(" ").append(data.get(j).getKey()).append("\n");
            }

            results.add(result.toString());
        }

        return results;
    }
}