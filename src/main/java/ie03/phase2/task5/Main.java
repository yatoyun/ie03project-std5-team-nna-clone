package ie03.phase2.task5;

public class Main {
    public static void main(String[] args) {
        // input 01
        Input input = new Input(System.in);
        int w = input.nextInt();
        int h = input.nextInt();
        Grid grid = new Grid(w, h);

        int n = input.nextInt();
        grid.shelvesInitializer(input, n);

        // input 02 and solve
        int q = input.nextInt();

        SolveRoutes sr = new SolveRoutes(input, grid);

        for (int i = 0; i < q; i++) {
            int m = input.nextInt();

            // reset dist_graph
            sr.resetGlaph(m);

            // solve
            TSP tsp = sr.solve();
            System.out.println(tsp.getMinRouteValue());
        }
    }
}