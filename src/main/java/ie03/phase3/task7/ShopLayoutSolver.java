package ie03.phase3.task7;

import ie03.phase2.task5.Grid;
import ie03.phase2.task6.ShortestPathRouteSolver;

import java.awt.*;
import java.util.*;

public class ShopLayoutSolver {
    ProductsManager productsData;
    Grid grid;
    ShortestPathRouteSolver sprSolver;
    private Map<String, Integer> prodName2prodIdxMap = new HashMap<>();
    private Map<String, String> shelfName2prodNameMap = new HashMap<>();
    private ArrayList<String> prodNameList = new ArrayList<>();

    public ShopLayoutSolver(ProductsManager productsData, Grid grid) {
        this.productsData = productsData;
        this.grid = grid;
        grid.putGates();
        grid.getDistGraph();
        sprSolver = new ShortestPathRouteSolver(grid);
        for (String product : productsData.getProducts()) {
            prodName2prodIdxMap.put(product, prodName2prodIdxMap.size());
        }
        prodNameList.addAll(productsData.getProducts());
    }

    private int[][] composeAdjMatrix() {

        int numProducts = productsData.getProducts().size();

        int[][] adjMatrix = new int[numProducts][numProducts];

        for (int i = 0; i < numProducts; i++) {
            Arrays.fill(adjMatrix[i], 0);
        }

        for (Map.Entry<String, Integer> entry : productsData.getSortedPurchaseCount()) {
            String product = entry.getKey();
            int productIndex = prodName2prodIdxMap.get(product);
            adjMatrix[productIndex][productIndex] = entry.getValue();
        }

        for (Map.Entry<String, Integer> entry : productsData.getSortedPurchasePairCount()) {
            String[] products = entry.getKey().split(" ");
            int productIndex1 = prodName2prodIdxMap.get(products[0]);
            int productIndex2 = prodName2prodIdxMap.get(products[1]);
            adjMatrix[productIndex1][productIndex2] = entry.getValue();
            adjMatrix[productIndex2][productIndex1] = entry.getValue();
        }
        return adjMatrix;
    }

    public ArrayList<Map.Entry<String, Point>> getShoplayout() {
        ArrayList<Map.Entry<String, Integer>> sortedPurchaseCountList = productsData.getSortedPurchaseCount();

        int numProducts = productsData.getProducts().size();
        int[][] adjMatrix = composeAdjMatrix();


        Queue<Map.Entry<ArrayList<String>, ArrayList<String>>> q = new LinkedList<>();
        ArrayList<String> left = new ArrayList<>();
        // Even me don't get it :(
        ArrayList<String> right = grid.shelves.keySet().stream().filter(s -> !s.equals("EN") && !s.equals("EX")).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        for (int i = 0; i < numProducts; i++) {
            left.add(sortedPurchaseCountList.get(i).getKey());
        }
        q.add(new AbstractMap.SimpleEntry<>(left, right));

        while (!q.isEmpty()) {
            Map.Entry<ArrayList<String>, ArrayList<String>> entry = q.poll();
            ArrayList<String> leftList = entry.getKey();
            ArrayList<String> rightList = entry.getValue();

            if (leftList.size() == 0 || rightList.size() == 0) {
                continue;
            }

            String product = leftList.get(0);
            int productIndex = prodName2prodIdxMap.get(product);

            if (adjMatrix[productIndex][productIndex] == 0) {
                continue;
            }

            ArrayList<String> nextRightList = new ArrayList<>();
            String chosenShelf = "";

            for (String shelf : rightList) {
                if (shelfName2prodNameMap.containsKey(shelf)) {
                    continue;
                }
                String[] shelfName = new String[1];
                shelfName[0] = shelf;
                Map.Entry<Integer, ArrayList<String>> result = sprSolver.solve(shelfName);
                ArrayList<String> possibleRightList = result.getValue();
                if (nextRightList.size() < possibleRightList.size()) {
                    nextRightList = possibleRightList;
                    chosenShelf = shelf;
                }
            }

            // in case
            if (nextRightList.size() == 0) {
                continue;
            }

            // decide
            shelfName2prodNameMap.put(chosenShelf, product);

            ArrayList<String> nextLeftList = new ArrayList<>(leftList);

            for (String prod : prodNameList) {
                int prodIndex = prodName2prodIdxMap.get(prod);
                if (adjMatrix[productIndex][prodIndex] == 0) {
                    nextLeftList.remove(prod);
                }
            }
            adjMatrix[productIndex][productIndex] = 0;

            q.add(new AbstractMap.SimpleEntry<>(nextLeftList, nextRightList));

            leftList.remove(0);
            q.add(new AbstractMap.SimpleEntry<>(leftList, rightList));
        }


        ArrayList<Map.Entry<String, Point>> result = new ArrayList<>();
        for (Map.Entry<String, String> entry : shelfName2prodNameMap.entrySet()) {
            String shelf = entry.getKey();
            String product = entry.getValue();
            result.add(new AbstractMap.SimpleEntry<>(product, grid.getShelfPoint(shelf)));
        }
        return result;
    }
}
