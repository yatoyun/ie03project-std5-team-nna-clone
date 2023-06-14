package ie03.phase2.task5;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ie03.phase2.task5.generator.TestCaseGenerator;
import org.junit.jupiter.api.DynamicTest;

import ie03.TestUtils;
import org.junit.jupiter.api.TestFactory;

import java.io.*;
import java.util.*;
import java.nio.file.*;

public class MainTest {

    String execute(String input) throws Exception{
        TestUtils test = new TestUtils(new Main());
        return test.execute(input);
    }

    String getFileContent(String path) throws IOException, NullPointerException {
        return new String(Files.readAllBytes(Paths.get(Objects.requireNonNull(getClass().getResource(path)).getPath())));
    }

    @TestFactory
    Collection<DynamicTest> generatedTest() throws Exception {

        final String buildPath = System.getProperty("user.dir") + "/build";
        final String TEST_CASES_PREFIX = buildPath + "/generated_testcases/phase2/task5";

        // create directory if it doesn't exist
        final Path path = Paths.get(TEST_CASES_PREFIX);
        Files.createDirectories(path);

        final int numTestCases = 25;
        final String INPUT_FILE_PREFIX = "example";
        final String INPUT_FILE_EXTENSION = "_in.txt";
        final String OUTPUT_FILE_PREFIX = "example";
        final String OUTPUT_FILE_EXTENSION = "_out_actual.txt";
        final String EXPECT_FILE_PREFIX = "example";
        final String EXPECT_FILE_EXTENSION = "_out_expect.txt";

        List<DynamicTest> tests = new ArrayList<>();

        for (int i = 1; i <= numTestCases; i++) {
            TestCaseGenerator generator = new TestCaseGenerator();
            String input_fileName = INPUT_FILE_PREFIX + i + INPUT_FILE_EXTENSION;
            String output_fileName = OUTPUT_FILE_PREFIX + i + OUTPUT_FILE_EXTENSION;
            String expect_fileName = EXPECT_FILE_PREFIX + i + EXPECT_FILE_EXTENSION;

            generator.setTestText(i);

            String input = generator.getIntputText();
            String outputActual = execute(input);
            String outputExpected = generator.getOutputText();

            // write input to file
            Files.write(Paths.get(TEST_CASES_PREFIX + "/" + input_fileName), input.getBytes());
            // write output to file
            Files.write(Paths.get(TEST_CASES_PREFIX + "/" + output_fileName), outputActual.getBytes());
            // write confirmation to file
            Files.write(Paths.get(TEST_CASES_PREFIX + "/" + expect_fileName), outputExpected.getBytes());


            tests.add(DynamicTest.dynamicTest("Generated Test " + i, () -> {

                execute(input);
                System.err.println("[Input] \n" + input);
                System.err.println("[Actual Output] \n" + outputActual);
                System.err.println("[Expected Output] \n" + outputExpected);

                assertEquals(outputExpected, outputActual);
            }));
        }
        return tests;
    }


    @TestFactory
    Collection<DynamicTest> exampleTest() {

        List<DynamicTest> tests = new ArrayList<>();

        String input_path = "/phase2/task5/example_in.txt";
        String output_path = "/phase2/task5/example_out.txt";

        tests.add(DynamicTest.dynamicTest("Example Test", () -> {

            String input = getFileContent(input_path);
            String outputActual = execute(input);
            String outputExpected = getFileContent(output_path);


            System.err.println("[Input] \n" + input);
            System.err.println("[Actual Output] \n" + outputActual);
            System.err.println("[Expected Output] \n" + outputExpected);

            assertEquals(outputExpected, outputActual);
        }));

        return tests;
    }
}