package ie03.phase3.task7;

import ie03.phase1.task2.PurchaseDataEnhanced;

import java.util.ArrayList;

public class ProductsManager extends PurchaseDataEnhanced {
    private final ArrayList<String> products = new ArrayList<>();
    public void addProduct(String product) {
        products.add(product);
    }
    public ArrayList<String> getProducts() {
        return products;
    }
}
