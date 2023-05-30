package ie03.phase2.task4.generator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CorrectOutputWriter extends ie03.phase1.task1.generator.CorrectOutputWriter {
    String ITEMS;
    List<Integer> Queries;

    public CorrectOutputWriter(List<Integer> queries, String items, String fileName, String filepath) {
        super(items, fileName, filepath);
        this.ITEMS = items;
        this.Queries = queries;
    }

    public boolean writeOutput() {
        CorrectOutputGenerator cog = new CorrectOutputGenerator(ITEMS, Queries);
        String out = cog.writeCorrectOutput();
        try (FileWriter writer = new FileWriter(FILEPATH + fileName)) {
            writer.write(out);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
