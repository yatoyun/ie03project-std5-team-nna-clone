package ie03.phase2.task5;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // input 01
        Input input = new Input(System.in);
        int w = input.nextInt();
        int h = input.nextInt();
        Grid grid = new Grid(w, h);

        int n = input.nextInt();
        grid.shelvesInitializer(input, n);
        grid.getDistGraph();

        // input 02 and solve
        int q = input.nextInt();

        for (int i = 0; i < q; i++) {
            int m = input.nextInt();
            String[] inputRoute = new String[m];

            for (int j = 0; j < m; j++)
                inputRoute[j] = input.next();
            SolveRoutes sr = new SolveRoutes(inputRoute, grid);
            // reset dist_graph
            sr.resetGlaph(m);

            // solve
            GraphBuilder graphBl = sr.GetGraphBl();
            TSP tsp = new TSP(graphBl);
            tsp.solveTSP();
            System.out.println(tsp.getMinRouteValue());
        }
    }

}