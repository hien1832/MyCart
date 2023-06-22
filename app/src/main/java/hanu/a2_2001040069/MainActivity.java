package hanu.a2_2001040069;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import hanu.a2_2001040069.Tools.ProductListConverter;
import hanu.a2_2001040069.adapters.ProductListAdapter;
import hanu.a2_2001040069.models.Product;

public class MainActivity extends AppCompatActivity {
    private EditText searchInput;
    private ImageButton searchButton;
    private RecyclerView productRecyclerView;
    private ProductListAdapter productListAdapter;
    private List<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //initialize view elements
        productRecyclerView = findViewById(R.id.productRecyclerView);
        searchInput = findViewById(R.id.searchInput);
        searchButton = findViewById(R.id.searchButton);

        //set product list
        products = new ArrayList<>();
        ProductListConverter.ConvertProductList(products);

        //inflate product list
        productRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        productListAdapter = new ProductListAdapter(products);
        productRecyclerView.setAdapter(productListAdapter);

        //handle search function
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyword = searchInput.getText().toString().toLowerCase();
                search(keyword);
            }
        });
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
            Intent intent = new Intent(MainActivity.this, CartActivity.class);
            startActivity(intent);
        }
        return false;
    }

    private void search(String keyword) {
        List<Product> searchedList = new ArrayList<>();
        if (keyword.isEmpty()) {
            searchedList = products;
        } else {
            for(Product product : products ) {
                if (product.getName().toLowerCase().contains(keyword)) {
                    searchedList.add(product);
                }
            }
        }
        if (searchedList.isEmpty()) {
            Toast.makeText(this, R.string.search_not_found, Toast.LENGTH_SHORT).show();
        } else {
            productListAdapter.showSearchedList(searchedList);
        }
    }
}