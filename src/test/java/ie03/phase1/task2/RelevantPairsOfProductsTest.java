package ie03.phase1.task2;

import ie03.TestRunner;
import ie03.TestInterface;
import ie03.TestCaseWriter;
import ie03.phase2.task4.generator.Generator;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RelevantPairsOfProductsTest extends TestRunner implements TestInterface {

    @TestFactory
    public Collection<DynamicTest> generatedTest() throws Exception {
        TestCaseWriter tcWriter = new TestCaseWriter("/generated_testcases/phase1/task2");
        final int numTestCases = 10;
        Generator generator = new Generator();
        List<DynamicTest> tests = new ArrayList<>();
        RelevantPairsOfProducts rpoProduct = new RelevantPairsOfProducts();

        for (int i = 1; i <= numTestCases; i++) {
            generator.generateInputAndOutput(i);
            String input = generator.getInput();
            String outputActual = execute(input, rpoProduct);
            String outputExpected = generator.getOutput();

            tcWriter.writeTestCase(input, "testcase_" + i + "_in.txt");
            tcWriter.writeTestCase(outputActual, "testcase_" + i + "_out_actual.txt");
            tcWriter.writeTestCase(outputExpected, "testcase_" + i + "_out_expect.txt");

            tests.add(DynamicTest.dynamicTest("Task2 Generated Test " + i, () -> {
                execute(input, rpoProduct);
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

        String input_path = "/phase1/task2/example_in.txt";
        String output_path = "/phase1/task2/example_out.txt";

        String input = getFileContent(input_path);
        String outputExpected = getFileContent(output_path);

        RelevantPairsOfProducts rpoProduct = new RelevantPairsOfProducts();

        tests.add(DynamicTest.dynamicTest("Task2 Example Test", () -> {

            String outputActual = execute(input, rpoProduct);

            System.err.println("[Input] \n" + input);
            System.err.println("[Actual Output] \n" + outputActual);
            System.err.println("[Expected Output] \n" + outputExpected);

            assertEquals(outputExpected, outputActual);
        }));

        return tests;
    }
}