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
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import devandroid.frederico.cafexyz.R;
import devandroid.frederico.cafexyz.data.ApiClient;
import devandroid.frederico.cafexyz.data.ApiInterface;
import devandroid.frederico.cafexyz.data.ApiResponse;
import devandroid.frederico.cafexyz.data.ProductModel;
import devandroid.frederico.cafexyz.ui.cart.SharedViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment implements RecycleViewInterface {

    private SharedViewModel sharedViewModel;
    private ArrayList<ProductModel> productModelArrayList;
    CardAdapter cardAdapter;

    private ProgressBar progressBar;


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
        RecyclerView productRecycle = view.findViewById(R.id.product_recycle); // Refatorar com ViewBinding
        progressBar = view.findViewById(R.id.progressBar);

        productModelArrayList = new ArrayList<ProductModel>();

        cardAdapter = new CardAdapter(getContext(), productModelArrayList, this, sharedViewModel);

        productRecycle.setAdapter(cardAdapter);
        populateProduct();

        return view;
    }

    public void populateProduct(){
        progressBar.setVisibility(View.VISIBLE);
        ApiClient.getClient().create(ApiInterface.class).getProductList().enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.code() == 200) {
                    if (response.body().isStatus()) {
                        productModelArrayList.addAll(response.body().getData());
                        cardAdapter.notifyDataSetChanged();
                    }
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

            }
        });
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
    public void bottomBarVisibility() {
        if (bottomBarVisibilityListener != null) {
            bottomBarVisibilityListener.setBottomBarVisibility(View.VISIBLE);
        }
    }
}