package ie03.phase2.task5;

import java.util.*;

public class KruskalTSP {
    private static class Edge implements Comparable<Edge> {
        int u, v;
        double weight;

        public Edge(int u, int v, double weight) {
            this.u = u;
            this.v = v;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge other) {
            return Double.compare(this.weight, other.weight);
        }
    }

    private static class UnionFind {
        private int[] parent, size;

        public UnionFind(int n) {
            parent = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        public int find(int x) {
            if (parent[x] == x) {
                return x;
            } else {
                return parent[x] = find(parent[x]);
            }
        }

        public void unite(int x, int y) {
            x = find(x);
            y = find(y);
            if (x == y) return;

            if (size[x] < size[y]) {
                int temp = x;
                x = y;
                y = temp;
            }
            size[x] += size[y];
            parent[y] = x;
        }

        public boolean same(int x, int y) {
            return find(x) == find(y);
        }

        public int size(int x) {
            return size[find(x)];
        }
    }

    private int go_next(List<List<Integer>> relation, HashSet<Integer> unvisited_cities, int next){
        for(int i = 0; i < relation.get(next).size(); i++){
            if (unvisited_cities.contains(relation.get(next).get(i))){
                return relation.get(next).get(i);
            }
        }

        return -1;
    }


    public ArrayList<String> kruskal(int N, int st, String[] stopovers, Map<String, Integer> originalDistDict) {
        ArrayList<Edge> edges = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                String combName = CombinationName.get(stopovers[i], stopovers[j]);
                double dist = originalDistDict.get(combName);
                edges.add(new Edge(i, j, dist));
            }
        }
        Collections.sort(edges);

        UnionFind uf = new UnionFind(N);
        List<List<Integer>> relation = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            relation.add(new ArrayList<>());
        }
        for (Edge e : edges) {
            if (!uf.same(e.u, e.v) && relation.get(e.u).size() < 2 && relation.get(e.v).size() < 2) {
                uf.unite(e.u, e.v);
                relation.get(e.u).add(e.v);
                relation.get(e.v).add(e.u);
            }
        }

        ArrayList<Integer> linearEdge = new ArrayList<>();
        for(int i=0; i<N;i++){
            if (relation.get(i).size() == 1){
                linearEdge.add(i);
            }
        }
        relation.get(linearEdge.get(0)).add(linearEdge.get(1));
        relation.get(linearEdge.get(1)).add(linearEdge.get(0));

        ArrayList<Integer> tour = new ArrayList<>();
        int currentCity = st;
        HashSet<Integer> unvisitedCities = new HashSet<>();
        for (int i = 0; i < N; i++) {
            unvisitedCities.add(i);
        }

        tour.add(currentCity);
        unvisitedCities.remove(currentCity);

        int nextCity = relation.get(currentCity).get(0);
        while (!unvisitedCities.isEmpty()){
            unvisitedCities.remove(nextCity);
            tour.add(nextCity);
            nextCity = go_next(relation, unvisitedCities, nextCity);
        }

        return reorder(stopovers, tour);
    }

    public ArrayList<String> reorder(String[] array, ArrayList<Integer> indices) {
        ArrayList<String> reordered = new ArrayList<>();
        for (int index : indices) {
            reordered.add(array[index]);
        }
        return reordered;
    }
}

