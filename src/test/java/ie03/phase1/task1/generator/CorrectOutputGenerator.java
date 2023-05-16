package ie03.phase1.task1.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CorrectOutputGenerator {
    final private Map<String, Integer> purchaseData;
    final private List<Map.Entry<String, Integer>> outputList;
    final private StringBuilder sb = new StringBuilder();


    CorrectOutputGenerator(Map<String, Integer> purchaseData) {
        this.purchaseData = purchaseData;
        outputList = new ArrayList<>();
    }

    private void generatePurchaseList() {
        for (Map.Entry<String, Integer> entry : purchaseData.entrySet()) {
            outputList.add(entry);
        }
    }

    void generateOutputList(int first, int last) {
        generatePurchaseList();
        for (int i = first - 1; i < last; i++) {
            int count = outputList.get(i).getValue();
            String word = outputList.get(i).getKey();

            sb.append(count).append(" ").append(word).append("\n");
        }
    }

    String generateOutput() {
        String output = sb.toString();

        return output;
    }
}
