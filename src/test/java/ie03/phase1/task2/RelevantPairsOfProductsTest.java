package ie03.phase1.task2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ie03.phase1.task2.RelevantPairsOfProducts;
import org.junit.jupiter.api.Test;

import ie03.TestUtils;

import java.nio.file.*;

public class RelevantPairsOfProductsTest {

    @Test
    void testOutput() throws Exception{

        final String input_path = "/phase1/task2/example_in.txt";
        final String output_path = "/phase1/task2/example_out.txt";

        TestUtils test = new TestUtils(new RelevantPairsOfProducts());
        String output = test.execute(getClass().getResource(input_path).getPath());
        final String outputExpected = new String(Files.readAllBytes(Paths.get(getClass().getResource(output_path).getPath())));
        assertEquals(outputExpected, output);
    }
}