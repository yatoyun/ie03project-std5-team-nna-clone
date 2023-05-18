package ie03.phase2.task5;

import java.awt.*;
import java.util.HashMap;
import java.util.Scanner;

public class Grid {

    public static int w, h;
    public static int grid[][];
    public static HashMap<String, Point> shelves = new HashMap<String, Point>();

    public static void set_grid() {
        Scanner sc = new Scanner(System.in);
        // first input to set w, h, and shelve positions

        w = sc.nextInt();
        h = sc.nextInt();
        int n = sc.nextInt();
        grid = new int[w][h];

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if ((i == 0 && j == h - 1) || (i == w - 1 && j == h - 1)) {
                    // upper left and lower right corner
                    grid[i][j] = Integer.MAX_VALUE;
                } else if ((i != 1 && i != w - 2) && j == 0) {
                    // lower wall
                    grid[i][j] = Integer.MAX_VALUE;
                } else {
                    grid[i][j] = 1;
                }
            }
        }

        for (int i = 0; i < n; i++) {

            int x = sc.nextInt();
            int y = sc.nextInt();
            String s = sc.next();
            String d = sc.next();

            grid[x][y] = Integer.MAX_VALUE;

            Point pos = new Point();

            if (d.compareTo("N") == 0) {
                pos.x = x;
                pos.y = y + 1;
            } else if (d.compareTo("S") == 0) {
                pos.x = x;
                pos.y = y - 1;
            } else if (d.compareTo("E") == 0) {
                pos.x = x + 1;
                pos.y = y;
            } else if (d.compareTo("W") == 0) {
                pos.x = x - 1;
                pos.y = y;
            }

            shelves.put(s, pos);
        }
        // set entry point
        Point pos = new Point();
        pos.x = 1;
        pos.y = 0;
        shelves.put("EN", pos);

        // set exit point
        pos = new Point();
        pos.x = w-2;
        pos.y = 0;
        shelves.put("EX", pos);
    }
}
