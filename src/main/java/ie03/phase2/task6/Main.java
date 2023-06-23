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

        SolveDijkstra solver = new SolveDijkstra(grid);
        HashMap<Point, String> shelvesByPoint = new HashMap<>();
        for (Entry<String, Point> entry : grid.shelves.entrySet()) {
            if (!entry.getKey().equals("EN"))
                shelvesByPoint.put(entry.getValue(), entry.getKey());
        }

        HashSet<ArrayList<String>> stopoversList;
        // input 02
        
        int q = input.nextInt();
        for (int i = 0; i < q; i++) {
            int m = input.nextInt();

            String[] inputRoute = new String[m];
            for (int j = 0; j < m; j++)
                inputRoute[j] = input.next();

            SolveRoutes sr = new SolveRoutes(inputRoute, grid);
            // reset dist_graph
            sr.resetGlaph(m);
            GetVisitableWaypoints gvw = new GetVisitableWaypoints(shelvesByPoint, solver, grid);

            TSP tsp = sr.solve();
            int minDist = tsp.getMinRouteValue();
            stopoversList = tsp.getMinRoutePath();
            // debug
//            System.out.println("bitDP:");
//            System.out.println(stopoversList);
            ArrayList<String> finalVisitedList = new ArrayList<>();
            for (ArrayList<String> stopovers : stopoversList) {

                ArrayList<String> vistedList;
                ArrayList<String> waypoints;
                HashSet<String> visited = new HashSet<>();
                visited.add("EN");
                visited.add("EX");
                Point cpos = new Point(1, 0);
                Point npos;

                // solve
                for (String stopover : stopovers) {
                    npos = grid.shelves.get(stopover);
                    waypoints = gvw.solveWaypoints(cpos, npos, stopovers);
                    visited.addAll(waypoints);
                    cpos = npos;
                }
                npos = new Point(w-2, 0);
                waypoints = gvw.solveWaypoints(cpos, npos, stopovers);
                visited.addAll(waypoints);
                visited.remove("EN");
                visited.remove("EX");
                vistedList = new ArrayList<>(visited);

                // find largest visited list
                if (finalVisitedList.size() < vistedList.size()) {
                    finalVisitedList = vistedList;
                }

            }
            Collections.sort(finalVisitedList);

            // output
            System.out.print(minDist);
            for (String s : finalVisitedList) {
                System.out.print(" " + s);
            }
            System.out.print("\n");
        }
    }
}

