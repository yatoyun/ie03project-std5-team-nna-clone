package ie03.phase2.task5;

public class Main {
    public static void main(String[] args) {
        // input 01
        int w = Input.nextInt();
        int h = Input.nextInt();
        Grid grid = new Grid(w, h);

        int n = Input.nextInt();
        grid.shelvesInitializer(n);

        // input 02 and solve
        Outputs.inputSecond(grid);
    }
}