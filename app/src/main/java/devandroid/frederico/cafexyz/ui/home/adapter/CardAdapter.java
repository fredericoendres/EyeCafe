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
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import devandroid.frederico.cafexyz.data.database.AppDB;
import devandroid.frederico.cafexyz.data.api.ProductModel;
import devandroid.frederico.cafexyz.R;
import devandroid.frederico.cafexyz.data.database.TransactionEntity;
import devandroid.frederico.cafexyz.databinding.CardLayoutBinding;
import devandroid.frederico.cafexyz.ui.cart.SharedViewModel;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder>
        implements LancamentoLongPress.OnDismissListener{

    private final RecycleViewInterface recycleViewInterface;
    private final Context context;
    private final ArrayList<ProductModel> productModelArrayList;
    private int[] clickCount;
    private SharedViewModel sharedViewModel;
    private List<TransactionEntity> roomDataList;
    private AppDB database;
    private boolean isFirstClick = true;
    private CardLayoutBinding binding;


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

    @Override
    public void onDismiss() {
        notifyDataSetChanged();
        if(sharedViewModel.cartSize() > 0) {
            recycleViewInterface.bottomBarVisibility();
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private final CardLayoutBinding binding;

        public ViewHolder(@NonNull View itemView, RecycleViewInterface recycleViewInterface) {
            super(itemView);
            binding = CardLayoutBinding.bind(itemView);
            itemView.setOnClickListener(view -> {
                if (recycleViewInterface != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        ProductModel productModel = productModelArrayList.get(position);
                        int productCount = sharedViewModel.productCount(productModel.getProductTitle());
                        if (productCount == 0) {
                            binding.qntItem.setVisibility(View.VISIBLE);
                        } else {
                            binding.qntItem.setVisibility(productCount > 0 ? View.VISIBLE : View.INVISIBLE);
                        }
                        productCount++;
                        binding.qntItem.setText(String.valueOf(productCount));
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
            binding.productPrice.setText(formattedPrice);
            binding.productTitle.setText(productModel.getProductTitle()+"");
            RequestOptions requestOptions = new RequestOptions();
            requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(25));
            Glide.with(context).load(productModel.getProductImage()).apply(requestOptions).into(binding.productImage);
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                int productCount = sharedViewModel.productCount(productModel.getProductTitle());
                binding.qntItem.setVisibility(productCount > 0 ? View.VISIBLE : View.INVISIBLE);
                binding.qntItem.setText(String.valueOf(productCount));
            }
        }

        private void showLancamentoDialogFragment() {
            LancamentoLongPress dialogFragment = new LancamentoLongPress();
            dialogFragment.setOnDismissListener(CardAdapter.this);
            dialogFragment.show(((FragmentActivity) context).getSupportFragmentManager(), "LancamentoDialogFragment");

            ProductModel productModel = productModelArrayList.get(getAdapterPosition());
            dialogFragment.setData(productModel.getProductTitle(), productModel.getProductPrice().toString(), productModel.getProductImage());
        }
    }

}