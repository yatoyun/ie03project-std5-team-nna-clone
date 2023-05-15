package ie03.phase2.task6;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.*;

import ie03.TestUtils;

public class RouteSimulationWithPathRouteTest {

    @Test
    void testOutput() throws Exception{
        /*
        // read input file
        final String input_path = "/phase2/task6/example1_in.txt";
        final FileInputStream input = new FileInputStream(getClass().getResource(input_path).getPath());
        System.setIn(input);

        // set output stream for testing
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        String[] args = null;
        RouteSimulationWithPathRoute.main(args);

        // read output file
        final String output_path = "/phase2/task6/example1_out.txt";
        final String output = new String(Files.readAllBytes(Paths.get(getClass().getResource(output_path).getPath())));

        // reset input and output streams
        System.setIn(System.in);
        System.setOut(System.out);

        // compare output
        assertEquals(output.toString(), outContent.toString());
        */

        final String input_path = "/phase2/task6/example1_in.txt";
        final String output_path = "/phase2/task6/example1_out.txt";

        TestUtils test = new TestUtils(new RouteSimulationWithPathRoute());
        String output = test.execute(getClass().getResource(input_path).getPath());
        final String outputExpected = new String(Files.readAllBytes(Paths.get(getClass().getResource(output_path).getPath())));
        // assertEquals(outputExpected, output);

    }
}