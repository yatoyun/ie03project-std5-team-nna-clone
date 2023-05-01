package src.main.java.ie03.phase2.task4;

public class FormatOutput {
    String deleteColon(String str) {
        String output = str.replace(",", "").replace("(", "").replace(")", "");

        return output;
    }
}
