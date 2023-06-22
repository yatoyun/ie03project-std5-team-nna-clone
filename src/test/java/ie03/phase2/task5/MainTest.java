package ie03.phase2.task5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.io.IOException;
import java.util.*;

import ie03.*;

import ie03.phase2.task5.generator.TestCaseGenerator;

public class MainTest extends TestRunner implements TestInterface {

    @TestFactory
    public Collection<DynamicTest> generatedTest() throws Exception {


        TestCaseWriter tcWriter = new TestCaseWriter("/generated_testcases/phase2/task5");

        final int numTestCases = 25;

        List<DynamicTest> tests = new ArrayList<>();

        Main main = new Main();

        for (int i = 1; i <= numTestCases; i++) {

            int currentI = i;
            tests.add(DynamicTest.dynamicTest("Generated Test " + i, () -> {
                TestCaseGenerator generator = new TestCaseGenerator();
                generator.setTestText(currentI);
                generator.runGenerator();

                String input = generator.getIntputText();
                String outputActual = execute(input, main);
                String outputExpected = generator.getOutputText();

                // save image
                generator.saveImage(TEST_CASES_PREFIX + "/" + input_fileName);

                // write input to file
                tcWriter.writeTestCase(input, "testcase_" + currentI + "_in.txt");
                // write output to file
                tcWriter.writeTestCase(outputActual, "testcase_" + currentI + "_out_actual.txt");
                // write confirmation to file
                tcWriter.writeTestCase(outputExpected, "testcase_" + currentI + "_out_expect.txt");

                // print
                System.err.println("[Input] \n" + input);
                System.err.println("[Actual Output] \n" + outputActual);
                System.err.println("[Expected Output] \n" + outputExpected);

                assertEquals(outputExpected, outputActual);
            }));
        }
        return tests;
    }


    @TestFactory
    public Collection<DynamicTest> exampleTest() throws IOException {

        List<DynamicTest> tests = new ArrayList<>();

        String input_path = "/phase2/task5/example_in.txt";
        String output_path = "/phase2/task5/example_out.txt";
        String input = getFileContent(input_path);
        String outputExpected = getFileContent(output_path);

        Main main = new Main();

        tests.add(DynamicTest.dynamicTest("Example Test", () -> {

            String outputActual = execute(input, main);

            System.err.println("[Input] \n" + input);
            System.err.println("[Actual Output] \n" + outputActual);
            System.err.println("[Expected Output] \n" + outputExpected);

            assertEquals(outputExpected, outputActual);
        }));

        return tests;
    }
}