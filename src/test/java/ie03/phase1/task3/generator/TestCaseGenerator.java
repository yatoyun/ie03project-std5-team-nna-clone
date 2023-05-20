package ie03.phase1.task3.generator;

import ie03.phase1.task3.Grid;

import java.awt.*;
import java.util.*;

public class TestCaseGenerator {
    int[][] weight;
    Grid grid;
    TestCaseSolver solver;
    ArrayList<Object[]> shelves;
    ArrayList<Object[]> routes;

    public TestCaseGenerator(int w, int h) {
        grid = new Grid(w, h);
        shelves = new ArrayList<>();

        //copy weight
        weight = new int[w][h];

        solver = new TestCaseSolver(grid);
    }

    private boolean isValid(Point p) {
        return grid.isValid(p) && weight[p.x][p.y] != Integer.MAX_VALUE;
    }

    private boolean isUnreachable(Point p) {
        return solver.solve(new Point(1, 0), p) == -1 || solver.solve(p, new Point(grid.w-2, 0)) == -1;
    }

    private void clearShelves() {
        shelves.clear();
        grid.shelves.clear();
        int w = grid.w;
        int h = grid.h;
        for (int i = 0; i < w; i++) {
            System.arraycopy(grid.weight[i], 0, weight[i], 0, h);
        }
        // set additional weight (to avoid putting around enter and exit)
        weight[1][0] = Integer.MAX_VALUE;
        weight[1][1] = Integer.MAX_VALUE;
        weight[w-2][0] = Integer.MAX_VALUE;
        weight[w-2][1] = Integer.MAX_VALUE;

    }

    public Grid getGrid() {
        return grid;
    }

    public ArrayList<Object[]> getRoutes() {
        return routes;
    }

    public void putShelves(int n) {
        clearShelves();
        Random rand = new Random();
        for (int cnt = 0; cnt < n;) {
            int x = rand.nextInt(grid.w);
            int y = rand.nextInt(grid.h);
            int dnum = rand.nextInt(4);
            String d = "";

            Point pos = new Point();
            switch (dnum) {
                case 0 -> {
                    d = "N";
                    pos.x = x;
                    pos.y = y + 1;
                }
                case 1 -> {
                    d = "S";
                    pos.x = x;
                    pos.y = y - 1;
                }
                case 2 -> {
                    d = "E";
                    pos.x = x + 1;
                    pos.y = y;
                }
                case 3 -> {
                    d = "W";
                    pos.x = x - 1;
                    pos.y = y;
                }
            }

            String s = "S" + cnt;

            // if shelf is not put yet
            if (isValid(pos) && isValid(new Point(x, y))) {
                shelves.add(new Object[]{x, y, s, d});
                grid.setShelf(new Point(x, y), s, d);
                weight[x][y] = Integer.MAX_VALUE;
                weight[pos.x][pos.y] = Integer.MAX_VALUE;
                cnt++;
            }

            for (int i = (cnt==n)?0:n; i < n; i++) {
                // check if the shelf is unreachable
                Point p = new Point((int)shelves.get(i)[0], (int)shelves.get(i)[1]);
                System.out.println(p + " " + (String)shelves.get(i)[2]);
                if (isUnreachable(p)) {
                    clearShelves();
                    cnt = 0;
                    break;
                }
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
                line += " " + order[j];
            }
            line += "\n";
            sb.append(line);
        }

        return sb.toString();
    }


}
