package ie03;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.io.*;
import java.util.*;
import java.nio.file.*;

public interface TestInterface {

    public abstract String getFileContent(String path) throws IOException, NullPointerException;

    @TestFactory
    public abstract Collection<DynamicTest> exampleTest();

    @TestFactory
    public abstract Collection<DynamicTest> generatedTest();

}
