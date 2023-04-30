package src.test.java.phase1.task1;

import java.util.*;

public class TestCaseGenerator {
    private static final String[] products = {
            "laptop", "fridge", "headphones", "tv", "monitor", "iphone", "toaster", "blender",
            "tea", "butter", "bread", "onion", "carrot", "potato", "rice", "water", "juice", "coffee", "milk", "coke",
            "ayhjls", "alkjuy", "yutreq", "oiuytr", "plkjhu", "jklkjh", "iuytrew", "uiuytr", "azerty", "qwerty"
    };
    private Map<String, Integer> purchasesData;
    private String output;

    String generateTestCase(int caseNumber) {
        Random rand = new Random();
        StringBuilder sb1 = new StringBuilder();//store purchaseData
        StringBuilder sb2 = new StringBuilder();//store query

        final int numCustomers = caseNumber * 10;
        //N
        sb1.append(numCustomers).append("\n");

        for (int i = 0; i < numCustomers; i++) {
            Set<String> Purchases = new HashSet<>();
            int numPurchases = rand.nextInt(10) + 1;

            //Create a list of unduplicated purchases
            while (Purchases.size() < numPurchases) {
                int productId = rand.nextInt(products.length);
                Purchases.add(products[productId]);
            }

            //Sort by dictionary order
            List<String> sortedPurchases = new ArrayList<>(Purchases);
            Collections.sort(sortedPurchases);

            sb1.append(numPurchases);
            for (String product : sortedPurchases) {
                sb1.append(" ").append(product);
            }
            sb1.append("\n");
        }

        Counter counter = new Counter(sb1.toString());
        final int totalPurchases = counter.countWords();
        purchasesData = counter.getSortedWordCount();
        CorrectOutputGenerator cog = new CorrectOutputGenerator(purchasesData);

        int numQueries = rand.nextInt(10) + 1;
        sb2.append(numQueries).append("\n");

        for (int i = 0; i < numQueries; i++) {
            int a = rand.nextInt(totalPurchases - 1) + 1;
            int b = rand.nextInt(totalPurchases - a) + a + 1; //a < b

            sb2.append(a).append(" ").append(b).append("\n");
            cog.generateOutputList(a, b);
        }
        output = cog.generateOutput();

        String queries = sb2.toString();

        return sb1.append(queries).toString();
    }

    Map<String, Integer> getPurchasesData() {
        return purchasesData;
    }

    String getOutput() {
        return output;
    }
}
