package ie03.phase2.task5;

public class SolveRoutes {
    private final GraphBuilder graphBl;

    public SolveRoutes(String[] inputRoute, Grid grid) {
        this.graphBl = new GraphBuilder(inputRoute, grid);
    }

    public void resetGlaph(int m){
        graphBl.getDistGlaph(m);
    }

    public String[] getStopOvers(){
        return graphBl.stopovers;
    }


    /* If you want to know which route you went through,
    just comment out the line that says for debug and put it back in the code and you will see it.
     */
    public GraphBuilder GetGraphBl() {
        return graphBl;
    }
}
