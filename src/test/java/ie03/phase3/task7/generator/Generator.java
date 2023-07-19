package ie03.phase3.task7.generator;

import ie03.phase1.task3.Grid;
import ie03.phase1.task3.generator.TestCaseGenerator;

import java.util.*;

/*
Width Height S
x1 y1 id1 direction1
x2 y2 id2 d2
:
xS yS idS dS
P
s1
s2
:
sP
N
M1 p1,1 p1,2... p1,M1
M2 p2,1 p2,2 ... p2,M2
:
MQ pQ,1 pQ,2 ... pQ,M2
*/
public class Generator {
    protected int base;
    protected int w, h, n;

    protected List<String> purchaseList = new ArrayList<>();
    protected final String[] products = {
            "laptop", "fridge", "headphones", "tv", "monitor", "iphone", "toaster", "blender",
            "tea", "butter", "bread", "onion", "carrot", "potato", "rice", "water", "juice", "coffee", "milk", "coke",
            "ayhjls", "alkjuy", "yutreq", "oiuytr", "plkjhu", "jklkjh", "iuytrew", "uiuytr", "azerty", "qwerty"
    };

    Generator(int num) {
        this.base = num;
    }

    int setGridSize(int i) {
        return 4 * i;
    }

    String putShelves() {
        int size = setGridSize(base);

        TestCaseGenerator shelvesGenerator = new TestCaseGenerator(size, size);
        shelvesGenerator.putShelves(Math.min(3 * base, 25));

        StringBuilder sb = new StringBuilder();
        Grid grid = shelvesGenerator.getGrid();
        ArrayList<Object[]> shelves = shelvesGenerator.getShelves();

        w = grid.w;
        h = grid.h;
        n = shelves.size();

        String line = w + " " + h + " " + n + "\n";
        sb.append(line);

        for (int i = 0; i < n; i++) {
            Object[] shelf = shelves.get(i);
            line = shelf[0] + " " + shelf[1] + " " + shelf[2] + " " + shelf[3];
            sb.append(line);

            if (i != n - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    String putProducts() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();

        sb.append(n).append("\n");
        for (int i = 0; i < n; i++) {
            purchaseList.add(products[rand.nextInt(30)]);
            sb.append(purchaseList.get(i));
            if (i != n - 1) sb.append("\n");
        }
        return sb.toString();
    }

    String createPurchaseData() {
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        int numOfCustomer = base * 10;

        sb.append(numOfCustomer).append("\n");
        for (int i = 0; i < numOfCustomer; i++) {
            Set<String> purchases = new HashSet<>();
            int numPurchases = rand.nextInt(n) + 1;

            while (purchases.size() < numPurchases) {
                int productId = rand.nextInt(n);
                purchases.add(purchaseList.get(productId));
            }

            List<String> sortedPurchasesList = new ArrayList<>(purchases);
            Collections.sort(sortedPurchasesList);

            sb.append(numPurchases);
            for (String product : sortedPurchasesList) {
                sb.append(" ").append(product);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
