package ie03.phase2.task4.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class InputParser {
    public List<List<String>> parseData(String data) {
        Scanner scanner = new Scanner(data);
        int numLines = Integer.parseInt(scanner.nextLine());

        List<List<String>> allItems = new ArrayList<>();
        for (int i = 0; i < numLines; i++) {
            List<String> items = new ArrayList<>();
            String line = scanner.nextLine();
            String[] splitLine = line.split(" ");

            for (int j = 1; j < splitLine.length; j++) {
                items.add(splitLine[j]);
            }

            allItems.add(items);
        }
        return allItems;
    }
}