package ie03.phase1.task1.generator;

import java.io.FileWriter;
import java.io.IOException;

public class TestCaseWriter {
    final String testCase;
    final String fileName;

    final String FILEPATH = "src/test/resources/phase1/task1/";

    TestCaseWriter(String testCase, String fileName) {
        this.testCase = testCase;
        this.fileName = fileName;
    }

    boolean writeTestcase() {
        try (FileWriter writer = new FileWriter(FILEPATH + fileName)) {
            writer.write(testCase);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
