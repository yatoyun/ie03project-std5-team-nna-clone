package ie03.phase2.task5.generator;

import ie03.phase2.task5.CombinationName;
import ie03.phase2.task5.GraphBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class TSPTEST {
    private final GraphBuilder graphBl;
    private String[] prev;
    private final int n;
    private ArrayList<String> path;
    private int minimumRouteValue;
    private HashMap<String, Integer> firstDists;
    private HashMap<String, Integer> endDists;

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
        prev = new String[n]; // for debug
        String[] data = new String[n];
        firstDists = new HashMap<>();
        endDists = new HashMap<>();

        for (int i = 1; i< n; i++){
            String comb_name = CombinationName.get(graphBl.stopovers[0], graphBl.stopovers[i]);
            if (graphBl.distGraph.get(comb_name) != null){
                firstDists.put(graphBl.stopovers[i], graphBl.distGraph.get(comb_name));
            }
        }

        for (int i = 1; i < n; i++){
            String comb_name = CombinationName.get(graphBl.stopovers[i], graphBl.stopovers[n]);
            if (graphBl.distGraph.get(comb_name) != null){
                endDists.put(graphBl.stopovers[i], graphBl.distGraph.get(comb_name));
            }
        }

        minimumRouteValue = Integer.MAX_VALUE;
        initializeTSP(graphBl.stopovers, 1, n-1, 0);


    }

    static void swap(String[] arr, int i, int j) {
        String temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private void initializeTSP(String[] arr, int index, int n, int dist){
        if (index == n+1)
        {
            dist += endDists.get(arr[index-1]);
            if (minimumRouteValue > dist){
                minimumRouteValue = dist;
                path = new ArrayList<String>(Arrays.asList(arr));
            }
            return;
        }
        int value;

        for (int i = index; i <= n; i++) {
            if (index == 1) {
                value = firstDists.get(arr[index]);
            }else{
                String comb_name = CombinationName.get(arr[index - 1], arr[index]);
                value = graphBl.distGraph.get(comb_name); // 新たに追加されたルートの距離を追加
            }

            if (dist+value >= minimumRouteValue){
                continue;
            }
            swap(arr, index, i);
            initializeTSP(arr, index + 1, n, dist+value);
            swap(arr, index, i); // 元に戻す
        }

    }
}
