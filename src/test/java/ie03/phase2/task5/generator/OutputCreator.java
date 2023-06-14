package ie03.phase2.task5.generator;

import ie03.phase2.task5.Grid;
import ie03.phase2.task5.GraphBuilder;

import java.util.ArrayList;

public class OutputCreator implements ICreator<String> {
    private final ArrayList<Object[]> routes;
    private final Grid grid;

    public OutputCreator(Grid grid, ArrayList<Object[]> routes){
        this.routes = routes;
        this.grid = grid;
    }

    @Override
    public String getTestText(){
        StringBuilder sb = new StringBuilder();
        StopOversInput soi = new StopOversInput();
        GraphBuilder graphBl = new GraphBuilder(soi, grid);

        for (final Object[] route : routes) {
            soi.reset(route);
            graphBl.getDistGlaph(soi.getLength());
            TSPTEST tsp = new TSPTEST(graphBl);
            tsp.solveTSP();

            String line = getOutput(tsp);
            sb.append(line);
        }
        return sb.toString();
    }

    protected String getOutput(TSPTEST tsp){
        return tsp.getMinRouteValue() + "\n";
    }
}
