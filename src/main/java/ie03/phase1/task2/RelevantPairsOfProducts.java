package ie03.phase1.task2;

import java.io.*;
import java.lang.*;
import java.util.*;

public class RelevantPairsOfProducts {
    public static void main(String[] args) {
        // System.in -> キーボード入力を受け付けるための引数
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n;
        String[] lines;

        ProductsData productsData = new ProductsData();

        //get N
        lines = get_line(br);
        n = get_n(lines);

        for (int i = 0; i < n; i++) {
            lines = get_line(br);
            int m = get_n(lines);
            ArrayList<String> customer = new ArrayList<>();
            for (int j = 0; j < m; j++) {
                final String product = lines[j + 1];
                customer.add(product);
            }
            productsData.addCustomer(customer);
        }

        //get Q
        lines = get_line(br);
        int q = get_n(lines);

        productsData.setPurchasePair();
        productsData.sortPurchasePair();

        //output
        for (int i = 0; i < q; i++) {
            String[] query = get_line(br);
            int a = Integer.parseInt(query[0]) - 1;
            int b = Integer.parseInt(query[1]) - 1;

            for (int j = a; j <= b && j < productsData.sortedPurchasePair.size(); j++) {
                Map.Entry<String, Integer> entry = productsData.sortedPurchasePair.get(j);
                System.out.println(entry.getValue() + " " + entry.getKey());
            }
        }
    }

     private static int get_n(String[] lines) {
        int n = 0;
         if (lines.length >= 1)
             n = Integer.parseInt(lines[0]);
         else{
             System.err.println("Error : Input is not valid\n");
             System.exit(0);
         }
         return n;
     }

     private static String[] get_line(BufferedReader br) {
         String[] line = null;
         try {
             // キーボード入力を受け付ける
             line = br.readLine().split(" ");
         } catch (IOException e) {
             System.err.println(e.getMessage());
         }
         return line;
     }
}