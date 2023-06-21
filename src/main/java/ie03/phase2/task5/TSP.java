package ie03.phase2.task5;

import java.util.*;

public class TSP {
    private final GraphBuilder graphBl;
    private int[][] prev;
    private final int n;
    private ArrayList<String> path;
    private int minimumRouteValue = Integer.MAX_VALUE;

    private HashMap<String, Integer> lasttDist = new HashMap<>();

    public TSP(GraphBuilder graphBl){
        this.graphBl = graphBl;
        this.n = this.graphBl.stopovers.length - 1; // EX is excluded
    }

    public int getMinRouteValue(){
        return minimumRouteValue;
    }

    public ArrayList<String> getMinRoutePath(){
        return path;
    }

    public void solveTSP(){
        // approximate
        ApproximateSolve approximateSr = new ApproximateSolve(graphBl);
        approximateSr.solveTSP();
        minimumRouteValue = approximateSr.getMinRouteValue();

        for (int i = 1; i < n; i++) {
            String comb_name = CombinationName.get(graphBl.stopovers[i], graphBl.stopovers[n]);
            if (graphBl.distGraph.get(comb_name) != null) {
                lasttDist.put(graphBl.stopovers[i], graphBl.distGraph.get(comb_name));
            }
        }

        int[][] dp = new int[1 << n][n];

        // run dp
        solveDP(dp);

        int last = -1; // for debug
        for (int i = 1; i < n; i++) {
            minimumRouteValue = Math.min(minimumRouteValue, dp[(1 << n) - 1][i] + lasttDist.get(graphBl.stopovers[i]));
            last = i; // for debug
        }

        // Reverse the PATH
        getRoutePath(last);
    }

    private void solveDP(int[][] dp){
        prev = new int[1 << n][n]; // for debug
        for (int[] row : dp) Arrays.fill(row, 25*25);
        dp[1][0] = 0;
        HashSet<Integer> pruning = new HashSet<>();
        int currentBest = Integer.MAX_VALUE;

        for (int mask = 1; mask < (1 << n); mask++) {
            if (pruning.contains(mask)) continue;
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    for (int j = 1; j < n; j++) {
                        // if combName exists and has not yet passed
                        if ((mask & (1 << j)) == 0 && i != j){
                            // make combination name
                            String combName = CombinationName.get(graphBl.stopovers[i], graphBl.stopovers[j]);
                            dp[mask | (1 << j)][j] = Math.min(dp[mask | 1 << j][j], dp[mask][i] + graphBl.distGraph.get(combName));
                            currentBest = Math.min(currentBest, dp[mask | 1 << j][j] + lasttDist.get(graphBl.stopovers[j]));
                            prev[mask | 1 << j][j] = i; //for debug
                        }
                    }
                }
            }
            if (currentBest >= minimumRouteValue) {
                pruningAddMask(pruning, mask);
            }
        }
    }

    private void pruningAddMask(HashSet<Integer> pruning, int mask){
        for (int submask = 0; submask < (1 << n - 1); submask++) {
            if ((submask & mask) == submask) {
                pruning.add(submask);
            }
        }
    }

    private void getRoutePath(int last){
        path = new ArrayList<String>();
        int mask = (1 << n) - 1;
        while (last != 0) {
            path.add(graphBl.stopovers[last]);
            int temp = mask;
            mask ^= 1 << last;
            last = prev[temp][last];
        }
        Collections.reverse(path);
    }
}
