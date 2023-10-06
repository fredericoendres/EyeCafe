package devandroid.frederico.cafexyz.ui.home.adapter;

import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

import devandroid.frederico.cafexyz.R;
import devandroid.frederico.cafexyz.data.ProductModel;
import devandroid.frederico.cafexyz.ui.MainActivity;


public class HomeFragment extends Fragment {

    private NavController navController;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        RecyclerView productRecycle = view.findViewById(R.id.product_recycle);


        Button button = view.findViewById(R.id.buttonhome);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(requireActivity(), R.id.paymentFragment);
            }
        });


        ArrayList<ProductModel> productModelArrayList = new ArrayList<ProductModel>();
        productModelArrayList.add(new ProductModel("Ovos com bacon", "R$ 25,00", R.drawable.ovos));
        productModelArrayList.add(new ProductModel("Kit café com waffle", "R$ 30,00", R.drawable.waffles));

        CardAdapter courseAdapter = new CardAdapter(getContext(), productModelArrayList);

        productRecycle.setAdapter(courseAdapter);

        return view;
    }
}