package ie03.phase2.task4.generator;

import ie03.phase1.task1.generator.Counter;

import java.util.*;

public class TestCaseGenerator extends ie03.phase1.task1.generator.TestCaseGenerator {
    String items;
    String queries;

    List<Integer> queryList = new ArrayList<>();

    @Override
    public String generateTestCase(int caseNumber) {
        Random rand = new Random();
        StringBuilder sb1 = new StringBuilder();//store purchaseData
        StringBuilder sb2 = new StringBuilder();//store query

        final int numCustomers = caseNumber * 1000;
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

        items = sb1.toString();
        Counter counter = new Counter(items);
        final int totalPurchases = counter.countWords();
        int numQueries = rand.nextInt(10) + 1;
        sb2.append(numQueries).append("\n");

        for (int i = 0; i < numQueries; i++) {
            int a = rand.nextInt(totalPurchases - 1) + 1;
            int b = rand.nextInt(totalPurchases - a) + a + 1; //a < b

            sb2.append(a).append(" ").append(b).append("\n");
            queryList.add(a);
            queryList.add(b);
        }

        queries = sb2.toString();

        return sb1.append(queries).toString();
    }

    public String getItems() {
        return items;
    }

    public List<Integer> getQueryList() {
        return queryList;
    }
}
