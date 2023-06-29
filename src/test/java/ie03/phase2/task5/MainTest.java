package ie03.phase2.task5;

import ie03.TestCaseWriter;
import ie03.TestInterface;
import ie03.TestRunner;
import ie03.phase2.task5.generator.TestCaseGenerator;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest extends TestRunner implements TestInterface {

    public Collection<DynamicTest> generatedTest() throws Exception {


        TestCaseWriter tcWriter = new TestCaseWriter("/generated_testcases/phase2/task5");

        final int numTestCases = 25;

        List<DynamicTest> tests = new ArrayList<>();

        Main main = new Main();
        int[] falsecount = {0};
        int[] totalcount = {0};

        for (int i = 1; i <= numTestCases; i++) {

            int currentI = i;
            tests.add(DynamicTest.dynamicTest("Task5 Generated Test " + i, () -> {
                TestCaseGenerator generator = new TestCaseGenerator();
                generator.setTestText(currentI);
                generator.runGenerator();

                String input = generator.getIntputText();
                String outputActual = execute(input, main);
                String outputExpected = generator.getOutputText();

                // save image
                generator.saveImage(System.getProperty("user.dir") + "/build"+"/generated_testcases/phase2/task5/testcase_" + currentI);

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

                falsecount[0] += countDifferences(outputExpected.split("\n"), outputActual.split("\n"), currentI);
                totalcount[0] += outputExpected.split("\n").length;
                assertEquals(outputExpected, outputActual);

            }));
        }
        tests.add(DynamicTest.dynamicTest("Task5 Generated Test Result", () -> {
            System.err.println("Total count : " + totalcount[0] + "\nTotal false: " + falsecount[0]);
            System.err.println("Average true rate: " + (1 - ((double) falsecount[0] / totalcount[0])));
        }));
        return tests;
    }


    public Collection<DynamicTest> exampleTest() throws IOException {

        List<DynamicTest> tests = new ArrayList<>();

        String input_path = "/phase2/task5/example_in.txt";
        String output_path = "/phase2/task5/example_out.txt";
        String input = getFileContent(input_path);
        String outputExpected = getFileContent(output_path);

        Main main = new Main();

        tests.add(DynamicTest.dynamicTest("Task5 Example Test", () -> {

            String outputActual = execute(input, main);

            System.err.println("[Input] \n" + input);
            System.err.println("[Actual Output] \n" + outputActual);
            System.err.println("[Expected Output] \n" + outputExpected);

            assertEquals(outputExpected, outputActual);
        }));


        return tests;
    }
    static int countDifferences(String[] expectedOutput, String[] actualOutput, int currentI) {
        int differences = 0;

        for (int i = 0; i < Math.min(currentI, 20); i++) {
            if (!expectedOutput[i].equals(actualOutput[i])) {
                differences++;
            }
        }
        return differences;
    }
}
