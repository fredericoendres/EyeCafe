package devandroid.frederico.cafexyz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<ProductModel> productModelArrayList;

    public CardAdapter(Context context, ArrayList<ProductModel> productModelArrayList) {
        this.context = context;
        this.productModelArrayList = productModelArrayList;
    }

    @NonNull
    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardAdapter.ViewHolder holder, int position) {
        ProductModel model = productModelArrayList.get(position);
        holder.productTitle.setText(model.getProductTitle());
        holder.productPrice.setText(model.getProductPrice());
        holder.productImage.setImageResource(model.getProductImage());
    }

    @Override
    public int getItemCount() {
        return productModelArrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView productTitle;
        private final TextView productPrice;
        private final ImageView productImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productTitle = itemView.findViewById(R.id.product_title);
            productPrice = itemView.findViewById(R.id.product_price);
        }
    }

}
