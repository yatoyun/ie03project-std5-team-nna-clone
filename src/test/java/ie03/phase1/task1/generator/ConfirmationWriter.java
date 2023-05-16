package ie03.phase1.task1.generator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class ConfirmationWriter {
    final private Map<String, Integer> wordCount;
    final private String fileName;

    final String FILEPATH = "src/test/resources/phase1/task1/";

    ConfirmationWriter(Map<String, Integer> wordCount, String fileName) {
        this.wordCount = wordCount;
        this.fileName = fileName;
    }

    boolean writeConfirmationText() {
        try (FileWriter writer = new FileWriter(FILEPATH + fileName)) {
            for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
                String word = entry.getKey();
                int count = entry.getValue();

                writer.write(count + " " + word + "\n");
            }
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}