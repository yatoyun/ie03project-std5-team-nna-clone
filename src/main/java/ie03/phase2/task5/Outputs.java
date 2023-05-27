package ie03.phase2.task5;

public class Outputs {
    public static void inputSecond(Grid grid) {
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
