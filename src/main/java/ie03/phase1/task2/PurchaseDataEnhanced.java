package ie03.phase1.task2;

import ie03.phase1.task1.PurchaseData;
import java.util.*;

public class PurchaseDataEnhanced extends PurchaseData {
    private final Map<String, Integer> purchasePair = new HashMap<>();
    private ArrayList<Map.Entry<String, Integer>> sortedPurchasePair = null;

    private void setPurchasePair() {
        for (ArrayList<String> customer : data) {
            for (int i = 0; i < customer.size(); i++) {
                for (int j = i + 1; j < customer.size(); j++) {
                    String key = set2key(customer.get(i), customer.get(j));
                    if (purchasePair.containsKey(key)) {
                        purchasePair.put(key, purchasePair.get(key) + 1);
                    } else {
                        purchasePair.put(key, 1);
                    }
                }
            }
        }
    }

    public ArrayList<Map.Entry<String, Integer>> getSortedPurchasePairCount() {
        setPurchasePair();
        sortedPurchasePair = new ArrayList<>(purchasePair.entrySet());
        final Comparator<Map.Entry<String, Integer>> valueComp = Map.Entry.comparingByValue(Comparator.reverseOrder());
        final Comparator<Map.Entry<String, Integer>> keyComp = Map.Entry.comparingByKey();
        sortedPurchasePair.sort(valueComp.thenComparing(keyComp));
        return sortedPurchasePair;
    }

    private static String set2key(String a, String b) {
        if (a.compareTo(b) < 0) {
            return a + " " + b;
        } else {
            return b + " " + a;
        }
    }
}
