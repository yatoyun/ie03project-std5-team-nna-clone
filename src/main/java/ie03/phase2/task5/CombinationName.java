package ie03.phase2.task5;

public class CombinationName {
    public static String get(String first_stopover, String second_stopover) {
        String concat_names;

        // Alphabetized and concatenate
        if (first_stopover.compareTo(second_stopover) < 0) {
            concat_names = first_stopover + "-" + second_stopover;
        } else {
            concat_names = second_stopover + "-" + first_stopover;
        }
        return concat_names;
    }
}
