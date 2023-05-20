package ie03.phase1.task3.generator;

public class TestCaseIntegrator extends TestCaseGenerator {

    public TestCaseIntegrator(int w, int h) {
        super(w, h);
    }

    public String getOutput() {
        StringBuilder sb = new StringBuilder();
        final int q = routes.size();
        for (int i = 0; i < q; i++) {
            Object[] route = routes.get(i);
            String line = solver.move(route) + "\n";
            sb.append(line);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        int w = 4;
        int h = 4;
        int n = 3;
        int q = 4;
        int maxRouteLength = 1;

        TestCaseIntegrator generator = new TestCaseIntegrator(w, h);
        generator.putShelves(n);
        generator.generateRouteRandomly(q, maxRouteLength);

        System.out.println(generator.getInput());
        System.out.println(generator.getOutput());
    }
}
