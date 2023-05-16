package ie03.phase1.task1.generator;

import java.io.FileWriter;
import java.io.IOException;

public class CorrectOutputWriter {

    final String output;
    final String fileName;

    final String FILEPATH = "src/test/resources/phase1/task1/";

    CorrectOutputWriter(String output, String fileName) {
        this.output = output;
        this.fileName = fileName;
    }

    boolean writeOutput() {
        try (FileWriter writer = new FileWriter(FILEPATH + fileName)) {
            writer.write(output);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
