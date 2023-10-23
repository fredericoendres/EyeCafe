package devandroid.frederico.cafexyz.ui.home.adapter;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import devandroid.frederico.cafexyz.R;
import devandroid.frederico.cafexyz.data.ProductModel;
import devandroid.frederico.cafexyz.ui.MainActivity;
import devandroid.frederico.cafexyz.ui.cart.CartFragment;
import devandroid.frederico.cafexyz.ui.cart.CartViewModel;
import devandroid.frederico.cafexyz.ui.payment.PaymentFragment;


public class HomeFragment extends Fragment implements RecycleViewInterface {

    private CartViewModel cartViewModel;
    private NavController navController;
    private ArrayList<ProductModel> cartItems = new ArrayList<>();
    private ArrayList<ProductModel> productModelArrayList;

    private BottomBarVisibilityListener bottomBarVisibilityListener;

    public HomeFragment() {
        // Required empty public constructor
    }


    public interface BottomBarVisibilityListener {
        void setBottomBarVisibility(int visibility);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cartViewModel = new ViewModelProvider(requireActivity()).get(CartViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        RecyclerView productRecycle = view.findViewById(R.id.product_recycle);

        /*NavController navController = NavHostFragment.findNavController(this);

        Button button = view.findViewById(R.id.buttonhome);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //o erro anterior estava acontecendo pois o button ta dentro do fragment, e nao na activity, sendo
                //assim eu tinha que declarar o findNavController pra procurar dentro desse fragment
                navController.navigate(R.id.cartFragment);
            }
        });*/

        productModelArrayList = new ArrayList<ProductModel>();
        productModelArrayList.add(new ProductModel("Ovos com bacon", "R$ 25,00", R.drawable.ovos));
        productModelArrayList.add(new ProductModel("Kit café com waffle", "R$ 30,00", R.drawable.waffles));

        CardAdapter courseAdapter = new CardAdapter(getContext(), productModelArrayList, this);

        productRecycle.setAdapter(courseAdapter);

        return view;
    }

    private void addToCart(ProductModel productModel) {
        cartItems.add(productModel);
        cartViewModel.setCartItems(cartItems);
        }




    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BottomBarVisibilityListener) {
            bottomBarVisibilityListener = (BottomBarVisibilityListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement BottomBarVisibilityListener");
        }
    }

    @Override
    public void onItemClick(int position) {
        ProductModel productModel = productModelArrayList.get(position);
        addToCart(productModel);
        if (bottomBarVisibilityListener != null) {
            bottomBarVisibilityListener.setBottomBarVisibility(View.VISIBLE);
        }
    }
}