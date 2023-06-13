package ie03.phase2.task5.generator;

import ie03.phase1.task3.Grid;
import ie03.phase2.task5.SolveRoutes;

import java.util.ArrayList;

public class OutputCreator implements ICreator<String> {
    private final ArrayList<Object[]> shelves;
    private final ArrayList<Object[]> routes;
    private final TestCaseSolver solver;

    public OutputCreator(Grid grid, ArrayList<Object[]> shelves, ArrayList<Object[]> routes){
        this.shelves = shelves;
        this.routes = routes;
        this.solver = new TestCaseSolver(grid);
    }

    @Override
    public String getTestText(){
        StringBuilder sb = new StringBuilder();
        for (final Object[] route : routes) {
            String line = solver.move(route) + "\n";
            sb.append(line);
        }
        return sb.toString();
    }
}
