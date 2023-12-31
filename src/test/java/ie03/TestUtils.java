package ie03;

import java.io.*;
import java.lang.reflect.Method;

public class TestUtils {
    public Object cls;

    public TestUtils(Object cls) {
        this.cls = cls;
    }

    public String execute(String in) throws Exception {
        final InputStream stdin = System.in;
        final PrintStream stdout = System.out;

        final InputStream input = new StringBufferInputStream(in);
        System.setIn(input);

        // set output stream for testing
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        final String[] args = null;
        Method mainMethod = cls.getClass().getMethod("main", String[].class);
        try {
            mainMethod.invoke(null, (Object) args);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // reset input and output streams
            System.setIn(stdin);
            System.setOut(stdout);
        }
        return outContent.toString();
    }


}