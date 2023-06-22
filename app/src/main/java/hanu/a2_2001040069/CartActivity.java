package hanu.a2_2001040069;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import hanu.a2_2001040069.Tools.ProductListConverter;
import hanu.a2_2001040069.adapters.ProductCartAdapter;
import hanu.a2_2001040069.db.CartDataManager;
import hanu.a2_2001040069.models.Product;

public class CartActivity extends AppCompatActivity {

    public static RecyclerView cartRecyclerView;
    public static ProductCartAdapter productCartAdapter;
    public static TextView totalPrice;
    public static Context cartActivityContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cartActivityContext = this;

        totalPrice = findViewById(R.id.totalPrice);

        CartDataManager cartDataManager = CartDataManager.getInstance(CartActivity.this);
        ArrayList<Product> products = (ArrayList<Product>) cartDataManager.getCart();

        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        productCartAdapter = new ProductCartAdapter(products);
        cartRecyclerView.setAdapter(productCartAdapter);

    }

    public static void updateData(){
        CartDataManager cartDataManager = CartDataManager.getInstance(cartActivityContext);
        ArrayList<Product> products = (ArrayList<Product>) cartDataManager.getCart();
        productCartAdapter = new ProductCartAdapter(products);
        cartRecyclerView.setAdapter(productCartAdapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.cart, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.cart) {
            Toast.makeText(this, R.string.already_in_cart, Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}