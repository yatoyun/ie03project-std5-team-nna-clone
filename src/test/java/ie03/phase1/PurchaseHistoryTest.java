package ie03.phase1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ie03.TestUtils;
import ie03.phase2.task6.RouteSimulationWithPathRoute;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.*;

public class PurchaseHistoryTest {

    @Test
    void testOutput1() throws Exception{
        final String input_path = "/phase1/task1/example1_in.txt";
        final String output_path = "/phase1/task1/example1_out.txt";

        TestUtils test = new TestUtils(new PurchaseHistory());
        String output = test.execute(getClass().getResource(input_path).getPath());
        final String outputExpected = new String(Files.readAllBytes(Paths.get(getClass().getResource(output_path).getPath())));

        assertEquals(outputExpected, output);
    }

    @Test
    void testOutput2() throws Exception{
        final String input_path = "/phase1/task1/example2_in.txt";
        final String output_path = "/phase1/task1/example2_out.txt";

        TestUtils test = new TestUtils(new PurchaseHistory());
        String output = test.execute(getClass().getResource(input_path).getPath());
        final String outputExpected = new String(Files.readAllBytes(Paths.get(getClass().getResource(output_path).getPath())));

        assertEquals(outputExpected, output);
    }
}