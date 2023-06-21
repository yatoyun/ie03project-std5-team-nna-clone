package ie03;

import java.io.*;
import java.util.*;
import java.nio.file.*;

public class TestCaseWriter {

    String writePathPrefix;

    public TestCaseWriter(String pathPrefix) throws IOException, NullPointerException {
        /*
        example: TestCaseWriter("/generated_testcases/phase2/task5");
        Then the file will be written to "build/generated_testcases/phase2/task5"
        */
        final String buildPath = System.getProperty("user.dir") + "/build";
        writePathPrefix = buildPath + pathPrefix;
        final Path path = Paths.get(writePathPrefix);
        Files.createDirectories(path);
    }

    public void writeTestCase(String input, String filename) throws IOException, NullPointerException {
        /*
        example: writeTestCase(input, "example_1_in.txt");
        Then the file name will be "example_1_in.txt"
         */
        Files.write(Paths.get(writePathPrefix + "/" + filename), input.getBytes());
    }
}
