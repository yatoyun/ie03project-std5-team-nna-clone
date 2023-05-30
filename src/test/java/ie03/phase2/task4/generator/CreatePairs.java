package ie03.phase2.task4.generator;

public class CreatePairs {
    String create(String item1, String item2) {
        String pair;
        if (item1.compareTo(item2) <= 0) {
            pair = item1 + " " + item2;
        } else {
            pair = item2 + " " + item1;
        }
        return pair;
    }
}