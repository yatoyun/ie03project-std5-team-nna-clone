import java.util.*;
import java.io.*;

public class PurchaseHistory {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(reader.readLine());
        Map<String, Integer> purchaseCount = new HashMap<>();

        for (int i = 0; i < n; i++) {
            String[] products = reader.readLine().split(" ");
            int m = Integer.parseInt(products[0]);

            for (int j = 1; j <= m; j++) {
                String product = products[j];
                purchaseCount.put(product, purchaseCount.getOrDefault(product, 0) + 1);
            }
        }

        List<Map.Entry<String, Integer>> sortedPurchaseCount = new ArrayList<>(purchaseCount.entrySet());
        sortedPurchaseCount.sort((a, b) -> {
            int cmp = -Integer.compare(a.getValue(), b.getValue());
            if (cmp != 0) {
                return cmp;
            }
            return a.getKey().compareTo(b.getKey());
        });

        int q = Integer.parseInt(reader.readLine());

        for (int i = 0; i < q; i++) {

            String[] query = reader.readLine().split(" ");
            int a = Integer.parseInt(query[0]) - 1;
            int b = Integer.parseInt(query[1]) - 1;

            for (int j = a; j <= b && j < sortedPurchaseCount.size(); j++) {
                Map.Entry<String, Integer> entry = sortedPurchaseCount.get(j);
                System.out.println(entry.getValue() + " " + entry.getKey());
            }
        }
    }
}
