package ie03.phase1.task1.generator;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

public class Counter {
    private final Map<String, Integer> wordCount = new HashMap<>();
    private final Pattern pattern = Pattern.compile("\\b[a-zA-Z]+\\b");
    private final String text;

    private int total;

    public Counter(String text) {
        this.text = text;
        this.total = 0;
    }

    public int countWords() {
        String[] words = text.split("\\s+");
        for (String word : words) {
            if (pattern.matcher(word).matches()) { // Check if the word is not a number
                wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
            }
        }

        total = wordCount.size();
        return total;
    }

    Map<String, Integer> getSortedWordCount() {
        TreeMap<String, Integer> sortedWordCount = new TreeMap<>((word1, word2) -> {
            int compare = wordCount.get(word2).compareTo(wordCount.get(word1));
            if (compare != 0) {
                return compare;
            } else {
                return word1.compareTo(word2);
            }
        });

        sortedWordCount.putAll(wordCount);
        return sortedWordCount;
    }
}
