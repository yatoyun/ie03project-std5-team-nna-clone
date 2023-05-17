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

        //get N
        lines = get_line(br);
        n = get_n(lines);

        //get N input and set to map
        Map<String, Integer> CombDict = new HashMap<>();
        for(int i=0;i<n;i++){
            lines = get_line(br);
            int m = get_n(lines);
            get_comb(CombDict, m, lines);
        }

        //sort based on key
        List<Map.Entry<String, Integer>> sortedPurchaseCount = new ArrayList<>(CombDict.entrySet());
        sortedPurchaseCount.sort((a, b) -> {
            int cmp = -Integer.compare(a.getValue(), b.getValue());
            if (cmp != 0) {
                return cmp;
            }
            return a.getKey().compareTo(b.getKey());
        });

        //get Q
        lines = get_line(br);
        int q = get_n(lines);

        //output
        for (int i = 0; i < q; i++) {
            String[] query = get_line(br);
            int a = Integer.parseInt(query[0]) - 1;
            int b = Integer.parseInt(query[1]) - 1;

            for (int j = a; j <= b && j < sortedPurchaseCount.size(); j++) {
                Map.Entry<String, Integer> entry = sortedPurchaseCount.get(j);
                System.out.println(entry.getValue() + " " + entry.getKey());
            }
        }
    }

     private static int get_n(String[] lines) {
        int n = 0;
         if (lines.length >= 1)
             n = Integer.parseInt(lines[0]);
         else{
             System.out.println("Error : Input is not valid\n");
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
             System.out.println(e.getMessage());
         }
         return line;
     }

     private static void get_comb(Map<String, Integer> CombDict, int m, String[] name_list){
         for(int i=1;i<=m;i++){
             for(int j=i+1;j<=m;j++){
                 String set_name = get_key_name(name_list[i], name_list[j]);
                 CombDict.put(set_name, CombDict.getOrDefault(set_name, 0) + 1);
             }
         }
     }

     private static String get_key_name(String a, String b){
         String concat_names;

         if (a.compareTo(b) < 0){
             concat_names = a + " " + b;
         }else{
             concat_names = b + " " + a;
         }
         return concat_names;
     }
}