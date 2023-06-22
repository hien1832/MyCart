package hanu.a2_2001040069.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.List;

import hanu.a2_2001040069.CartActivity;
import hanu.a2_2001040069.models.Product;

public class CartDataManager {
    private static CartDataManager instance;

    private DbHelper dbHelper;
    private SQLiteDatabase db;

    private CartDataManager(Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public static CartDataManager getInstance(Context context) {
        if (instance == null) {
            instance = new CartDataManager(context);
        }
        return instance;
    }

    public List<Product> getCart() {
        String sql = "SELECT * FROM cart";
        Cursor cursor = db.rawQuery(sql, null);
        CartItemWrapper cartItemWrapper = new CartItemWrapper(cursor);
        return cartItemWrapper.getProducts();
    }

    public boolean addToCart(Product product) {
        if (findById(product.getId()) == null) {
            product.setQuantity(product.getQuantity() + 1);
            String sql = "INSERT INTO cart (id, thumbnail, name, category, unitPrice, quantity) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindLong(1, product.getId());
            statement.bindString(2, product.getThumbnail());
            statement.bindString(3, product.getName());
            statement.bindString(4, product.getCategory());
            statement.bindLong(5, product.getUnitPrice());
            statement.bindLong(6, product.getQuantity());

            long index = statement.executeInsert();

            return index > 0;
        } else {
            return addQuantity(findById(product.getId()));
        }
    }

    public boolean addQuantity(Product product) {
        product.setQuantity(product.getQuantity() + 1);
        SQLiteStatement statement = db.compileStatement("UPDATE cart SET quantity = ? WHERE id = ?");
        statement.bindLong(1, product.getQuantity());
        statement.bindLong(2, product.getId());
        long index = statement.executeUpdateDelete();
        CartActivity.updateData();
        return index > 0;
    }

    public boolean minusQuantity(Product product) {
        if (product.getQuantity() > 1) {
            product.setQuantity(product.getQuantity() - 1);
            SQLiteStatement statement = db.compileStatement("UPDATE cart SET quantity = ? WHERE id = ?");
            statement.bindLong(1, product.getQuantity());
            statement.bindLong(2, product.getId());
            long index = statement.executeUpdateDelete();
            CartActivity.updateData();
            return index > 0;
        } else {
            return remove(product.getId());
        }
    }

    public Product findById(int id) {
        String sql = "SELECT * FROM cart WHERE id = " + id;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            CartItemWrapper cartItemWrapper = new CartItemWrapper(cursor);
            cartItemWrapper.moveToFirst();
            return cartItemWrapper.getProduct();
        } else return null;
    }

    public boolean remove(int id) {
        int index = db.delete("cart", "id = ?", new String[] {id + ""});
        CartActivity.updateData();
        return index > 0;
    }
}
