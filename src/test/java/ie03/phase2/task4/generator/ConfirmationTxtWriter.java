package ie03.phase2.task4.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

public class ConfirmationTxtWriter {
    final String ITEMS;
    final String FILE_NAME;
    final String FILE_PATH;
    List<Map.Entry<String, Integer>> list;

    ConfirmationTxtWriter(String items, String file, String path) {
        this.ITEMS = items;
        this.FILE_NAME = file;
        this.FILE_PATH = path;
    }

    public boolean write() {
        String shoppingList = ITEMS;
        Map<String, Integer> pairCount = processShoppingList(shoppingList);

        // sort the pairs by their count, then in lexicographic order
        list = new ArrayList<>(pairCount.entrySet());
        list.sort(Map.Entry.<String, Integer>comparingByValue().reversed().thenComparing(Map.Entry.comparingByKey()));

        // output the pairs and their counts to a file
        try (PrintWriter writer = new PrintWriter(new File(FILE_PATH + FILE_NAME))) {
            for (Map.Entry<String, Integer> entry : list) {
                writer.println(entry.getValue() + " " + entry.getKey());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Map<String, Integer> processShoppingList(String shoppingList) {
        Map<String, Integer> pairCount = new HashMap<>();
        String[] lines = shoppingList.split("\n");
        CreatePairs cp = new CreatePairs();

        for (String line : lines) {
            List<String> items = Arrays.asList(line.split(" "));
            for (int i = 1; i < items.size(); i++) {
                for (int j = i + 1; j < items.size(); j++) {
                    // ensure the items are in lexicographic order
                    String pair = cp.create(items.get(i), items.get(j));
                    pairCount.put(pair, pairCount.getOrDefault(pair, 0) + 1);
                }
            }
        }
        return pairCount;
    }

    public String getListToString() {
        return list.stream()
                .map(entry -> entry.getValue() + " " + entry.getKey())
                .collect(Collectors.joining("\n"));
    }
}


