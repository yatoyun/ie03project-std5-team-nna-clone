package ie03.phase2.task6;

import ie03.phase2.task5.Grid;
import ie03.phase1.task3.SolveDijkstra;
import ie03.phase2.task5.Grid;
import ie03.phase2.task5.Input;
import ie03.phase2.task5.SolveRoutes;
import ie03.phase2.task5.TSP;

import java.awt.*;
import java.util.*;


public class ShortestPathRouteSolver {
    Grid grid;
    SolveDijkstra solver;
    HashMap<Point, String> shelvesByPoint;

    public ShortestPathRouteSolver(Grid grid) {
        this.grid = grid;
        this.solver = new SolveDijkstra(grid);
        this.shelvesByPoint = new HashMap<>();
        for (Map.Entry<String, Point> entry : grid.shelves.entrySet()) {
            if (!entry.getKey().equals("EN"))
                shelvesByPoint.put(entry.getValue(), entry.getKey());
        }
    }

    public Map.Entry<Integer, ArrayList<String>> solve(String[] inputRoute) {
        int m = inputRoute.length;
        SolveRoutes sr = new SolveRoutes(inputRoute, grid);
        // reset dist_graph
        sr.resetGlaph(m);
        GetVisitableWaypoints gvw = new GetVisitableWaypoints(shelvesByPoint, solver, grid);

        TSP tsp = sr.solve();
        int minDist = tsp.getMinRouteValue();
        HashSet<ArrayList<String>> stopoversList = tsp.getMinRoutePath();
        System.err.println(stopoversList);

        ArrayList<String> finalVisitedList = new ArrayList<>();

        for (ArrayList<String> stopovers : stopoversList) {
            ArrayList<String> vistedList;
            ArrayList<String> waypoints;
            HashSet<String> visited = new HashSet<>();
            visited.add("EN");
            visited.add("EX");
            Point cpos = new Point(1, 0);
            Point npos;

            for (String stopover : stopovers) {
                npos = grid.shelves.get(stopover);
                waypoints = gvw.solveWaypoints(cpos, npos, stopovers);
                visited.addAll(waypoints);
                cpos = npos;
            }
            npos = new Point(grid.w - 2, 0);
            waypoints = gvw.solveWaypoints(cpos, npos, stopovers);
            visited.addAll(waypoints);
            visited.remove("EN");
            visited.remove("EX");
            vistedList = new ArrayList<>(visited);
            System.err.println("visitedList: " + vistedList);
            if (finalVisitedList.size() < vistedList.size()) {
                finalVisitedList = vistedList;
            }
        }
        Collections.sort(finalVisitedList);
        Map.Entry<Integer, ArrayList<String>> result = new AbstractMap.SimpleEntry<>(minDist, finalVisitedList);
        return result;
    }
}
