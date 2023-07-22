package ie03.phase1.task1;

import java.util.*;
import java.io.*;

public class PurchaseHistory {
    public static void main(String[] args) throws IOException {
        final Scanner sc = new Scanner(System.in);

        PurchaseData purchaseData = new PurchaseData();

        final int numberOfCustomer = Integer.parseInt(sc.next());
        for (int i = 0; i < numberOfCustomer; i++) {
            final int m = Integer.parseInt(sc.next());
            ArrayList<String> customer = new ArrayList<>();
            for (int j = 0; j < m; j++) {
                final String product = sc.next();
                customer.add(product);
            }
            purchaseData.addCustomer(customer);
        }

        ArrayList<Map.Entry<String, Integer>> sortedPurchaseCount = purchaseData.getSortedPurchaseCount();

        final int q = Integer.parseInt(sc.next());
        displayInformation(sc, q, sortedPurchaseCount);
    }

    private static void displayInformation(Scanner sc, int q, List<Map.Entry<String, Integer>> sortedPurchaseCount) {
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
