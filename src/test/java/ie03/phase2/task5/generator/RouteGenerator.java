package ie03.phase2.task5.generator;

import java.util.ArrayList;
import java.util.Random;

public class RouteGenerator implements IGenerator<ArrayList<Object[]>> {
    private final ArrayList<Object[]> shelves;
    private final ArrayList<Object[]> routes;
    private final int numRoutes;
    private final int maxWayPoints;

    public RouteGenerator(ArrayList<Object[]> shelves, ArrayList<Object[]> routes, int numRoutes, int maxWayPoints){
        this.shelves = shelves;
        this.routes = routes;
        this.numRoutes = numRoutes;
        this.maxWayPoints = maxWayPoints;
    }

    @Override
    public void generate(){
        Random rand = new Random();
        for (int cnt = 0; cnt < numRoutes; cnt++) {
            int m = 1 + rand.nextInt(maxWayPoints);
            generateRouteManually(m);
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
}
