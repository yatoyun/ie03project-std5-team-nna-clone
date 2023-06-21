package ie03.phase1.task3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ie03.phase1.task3.generator.TestCaseIntegrator;
import org.junit.jupiter.api.DynamicTest;

import ie03.*;
import org.junit.jupiter.api.TestFactory;

import java.io.*;
import java.util.*;
import java.nio.file.*;

public class MainTest extends TestRunner implements TestInterface {

    public String execute(String input) throws Exception{
        TestUtils test = new TestUtils(new Main());
        return test.execute(input);
    }

    @TestFactory
    public Collection<DynamicTest> generatedTest() throws Exception {

        TestCaseWriter tcWriter = new TestCaseWriter("/generated_testcases/phase1/task3");

        final int numTestCases = 25;

        List<DynamicTest> tests = new ArrayList<>();

        for (int i = 1; i <= numTestCases; i++) {
            TestCaseIntegrator generator = new TestCaseIntegrator(4 * i, 4 * i);

            generator.putShelves(Math.min(3*i, 25));
            generator.generateRouteRandomly(Math.min(3*i, 20), 1);

            String input = generator.getInput();
            String outputActual = execute(input);
            String outputExpected = generator.getOutput();

            // write input to file
            tcWriter.writeTestCase(input, "testcase_" + i + "_in.txt");
            // write output to file
            tcWriter.writeTestCase(outputActual, "testcase_" + i + "_out_actual.txt");
            // write confirmation to file
            tcWriter.writeTestCase(outputExpected, "testcase_" + i + "_out_expected.txt");

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
    public Collection<DynamicTest> exampleTest() {

        List<DynamicTest> tests = new ArrayList<>();

        String input_path = "/phase1/task3/example_in.txt";
        String output_path = "/phase1/task3/example_out.txt";

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