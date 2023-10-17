package devandroid.frederico.cafexyz.ui.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import devandroid.frederico.cafexyz.data.ProductModel;
import devandroid.frederico.cafexyz.R;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private final RecycleViewInterface recycleViewInterface;
    private final Context context;
    private final ArrayList<ProductModel> productModelArrayList;
    private int[] clickCount;


    public CardAdapter(Context context, ArrayList<ProductModel> productModelArrayList, RecycleViewInterface recycleViewInterface) {
        this.context = context;
        this.productModelArrayList = productModelArrayList;
        this.recycleViewInterface = recycleViewInterface;
        this.clickCount = new int[productModelArrayList.size()];
    }

    @NonNull
    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new ViewHolder(view, recycleViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull CardAdapter.ViewHolder holder, int position) {
        ProductModel model = productModelArrayList.get(position);
        holder.productTitle.setText(model.getProductTitle());
        double totalValue = model.getProductPrice();
        holder.productPrice.setText(String.format("R$ %.2f", totalValue));
        holder.productImage.setImageResource(model.getProductImage());
    }

    @Override
    public int getItemCount() {
        return productModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView productTitle;
        private final TextView productPrice;
        private final ImageView productImage;
        Button qntBtn;

        public ViewHolder(@NonNull View itemView, RecycleViewInterface recycleViewInterface) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productTitle = itemView.findViewById(R.id.product_title);
            productPrice = itemView.findViewById(R.id.product_price);
            qntBtn = itemView.findViewById(R.id.qnt_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recycleViewInterface != null) {
                        int position = getAdapterPosition();
                        qntBtn.setVisibility(View.VISIBLE);
                        if(position != RecyclerView.NO_POSITION) {
                            clickCount[position]++;
                            qntBtn.setText(String.valueOf(clickCount[position]));
                            recycleViewInterface.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

}
