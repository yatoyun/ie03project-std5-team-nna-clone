package ie03.phase1.task3.generator;

import ie03.phase1.task3.Grid;

import java.awt.*;
import java.util.*;

public class TestCaseGenerator {
    Grid grid;
    TestCaseSolver solver;
    ArrayList<Object[]> shelves;
    ArrayList<Object[]> routes;

    public TestCaseGenerator(int w, int h) {
        grid = new Grid(w, h);
        shelves = new ArrayList<>();
        solver = new TestCaseSolver(grid);
        routes = new ArrayList<>();
    }

    private void reset() {
        shelves.clear();
        grid.clearGrid();
    }

    private boolean isAllShelvesReachable() {
        Set<Point> dposSet = grid.getShelfPoints();
        Set<Point> visited = new HashSet<>();
        // use queue
        Queue<ArrayList<Point>> q = new LinkedList<>();
        // add start point as initialisation
        q.add(new ArrayList<>(Collections.singletonList(new Point(1, 0))));
        while (!q.isEmpty()) {
            ArrayList<Point> path = q.poll();
            Point lastPoint = path.get(path.size()-1);

            if (lastPoint.equals(new Point(grid.w-2, 0)) || path.size() > grid.w*grid.h) {
                continue;
            }

            dposSet.remove(lastPoint);

            int[] dx = {1, 0, -1, 0}; // E, N, W, S
            int[] dy = {0, 1, 0, -1}; // E, N, W, S
            for (int i = 0; i < 4; i++) {
                Point npos = new Point(lastPoint.x + dx[i], lastPoint.y + dy[i]);
                if ((!grid.isValid(npos)) || path.contains(npos) || visited.contains(npos)) {
                    continue;
                }
                ArrayList<Point> newPath = new ArrayList<>(path);
                newPath.add(npos);
                visited.add(npos);
                q.add(new ArrayList<>(newPath));
            }
        }
        return dposSet.isEmpty();
    }

    public Grid getGrid() {
        return grid;
    }

    public ArrayList<Object[]> getRoutes() {
        return routes;
    }

    public void putShelves(int n) {
        shelves.clear();
        Random rand = new Random();
        // points where shelves cannot be placed
        Set<Point> candidatePoints = new HashSet<>();
        for (int cnt = 0; cnt < n;) {
            if (cnt == 0) {
                candidatePoints.clear();
                // initialize set of possible points
                for (int i = 0; i < grid.w; i++) {
                    for (int j = 0; j < grid.h; j++) {
                        Point p = new Point(i, j);
                        if (grid.isValid(p)) {
                            candidatePoints.add(p);
                        }
                    }
                }
                // remove points where shelves cannot be placed
                // on and in front of the entrance and exit
                candidatePoints.remove(new Point(1, 0));
                candidatePoints.remove(new Point(1, 1));
                candidatePoints.remove(new Point(grid.w-2, 0));
                candidatePoints.remove(new Point(grid.w-2, 1));
            }

            if (candidatePoints.isEmpty()) {
                // no more possible points
                // reset and try again
                System.out.println("reset");
                reset();
                cnt = 0;
                continue;
            }

            Point p = (Point) candidatePoints.toArray()[rand.nextInt(candidatePoints.size())];

            int dnum = rand.nextInt(4);
            String d = "";

            Point dpos = new Point(p.x, p.y);
            switch (dnum) {
                case 0 -> {
                    d = "N";
                    dpos.y = p.y + 1;
                }
                case 1 -> {
                    d = "S";
                    dpos.y = p.y - 1;
                }
                case 2 -> {
                    d = "E";
                    dpos.x = p.x + 1;
                }
                case 3 -> {
                    d = "W";
                    dpos.x = p.x - 1;
                }
            }

            String s = "S" + cnt;

            if (!candidatePoints.contains(dpos)) {
                // if the direction is not possible, try again
                // no need to reset
                continue;
            }

            candidatePoints.remove(p);
            candidatePoints.remove(dpos);
            shelves.add(new Object[]{p.x, p.y, s, d});
            grid.setShelf(p, s, d);
            cnt++;

            if (cnt == n && !isAllShelvesReachable()) {
                // if unreachable, reset and try again
                reset();
                cnt = 0;
            }

        }
    }

    public void generateRouteManually(int m) {
        Random rand = new Random();
        Object[] route = new Object[m+1];
        route[0] = m;
        for (int cnt = 0; cnt < m; cnt++) {
            int idx = rand.nextInt(shelves.size());
            route[cnt+1] = shelves.get(idx)[2];
        }
        routes.add(route);
    }

    public void generateRouteRandomly(int q, int maxRouteLength) {
        Random rand = new Random();
        for (int cnt = 0; cnt < q; cnt++) {
            int m = 1 + rand.nextInt(maxRouteLength);
            generateRouteManually(m);
        }
    }

    public String getInput() {
        // grid
        StringBuilder sb = new StringBuilder();
        final int w = grid.w;
        final int h = grid.h;
        final int n = shelves.size();

        String line = w + " " + h + " " + n + "\n";
        sb.append(line);

        for (int i = 0; i < n; i++) {
            Object[] shelf = shelves.get(i);
            line = shelf[0] + " " + shelf[1] + " " + shelf[2] + " " + shelf[3] + "\n";
            sb.append(line);
        }

        // routes
        final int q = routes.size();
        line = q + "\n";
        sb.append(line);

        for (int i = 0; i < q; i++) {
            Object[] order = routes.get(i);
            line = order[0].toString();
            for (int j = 1; j < order.length; j++) {
                line = line + " " + order[j];
            }
            line += "\n";
            sb.append(line);
        }

        return sb.toString();
    }


}
