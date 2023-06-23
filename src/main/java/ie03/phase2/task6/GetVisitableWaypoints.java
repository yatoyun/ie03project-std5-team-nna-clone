package ie03.phase2.task6;

import ie03.phase1.task3.SolveDijkstra;
import ie03.phase2.task5.Grid;

import java.awt.*;
import java.util.*;

public class GetVisitableWaypoints {
    private HashMap<Point, String> shelvesByPoint;
    private SolveDijkstra solver;
    private Grid grid;

    public GetVisitableWaypoints(HashMap<Point, String> shelvesByPoint, SolveDijkstra solver, Grid grid){
        this.shelvesByPoint = shelvesByPoint;
        this.solver = solver;
        this.grid = grid;
    }

    public ArrayList<String> solveWaypoints(Point cpos, Point npos, ArrayList<String> stopovers) {
        // trace the shortest path from npos to cpos and return list of steped shelves

        HashSet<String> stopoversSet = new HashSet<>(stopovers);

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
}
