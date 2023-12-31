package ie03.phase1.task1.generator;

public class InterFaceTask1Task4 {
    private String input;

    private String query;

    public void generateInput(int caseNum) {
        Generator generator = new Generator();
        generator.generateInputAndOutput(caseNum * caseNum * caseNum);

        input = generator.getInput();
        query = generator.getQuery();
    }

    public String getQuery() {
        return this.query;
    }

    public String getInput() {
        return this.input;
    }
}
