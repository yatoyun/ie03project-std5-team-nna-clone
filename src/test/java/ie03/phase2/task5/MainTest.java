package ie03.phase2.task5;

import ie03.TestUtils;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {

    String execute(String input) throws Exception{
        TestUtils test = new TestUtils(new Main());
        return test.execute(input);
    }

    String getFileContent(String path) throws Exception{
        final String fileContent = new String(Files.readAllBytes(Paths.get(getClass().getResource(path).getPath())));
        return fileContent;
    }

    @TestFactory
    Collection<DynamicTest> exampleTest() {

        List<DynamicTest> tests = new ArrayList<>();

        String input_path = "/phase2/task5/example_in.txt";
        String output_path = "/phase2/task5/example_out.txt";

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
