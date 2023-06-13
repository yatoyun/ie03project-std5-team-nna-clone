package ie03.phase1.task1.generator;

public class StringFormatter {
    StringBuilder sb = new StringBuilder();

    String concatenate(String a, String b) {
        sb.append(a).append("\n").append(b);
        return sb.toString();
    }
}
