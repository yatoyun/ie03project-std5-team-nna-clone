package ie03.phase2.task5;

import ie03.phase1.task3.SolveDijkstra;

import java.awt.*;
import java.util.HashMap;

public class GraphBuilder {
    public HashMap<String, Integer> distGraph;
    public SolveDijkstra getRoute;
    public HashMap<String, Point> shelves;
    public String[] stopovers;
    public String[] inputRoute;

    public GraphBuilder(String[] inputRoute, Grid grid){
        this.inputRoute = inputRoute;
        this.shelves = grid.shelves;
        this.getRoute = new SolveDijkstra(grid);
    }

    public void getDistGlaph(int m) {
        // reset
        stopovers = new String[m + 2];
        distGraph = new HashMap<>();

        // set stopovers including entry and exit
        stopovers[0] = "EN";
        stopovers[m + 1] = "EX";
        for (int i = 1; i < m + 1; i++) {
            stopovers[i] = inputRoute[i-1];
        }
        // get distances between each stopover and stopover
        getEachDists(stopovers, m + 2);
    }

    public void getEachDists(String[] stopovers, int num_stopovers) {
        // num_stopovers_C_2
        for (int i = 0; i < num_stopovers; i++) {
            for (int j = i + 1; j < num_stopovers; j++) {
                distGraph.put(CombinationName.get(stopovers[i], stopovers[j]), // name of comb
                        getRoute.solveDist(      // value of dists
                                shelves.get(stopovers[i]), shelves.get(stopovers[j])));
            }
        }
    }

}
