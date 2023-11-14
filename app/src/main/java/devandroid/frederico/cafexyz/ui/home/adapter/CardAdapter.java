package devandroid.frederico.cafexyz.ui.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import devandroid.frederico.cafexyz.data.ProductModel;
import devandroid.frederico.cafexyz.R;
import devandroid.frederico.cafexyz.ui.cart.SharedViewModel;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private final RecycleViewInterface recycleViewInterface;
    private final Context context;
    private final ArrayList<ProductModel> productModelArrayList;
    private int[] clickCount;

    private SharedViewModel sharedViewModel;



    public CardAdapter(Context context, ArrayList<ProductModel> productModelArrayList, RecycleViewInterface recycleViewInterface, SharedViewModel sharedViewModel) {
        this.context = context;
        this.sharedViewModel = sharedViewModel;
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
        holder.bind(productModelArrayList.get(position));
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
            itemView.setOnClickListener(view -> {
                if (recycleViewInterface != null) {
                    int position = getAdapterPosition();
                    qntBtn.setVisibility(View.VISIBLE);
                    if(position != RecyclerView.NO_POSITION) {
                        ProductModel productModel = productModelArrayList.get(position);
                        qntBtn.setText(String.valueOf(sharedViewModel.productCount(productModel.getProductTitle())));
                        recycleViewInterface.bottomBarVisibility();
                        sharedViewModel.addToCart(productModel);
                        sharedViewModel.notifyCartUpdate();
                    }
                }
            });

            itemView.setOnLongClickListener(v -> {
                showLancamentoDialogFragment();
                return true;
            });
        }

        public void bind(ProductModel productModel){
            double price = productModel.getProductPrice();
            String formattedPrice = String.format("R$ %.2f", price);
            productPrice.setText(formattedPrice);
            productTitle.setText(productModel.getProductTitle()+"");
            Glide.with(context).load(productModel.getProductImage()).into(productImage);
        }

        private void showLancamentoDialogFragment() {
            LancamentoDialogFragment dialogFragment = new LancamentoDialogFragment();
            dialogFragment.show(((FragmentActivity) context).getSupportFragmentManager(), "LancamentoDialogFragment");

        }
    }

}