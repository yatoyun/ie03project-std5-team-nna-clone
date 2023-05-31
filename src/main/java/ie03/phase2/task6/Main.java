package ie03.phase2.task6;

import ie03.phase1.task3.SolveDijkstra;
import ie03.phase2.task5.Grid;
import ie03.phase2.task5.Input;

import java.awt.*;
import java.util.*;
import java.util.Map.*;

public class Main {

    static HashMap<Point, String> shelvesByPoint = new HashMap<>();
    static SolveDijkstra solver;
    static Grid grid;
    static String[] stopovers;


    static ArrayList<String> solveWaypoints(Point cpos, Point npos) {
        // trace the shortest path from npos to cpos and return list of steped shelves

        HashSet<String> stopoversSet = new HashSet<>(Arrays.asList(stopovers));

        // solve distance using dikstra
        int[][] totaldist = solver.solveTotalDist(cpos, npos);
        ArrayList<String> route = new ArrayList<>();

        // find the shortest route

        int[] dx = {1, 0, -1, 0}; // E, N, W, S
        int[] dy = {0, 1, 0, -1}; // E, N, W, S

        Queue<Point> queue = new LinkedList<>();
        queue.add(npos);

        while (!queue.isEmpty()) {
            Point p = queue.poll();
            for (int i = 0; i < 4; i++) {
                Point np = new Point(dx[i] + p.x, dy[i] + p.y);

                if (!grid.isValid(np)) {
                    continue;
                }

                if (totaldist[np.x][np.y] == totaldist[p.x][p.y] - 1) {
                    queue.add(np);
                    String npShelfName = shelvesByPoint.get(np);

                    if (!shelvesByPoint.containsKey(np)) {
                        continue;
                    }

                    // if there exsites a shelf in front of on np, add it to route
                    // however we bust avoid adding shelf if the last element of route is the same totaldist value as np's totaldist value

                    if (route.size() == 0) {
                        route.add(npShelfName);
                        continue;
                    }

                    // if the last element of route is the same totaldist value as np's totaldist value, we should not add shelf
                    // we need to compare which is smaller the shelves' name as dictionary order

                    Point lastShelfPos = grid.shelves.get(route.get(route.size() - 1));
                    String lastShelfName = shelvesByPoint.get(lastShelfPos);

                    if (totaldist[lastShelfPos.x][lastShelfPos.y] == totaldist[np.x][np.y] && !stopoversSet.contains(npShelfName)) {
                        if (lastShelfName.compareTo(npShelfName) > 0) {
                            route.remove(route.size() - 1);
                            route.add(npShelfName);
                        }
                    } else {
                        route.add(npShelfName);
                    }
                }
            }
        }

        return route;
    }

    public static void main(String[] args) {
        // input 01
        int h = Input.nextInt();
        int w = Input.nextInt();
        grid = new Grid(h, w);
        int n = Input.nextInt();
        grid.shelvesInitializer(n);

        solver = new SolveDijkstra(grid);
        for (Entry<String, Point> entry : grid.shelves.entrySet()) {
            if (!entry.getKey().equals("EN"))
                shelvesByPoint.put(entry.getValue(), entry.getKey());
        }

        // input 02
        RouteSimulationWithPathRoute sr = new RouteSimulationWithPathRoute(grid);
        int q = Input.nextInt();
        for (int i = 0; i < q; i++) {
            int m = Input.nextInt();

            // reset dist_graph
            sr.resetDistGraph();
            sr.resetStopovers(m);
            stopovers = sr.stopovers;

            int minDist = sr.solveTSP();
            ArrayList<String> stopovers = sr.solveAndGetRouteTSP();
            ArrayList<String> waypoints;
            HashSet<String> visited = new HashSet<>();
            Point cpos = new Point(1, 0);
            Point npos;
            // solve
            for (String stopover : stopovers) {
                npos = grid.shelves.get(stopover);
                waypoints = solveWaypoints(cpos, npos);
                visited.addAll(waypoints);
                cpos = npos;
            }
            npos = new Point(w-2, 0);
            waypoints = solveWaypoints(cpos, npos);
            visited.addAll(waypoints);
            ArrayList<String> visitedList = new ArrayList<>(visited);
            Collections.sort(visitedList);

            System.out.print(minDist);
            for (String s : visitedList) {
                System.out.print(" " + s);
            }
            System.out.println();
        }
    }
}

