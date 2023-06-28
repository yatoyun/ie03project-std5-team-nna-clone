package ie03.phase2.task4.generator;

import java.util.ArrayList;
import java.util.List;

class PairGenerator {
    public List<String> generatePairs(List<List<String>> allItems) {
        List<String> pairs = new ArrayList<>();
        for (List<String> items : allItems) {
            for (int j = 0; j < items.size(); j++) {
                for (int k = j + 1; k < items.size(); k++) {
                    String pair;
                    if (items.get(j).compareTo(items.get(k)) < 0) {
                        pair = items.get(j) + " " + items.get(k);
                    } else {
                        pair = items.get(k) + " " + items.get(j);
                    }

                    pairs.add(pair);
                }
            }
        }
        return pairs;
    }
}