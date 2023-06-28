package ie03.phase2.task4.generator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

class PairSorter {
    public List<Map.Entry<String, Integer>> sortPairs(Map<String, Integer> countMap) {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(countMap.entrySet());
        list.sort(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                int countCompare = o2.getValue().compareTo(o1.getValue());
                if (countCompare != 0)
                    return countCompare;
                else
                    return o1.getKey().compareTo(o2.getKey());
            }
        });
        return list;
    }
}