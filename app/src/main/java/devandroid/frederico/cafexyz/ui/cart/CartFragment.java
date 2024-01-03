package devandroid.frederico.cafexyz.ui.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import devandroid.frederico.cafexyz.R;
import devandroid.frederico.cafexyz.data.api.ProductModel;
import devandroid.frederico.cafexyz.databinding.CartFragmentBinding;
import devandroid.frederico.cafexyz.databinding.HomeFragmentBinding;

public class CartFragment extends Fragment {

    public CartFragment() {}
    private SharedViewModel sharedViewModel;
    private CartFragmentBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = CartFragmentBinding.inflate(inflater, container, false);
        ArrayList<ProductModel> cartItems = sharedViewModel.getCartItems();
        CartAdapter cartAdapter = new CartAdapter(getContext(), sharedViewModel);
        binding.cartRecycle.setAdapter(cartAdapter);
        return binding.getRoot();
    }
}