package src.main.java.ie03.phase2.task4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenerateCombination {
    private HashMap<PurchasePair, Integer> combinations = new HashMap<>();

    public Map<PurchasePair, Integer> generateAndStoreCombinations(List<String> inputStrings) {
        for (int i = 0; i < inputStrings.size(); i++) {
            for (int j = i + 1; j < inputStrings.size(); j++) {
                PurchasePair pair = new PurchasePair(inputStrings.get(i), inputStrings.get(j));
                combinations.put(pair, combinations.getOrDefault(pair, 0) + 1);
            }
        }

        return combinations;
    }

    //    public List<Map.Entry<PurchasePair, Integer>> sortCombination(Map<PurchasePair, Integer> map) {
//        List<Map.Entry<PurchasePair, Integer>> sortedList = new ArrayList<>(combinations.entrySet());
//        sortedList.sort((entry1, entry2) -> {
//            PurchasePair pair1 = entry1.getKey();
//            PurchasePair pair2 = entry2.getKey();
//
//            int compareA = pair1.getA().compareTo(pair2.getA());
//            if (compareA != 0) {
//                return compareA;
//            }
//
//            return pair1.getB().compareTo(pair2.getB());
//        });
//
//        return sortedList;
//    }
    public List<Map.Entry<PurchasePair, Integer>> sortCombination(Map<PurchasePair, Integer> map) {
        List<Map.Entry<PurchasePair, Integer>> sortedList = new ArrayList<>(map.entrySet());
        sortedList.sort((entry1, entry2) -> {
            int valueComparison = -Integer.compare(entry1.getValue(), entry2.getValue());
            if (valueComparison != 0) {
                return valueComparison;
            }

            PurchasePair pair1 = entry1.getKey();
            PurchasePair pair2 = entry2.getKey();

            int compareA = pair1.getA().compareTo(pair2.getA());
            if (compareA != 0) {
                return compareA;
            }

            return pair1.getB().compareTo(pair2.getB());
        });

        return sortedList;
    }


    public void printCombinations(List<Map.Entry<PurchasePair, Integer>> sortedList, int first, int last) {
        FormatOutput fo = new FormatOutput();
        
        for (int i = first - 1; i < last; i++) {
            Map.Entry<PurchasePair, Integer> entry = sortedList.get(i);
            System.out.println(entry.getValue() + " " + fo.deleteColon(entry.getKey().toString()));
        }

//  for debug
//        System.out.println("Confirm");
//        for (int i = 0; i < sortedList.size(); i++) {
//            System.out.println(sortedList.get(i));
//
//        }
//        for (Map.Entry<PurchasePair, Integer> entry : sortedEntries) {
//            System.out.println(entry.getValue() + " " + fo.deleteColon(entry.getKey().toString()));
//        }
    }
}
