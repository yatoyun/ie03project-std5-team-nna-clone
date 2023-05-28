package ie03.phase1.task3;

import java.util.*;
import java.awt.*;

public class Grid {
    public int w;
    public int h;
    public int[][] weight;
    public HashMap<String, Point> shelves;

    public Grid(int w, int h) {
        this.w = w;
        this.h = h;
        this.weight = new int[w][h];
        this.shelves = new HashMap<>();
        clearGrid();
    }

    public Point getShelfPoint(String s) {
        return shelves.get(s);
    }

    public Set<Point> getShelfPoints() {
        return new HashSet<>(shelves.values());
    }

    public void clearGrid() {
        shelves.clear();
        // initialize grid
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if ((i == 0 && j == h-1) || (i == w-1 && j == h-1)) {
                    // upper left and lower right corner
                    weight[i][j] = Integer.MAX_VALUE;
                } else if ((i != 1 && i != w-2) && j == 0) {
                    // lower wall
                    weight[i][j] = Integer.MAX_VALUE;
                } else {
                    weight[i][j] = 1;
                }
            }
        }
    }

    public void setShelf(Point p, String s, String d) {
        weight[p.x][p.y] = Integer.MAX_VALUE;
        Point pos = new Point();

        switch (d) {
            case "N":
                pos.x = p.x;
                pos.y = p.y + 1;
                break;
            case "S":
                pos.x = p.x;
                pos.y = p.y - 1;
                break;
            case "E":
                pos.x = p.x + 1;
                pos.y = p.y;
                break;
            case "W":
                pos.x = p.x - 1;
                pos.y = p.y;
        }
        shelves.put(s, pos);
    }

    public boolean isValid(Point p) {
        if (p.x < 0 || p.x >= w || p.y < 0 || p.y >= h) {
            // check if out of bounds
            return false;
        } else return weight[p.x][p.y] != Integer.MAX_VALUE;
        // simplified else statement
    }

}
