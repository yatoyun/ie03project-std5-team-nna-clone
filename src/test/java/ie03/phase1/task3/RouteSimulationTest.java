package ie03.phase1.task3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ie03.TestUtils;

import java.nio.file.*;

public class RouteSimulationTest {

    @Test
    void testOutput() throws Exception{
        final String input_path = "/phase1/task3/example_in.txt";
        final String output_path = "/phase1/task3/example_out.txt";

        TestUtils test = new TestUtils(new RouteSimulation());
        String output = test.execute(getClass().getResource(input_path).getPath());
        final String outputExpected = new String(Files.readAllBytes(Paths.get(getClass().getResource(output_path).getPath())));
        assertEquals(outputExpected, output);

    }
}