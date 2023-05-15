package ie03.phase2.task4;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ie03.TestUtils;

import java.io.*;
import java.nio.file.*;

public class MainTest {

    @Test
    void testOutput() throws Exception{
        final String input_path = "/phase1/task2/example_in.txt";
        final String output_path = "/phase1/task2/example_out.txt";

        TestUtils test = new TestUtils(new Main());
        String output = test.execute(getClass().getResource(input_path).getPath());
        final String outputExpected = new String(Files.readAllBytes(Paths.get(getClass().getResource(output_path).getPath())));
        assertEquals(outputExpected, output);
    }
}
