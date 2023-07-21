package ie03.phase1.task1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.io.IOException;
import java.util.*;

import ie03.*;

import ie03.phase1.task1.generator.Generator;

public class PurchaseHistoryTest extends TestRunner implements TestInterface {

    @TestFactory
    public Collection<DynamicTest> generatedTest() throws Exception {

        TestCaseWriter tcWriter = new TestCaseWriter("/generated_testcases/phase1/task1");

        final int numTestCases = 10;

        Generator generator = new Generator();

        List<DynamicTest> tests = new ArrayList<>();

        PurchaseHistory psHistory = new PurchaseHistory();

        //start from i = 3 because test cases 1,2 has already been created.
        for (int i = 1; i <= numTestCases; i++) {

            generator.generateInputAndOutput(i);
            String input = generator.getInput();
            String outputActual = execute(input, psHistory);
            String outputExpected = generator.getOutput();

            // write input to file
            tcWriter.writeTestCase(input, "testcase_" + i + "_in.txt");
            // write output to file
            tcWriter.writeTestCase(outputActual, "testcase_" + i + "_out_actual.txt");
            // write confirmation to file
            tcWriter.writeTestCase(outputExpected, "testcase_" + i + "_out_expected.txt");


            tests.add(DynamicTest.dynamicTest("Task1 Example Test " + i, () -> {
                execute(input, psHistory);

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

        PurchaseHistory psHistory = new PurchaseHistory();

        for (int i = 1; i < 3; i++) {
            String input_path = "/phase1/task1/example" + i + "_in.txt";
            String output_path = "/phase1/task1/example" + i + "_out.txt";

            String input = getFileContent(input_path);
            String outputExpected = getFileContent(output_path);

            tests.add(DynamicTest.dynamicTest("Task1 Generated Test " + i, () -> {

                String outputActual = execute(input, psHistory);

                System.err.println("[Input] \n" + input);
                System.err.println("[Actual Output] \n" + outputActual);
                System.err.println("[Expected Output] \n" + outputExpected);

                assertEquals(outputExpected, outputActual);
            }));
        }

        return tests;
    }

}