package ie03.phase2.task4;

import ie03.phase1.task2.ProductsData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        long startTime = System.nanoTime();

        int N = Integer.parseInt(sc.next());

        ProductsData productsData = new ProductsData();

        // Read strings from standard input
        for (int i = 0; i < N; i++) {
            int m = Integer.parseInt(sc.next());
            List<String> purchases = new ArrayList<>();
            for (int j = 0; j < m; j++) {
                String items = sc.next();
                purchases.add(items);
            }
            productsData.addCustomer((ArrayList<String>) purchases);
        }

        productsData.setPurchasePair();
        productsData.sortPurchasePair();


        int q = Integer.parseInt(sc.next());
        for (int i = 0; i < q; i++) {
            int first = Integer.parseInt(sc.next());
            int last = Integer.parseInt(sc.next());
            for (int j = first - 1; j < last; j++) {
                Map.Entry<String, Integer> entry = productsData.sortedPurchasePair.get(j);
                System.out.println(entry.getValue() + " " + entry.getKey());
            }
        }

        long endTime = System.nanoTime();
        long result = (long) ((endTime - startTime) / 1e6);
        System.err.println(result + " ms");
    }
}
