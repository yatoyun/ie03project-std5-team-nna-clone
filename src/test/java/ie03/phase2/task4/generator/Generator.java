package ie03.phase2.task4.generator;

import ie03.phase1.task1.generator.InterFaceTask1Task4;

import java.util.List;
import java.util.Map;

public class Generator {
    private String input;
    private String output;
    private String query;


    public void generateInputAndOutput(int numCase) {
        InterFaceTask1Task4 generator = new InterFaceTask1Task4();
        final StringBuilder sb = new StringBuilder();
        generator.generateInput(numCase);

        input = generator.getInput();
        query = generator.getQuery();

        InputParser inputParser = new InputParser();
        List<List<String>> allItems = inputParser.parseData(input);

        PairGenerator pairGenerator = new PairGenerator();
        List<String> pairs = pairGenerator.generatePairs(allItems);

        PairCounter pairCounter = new PairCounter();
        Map<String, Integer> countMap = pairCounter.countPairs(pairs);

        PairSorter pairSorter = new PairSorter();
        List<Map.Entry<String, Integer>> sortedList = pairSorter.sortPairs(countMap);

        QueryExecutor qe = new QueryExecutor(sortedList);
        List<String> results = qe.executeInstructions(query);

        for (String result : results) {
            sb.append(result);
        }
        output = sb.toString();
    }

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }
}

