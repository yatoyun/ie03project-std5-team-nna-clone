package ie03.phase1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import ie03.TestUtils;

import java.io.*;
import java.nio.file.*;

public class RelevantPairsOfProductsTest {

    @Test
    void testOutput() throws Exception{
        /*
        // read input file
        final String input_path = "/phase1/task2/example_in.txt";
        final FileInputStream input = new FileInputStream(getClass().getResource(input_path).getPath());
        System.setIn(input);

        // set output stream for testing
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        String[] args = null;
        RelevantPairsOfProducts.main(args);

        // read output file
        final String output_path = "/phase1/task2/example_out.txt";
        final String output = new String(Files.readAllBytes(Paths.get(getClass().getResource(output_path).getPath())));


        // reset input and output streams
        System.setIn(System.in);
        System.setOut(System.out);

        // compare output
        assertEquals(output.toString(), outContent.toString());
        */

        final String input_path = "/phase1/task2/example_in.txt";
        final String output_path = "/phase1/task2/example_out.txt";

        TestUtils test = new TestUtils(new RelevantPairsOfProducts());
        String output = test.execute(getClass().getResource(input_path).getPath());
        final String outputExpected = new String(Files.readAllBytes(Paths.get(getClass().getResource(output_path).getPath())));
        assertEquals(outputExpected, output);
    }
}