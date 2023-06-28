package ie03.phase1.task2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.io.IOException;
import java.util.*;

import ie03.*;

public class RelevantPairsOfProductsTest extends TestRunner implements TestInterface {

    @TestFactory
    public Collection<DynamicTest> generatedTest() throws Exception {
        return new ArrayList<>();
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