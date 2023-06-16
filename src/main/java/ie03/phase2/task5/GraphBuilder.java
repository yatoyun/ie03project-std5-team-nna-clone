package ie03.phase2.task5;

import ie03.phase1.task3.SolveDijkstra;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;

public class GraphBuilder {
    public HashMap<String, Integer> distGraph;
    public String[] stopovers;
    public String[] inputRoute;

    public GraphBuilder(String[] inputRoute, Grid grid){
        this.inputRoute = inputRoute;
        this.distGraph = grid.distGraph;
    }

    public void getDistGlaph(int m) {
        // reset
        stopovers = new String[m + 2];

        // set stopovers including entry and exit
        stopovers[0] = "EN";
        stopovers[m + 1] = "EX";
        for (int i = 1; i < m + 1; i++) {
            stopovers[i] = inputRoute[i-1];
        }
    }

}
