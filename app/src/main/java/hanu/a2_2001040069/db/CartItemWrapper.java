package hanu.a2_2001040069.db;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.ArrayList;
import java.util.List;

import hanu.a2_2001040069.models.Product;

public class CartItemWrapper extends CursorWrapper {
    public CartItemWrapper(Cursor cursor) {
        super(cursor);
    }

    public Product getProduct() {
        int id = getInt(getColumnIndex("id"));
        String thumbnail = getString(getColumnIndex("thumbnail"));
        String name = getString(getColumnIndex("name"));
        String category = getString(getColumnIndex("category"));
        int unitPrice = getInt(getColumnIndex("unitPrice"));
        int quantity = getInt(getColumnIndex("quantity"));

        Product product = new Product(id, thumbnail, name, category, unitPrice, quantity);
        return product;
    }

    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        moveToFirst();
        while (!isAfterLast()) {
            Product product = getProduct();
            products.add(product);
            moveToNext();
        }
        return products;
    }
}
