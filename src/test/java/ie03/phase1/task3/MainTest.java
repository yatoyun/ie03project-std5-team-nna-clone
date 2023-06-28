package ie03.phase1.task3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ie03.phase1.task3.generator.TestCaseIntegrator;
import org.junit.jupiter.api.DynamicTest;

import ie03.*;
import org.junit.jupiter.api.TestFactory;

import java.io.IOException;
import java.util.*;

public class MainTest extends TestRunner implements TestInterface {

    @TestFactory
    public Collection<DynamicTest> generatedTest() throws Exception {

        TestCaseWriter tcWriter = new TestCaseWriter("/generated_testcases/phase1/task3");

        final int numTestCases = 25;

        List<DynamicTest> tests = new ArrayList<>();

        Main main = new Main();

        for (int i = 1; i <= numTestCases; i++) {
            TestCaseIntegrator generator = new TestCaseIntegrator(4 * i, 4 * i);

            generator.putShelves(Math.min(3*i, 25));
            generator.generateRouteRandomly(Math.min(3*i, 20), 1);

            String input = generator.getInput();
            String outputActual = execute(input, main);
            String outputExpected = generator.getOutput();

            // write input to file
            tcWriter.writeTestCase(input, "testcase_" + i + "_in.txt");
            // write output to file
            tcWriter.writeTestCase(outputActual, "testcase_" + i + "_out_actual.txt");
            // write confirmation to file
            tcWriter.writeTestCase(outputExpected, "testcase_" + i + "_out_expected.txt");

            tests.add(DynamicTest.dynamicTest("Task3 Generated Test " + i, () -> {

                execute(input, main);
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

        String input_path = "/phase1/task3/example_in.txt";
        String output_path = "/phase1/task3/example_out.txt";
        String input = getFileContent(input_path);
        String outputExpected = getFileContent(output_path);

        Main main = new Main();

        tests.add(DynamicTest.dynamicTest("Task3 Example Test", () -> {

            String outputActual = execute(input, main);


            System.err.println("[Input] \n" + input);
            System.err.println("[Actual Output] \n" + outputActual);
            System.err.println("[Expected Output] \n" + outputExpected);

            assertEquals(outputExpected, outputActual);
        }));

        return tests;
    }
}