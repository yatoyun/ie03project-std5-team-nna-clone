package ie03.phase1.task1.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class OutputGenerator {
    final private Map<String, Integer> purchaseData;
    final private List<Map.Entry<String, Integer>> outputList;
    final private List<List<Integer>> queryList;
    final private StringBuilder sb = new StringBuilder();
    private String output;


    OutputGenerator(Map<String, Integer> purchaseData) {
        this.purchaseData = purchaseData;
        queryList = new ArrayList<>();
        outputList = new ArrayList<>();
    }

    void stringQueryToList(String stringQuery) {
        Scanner sc = new Scanner(stringQuery);

        if (sc.hasNextLine()) {
            sc.nextLine();
        }

        while (sc.hasNextLine()) {
            List<Integer> query = new ArrayList<>();
            String line = sc.nextLine();
            String[] parts = line.split(" ");
            for (String part : parts) {
                query.add(Integer.parseInt(part));
            }
            queryList.add(query);
        }
        sc.close();
    }

    void generateOutput() {
        for (Map.Entry<String, Integer> entry : purchaseData.entrySet()) {
            outputList.add(entry);
        }
        //sort by dictionary order
        outputList.sort(Map.Entry.<String, Integer>comparingByValue()
                .reversed()
                .thenComparing(Map.Entry.comparingByKey()));
        for (int i = 0; i < queryList.size(); i++) {
            for (int j = queryList.get(i).get(0) - 1; j <= queryList.get(i).get(1) - 1; j++) {
                int count = outputList.get(j).getValue();
                String word = outputList.get(j).getKey();
                sb.append(count).append(" ").append(word).append("\n");
            }
        }
        this.output = sb.toString();
    }

    String getOutput() {
        return this.output;
    }
}
