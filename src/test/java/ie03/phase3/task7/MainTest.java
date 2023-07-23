package ie03.phase3.task7;

import ie03.TestCaseWriter;
import ie03.TestInterface;
import ie03.TestRunner;
import ie03.phase3.task7.generator.Generator;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class MainTest extends TestRunner implements TestInterface {

    @TestFactory
    public Collection<DynamicTest> generatedTest() throws Exception {

        TestCaseWriter tcWriter = new TestCaseWriter("/generated_testcases/phase3/task7");

        final int numTestCases = 5;

        ie03.phase3.task7.Main main = new ie03.phase3.task7.Main();
        List<DynamicTest> tests = new ArrayList<>();

        for (int i = 1; i <= numTestCases; i++) {
            Generator generator = new Generator((int) Math.ceil(i/2.));

            // generate test case
            String input = (
                generator.putShelves() + "\n" +
                generator.putProducts() + "\n" +
                generator.createPurchaseData()
            );

            System.err.println(input);
            String outputActual = execute(input, main);

            tcWriter.writeTestCase(input, "testcase_" + i + "_in.txt");
            // write output to file
            tcWriter.writeTestCase(outputActual, "testcase_" + i + "_out_actual.txt");

            tests.add(DynamicTest.dynamicTest("Task7 Generated Test " + i, () -> {

                assertDoesNotThrow(() -> execute(input, main));
                System.err.println("[Input] \n" + input);
                System.err.println("[Actual Output] \n" + outputActual);

            }));
        }
        return tests;
    }

    @Override
    public Collection<DynamicTest> exampleTest() {
        return null;
    }
}