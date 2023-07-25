package ie03.phase2.task6;

import ie03.phase1.task3.SolveDijkstra;
import ie03.phase2.task5.CombinationName;
import ie03.phase2.task5.Grid;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class AllVisitableWaypoints {
    private Grid grid;
    private SolveDijkstra solver;

    private HashMap<String, Point> shelves;
    private String[] shelvesStringList;

    private HashMap<String, Integer> shelvesIndexDict;

    private HashMap<String, Integer> allwaypoints = new HashMap<>();

    private HashMap<Point, String> shelvesByPoint;
    public AllVisitableWaypoints(Grid grid, HashMap<Point, String> shelvesByPoint){
        this.shelves = grid.shelves;
        this.solver = new SolveDijkstra(grid);
        this.shelvesByPoint = shelvesByPoint;
        this.grid = grid;
    }

    public String IndexToString(int index){
        return shelvesStringList[index];
    }

    public HashMap<String, Integer> GetWaypoints(ArrayList<String> inputRoute){
        // make a list of all waypoints
        shelvesStringList = new String[shelves.size()];
        shelvesIndexDict = new HashMap<>();
        // EN
        shelvesStringList[0] = "EN";
        shelvesIndexDict.put("EN", 0);
        // EX
        shelvesStringList[shelves.size() - 1] = "EX";
        shelvesIndexDict.put("EX", shelves.size() - 1);
        int i = 1;
        for (String shelf : shelves.keySet()){
            if (!shelf.equals("EN") && !shelf.equals("EX")) {
                shelvesStringList[i] = shelf;
                shelvesIndexDict.put(shelf, i++);
            }
        }
        GetVisitableWaypoints gvw = new GetVisitableWaypoints(shelvesByPoint, solver, grid);
        Point cpos;
        Point npos;
        ArrayList<String> waypoints;
        ArrayList<String> unVisitable = new ArrayList<>(inputRoute);
        unVisitable.add("EN");
        unVisitable.add("EX");
        HashSet<String> unVisitableSet = new HashSet<>(unVisitable);

        // get all combinations of waypoints
        for (i = 0; i < shelvesStringList.length; i++) {
            for (int j = i + 1; j < shelvesStringList.length; j++) {
                // EN-Ex is not a valid combination
                if (i == 0 && j == shelvesStringList.length - 1)
                    continue;
                if (!unVisitableSet.contains(shelvesStringList[i]) && !unVisitableSet.contains(shelvesStringList[j]))
                    continue;
                // get all possible routes
                waypoints = gvw.solveWaypoints(shelvesStringList[i], shelvesStringList[j], unVisitable);
                addWaypoint(waypoints, i, j);
            }
        }
        return allwaypoints;
    }

    private void addWaypoint(ArrayList<String> waypoint, int i, int j){
        String combName = CombinationName.get(shelvesStringList[i], shelvesStringList[j]);
        allwaypoints.put(combName, getCombValue(waypoint, i, j));
    }

    private Integer getCombValue(ArrayList<String> waypoint, int i, int j){
        Integer value = 0;
        for (String wp : waypoint){
            value += 1<<(shelvesIndexDict.get(wp));
        }
        if (i != 0 && (value & 1<<i) == 0)
            value += 1<<(i);
        if (j != shelvesStringList.length - 1 && (value & 1<<j) == 0)
            value += 1<<(j);

        if ((value & 1<<(shelvesStringList.length - 1)) != 0)
            value -= 1<<(shelvesStringList.length - 1);

        if ((value & 1) != 0)
            value -= 1;
        if ((value & 1<<(shelvesStringList.length - 1)) != 0)
            value -= 1<<(shelvesStringList.length - 1);

        return value;
    }
}
