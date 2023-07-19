package ie03.phase1.task1;

import java.util.*;

public class PurchaseData {
    ArrayList<ArrayList<String>> data = new ArrayList<>();
    Map<String, Integer> purchaseCount = new HashMap<>();
    ArrayList<Map.Entry<String, Integer>> sortedPurchaseCount = null;

    public void addCustomer(ArrayList<String> customer) {
        data.add(customer);
        for (String product : customer) {
            if (purchaseCount.containsKey(product)) {
                purchaseCount.put(product, purchaseCount.get(product) + 1);
            } else {
                purchaseCount.put(product, 1);
            }
        }
    }

    public void sortPurchaseCount() {
        sortedPurchaseCount = new ArrayList<>(purchaseCount.entrySet());
        final Comparator<Map.Entry<String, Integer>> valueComp = Map.Entry.comparingByValue(Comparator.reverseOrder());
        final Comparator<Map.Entry<String, Integer>> keyComp = Map.Entry.comparingByKey();
        sortedPurchaseCount.sort(valueComp.thenComparing(keyComp));
    }

}
