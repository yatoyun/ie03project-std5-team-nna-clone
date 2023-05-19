package ie03.phase1.task3;

import java.awt.Point;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        final int w = sc.nextInt();
        final int h = sc.nextInt();

        Grid grid = new Grid(w, h);

        int n = sc.nextInt();
        // input 01
        for (int i = 0; i < n; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            String s = sc.next();
            String d = sc.next();

            Point pos = new Point(x, y);
            grid.setShelf(pos, s, d);
        }

        final int q = sc.nextInt();
        SolveDijkstra sdk = new SolveDijkstra(grid);
        // input 02 and solve
        for (int i = 0; i < q; i++) {
            int m = sc.nextInt();
            int dist = 0;

            Point cpos = new Point(1, 0);
            Point npos;

            // solve

            for (int j = 0; j < m; j++) {
                String p = sc.next();
                npos = grid.shelves.get(p);
                dist += sdk.solveDist(cpos, npos);
                cpos = npos;
            }

            npos = new Point(w-2, 0);
            dist += sdk.solveDist(cpos, npos);

            System.out.println(dist);
        }

    }
}