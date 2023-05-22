package ie03.phase1.task3.generator;

public class TestCaseIntegrator extends TestCaseGenerator {

    public TestCaseIntegrator(int w, int h) {
        super(w, h);
    }

    public String getOutput() {
        StringBuilder sb = new StringBuilder();
        for (final Object[] route : routes) {
            String line = solver.move(route) + "\n";
            sb.append(line);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        int i = 5;
        int w = 4*i;
        int h = 4*i;
        int n = i*3;
        int q = 1;
        int maxRouteLength = 1;

        TestCaseIntegrator generator = new TestCaseIntegrator(w, h);
        generator.putShelves(n);
        generator.generateRouteRandomly(q, maxRouteLength);

        System.out.println(generator.getInput());
        System.out.println(generator.getOutput());
    }
}
