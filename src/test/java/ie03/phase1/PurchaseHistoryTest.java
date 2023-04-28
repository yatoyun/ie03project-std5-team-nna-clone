package ie03.phase1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.*;

public class PurchaseHistoryTest {

    @Test
    void testOutput1() throws Exception{
        // read input file
        final String input_path = "/phase1/task1/example1_in.txt";
        final FileInputStream input = new FileInputStream(getClass().getResource(input_path).getPath());
        System.setIn(input);

        // set output stream for testing
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        String[] args = null;
        PurchaseHistory.main(args);

        // read output file
        final String output_path = "/phase1/task1/example1_out.txt";
        final String output = new String(Files.readAllBytes(Paths.get(getClass().getResource(output_path).getPath())));


        // reset input and output streams
        System.setIn(System.in);
        System.setOut(System.out);

        // compare output
        assertEquals(output.toString(), outContent.toString());
    }

    @Test
    void testOutput2() throws Exception{
        // read input file
        final String input_path = "/phase1/task1/example2_in.txt";
        final FileInputStream input = new FileInputStream(getClass().getResource(input_path).getPath());
        System.setIn(input);

        // set output stream for testing
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        String[] args = null;
        PurchaseHistory.main(args);

        // read output file
        final String output_path = "/phase1/task1/example2_out.txt";
        final String output = new String(Files.readAllBytes(Paths.get(getClass().getResource(output_path).getPath())));


        // reset input and output streams
        System.setIn(System.in);
        System.setOut(System.out);

        // compare output
        assertEquals(output.toString(), outContent.toString());
    }
}