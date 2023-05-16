package ie03.phase1.task1;

import java.util.*;
import java.io.*;

public class PurchaseHistory {
    public static void main(String[] args) throws IOException {
        final Scanner sc = new Scanner(System.in);

        final int numberOfCustomer = Integer.parseInt(sc.next());
        final Map<String, Integer> purchaseCount = new HashMap<>();

        readPurchaseData(sc, numberOfCustomer, purchaseCount);
        final List<Map.Entry<String, Integer>> sortedPurchaseCount = sortPurchaseCount(purchaseCount);

        final int q = Integer.parseInt(sc.next());
        displayInformation(sc, q, sortedPurchaseCount);
    }

    private static void readPurchaseData(Scanner sc, int numberOfCustomer, Map<String, Integer> purchaseCount) throws IOException {
        for (int i = 0; i < numberOfCustomer; i++) {
            final int m = Integer.parseInt(sc.next());
            for (int j = 0; j < m; j++) {
                final String product = sc.next();
                if (purchaseCount.containsKey(product)) {
                    purchaseCount.put(product, purchaseCount.get(product) + 1);
                } else {
                    purchaseCount.put(product, 1);
                }
            }
        }
    }

    private static ArrayList<Map.Entry<String, Integer>> sortPurchaseCount(Map<String, Integer> purchaseCount) {
        final ArrayList<Map.Entry<String, Integer>> sortedPurchaseCount = new ArrayList<>(purchaseCount.entrySet());

        final Comparator<Map.Entry<String, Integer>> valueComp = Map.Entry.comparingByValue(Comparator.reverseOrder());
        final Comparator<Map.Entry<String, Integer>> keyComp = Map.Entry.comparingByKey();
        sortedPurchaseCount.sort(valueComp.thenComparing(keyComp));

        return sortedPurchaseCount;
    }

    private static void displayInformation(Scanner sc, int q, List<Map.Entry<String, Integer>> sortedPurchaseCount) throws IOException {
        for (int i = 0; i < q; i++) {
            final int a = Integer.parseInt(sc.next()) - 1;
            final int b = Integer.parseInt(sc.next()) - 1;

            for (int j = a; j <= b && j < sortedPurchaseCount.size(); j++) {
                Map.Entry<String, Integer> entry = sortedPurchaseCount.get(j);
                System.out.println(entry.getValue() + " " + entry.getKey());
            }
        }
    }
}
