package ie03.phase2.task5;

import ie03.TestUtils;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {

    String execute(String input) throws Exception{
        TestUtils test = new TestUtils(new Main());
        return test.execute(input);
    }

    String getFileContent(String path) throws IOException, NullPointerException {
        return new String(Files.readAllBytes(Paths.get(Objects.requireNonNull(getClass().getResource(path)).getPath())));
    }

    @TestFactory
    Collection<DynamicTest> exampleTest() throws Exception{

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
