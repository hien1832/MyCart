package hanu.a2_2001040069.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hanu.a2_2001040069.CartActivity;
import hanu.a2_2001040069.R;
import hanu.a2_2001040069.Tools.ProductListConverter;
import hanu.a2_2001040069.db.CartDataManager;
import hanu.a2_2001040069.models.Product;

public class ProductCartAdapter extends RecyclerView.Adapter<ProductCartAdapter.ViewHolder> {

    List<Product> cartList;

    public ProductCartAdapter(List<Product> cartList) {
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.product_cart_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = cartList.get(position);
        holder.bind(product);
        CartActivity.totalPrice.setText(Integer.toString(holder.calculateTotalPrice(cartList)));
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Product product;
        ImageView productThumbnail;
        TextView productName;
        TextView productUnitPrice;
        TextView productQuantity;
        TextView sumPrice;
        ImageButton addQuantity;
        ImageButton subtractQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.productThumbnail = itemView.findViewById(R.id.productThumbnail);
            this.productName = itemView.findViewById(R.id.productName);
            this.productUnitPrice = itemView.findViewById(R.id.productUnitPrice);
            this.productQuantity = itemView.findViewById(R.id.productQuantity);
            this.sumPrice = itemView.findViewById(R.id.sumPrice);
            this.addQuantity = itemView.findViewById(R.id.addQuantity);
            this.subtractQuantity = itemView.findViewById(R.id.subtractQuantity);
        }

        private void bind(Product product) {
            this.product = product;
            ProductListConverter.bindThumbnail(product.getThumbnail(), productThumbnail);
            productName.setText(product.getName());
            productUnitPrice.setText(Integer.toString(product.getUnitPrice()));
            productQuantity.setText(Integer.toString(product.getQuantity()));
            int sumPriceInt = product.getUnitPrice() * product.getQuantity();
            sumPrice.setText(Integer.toString(sumPriceInt));

            addQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Product productInDb = CartDataManager.getInstance(CartActivity.cartActivityContext).findById(product.getId());
                    CartDataManager.getInstance(CartActivity.cartActivityContext).addQuantity(productInDb);
                }
            });

            subtractQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Product productInDb = CartDataManager.getInstance(CartActivity.cartActivityContext).findById(product.getId());
                    CartDataManager.getInstance(CartActivity.cartActivityContext).minusQuantity(productInDb);
                }
            });
        }

        private int calculateTotalPrice(List<Product> productList) {
            int totalPrice = 0;
            for (Product product : productList) {
                int sumPrice = product.getUnitPrice() * product.getQuantity();
                totalPrice += sumPrice;
            }
            return totalPrice;
        }
    }
}
