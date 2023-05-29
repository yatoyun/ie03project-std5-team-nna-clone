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
    

    static ArrayList<String> solveWaypoints(Point cpos, Point npos) {
        // trace the shortest path from npos to cpos and return list of steped shelves

        // solve distance using dikstra
        int[][] totaldist = solver.solveTotalDist(cpos, npos);
        ArrayList<String> route = new ArrayList<>();

        // find the shortest route

        int[] dx = {1, 0, -1, 0}; // E, N, W, S
        int[] dy = {0, 1, 0, -1}; // E, N, W, S

        Point p = npos;

        while (!p.equals(cpos)) {
            for (int i = 0; i < 4; i++) {
                Point np = new Point(dx[i] + p.x, dy[i] + p.y);
                
                if (!grid.isValid(np)) {
                    continue;
                }
                
                if (totaldist[np.x][np.y] == totaldist[p.x][p.y] - 1) {
                    p = np;
                    if (shelvesByPoint.containsKey(p)) {
                        route.add(shelvesByPoint.get(p));
                        break;
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

