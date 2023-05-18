package ie03.phase2.task5;

import java.awt.*;
import java.util.*;

public class SolveRoutes {
    public static Scanner sc = new Scanner(System.in);
    public static HashMap<String, Integer> dist_graph;
    public static GetMinimumRoute get_route;
    public static HashMap<String, Point> shelves;

    public static void inputSecond(int w, int h, int[][] grid, HashMap<String, Point> shelves) {
        // SolveRoutes 02 and solve
        SolveRoutes.get_route = new GetMinimumRoute(w, h, grid);
        SolveRoutes.shelves = shelves;

        int q = sc.nextInt();
        for (int i = 0; i < q; i++) {
            int m = sc.nextInt();
            int dist;

            // reset dist_graph
            SolveRoutes.dist_graph = new HashMap<>();
            String[] stopovers = getDistGlaph(m);

            // solve
            dist = solveTSP(stopovers);

            System.out.println(dist);
        }
    }

    public static String[] getDistGlaph(int m) {
        String[] stopovers = new String[m + 2];

        // set stopovers including entry and exit
        stopovers[0] = "EN";
        stopovers[m + 1] = "EX";
        for (int i = 1; i < m + 1; i++) {
            stopovers[i] = sc.next();
        }
        // get distances between each stopover and stopover
        getEachDists(stopovers, m + 2);
        return stopovers;
    }

    public static void getEachDists(String[] stopovers, int num_stopovers) {
        // num_stopovers_C_2
        for (int i = 0; i < num_stopovers; i++) {
            for (int j = i + 1; j < num_stopovers; j++) {

                SolveRoutes.dist_graph.put(getCombName(stopovers[i], stopovers[j]), // name of comb
                        SolveRoutes.get_route.solveDist(      // value of dists
                                shelves.get(stopovers[i]), shelves.get(stopovers[j])));
            }
        }
    }

    public static String getCombName(String first_stopover, String second_stopover) {
        String concat_names;

        // Alphabetized and concatenate
        if (first_stopover.compareTo(second_stopover) < 0) {
            concat_names = first_stopover + "-" + second_stopover;
        } else {
            concat_names = second_stopover + "-" + first_stopover;
        }
        return concat_names;
    }

    /* If you want to know which route you went through,
    just comment out the line that says for debug and put it back in the code and you will see it.
     */
    public static int solveTSP(String[] stopovers) {
        int n = stopovers.length - 1; // EX is excluded
        int[][] dp = new int[1 << n][n];
//        int[][] prev = new int[1 << n][n]; // for debug
        for (int[] row : dp) Arrays.fill(row, 300);
        dp[1][0] = 0;

        for (int mask = 1; mask < (1 << n); mask++) {
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    for (int j = 0; j < n; j++) {
                        String comb_name = getCombName(stopovers[i], stopovers[j]);

                        if ((mask & (1 << j)) == 0 && dist_graph.get(comb_name) != null) {
                            dp[mask | (1 << j)][j] = Math.min(dp[mask | 1 << j][j], dp[mask][i] + dist_graph.get(comb_name));
//                            prev[mask | 1 << j][j] = i; //for debug
                        }
                    }
                }
            }
        }


        int res = Integer.MAX_VALUE;
//        int last = -1; // for debug
        for (int i = 0; i < n; i++) {
            String comb_name = getCombName(stopovers[i], stopovers[n]);
            if (dist_graph.get(comb_name) != null) {
                res = Math.min(res, dp[(1 << n) - 1][i] + dist_graph.get(comb_name));
//                last = i; // for debug
            }
        }
//        // for debug and show route
//        ArrayList<String> path = new ArrayList<String>();
//        int mask = (1 << n) - 1;
//        while (last != 0) {
//            path.add(stopovers[last]);
//            int temp = mask;
//            mask ^= 1 << last;
//            last = prev[temp][last];
//        }
//        Collections.reverse(path);
//
//        System.out.println("Path: " + String.join(" -> ", path));

        return res;
    }
}
