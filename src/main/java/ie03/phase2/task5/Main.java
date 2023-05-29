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
        int q = Input.nextInt();

        SolveRoutes sr = new SolveRoutes(grid);

        for (int i = 0; i < q; i++) {
            int m = Input.nextInt();

            // reset dist_graph
            sr.resetDistGraph();
            sr.resetStopovers(m);

            // solve
            System.out.println(sr.solveTSP());
        }
    }
}