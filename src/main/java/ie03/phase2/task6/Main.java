package ie03.phase2.task6;

import ie03.phase1.task3.SolveDijkstra;
import ie03.phase2.task5.Grid;
import ie03.phase2.task5.Input;
import ie03.phase2.task5.SolveRoutes;
import ie03.phase2.task5.TSP;

import java.awt.*;
import java.util.*;
import java.util.Map.*;

public class Main {
    public static void main(String[] args) {
        // input 01
        Input input = new Input(System.in);
        int h = input.nextInt();
        int w = input.nextInt();
        Grid grid = new Grid(h, w);
        int n = input.nextInt();
        grid.shelvesInitializer(input, n);
        grid.getDistGraph();

        ShortestPathRouteSolver sprSolver = new ShortestPathRouteSolver(grid);

        // input 02

        int q = input.nextInt();
        for (int i = 0; i < q; i++) {
            System.err.println("==========");
            int m = input.nextInt();

            String[] inputRoute = new String[m];
            for (int j = 0; j < m; j++)
                inputRoute[j] = input.next();

            Map.Entry<Integer, ArrayList<String>> result = sprSolver.solve(inputRoute);

            // output
            System.out.print(result.getKey());
            for (String s : result.getValue()) {
                System.out.print(" " + s);
            }
            System.out.print("\n");
        }
    }
}

