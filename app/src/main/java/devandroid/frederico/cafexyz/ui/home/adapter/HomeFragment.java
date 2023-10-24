package devandroid.frederico.cafexyz.ui.home.adapter;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import devandroid.frederico.cafexyz.R;
import devandroid.frederico.cafexyz.data.ProductModel;
import devandroid.frederico.cafexyz.ui.cart.SharedViewModel;


public class HomeFragment extends Fragment implements RecycleViewInterface {

    private SharedViewModel sharedViewModel;
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        RecyclerView productRecycle = view.findViewById(R.id.product_recycle);

        productModelArrayList = new ArrayList<ProductModel>();
        productModelArrayList.add(new ProductModel("Ovos com bacon", 25.00, R.drawable.ovos));
        productModelArrayList.add(new ProductModel("Kit café com waffle", 30.00, R.drawable.waffles));

        CardAdapter courseAdapter = new CardAdapter(getContext(), productModelArrayList, this, sharedViewModel);

        productRecycle.setAdapter(courseAdapter);

        return view;
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
    public void onItemClick(ProductModel productModel) {
        if (bottomBarVisibilityListener != null) {
            bottomBarVisibilityListener.setBottomBarVisibility(View.VISIBLE);
        }
    }
}