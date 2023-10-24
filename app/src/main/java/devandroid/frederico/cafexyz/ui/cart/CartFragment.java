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
import devandroid.frederico.cafexyz.data.ProductModel;

public class CartFragment extends Fragment {

    public CartFragment() {}
    private SharedViewModel sharedViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cart_fragment, container, false);
        RecyclerView cartRecycle = view.findViewById(R.id.cart_recycle);

        ArrayList<ProductModel> cartItems = sharedViewModel.getCartItems();

        CartAdapter cartAdapter = new CartAdapter(getContext(), cartItems);
        cartRecycle.setAdapter(cartAdapter);

        return view;
    }
}