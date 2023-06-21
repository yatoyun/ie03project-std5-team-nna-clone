package ie03.phase2.task5;

import ie03.phase1.task3.SolveDijkstra;

import java.awt.*;
import java.util.HashMap;

public class Grid extends ie03.phase1.task3.Grid {
    public HashMap<String, Integer> distGraph = new HashMap<>();
    public Grid(int w, int h) {
        super(w, h);
    }

    public void shelvesInitializer(Input input, int n) {
        for (int i = 0; i < n; i++) {

            int x = input.nextInt();
            int y = input.nextInt();
            String s = input.next();
            String d = input.next();

            Point p = new Point(x, y);
            setShelf(p, s, d);
        }

        // set entry point
        Point pos = new Point(1, 0);
        shelves.put("EN", pos);

        // set exit point
        pos = new Point(w-2, 0);
        shelves.put("EX", pos);
    }

    public void getDistGraph() {
        SolveDijkstra getRoute = new SolveDijkstra(this);

        getEachDists(shelves.keySet().toArray(new String[shelves.size()]), shelves.size(), getRoute);
    }

    private void getEachDists(String[] stopovers, int num_stopovers, SolveDijkstra getRoute) {
        // num_stopovers_C_2
        for (int i = 0; i < num_stopovers; i++) {
            for (int j = i + 1; j < num_stopovers; j++) {
                distGraph.put(CombinationName.get(stopovers[i], stopovers[j]), // name of comb
                        getRoute.solveDist(      // value of dists
                                shelves.get(stopovers[i]), shelves.get(stopovers[j])));
            }
        }
    }
}
