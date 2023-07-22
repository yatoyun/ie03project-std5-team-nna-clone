package ie03.phase1.task1;

import java.util.*;

public class PurchaseData {
    protected final ArrayList<ArrayList<String>> data = new ArrayList<>();
    private final Map<String, Integer> purchaseCount = new HashMap<>();

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

    public ArrayList<Map.Entry<String, Integer>> getSortedPurchaseCount() {
        ArrayList<Map.Entry<String, Integer>> sortedPurchaseCount = new ArrayList<>(purchaseCount.entrySet());
        final Comparator<Map.Entry<String, Integer>> valueComp = Map.Entry.comparingByValue(Comparator.reverseOrder());
        final Comparator<Map.Entry<String, Integer>> keyComp = Map.Entry.comparingByKey();
        sortedPurchaseCount.sort(valueComp.thenComparing(keyComp));
        return sortedPurchaseCount;
    }

}
