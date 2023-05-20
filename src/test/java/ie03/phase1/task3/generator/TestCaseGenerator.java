package ie03.phase1.task3.generator;

import ie03.phase1.task3.Grid;

import java.awt.*;
import java.util.*;

public class TestCaseGenerator {
    Grid grid;
    ArrayList<Object[]> shelves;
    ArrayList<Object[]> orders;

    public TestCaseGenerator(int w, int h) {
        grid = new Grid(w, h);
    }

    public void putShelf(int n) {
        shelves = new ArrayList<>();
        Random rand = new Random();
        for (int cnt = 0; cnt < n;) {
            int x = rand.nextInt(grid.w-1);
            int y = rand.nextInt(grid.h-1);
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
            if (grid.weight[x][y] != Integer.MAX_VALUE && grid.weight[pos.x][pos.y] != Integer.MAX_VALUE) {
                grid.weight[pos.x][pos.y] = Integer.MAX_VALUE;
                shelves.add(new Object[]{x, y, s, d});
                // if shelf is not put yet
                grid.setShelf(new Point(x, y), s, d);
                cnt++;
            }
        }
    }

    public void generateOrder(int q) {
        orders = new ArrayList<>();
        Random rand = new Random();
        for (int cnt = 0; cnt < q; cnt++) {
            int m = 1 + rand.nextInt(shelves.size()-1);
            Object[] order = new Object[m+1];
            order[0] = m;
            for (int i = 1; i < m+1; i++) {
                int idx = rand.nextInt(shelves.size()-1);
                order[i] = shelves.get(idx)[2];
            }
            orders.add(order);
        }
    }

    public void printGrid() {
        final int w = grid.w;
        final int h = grid.h;
        final int n = shelves.size();
        System.out.println(w + " " + h + " " + n);
        for (int i = 0; i < n; i++) {
            Object[] shelf = shelves.get(i);
            System.out.println(shelf[0] + " " + shelf[1] + " " + shelf[2] + " " + shelf[3]);
        }
    }

    public void printOrders() {
        final int n = orders.size();
        System.out.println(n);
        for (int i = 0; i < n; i++) {
            Object[] order = orders.get(i);
            System.out.print(order[0]);
            for (int j = 1; j < order.length; j++) {
                System.out.print(" " + order[j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        TestCaseGenerator tcg = new TestCaseGenerator(10, 10);
        tcg.putShelf(8);
        tcg.generateOrder(5);
        tcg.printGrid();
        tcg.printOrders();
    }
}
