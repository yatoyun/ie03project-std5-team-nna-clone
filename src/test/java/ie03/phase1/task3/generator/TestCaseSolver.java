package ie03.phase1.task3.generator;

import ie03.phase1.task3.Grid;

import java.util.*;
import java.awt.*;

public class TestCaseSolver {
    Grid grid;

    public TestCaseSolver(Grid grd) {
        grid = grd;
    }

    private int solve(Point cpos, Point dpos) {
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
                if (path.size() >= minDist) {
                    continue;
                }
                int[] dx = {1, 0, -1, 0}; // E, N, W, S
                int[] dy = {0, 1, 0, -1}; // E, N, W, S
                for (int i = 0; i < 4; i++) {
                    Point npos = new Point(lastPoint.x + dx[i], lastPoint.y + dy[i]);
                    if (grid.isInvalid(npos) || path.contains(npos)) {
                        continue;
                    }
                    path.add(npos);
                    q.add(new ArrayList<>(path));
                }
            }
        }
        return minDist;
    }

    public void move(Object[] order) {
        int m = (int) order[0];
        int dist = 0;

        Point cpos = new Point(1, 0);
        Point npos;

        for (int j = 1; j <= m; j++) {
            npos = grid.shelves.get((String) order[j]);
            dist += solve(cpos, npos);
            cpos = npos;
        }

        npos = new Point(grid.w-2, 0);
        dist += solve(cpos, npos);


        System.out.println(dist);
    }

    public static void main(String[] args) {
        TestCaseGenerator tcg = new TestCaseGenerator(10, 10);
        tcg.putShelf(10);
        tcg.generateOrder(10);
        tcg.printGrid();
        tcg.printOrders();
        Grid grid = tcg.getGrid();
        TestCaseSolver tcs = new TestCaseSolver(grid);
        for (Object[] order : tcg.getOrders()) {
            tcs.move(order);
        }
    }
}
