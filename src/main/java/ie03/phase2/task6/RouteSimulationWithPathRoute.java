package ie03.phase2.task6;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.Scanner;

public class RouteSimulationWithPathRoute extends ie03.phase1.RouteSimulation {
    public static HashMap<Point, String> shelvesByPoint = new HashMap<Point, String>();

    public static SimpleEntry<Integer, ArrayList<String>> solveDistAndRoute(Point cpos, Point npos) {
        // solve distance using dikstra
        int[][] totaldist = solveTotalDist(cpos, npos);
        int dist = totaldist[npos.x][npos.y];
        ArrayList<String> route = new ArrayList<String>();

        // find shortest route

        int[] dx = {1, 0, -1, 0}; // E, N, W, S
        int[] dy = {0, 1, 0, -1}; // E, N, W, S

        Point p = npos;

        while (! p.equals(cpos)) {
            for (int i = 0; i < 4; i++) {
                Point np = new Point(dx[i] + p.x, dy[i] + p.y);

                if (np.x < 0 || np.x >= w || np.y < 0 || np.y >= h) {
                    // check if out of bounds
                    continue;
                } else if (weights[np.x][np.y] == Integer.MAX_VALUE) {
                    // check if wall
                    continue;
                } else if (totaldist[np.x][np.y] == totaldist[p.x][p.y] - 1) {
                    p = np;
                    if (shelvesByPoint.containsKey(p)) {
                        route.add(shelvesByPoint.get(p));
                        break;
                    }
                }
            }
        }
        return new SimpleEntry<Integer, ArrayList<String>>(dist, route);
    }

    public static void inputSecond() {
        // input 02 and solve

        int q = sc.nextInt();
        for (int i = 0; i < q; i++) {
            int m = sc.nextInt();
            int dist = 0;
            ArrayList<String> route = new ArrayList<String>();

            Point cpos = new Point(1, 0);
            Point npos = new Point();

            // solve

            for (int j = 0; j < m; j++) {
                String p = sc.next();
                npos = shelves.get(p);
                SimpleEntry<Integer, ArrayList<String>> distAndRoute = solveDistAndRoute(cpos, npos);
                dist += distAndRoute.getKey();
                route.addAll(distAndRoute.getValue());
                cpos = npos;
            }

            npos = new Point(w-2, 0);
            SimpleEntry<Integer, ArrayList<String>> distAndRoute = solveDistAndRoute(cpos, npos);
            dist += distAndRoute.getKey();
            route.addAll(distAndRoute.getValue());

            // sort route
            Collections.sort(route);

            System.out.print(dist);
            for (String e : route) {
                System.out.print(" " + e);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        inputFirst();
        for (Entry<String, Point> entry : shelves.entrySet()) {
            shelvesByPoint.put(entry.getValue(), entry.getKey());
        }
        inputSecond();
    }

}
