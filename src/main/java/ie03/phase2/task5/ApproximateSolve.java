package ie03.phase2.task5;

import java.util.*;

public class ApproximateSolve {
    private final GraphBuilder graphBl;
    private final int n;
    private ArrayList<String> FinalPath;
    private int minimumRouteValue = Integer.MAX_VALUE;
    private final HashMap<String, Integer> originalDistDict;

    public ApproximateSolve(GraphBuilder graphBl){
        this.graphBl = graphBl;
        this.n = this.graphBl.stopovers.length; // EX is excluded
        this.originalDistDict = new HashMap<>(graphBl.distGraph);
    }

    public int getMinRouteValue(){
        return minimumRouteValue;
    }

    public ArrayList<String> getMinRoutePath(){
        return FinalPath;
    }

    public void solveTSP(){
        String combName = CombinationName.get("EN", "EX");
        originalDistDict.put(combName, 0);

        minusKMeanDistance(n);
        ArrayList<String> path = new ArrayList<>();

        for (int flag = 0; flag < 2; flag++) {
            for (int st = 0; st < n; st++) {
                if (flag == 0) {
                    KruskalTSP kruTsp = new KruskalTSP();
                    path = kruTsp.kruskal(n, st, graphBl.stopovers, originalDistDict);
                } else {
                    path = new ArrayList<>();
                    greedyDist(path, graphBl.stopovers, st);
                }

                twoOpt(path);
                oneOrOpt(path);
                twoOrOpt(path);

                rearrangeList(path);
                int value = totalDists(path);

                if (!path.get(0).equals("EN")) {
                    Collections.reverse(path);
                }
                if (!(path.get(0).equals("EN") && path.get(path.size() - 1).equals("EX")))
                    continue;

                if (value == minimumRouteValue) {
                    compareOrder(FinalPath, path);
                }else if (value < minimumRouteValue){
                    FinalPath = path;
                    minimumRouteValue = value;
                }
            }
        }
        if (!(FinalPath.get(0).equals("EN") && FinalPath.get(path.size()-1).equals("EX")))
            System.out.println(FinalPath);
        FinalPath.remove("EN");
    }

    private void compareOrder(ArrayList<String> finalPath, ArrayList<String> currentPath){
        for (int i = 0; i<finalPath.size();i++){
            int check =  finalPath.get(i).compareTo(currentPath.get(i));

            if (check < 0)
                return;
            else if(check > 0){
                finalPath = currentPath;
            }
        }
    }

    private void rearrangeList(ArrayList<String> list) {
        int indexEN = list.indexOf("EN");
        int indexEX = list.indexOf("EX");

        // Check if "EN" and "EX" are adjacent and "EN" comes before "EX"
        if (indexEN == indexEX - 1) {
            // Rotate the list so that "EN" is at index 0 and "EX" is at the last index
            Collections.rotate(list, -(indexEN+1));
        } else if (indexEX == indexEN - 1) {
            // If "EX" comes before "EN", rotate the list so that "EX" is at index 0
            // Then, reverse the list so that "EN" is at index 0 and "EX" is at the last index
            Collections.rotate(list, -(indexEX+1));
            Collections.reverse(list);
        }
    }






    private void greedyDist(ArrayList<String> path, String[] stopovers, int st){
        String lastCity = stopovers[st]; // 最初の都市
        HashSet<String> visitedCities = new HashSet<>();
        path.add(lastCity);
        int index = 1;

        visitedCities.add(lastCity);
        ArrayList<String> newList = new ArrayList<>(Arrays.asList(stopovers));
        newList.removeIf(element -> element.equals(stopovers[st]));

        while (visitedCities.size() < n) {
            String closestCity = null;
            int closestDist = Integer.MAX_VALUE;
            for (String city : newList) {
                if (!visitedCities.contains(city)) {
                    String comb_name = CombinationName.get(lastCity, city);
                    int dist = originalDistDict.get(comb_name);
                    if (dist < closestDist) {
                        closestDist = dist;
                        closestCity = city;
                    }
                }
            }
            lastCity = closestCity;
            visitedCities.add(lastCity);
            path.add(lastCity);
        }
    }

