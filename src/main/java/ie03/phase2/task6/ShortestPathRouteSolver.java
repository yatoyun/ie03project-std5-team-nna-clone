package ie03.phase2.task6;

import ie03.phase1.task3.SolveDijkstra;
import ie03.phase2.task5.GraphBuilder;
import ie03.phase2.task5.Grid;
import ie03.phase2.task5.SolveRoutes;

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

        AllVisitableWaypoints avw = new AllVisitableWaypoints(grid, shelvesByPoint);
        HashMap<String, Integer> allwaypoints = avw.GetWaypoints(new ArrayList<>(Arrays.asList(inputRoute)));

        // create tsp solver
        GraphBuilder graphBl = sr.GetGraphBl();
        TSP tsp = new TSP(graphBl, allwaypoints);

        // solve tsp
        tsp.solveTSP();

        // get result
        int minDist = tsp.getMinRouteValue();
//        ArrayList<String> stopoversList = tsp.getMinRoutePath();
        int maxlVisitablePath = tsp.getFinalVisitablePath();

        ArrayList<String> finalVisitedList = new ArrayList<>();

        // get all stopovers
        for (int i=1;i<grid.shelves.size();i++){
            if ((1<<i & maxlVisitablePath) >= 1){
                String stopover = avw.IndexToString(i);
                finalVisitedList.add(stopover);
            }
        }

        Collections.sort(finalVisitedList);
        Map.Entry<Integer, ArrayList<String>> result = new AbstractMap.SimpleEntry<>(minDist, finalVisitedList);
        return result;
    }
}
