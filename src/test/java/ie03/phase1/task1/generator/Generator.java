package ie03.phase1.task1.generator;

public class Generator {
    private String input;
    private String output;

    public void generateInputAndOutput(int numCase) {
        PurchaseDataGenerator gp = new PurchaseDataGenerator();
        String purchaseData = gp.generatePurchases(numCase);

        Counter counter = new Counter(purchaseData);
        int numPurchases = counter.countWords();

        GenerateQuery gq = new GenerateQuery(numPurchases);
        String query = gq.QueryGenerator();

        StringFormatter sf = new StringFormatter();
        input = sf.concatenate(purchaseData, query);

        OutputGenerator og = new OutputGenerator(counter.getSortedWordCount());
        og.stringQueryToList(query);
        og.generateOutput();
        output = og.getOutput();
    }

    public String getOutput() {
        return output;
    }

    public String getInput() {
        return input;
    }
}
