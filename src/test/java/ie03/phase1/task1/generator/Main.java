package ie03.phase1.task1.generator;

public class Main {
    public static void main(String[] args) {
        final int numTestCases = 10;
        final String INPUT_FILE_PREFIX = "example";
        final String INPUT_FILE_EXTENSION = "_in.txt";
        final String OUTPUT_FILE_PREFIX = "example";
        final String OUTPUT_FILE_EXTENSION = "_out.txt";
        final String CONFIRMATION_FILE_PREFIX = "example";
        final String CONFIRMATION_FILE_EXTENSION = "_confirm.txt";
        final String FILE_PATH = "src/test/resources/phase1/task1/";

        TestCaseGenerator generator = new TestCaseGenerator();

        //start from i = 3 because test cases 1,2 has already been created.
        for (int i = 3; i <= numTestCases; i++) {
            String input_fileName = INPUT_FILE_PREFIX + i + INPUT_FILE_EXTENSION;
            String output_fileName = OUTPUT_FILE_PREFIX + i + OUTPUT_FILE_EXTENSION;
            String confirmation_fileName = CONFIRMATION_FILE_PREFIX + i + CONFIRMATION_FILE_EXTENSION;
            String testCase = generator.generateTestCase(i);

            //generate testcases for input
            TestCaseWriter tw = new TestCaseWriter(testCase, input_fileName, FILE_PATH);
            if (tw.writeTestcase()) {
                System.out.println("Generated test case: " + input_fileName);
            } else {
                System.err.println("Failed to generate test case: " + input_fileName);
            }

            //generate txt files to confirm whether output is correct or not
            CorrectOutputWriter ow = new CorrectOutputWriter(generator.getOutput(), output_fileName, FILE_PATH);
            if (ow.writeOutput()) {
                System.out.println("Generated output txt: " + output_fileName);
            } else {
                System.err.println("Failed to output txt: " + output_fileName);
            }

            //generate txt files to confirm whether output is correct or not (full display)
            ConfirmationWriter cw = new ConfirmationWriter(generator.getPurchasesData(), confirmation_fileName, FILE_PATH);
            if (cw.writeConfirmationText()) {
                System.out.println("Generated confirmation txt: " + confirmation_fileName);
            } else {
                System.err.println("Failed to confirmation txt: " + confirmation_fileName);
            }
        }
    }
}
