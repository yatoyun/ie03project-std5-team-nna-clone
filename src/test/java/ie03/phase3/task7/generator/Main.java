package ie03.phase3.task7.generator;

public class Main {
    public static void main(String[] args) {
        Generator generator = new Generator(1);
        System.out.println(generator.putShelves());
        System.out.println(generator.putProducts());
        System.out.println(generator.createPurchaseData());
    }
}
