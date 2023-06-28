package ie03.phase2.task4.generator;

import java.util.List;
import java.util.Map;

class PairPrinter {
    public void printPairs(List<Map.Entry<String, Integer>> sortedList) {
        for (Map.Entry<String, Integer> entry : sortedList) {
            System.out.println(entry.getValue() + " " + entry.getKey());
        }
    }

    public void print(String string) {
        System.out.print(string);
    }
}