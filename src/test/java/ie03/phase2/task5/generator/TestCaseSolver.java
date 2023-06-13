package ie03.phase2.task5.generator;

import ie03.phase1.task3.Grid;

import java.awt.*;
import java.util.Queue;
import java.util.*;

public class TestCaseSolver {
    Grid grid;

    public TestCaseSolver(Grid grd) {
        grid = grd;
    }

    protected int solve(Point cpos, Point dpos) {

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
}
