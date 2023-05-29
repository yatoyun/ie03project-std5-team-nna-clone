package ie03.phase2.task6;

import java.util.*;

import ie03.phase2.task5.Grid;
import ie03.phase2.task5.SolveRoutes;

public class RouteSimulationWithPathRoute extends SolveRoutes {
    public RouteSimulationWithPathRoute(Grid grid) {
        super(grid);
    }

    /* If you want to know which route you went through,
    just comment out the line that says for debug and put it back in the code and you will see it.
     */
    public ArrayList<String> solveAndGetRouteTSP() {
        int n = stopovers.length - 1; // EX is excluded
        int[][] dp = new int[1 << n][n];
        int[][] prev = new int[1 << n][n];
        for (int[] row : dp) Arrays.fill(row, 300);
        dp[1][0] = 0;

        for (int mask = 1; mask < (1 << n); mask++) {
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    for (int j = 0; j < n; j++) {
                        String comb_name = getCombName(stopovers[i], stopovers[j]);

                        if ((mask & (1 << j)) == 0 && dist_graph.get(comb_name) != null) {
                            dp[mask | (1 << j)][j] = Math.min(dp[mask | 1 << j][j], dp[mask][i] + dist_graph.get(comb_name));
                            prev[mask | 1 << j][j] = i;
                        }
                    }
                }
            }
        }


        int res = Integer.MAX_VALUE;
        int last = -1;
        for (int i = 0; i < n; i++) {
            String comb_name = getCombName(stopovers[i], stopovers[n]);
            if (dist_graph.get(comb_name) != null) {
                res = Math.min(res, dp[(1 << n) - 1][i] + dist_graph.get(comb_name));
                last = i;
            }
        }

        ArrayList<String> path = new ArrayList<>();
        int mask = (1 << n) - 1;
        while (last != 0) {
            path.add(stopovers[last]);
            int temp = mask;
            mask ^= 1 << last;
            last = prev[temp][last];
        }

        Collections.reverse(path);

        return path;
    }


}