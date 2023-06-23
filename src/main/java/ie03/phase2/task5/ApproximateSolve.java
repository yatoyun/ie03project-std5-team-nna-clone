package ie03.phase2.task5;

import java.util.*;

public class ApproximateSolve {
    private final GraphBuilder graphBl;
    private final int n;
    private HashSet<ArrayList<String>> FinalPathList;
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

    public HashSet<ArrayList<String>> getMinRoutePath(){ return FinalPathList; }

    public void solveTSP(){
        String combName = CombinationName.get("EN", "EX");
        originalDistDict.put(combName, 0);

        minusKMeanDistance(n);
        ArrayList<String> path;

        for (int flag = 0; flag < 2; flag++) {
            for (int st = 0; st < n; st++) {
                if (flag == 0) {
                    KruskalTSP kruTsp = new KruskalTSP();
                    path = kruTsp.kruskal(n, st, graphBl.stopovers, originalDistDict);
                } else {
                    path = new ArrayList<>();
                    greedyDist(path, graphBl.stopovers, st);
                }

                // Simulated Annealing
                double T = 100.0;  // Initial temperature
                double T_min = 0.01;  // Minimum temperature
                double alpha = 0.9;  // Cooling rate
                Random random = new Random();
                while (T > T_min) {
                    // Create a new candidate solution
                    ArrayList<String> newPath = new ArrayList<>(path);
                    int optChoice = random.nextInt(4);  // Choose 3 different options
                    switch (optChoice) {
                        case 0:
                            twoOpt(newPath);
                            break;
                        case 1:
                            oneOrOpt(newPath);
                            break;
                        case 2:
                            twoOrOpt(newPath);
                            break;
                        case 3:
                            int first = random.nextInt(n - 1) + 1;
                            int second = random.nextInt(n - 2) + 1;
                            if (second == first) second++;
                            Collections.swap(newPath, first, second);
                    }

                    // Calculate the acceptance probability
                    int currentDistance = totalDists(path);
                    int newDistance = totalDists(newPath);
                    double ap = acceptanceProbability(currentDistance, newDistance, T);

                    // Decide whether to accept the new solution
                    if (ap > Math.random()) {
                        path = newPath;
                    }

                    // Cool down
                    T *= alpha;
                }

                rearrangeList(path);
                int value = totalDists(path);

                if (!path.get(0).equals("EN")) {
                    Collections.reverse(path);
                }
                if (!(path.get(0).equals("EN") && path.get(path.size() - 1).equals("EX")))
                    continue;

                if (value == minimumRouteValue) {
                    FinalPathList.add((ArrayList<String>) path.clone());
                }else if (value < minimumRouteValue){
                    FinalPathList = new HashSet<>();
                    FinalPathList.add((ArrayList<String>) path.clone());
                    minimumRouteValue = value;
                }
            }
        }

        for (ArrayList<String> finalPath : FinalPathList) {
            finalPath.remove("EN");
            finalPath.remove("EX");
        }
    }

    private double acceptanceProbability(int currentDistance, int newDistance, double T) {
        if (newDistance < currentDistance) {
            return 1.0;
        } else {
            return Math.exp((currentDistance - newDistance) / T);
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

    private void threeOpt(ArrayList<String> path) {
        int N = n;

        boolean improved = true;
        while (improved) {
            improved = false;
            for (int i = 0; i < N - 3; i++) {
                for (int j = i + 2; j < N - 1; j++) {
                    for (int k = j + 2; k < N + (i > 0 ? 1 : 0); k++) {
                        int l1 = getDistCombNodes(i, i+1, path);
                        int l2 = getDistCombNodes(j, j+1, path);
                        int l3 = getDistCombNodes(k % N, (k+1) % N, path);
                        int l4 = getDistCombNodes(i, j, path);
                        int l5 = getDistCombNodes(j+1, k % N, path);
                        int l6 = getDistCombNodes((k+1) % N, i+1, path);
                        if (l1 + l2 + l3 > l4 + l5 + l6) {
                            ArrayList<String> newTour1 = new ArrayList<>(path.subList(i+1, j+1));
                            Collections.reverse(newTour1);
                            path.subList(i+1, j+1).clear();
                            path.addAll(i+1, newTour1);
                            ArrayList<String> newTour2 = new ArrayList<>(path.subList(j+1, k % N));
                            Collections.reverse(newTour2);
                            path.subList(j+1, k % N).clear();
                            path.addAll(j+1, newTour2);
                            improved = true;
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


