package ie03;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.io.*;
import java.util.*;
import java.nio.file.*;

public class TestRunner {

    public String execute(String input, Object cls) throws Exception {
        TestUtils test = new TestUtils(cls);
        return test.execute(input);
    }
    public String getFileContent(String path) throws IOException, NullPointerException {
        return new String(Files.readAllBytes(Paths.get(Objects.requireNonNull(getClass().getResource(path)).getPath())));
    }

}
