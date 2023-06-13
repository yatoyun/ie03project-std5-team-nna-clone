package ie03.phase2.task5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class TSP {
    private final GraphBuilder graphBl;
    private int[][] prev;
    private final int n;
    private ArrayList<String> path;
    private int minimumRouteValue;

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
        int[][] dp = new int[1 << n][n];

        initializeTSP(dp);

        minimumRouteValue = Integer.MAX_VALUE;
        int last = -1; // for debug
        for (int i = 0; i < n; i++) {
            String comb_name = CombinationName.get(graphBl.stopovers[i], graphBl.stopovers[n]);
            if (graphBl.distGraph.get(comb_name) != null) {
                minimumRouteValue = Math.min(minimumRouteValue, dp[(1 << n) - 1][i] + graphBl.distGraph.get(comb_name));
                last = i; // for debug
            }
        }

        // Reverse the PATH
        getRoutePath(last);
    }

    private void initializeTSP(int[][] dp){
        prev = new int[1 << n][n]; // for debug
        for (int[] row : dp) Arrays.fill(row, 300);
        dp[1][0] = 0;

        for (int mask = 1; mask < (1 << n); mask++) {
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    for (int j = 0; j < n; j++) {
                        // make combination name
                        String combName = CombinationName.get(graphBl.stopovers[i], graphBl.stopovers[j]);

                        // if combName exists and has not yet passed
                        if ((mask & (1 << j)) == 0 && graphBl.distGraph.get(combName) != null) {
                            dp[mask | (1 << j)][j] = Math.min(dp[mask | 1 << j][j], dp[mask][i] + graphBl.distGraph.get(combName));
                            prev[mask | 1 << j][j] = i; //for debug
                        }
                    }
                }
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