    private void minusKMeanDistance(int k) {
        int N = graphBl.stopovers.length;
        double[] aveDist = new double[N];

        // Calculate average distance for each city
        for (int i = 0; i < N; i++) {
            List<Integer> distances = new ArrayList<>();
            for (int j = 0; j < N; j++) {
                if (i != j) {
                    String combName = CombinationName.get(graphBl.stopovers[i], graphBl.stopovers[j]);
                    distances.add(originalDistDict.get(combName));
                }
            }
            Collections.sort(distances);
            aveDist[i] = distances.subList(0, Math.min(N-1, k)).stream().mapToInt(Integer::intValue).average().getAsDouble();
        }

        // Subtract average distance from each distance
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i != j) {
                    String combName = CombinationName.get(graphBl.stopovers[i], graphBl.stopovers[j]);
                    int originalDist = originalDistDict.get(combName);
                    originalDistDict.put(combName, (int) (originalDist - (aveDist[i] + aveDist[j])));
                }
            }
        }
    }

    private int getDistCombNodes(int i, int j, ArrayList<String> path){
        String combName = CombinationName.get(path.get(i), path.get(j));
        return originalDistDict.get(combName);
    }

    private void twoOpt(ArrayList<String> path) {
        int N = n;

        boolean improved = true;
        while (improved) {
            improved = false;
            for (int i = 0; i < N - 2; i++) {
                for (int j = i + 2; j < N; j++) {
                    int l1 = getDistCombNodes(i, i+1, path);
                    int l2 = getDistCombNodes(j, (j+1)%N, path);
                    int l3 = getDistCombNodes(i, j, path);
                    int l4 = getDistCombNodes(i+1, (j+1)%N, path);
                    if (l1 + l2 > l3 + l4) {
                        ArrayList<String> newTour = new ArrayList<>(path.subList(i + 1, j + 1));
                        Collections.reverse(newTour);
                        path.subList(i + 1, j + 1).clear();
                        path.addAll(i + 1, newTour);
                        improved = true;
                    }
                }
            }
        }
    }

    private void oneOrOpt(ArrayList<String> path) {
        int N = n;

        boolean improved = true;
        while (improved) {
            improved = false;
            for (int i = 0; i < N; i++) {
                int i0 = i;
                int i1 = (i + 1) % N;
                int i2 = (i + 2) % N;
                for (int j = 0; j < N; j++) {
                    int j0 = j;
                    int j1 = (j + 1) % N;
                    if (j0 != i0 && j0 != i1) {
                        int l1 = getDistCombNodes(i0, i1, path);
                        int l2 = getDistCombNodes(i1, i2, path);
                        int l3 = getDistCombNodes(j0, j1, path);
                        int l4 = getDistCombNodes(j0, i1, path);
                        int l5 = getDistCombNodes(j1, i1, path);
                        int l6 = getDistCombNodes(i0, i2, path);
                        if (l1 + l2 + l3 > l4 + l5 + l6) {
                            String city = path.remove(i1);
                            if (i1 < j1) {
                                path.add(j0, city);
                            } else {
                                path.add(j1, city);
                            }
                            improved = true;
                        }
                    }
                }
            }
        }
    }

    private void twoOrOpt(ArrayList<String> path) {
        int N = n;

        boolean improved = true;
        while (improved) {
            improved = false;
            for (int i = 0; i < N; i++) {
                int i0 = i;
                int i1 = (i + 1) % N;
                int i2 = (i + 2) % N;
                int i3 = (i + 3) % N;
                for (int j = 0; j < N; j++) {
                    int j0 = j;
                    int j1 = (j + 1) % N;
                    if (j0 != i0 && j0 != i1 && j0 != i2) {
                        int l1 = getDistCombNodes(i0, i1, path);
                        int l2 = getDistCombNodes(i2, i3, path);
                        int l3 = getDistCombNodes(j0, j1, path);
                        int l4 = getDistCombNodes(j0, i1, path);
                        int l5 = getDistCombNodes(j1, i2, path);
                        int l6 = getDistCombNodes(i0, i3, path);
                        if (l1 + l2 + l3 > l4 + l5 + l6) {
                            if (i2 == 0) {
                                String city1 = path.remove(i1);
                                String city2 = path.remove(i2);
                                path.add(j0, city2);
                                path.add(j0, city1);
                            }else {
                                String city2 = path.remove(i2);
                                String city1 = path.remove(i1);
                                if (i1 < j1) {
                                    path.add(j0 - 1, city2);
                                    path.add(j0 - 1, city1);
                                } else {
                                    path.add(j1, city2);
                                    path.add(j1, city1);
                                }
                                improved = true;
                            }
                        }
                    }
                }
            }
        }
    }

    private int totalDists(ArrayList<String> path){
        int min = 0;

        for (int i = 1; i < path.size(); i++){
            String combName = CombinationName.get(path.get(i-1), path.get(i));
            if (combName.equals("EN-EX"))
                continue;
            min += graphBl.distGraph.get(combName);
        }
        String combName = CombinationName.get(path.get(0), path.get(path.size()-1));
        if (!combName.equals("EN-EX"))
            min += graphBl.distGraph.get(combName);

        return  min;
    }
}


