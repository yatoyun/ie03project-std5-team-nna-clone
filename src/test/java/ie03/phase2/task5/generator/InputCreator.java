package ie03.phase2.task5.generator;

import ie03.phase2.task5.Grid;

import java.util.ArrayList;

public class InputCreator implements ICreator<String>{
    private final Grid grid;
    private final ArrayList<Object[]> shelves;
    private final ArrayList<Object[]> routes;

    public InputCreator(Grid grid, ArrayList<Object[]> shelves, ArrayList<Object[]> routes){
        this.grid = grid;
        this.shelves = shelves;
        this.routes = routes;
    }

    public void initializeGenerators(int numShelves, int numRoutes, int numWayPoints){
        ShelvesGenerator shelvesGr = new ShelvesGenerator(grid, shelves, numShelves);
        RouteGenerator routeGr = new RouteGenerator(shelves, routes, numRoutes, numWayPoints);
        shelvesGr.generate();
        routeGr.generate();
    }

    @Override
    public String getTestText(){
        // grid
        StringBuilder sb = new StringBuilder();
        final int w = grid.w;
        final int h = grid.h;
        final int n = shelves.size();

        StringBuilder line = new StringBuilder(w + " " + h + " " + n + "\n");
        sb.append(line);

        for (int i = 0; i < n; i++) {
            Object[] shelf = shelves.get(i);
            line = new StringBuilder(shelf[0] + " " + shelf[1] + " " + shelf[2] + " " + shelf[3] + "\n");
            sb.append(line);
        }

        // routes
        final int q = routes.size();
        line = new StringBuilder(q + "\n");
        sb.append(line);

        for (int i = 0; i < q; i++) {
            Object[] order = routes.get(i);
            line = new StringBuilder(order[0].toString());
            for (int j = 1; j < order.length; j++) {
                line.append(" ").append(order[j]);
            }
            line.append("\n");
            sb.append(line);
        }

        return sb.toString();
    }
}
