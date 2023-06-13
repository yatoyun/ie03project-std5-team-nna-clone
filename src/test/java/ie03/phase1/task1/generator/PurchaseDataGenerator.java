package ie03.phase1.task1.generator;

import java.util.*;

public class PurchaseDataGenerator {
    protected final String[] products = {
            "laptop", "fridge", "headphones", "tv", "monitor", "iphone", "toaster", "blender",
            "tea", "butter", "bread", "onion", "carrot", "potato", "rice", "water", "juice", "coffee", "milk", "coke",
            "ayhjls", "alkjuy", "yutreq", "oiuytr", "plkjhu", "jklkjh", "iuytrew", "uiuytr", "azerty", "qwerty"
    };

    String generatePurchases(int caseNumber) {
        //store purchase data
        StringBuilder sb = new StringBuilder();
        final int NUM_CUSTOMER = caseNumber * 10;
        Random rand = new Random();
        //N
        sb.append(NUM_CUSTOMER).append("\n");
        for (int i = 0; i < NUM_CUSTOMER; i++) {
            Set<String> purchases = new HashSet<>();
            int numPurchases = rand.nextInt(10) + 1;

            //create a list of unduplicate purchases
            while (purchases.size() < numPurchases) {
                int productId = rand.nextInt(products.length);
                purchases.add(products[productId]);
            }

            //sort by dictionary order
            List<String> sortedPurchasesList = new ArrayList<>(purchases);
            Collections.sort(sortedPurchasesList);

            sb.append(numPurchases);
            for (String product : sortedPurchasesList) {
                sb.append(" ").append(product);
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
