package ie03.phase1.task3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DynamicTest;

import ie03.TestUtils;
import org.junit.jupiter.api.TestFactory;

import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RouteSimulationTest {

    String execute(String input) throws Exception{
        TestUtils test = new TestUtils(new RouteSimulation());
        return test.execute(input);
    }

    String getFileContent(String path) throws Exception{
        final String fileContent = new String(Files.readAllBytes(Paths.get(getClass().getResource(path).getPath())));
        return fileContent;
    }

    @TestFactory
    Collection<DynamicTest> exampleTest() {

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