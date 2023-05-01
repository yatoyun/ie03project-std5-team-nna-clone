package src.main.java.ie03.phase2.task4;

import java.util.Objects;

public class PurchasePair {
    private final String A;
    private final String B;

    PurchasePair(String first, String second) {
        if (first.compareTo(second) < 0) {
            this.A = first;
            this.B = second;
        } else {
            this.A = second;
            this.B = first;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(A, B) + Objects.hash(B, A);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PurchasePair other = (PurchasePair) obj;
        return (Objects.equals(A, other.A) && Objects.equals(B, other.B)) || (Objects.equals(A, other.B) && Objects.equals(B, other.A));
    }

    @Override
    public String toString() {
        return "(" + A + ", " + B + ")";
    }

    public String getA() {
        return A;
    }

    public String getB() {
        return B;
    }
}
