package ie03.phase2.task5.generator;

import ie03.phase2.task5.CombinationName;
import ie03.phase2.task5.GraphBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class TSPTEST {
    private final GraphBuilder graphBl;
    private final int n;
    private ArrayList<String> path;
    private int minimumRouteValue;
    private HashMap<String, Integer> firstDists;
    private HashMap<String, Integer> endDists;

    private int minEndDist = Integer.MAX_VALUE;

    public TSPTEST(GraphBuilder graphBl){
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
        firstDists = new HashMap<>();
        endDists = new HashMap<>();

        initializeDist(firstDists, 0);
        initializeDist(endDists, n);

        // set minimumRouteValue using greedy algorithm
        greedyDist();
        for (Integer value : endDists.values()) {
            if (minEndDist > value) minEndDist = value;
        }

        recursiveTSP(graphBl.stopovers, 1, n-1, 0);


    }

    static void swap(String[] arr, int i, int j) {
        String temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private void recursiveTSP(String[] arr, int index, int n, int dist){
        if (index == n+1) // if arrived end point and the total dist is minimum, reset
        {
            dist += endDists.get(arr[index-1]);
            if (minimumRouteValue > dist){
                minimumRouteValue = dist;
                path = new ArrayList<>(Arrays.asList(arr));
            }
            return;
        }

        int value; // temp variable
        for (int i = index; i <= n; i++) {
            if (index == 1) {
                value = firstDists.get(arr[i]);
            }else{
                String comb_name = CombinationName.get(arr[index - 1], arr[i]);
                value = graphBl.distGraph.get(comb_name); // 新たに追加されたルートの距離を追加
            }
            // the current total dist is already more than minimum, then backtrack
            if (dist+value+minEndDist > minimumRouteValue){
                continue;
            }
            swap(arr, index, i);
            recursiveTSP(arr, index + 1, n, dist+value);
            swap(arr, index, i); // 元に戻す
        }

    }

    private void initializeDist(HashMap<String, Integer> dists, int comparedNode){
        for (int i = 1; i < n; i++){
            String comb_name = CombinationName.get(graphBl.stopovers[i], graphBl.stopovers[comparedNode]);
            if (graphBl.distGraph.get(comb_name) != null){
                dists.put(graphBl.stopovers[i], graphBl.distGraph.get(comb_name));
            }
        }
    }

    private void greedyDist(){
        String lastCity = graphBl.stopovers[0]; // 最初の都市
        HashSet<String> visitedCities = new HashSet<>();
        visitedCities.add(lastCity);
        minimumRouteValue = 0;

        while (visitedCities.size() < n) {
            String closestCity = null;
            int closestDist = Integer.MAX_VALUE;
            for (String city : firstDists.keySet()) {
                if (!visitedCities.contains(city)) {
                    String comb_name = CombinationName.get(lastCity, city);
                    int dist = graphBl.distGraph.get(comb_name);
                    if (dist < closestDist) {
                        closestDist = dist;
                        closestCity = city;
                    }
                }
            }
            minimumRouteValue += closestDist;
            lastCity = closestCity;
            visitedCities.add(lastCity);
        }
        String comb_name = CombinationName.get(lastCity, graphBl.stopovers[n]);
        minimumRouteValue += graphBl.distGraph.get(comb_name); // 最後に最初の都市に戻る
    }
}
