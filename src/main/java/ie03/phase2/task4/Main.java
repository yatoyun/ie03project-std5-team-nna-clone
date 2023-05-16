package ie03.phase2.task4;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // Generate combinations
        GenerateCombination gc = new GenerateCombination();
        // Generate purchases list
        List<Map.Entry<PurchasePair, Integer>> sortedList = null;

        long startTime = System.nanoTime();

        int N = Integer.parseInt(sc.next());

        // Read strings from standard input
        for (int i = 0; i < N; i++) {
            int m = Integer.parseInt(sc.next());
            List<String> purchases = new ArrayList<>();

            for (int j = 0; j < m; j++) {
                String items = sc.next();
                purchases.add(items);
            }
            sortedList = gc.sortCombination(gc.generateAndStoreCombinations(purchases));
        }

        int q = Integer.parseInt(sc.next());
        for (int i = 0; i < q; i++) {
            int first = Integer.parseInt(sc.next());
            int last = Integer.parseInt(sc.next());
            gc.printCombinations(sortedList, first, last);
        }

        long endTime = System.nanoTime();
        long result = (long) ((endTime - startTime) / 1e9);
        System.err.println(result + "sec");
    }
}
