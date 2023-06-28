package ie03.phase2.task4.generator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class PairCounter {
    public Map<String, Integer> countPairs(List<String> pairs) {
        Map<String, Integer> countMap = new HashMap<>();
        for (String pair : pairs) {
            countMap.put(pair, countMap.getOrDefault(pair, 0) + 1);
        }
        return countMap;
    }
}