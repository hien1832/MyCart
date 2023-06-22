package hanu.a2_2001040069.Tools;


import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import androidx.core.os.HandlerCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import hanu.a2_2001040069.models.Product;

public class ProductListConverter {

    public static void ConvertProductList(List<Product> productList) {
        String jsonUrl = "https://hanu-congnv.github.io/mpr-cart-api/products.json";
        String json = JsonTool.loadJSON(jsonUrl);
        try {
            JSONArray jsonProducts = new JSONArray(json);
            for (int i = 0; i < jsonProducts.length(); i++) {
                JSONObject jsonProduct = jsonProducts.getJSONObject(i);
                int id = jsonProduct.getInt("id");
                String name = jsonProduct.getString("name");
                String thumbnail = jsonProduct.getString("thumbnail");
                String category = jsonProduct.getString("category");
                int unitPrice = jsonProduct.getInt("unitPrice");
                Product product = new Product(id, thumbnail, name, category, unitPrice);
                productList.add(product);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void bindThumbnail(String thumbnailURL, ImageView thumbnailView) {
        Handler handler = HandlerCompat.createAsync(Looper.getMainLooper());
        Constants.executor.execute(new Runnable() {
            @Override
            public void run() {
                Bitmap thumbnailBitmap = ImageTool.downloadImage(thumbnailURL);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        thumbnailView.setImageBitmap(thumbnailBitmap);
                    }
                });
            }
        });

    }
}
