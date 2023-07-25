package ie03.phase2.task6;

import ie03.phase1.task3.SolveDijkstra;
import ie03.phase2.task5.Grid;
import ie03.phase2.task5.CombinationName;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class GetVisitableWaypoints {
    private HashMap<Point, String> shelvesByPoint;
    private SolveDijkstra solver;
    private Grid grid;

    private ArrayList<String> sortedStrings;

    private ArrayList<String> maxRoute;

    public GetVisitableWaypoints(HashMap<Point, String> shelvesByPoint, SolveDijkstra solver, Grid grid){
        this.shelvesByPoint = shelvesByPoint;
        this.solver = solver;
        this.grid = grid;
        this.maxRoute = new ArrayList<>();

        // Create a list from elements of the HashMap
        List<Map.Entry<String, Integer>> list = new ArrayList<>(grid.distGraph.entrySet());

        // Sort the list
        list.sort(Map.Entry.comparingByValue());

        // Create an ArrayList<String> from the sorted list
        sortedStrings = list.stream()
                .map(Map.Entry::getKey)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<String> solveWaypoints(String cpos, String npos, ArrayList<String> stopovers) {
        maxRoute = new ArrayList<>();
        ArrayList<String> route = new ArrayList<>();
        HashSet<String> unVisitable = new HashSet<>(stopovers);

        String combName = CombinationName.get(cpos, npos);
        int minRoute = grid.distGraph.get(combName);


        HashSet<String> visitableSet = new HashSet<>(sortedStrings.subList(0, sortedStrings.indexOf(combName) + 1));

        int currentValue = 0;

        // perform DFS with backtracking
        dfsWithBacktracking(cpos, npos, route, minRoute, visitableSet, currentValue, unVisitable);

        if (maxRoute.size() == 0) {
            maxRoute.add(cpos);
            maxRoute.add(npos);
        }

        return maxRoute;
    }

    private void dfsWithBacktracking(String current, String end, ArrayList<String> route, int minRoute, HashSet<String> visitableSet, int currentValue, HashSet<String> unVisitable) {
        if ((currentValue > minRoute) || (currentValue == minRoute && !current.equals(end))) {
            return;
        }
        route.add(current);
        String combName;

        if (current.equals(end)) {
            if (route.size() > maxRoute.size()) {
                maxRoute = new ArrayList<>(route);
            }else if(route.size() == maxRoute.size()){
                // Convert both routes to a single string for comparison
                String routeStr = String.join("", route);
                String maxRouteStr = String.join("", maxRoute);

                // If route is lexicographically larger than maxRoute, update maxRoute
                if (routeStr.compareTo(maxRouteStr) < 0) {
                    maxRoute = new ArrayList<>(route);
                }
            }
        } else {
            for (String next : grid.shelves.keySet()) {
                combName = CombinationName.get(current, next);
                if ((route.contains(next) && !visitableSet.contains(combName)) || (next != end && unVisitable.contains(next))) {
                    continue;
                }
                int x = grid.distGraph.get(combName);
                dfsWithBacktracking(next, end, route, minRoute, visitableSet, currentValue + grid.distGraph.get(combName), unVisitable);
            }
        }

        route.remove(route.size() - 1);
    }
}
