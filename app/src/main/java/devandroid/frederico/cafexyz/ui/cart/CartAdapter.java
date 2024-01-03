package devandroid.frederico.cafexyz.ui.cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import devandroid.frederico.cafexyz.R;
import devandroid.frederico.cafexyz.data.api.ProductModel;
import devandroid.frederico.cafexyz.databinding.CardLayoutBinding;
import devandroid.frederico.cafexyz.databinding.CartRecycleBinding;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private final Context context;
    private final SharedViewModel sharedViewModel;
    private CartRecycleBinding binding;


    public CartAdapter(Context context, SharedViewModel sharedViewModel) {
        this.context = context;
        this.sharedViewModel = sharedViewModel;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_recycle, parent, false);
        return new CartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        ProductModel model = sharedViewModel.getCartItems().get(position);
        holder.binding.productTitle.setText(model.getProductTitle());
        double totalValue = model.getProductPrice();
        holder.binding.productPrice.setText(String.format("R$ %.2f", totalValue));
        Glide.with(context)
                .load(model.getProductImage())
                .into(holder.binding.productImage);
        holder.binding.editDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment navHostFragment = (NavHostFragment) ((AppCompatActivity) context).getSupportFragmentManager().findFragmentById(R.id.fragmentMain);
                NavController navController = navHostFragment.getNavController();

                DeleteDialogFragment deleteDialogFragment = new DeleteDialogFragment(context, sharedViewModel, model, CartAdapter.this);
                deleteDialogFragment.setNavController(navController);
                deleteDialogFragment.showPopupWindow(v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sharedViewModel.cartSize();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final CartRecycleBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CartRecycleBinding.bind(itemView);
        }
    }
}