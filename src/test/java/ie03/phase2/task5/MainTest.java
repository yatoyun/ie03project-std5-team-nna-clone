package ie03.phase2.task5;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DynamicTest;

import ie03.*;
import org.junit.jupiter.api.TestFactory;

import java.io.IOException;
import java.util.*;
public class MainTest extends TestRunner implements TestInterface {

    public String execute(String input) throws Exception{
        TestUtils test = new TestUtils(new Main());
        return test.execute(input);
    }

    @TestFactory
    public Collection<DynamicTest> generatedTest() throws Exception {
        return new ArrayList<>();
    }

    @TestFactory
    public Collection<DynamicTest> exampleTest() throws IOException {

        List<DynamicTest> tests = new ArrayList<>();

        String input_path = "/phase2/task5/example_in.txt";
        String output_path = "/phase2/task5/example_out.txt";
        String input = getFileContent(input_path);
        String outputExpected = getFileContent(output_path);

        tests.add(DynamicTest.dynamicTest("Example Test", () -> {

            String outputActual = execute(input);

            System.err.println("[Input] \n" + input);
            System.err.println("[Actual Output] \n" + outputActual);
            System.err.println("[Expected Output] \n" + outputExpected);

            assertEquals(outputExpected, outputActual);
        }));

        return tests;
    }
}
