package ie03.phase1.task1.generator;

import java.io.FileWriter;
import java.io.IOException;

public class CorrectOutputWriter {

    protected final String output;
    protected final String fileName;

    protected final String FILEPATH;

    public CorrectOutputWriter(String output, String fileName, String filepath) {
        this.output = output;
        this.fileName = fileName;
        this.FILEPATH = filepath;
    }

    public boolean writeOutput() {
        try (FileWriter writer = new FileWriter(FILEPATH + fileName)) {
            writer.write(output);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
