package ie03.phase1.task1.generator;

import java.io.FileWriter;
import java.io.IOException;

public class TestCaseWriter {
    final String testCase;
    final String fileName;

    final String FILEPATH;

    public TestCaseWriter(String testCase, String fileName, String filepath) {
        this.testCase = testCase;
        this.fileName = fileName;
        this.FILEPATH = filepath;
    }

    public boolean writeTestcase() {
        try (FileWriter writer = new FileWriter(FILEPATH + fileName)) {
            writer.write(testCase);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
