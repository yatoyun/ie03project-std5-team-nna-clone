package ie03.phase2.task6;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.nio.file.*;

import ie03.TestUtils;

public class RouteSimulationWithPathRouteTest {

    @Test
    void testOutput() throws Exception{

        final String input_path = "/phase2/task6/example1_in.txt";
        final String output_path = "/phase2/task6/example1_out.txt";

        TestUtils test = new TestUtils(new RouteSimulationWithPathRoute());
        String output = test.execute(getClass().getResource(input_path).getPath());
        final String outputExpected = new String(Files.readAllBytes(Paths.get(getClass().getResource(output_path).getPath())));

    }
}