package ie03.phase2.task5;

import java.awt.*;
import java.util.HashMap;

public class Outputs {
    public static void inputSecond(int w, int h, int[][] grid, HashMap<String, Point> shelves) {
        int q = Input.nextInt();

        SolveRoutes sr = new SolveRoutes(w, h, grid, shelves);
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
