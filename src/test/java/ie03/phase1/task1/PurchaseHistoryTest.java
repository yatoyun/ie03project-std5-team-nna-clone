package ie03.phase1.task1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ie03.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.DynamicTest;

import java.util.*;
import java.nio.file.*;

public class PurchaseHistoryTest {

    String execute(String input) throws Exception{
        TestUtils test = new TestUtils(new PurchaseHistory());
        return test.execute(input);
    }

    String getFileContent(String path) throws Exception{
        final String fileContent = new String(Files.readAllBytes(Paths.get(getClass().getResource(path).getPath())));
        return fileContent;
    }

    @TestFactory
    Collection<DynamicTest> exampleTest() {

        List<DynamicTest> tests = new ArrayList<>();

        for (int i=1; i<3; i++) {
            String input_path = "/phase1/task1/example" + i + "_in.txt";
            String output_path = "/phase1/task1/example" + i + "_out.txt";

            tests.add(DynamicTest.dynamicTest("Example Test " + i, () -> {

                String input = getFileContent(input_path);
                String outputActual = execute(input);
                String outputExpected = getFileContent(output_path);


                System.err.println("[Input] \n" + input);
                System.err.println("[Actual Output] \n" + outputActual);
                System.err.println("[Expected Output] \n" + outputExpected);

                assertEquals(outputExpected, outputActual);
            }));
        }

        return tests;
    }

}