package ie03.phase1.task3.generator;

import ie03.phase1.task3.Grid;

import java.util.*;
import java.awt.*;

public class TestCaseSolver {
    Grid grid;

    public TestCaseSolver(Grid grd) {
        grid = grd;
    }

    protected int solve(Point cpos, Point dpos) {
        int minDist = Integer.MAX_VALUE;
        // use queue
        Queue<ArrayList<Point>> q = new LinkedList<>();
        q.add(new ArrayList<>(Collections.singletonList(cpos)));
        while (!q.isEmpty()) {
            ArrayList<Point> path = q.poll();
            Point lastPoint = path.get(path.size()-1);
            if (lastPoint.equals(dpos)) {
                minDist = Math.min(minDist, path.size());
            } else {
                if (path.size() > grid.w*grid.h || path.size() > minDist) {
                    continue;
                }
                int[] dx = {1, 0, -1, 0}; // E, N, W, S
                int[] dy = {0, 1, 0, -1}; // E, N, W, S
                for (int i = 0; i < 4; i++) {
                    Point npos = new Point(lastPoint.x + dx[i], lastPoint.y + dy[i]);
                    if ((!grid.isValid(npos)) || path.contains(npos)) {
                        continue;
                    }
                    ArrayList<Point> newPath = new ArrayList<>(path);
                    newPath.add(npos);
                    q.add(new ArrayList<>(newPath));
                }
            }
        }
        return (minDist == Integer.MAX_VALUE) ? -1 : minDist-1;
    }

    public int move(Object[] order) {
        final int m = (int) order[0];
        int dist = 0;

        Point cpos = new Point(1, 0);
        Point npos;

        for (int j = 1; j <= m; j++) {
            npos = grid.getShelfPoint((String) order[j]);
            dist += solve(cpos, npos);
            cpos = npos;
        }

        npos = new Point(grid.w-2, 0);
        dist += solve(cpos, npos);

        return dist;
    }

    public static void main(String[] args) {
        TestCaseGenerator tcg = new TestCaseGenerator(10, 10);
        tcg.putShelves(10);
        tcg.generateRouteRandomly(10, 1);

        final Grid grid = tcg.getGrid();
        TestCaseSolver tcs = new TestCaseSolver(grid);

        for (Object[] route : tcg.getRoutes()) {
            System.out.println(tcs.move(route));
        }
    }

}
