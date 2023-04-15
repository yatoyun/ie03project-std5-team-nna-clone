import java.util.*;
import java.io.*;

public class PurchaseHistory {
    public static void main(String[] args) throws IOException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        final int numberOfCustomer = Integer.parseInt(reader.readLine());
        final Map<String, Integer> purchaseCount = new HashMap<>();

        readPurchaseData(reader, numberOfCustomer, purchaseCount);
        final List<Map.Entry<String, Integer>> sortedPurchaseCount = sortPurchaseCount(purchaseCount);

        final int q = Integer.parseInt(reader.readLine());
        displayInformation(reader, q, sortedPurchaseCount);
    }

    private static void readPurchaseData(BufferedReader reader, int numberOfCustomer, Map<String, Integer> purchaseCount) throws IOException {
        for (int i = 0; i < numberOfCustomer; i++) {
            String[] products = reader.readLine().split(" ");
            final int m = Integer.parseInt(products[0]);

            for (int j = 1; j <= m; j++) {
                String product = products[j];
                purchaseCount.put(product, purchaseCount.getOrDefault(product, 0) + 1);
            }
        }
    }

    private static List<Map.Entry<String, Integer>> sortPurchaseCount(Map<String, Integer> purchaseCount) {
        final List<Map.Entry<String, Integer>> sortedPurchaseCount = new ArrayList<>(purchaseCount.entrySet());

        // 降順にソートするためにコンパレータが負を返すようにする
        sortedPurchaseCount.sort((a, b) -> {
            final int cmp = -Integer.compare(a.getValue(), b.getValue());
            if (cmp != 0) {
                return cmp;
            }

            return a.getKey().compareTo(b.getKey());
        });

        return sortedPurchaseCount;
    }

    private static void displayInformation(BufferedReader reader, int q, List<Map.Entry<String, Integer>> sortedPurchaseCount) throws IOException {
        for (int i = 0; i < q; i++) {
            String[] query = reader.readLine().split(" ");
            final int a = Integer.parseInt(query[0]) - 1;
            final int b = Integer.parseInt(query[1]) - 1;

            for (int j = a; j <= b && j < sortedPurchaseCount.size(); j++) {
                Map.Entry<String, Integer> entry = sortedPurchaseCount.get(j);
                System.out.println(entry.getValue() + " " + entry.getKey());
            }
        }
    }
}
