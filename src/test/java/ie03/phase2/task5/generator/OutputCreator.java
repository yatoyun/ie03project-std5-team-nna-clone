package ie03.phase2.task5.generator;

import ie03.phase2.task5.Grid;
import ie03.phase2.task5.GraphBuilder;
import ie03.phase2.task5.Pair;
import ie03.phase2.task5.TSP;

import java.util.*;
import java.util.concurrent.*;
import java.util.Comparator;
import java.util.concurrent.ExecutionException;

public class OutputCreator implements ICreator<String> {
    private final ArrayList<Object[]> routes;
    private final Grid grid;

    public OutputCreator(Grid grid, ArrayList<Object[]> routes){
        this.routes = routes;
        this.grid = grid;
    }

    @Override
    public String getTestText() throws InterruptedException, ExecutionException {
        StringBuilder sb = new StringBuilder();

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()-2);
        CompletionService<Pair<Integer, TSPTEST>> completionService = new ExecutorCompletionService<>(executor);

        int index = 0;
        for (final Object[] route : routes) {
            int currentIndex = index;
            completionService.submit(() -> {
                String[] routeNode = new String[route.length - 1];
                for (int i = 1; i < route.length; i++)
                    routeNode[i - 1] = (String) route[i];
                GraphBuilder graphBl = new GraphBuilder((String[]) routeNode, grid);
                graphBl.getDistGlaph(route.length-1);
                TSPTEST tsp = new TSPTEST(graphBl);
                tsp.solveTSP();

                return new Pair<>(currentIndex, tsp);
            });
            index++;
        }

        List<Pair<Integer, TSPTEST>> results = new ArrayList<>();
        for (int i = 0; i < routes.size(); i++) {
            results.add(completionService.take().get());
        }

        results.sort(Comparator.comparing(Pair::getKey));
        for (Pair<Integer, TSPTEST> result : results) {
            sb.append(getOutput(result.getValue()));
        }

        executor.shutdown();

        return sb.toString();
    }

    protected String getOutput(TSPTEST tsp){
        return tsp.getMinRouteValue() + "\n";
    }
}
