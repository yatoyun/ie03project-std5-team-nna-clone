package ie03.phase2.task4.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CorrectOutputGenerator {
    String ITEMS;
    List<Integer> Queries;

    List<String> itemList;


    public CorrectOutputGenerator(String items, List<Integer> queries) {
        this.ITEMS = items;
        this.Queries = queries;
    }

    public String writeCorrectOutput() {
        String[] items = ITEMS.split("\n");
        itemList = new ArrayList<>(Arrays.asList(items));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Queries.size() / 2; i++) {
            for (int j = Queries.get(2 * i); j <= Queries.get(2 * i + 1); j++) {
                sb.append(itemList.get(j - 1)).append("\n");
            }
        }
        String output = sb.toString();

        return output;
    }
}
