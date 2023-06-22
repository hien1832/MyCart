package hanu.a2_2001040069.adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.List;

import hanu.a2_2001040069.CartActivity;
import hanu.a2_2001040069.Tools.ProductListConverter;
import hanu.a2_2001040069.db.CartDataManager;
import hanu.a2_2001040069.models.Product;
import hanu.a2_2001040069.R;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductHolder> {

    private List<Product> products;
    Context context;

    public ProductListAdapter(List<Product> products) {
        this.products = products;
    }

    public void showSearchedList(List<Product> searchedList) {
        this.products = searchedList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.product_list_item, parent, false);

        return new ProductHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Product product = this.products.get(position);
        holder.bind(product);

    }

    @Override
    public int getItemCount() {
        return this.products.size();
    }

    public class ProductHolder extends RecyclerView.ViewHolder {
        private Product product;
        private ImageView thumbnailView;
        private TextView nameView;
        private TextView unitPriceView;
        private ImageButton addToCart;
        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            thumbnailView = itemView.findViewById(R.id.thumbnailView);
            nameView = itemView.findViewById(R.id.nameView);
            unitPriceView = itemView.findViewById(R.id.unitPriceView);
            addToCart = itemView.findViewById(R.id.addToCart);
        }

        private void bind(Product product) {
            this.product = product;
            nameView.setText(product.getName());
            unitPriceView.setText(Integer.toString(product.getUnitPrice()));
            ProductListConverter.bindThumbnail(product.getThumbnail(), thumbnailView);
            addToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CartDataManager.getInstance(CartActivity.cartActivityContext).addToCart(product);
                    Toast.makeText(context, R.string.added_to_cart, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
