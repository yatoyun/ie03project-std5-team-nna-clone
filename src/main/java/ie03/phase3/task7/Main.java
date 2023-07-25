package ie03.phase3.task7;


import ie03.phase1.task3.SolveDijkstra;
import ie03.phase2.task5.Grid;

import java.awt.*;
import java.io.*;
import java.lang.reflect.Array;
import java.security.AllPermission;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // input 01
        int w = sc.nextInt();
        int h = sc.nextInt();
        Grid grid = new Grid(w, h);
        int s = sc.nextInt();
        for (int i = 0; i < s; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            String d = sc.next();
            String name = "S" + i;
            grid.setShelf(new Point(x, y), name, d);
        }

        // input 02
        int p = sc.nextInt();
        ProductsManager productsManager = new ProductsManager();
        for (int i = 0; i < p; i++) {
            String name = sc.next();
            productsManager.addProduct(name);
        }

        // input 03
        int n = sc.nextInt();
        for (int i = 0; i < n; i++) {
            int m = sc.nextInt();
            ArrayList<String> customer = new ArrayList<>();
            for (int j = 0; j < m; j++) {
                String product = sc.next();
                customer.add(product);
            }
            productsManager.addCustomer(customer);
        }

        ShopLayoutSolver splSolver = new ShopLayoutSolver(productsManager, grid);
        ArrayList<Map.Entry<String,Point>> sortedShelves = splSolver.getShoplayout();
        for (Map.Entry<String, Point> product : sortedShelves) {
            System.out.println(product.getKey() + " (" + product.getValue().x + ", " + product.getValue().y + ")");
        }
    }
}
