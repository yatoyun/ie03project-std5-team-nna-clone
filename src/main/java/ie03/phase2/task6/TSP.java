package ie03.phase2.task6;

import ie03.phase2.task5.CombinationName;
import ie03.phase2.task5.GraphBuilder;

import java.util.*;

public class TSP {
    private final GraphBuilder graphBl;
    private int[][] prev;
    private final int n;
    private ArrayList<String> minPath = new ArrayList<>();
    private int minimumRouteValue = Integer.MAX_VALUE;

    private HashMap<String, Integer> allwaypoints;

    private int finalVisitablePath = 0;

    private HashMap<String, Integer> lasttDist = new HashMap<>();

    private int[][] pathDp;

    public TSP(GraphBuilder graphBl, HashMap<String, Integer> allwaypoints){
        this.graphBl = graphBl;
        this.n = this.graphBl.stopovers.length - 1; // EX is excluded
        this.allwaypoints = allwaypoints;
    }

    public int getMinRouteValue(){
        return minimumRouteValue;
    }

    public ArrayList<String> getMinRoutePath(){
        return minPath;
    }

    public int getFinalVisitablePath(){
        return finalVisitablePath;
    }

    public void solveTSP(){
        // bitDP
        for (int i = 1; i < n; i++) {
            String comb_name = CombinationName.get(graphBl.stopovers[i], graphBl.stopovers[n]);
            if (graphBl.distGraph.get(comb_name) != null) {
                lasttDist.put(graphBl.stopovers[i], graphBl.distGraph.get(comb_name));
            }
        }

        int[][] dp = new int[1 << n][n];
        pathDp = new int[1 << n][n];

        // run dp
        solveDP(dp);

        int min = Integer.MAX_VALUE;
        int maxPath = 0;
        int last = -1;    // for debug
        for (int i = 1; i < n; i++) {
            int x = dp[(1 << n) - 1][i] + lasttDist.get(graphBl.stopovers[i]);
            String combName = CombinationName.get(graphBl.stopovers[i], graphBl.stopovers[n]);
            if (x == min) {
                int path = pathDp[(1 << n) - 1][i] | allwaypoints.get(combName);
                if (Integer.bitCount(maxPath) < Integer.bitCount(path)) {
                    maxPath = path;
                    last = i;
                }
            } else if (x < min) {
                min = x;
                maxPath = pathDp[(1 << n) - 1][i] | allwaypoints.get(combName);
                last = i;
            }
        }
        minimumRouteValue = min;
        finalVisitablePath = maxPath;


        // Reverse the PATH
        getRoutePath(last);
    }

    private void solveDP(int[][] dp){
        prev = new int[1 << n][n]; // for debug
        for (int[] row : dp) Arrays.fill(row, 25*25);
        dp[1][0] = 0;
        for (int mask = 1; mask < (1 << n); mask++) {
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    for (int j = 1; j < n; j++) {
                        // if combName exists and has not yet passed
                        if ((mask & (1 << j)) == 0 && i != j){
                            // make combination name
                            String combName = CombinationName.get(graphBl.stopovers[i], graphBl.stopovers[j]);
                            dp[mask | (1 << j)][j] = checkMin(dp, mask, i, j, combName);
                            prev[mask | 1 << j][j] = i; //for debug
                        }
                    }
                }
            }
        }
    }

    private int checkMin(int[][] dp, int mask, int i, int j, String combName){
        int x = dp[mask | 1 << j][j];
        int y = dp[mask][i]+ graphBl.distGraph.get(combName);

        // decide which is smaller and path is bigger

        if (x == y){
            // bigger path
            int xPath = pathDp[mask | 1 << j][j];
            int yPath = pathDp[mask][i] | allwaypoints.get(combName);
            if (Integer.bitCount(xPath) < Integer.bitCount(yPath)) {
                return x;
            }
            else{
                pathDp[mask | 1 << j][j] = yPath;
                return y;
            }
        }else if (x > y){
            pathDp[mask | 1 << j][j] = pathDp[mask][i] | allwaypoints.get(combName);
            return y;
        }else{
            return x;
        }
    }

    private void getRoutePath(int last){
        int mask = (1 << n) - 1;
        while (last != 0) {
            minPath.add(graphBl.stopovers[last]);
            int temp = mask;
            mask ^= 1 << last;
            last = prev[temp][last];
        }
        Collections.reverse(minPath);
    }
}
