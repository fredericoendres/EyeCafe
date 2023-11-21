package devandroid.frederico.cafexyz.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import devandroid.frederico.cafexyz.data.RoomDB;
import devandroid.frederico.cafexyz.data.RoomData;
import devandroid.frederico.cafexyz.ui.cart.SharedViewModel;
import devandroid.frederico.cafexyz.R;
import devandroid.frederico.cafexyz.ui.home.adapter.HomeFragment;

public class MainActivity extends AppCompatActivity implements HomeFragment.BottomBarVisibilityListener, SharedViewModel.CartListener {
    private SharedViewModel sharedViewModel;
    List<RoomData> roomDataList = new ArrayList<>();

    private View bottomBar;
    private View bottomBar2;
    private Animation fadeIn;
    private Animation fadeOut;
    @Override
    public void setBottomBarVisibility(int visibility) {
        if (bottomBar.getVisibility() != View.VISIBLE && visibility == View.VISIBLE) {
            bottomBar.startAnimation(fadeIn);
        }
        bottomBar.setVisibility(visibility);
    }

    @Override
    public void onCartUpdated(double totalValue) {
        TextView totalValueTextView = findViewById(R.id.totalBottom);
        totalValueTextView.setText(String.format("R$ %.2f", totalValue));
        int cartSize = sharedViewModel.cartSize();
        TextView itemCount = findViewById(R.id.itemTotal);
        itemCount.setText(String.format("%d items", cartSize));
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        sharedViewModel.setCartListener(this);

        ImageView nextButton = findViewById(R.id.arrowBottom);
        ImageView nextButton2 = findViewById(R.id.arrowBottom2);
        ImageView cartBottom = findViewById(R.id.cartBottom);
        TextView totalBottom2 = findViewById(R.id.totalBottom2);
        TextView itemCount = findViewById(R.id.itemTotal);
        bottomBar = findViewById(R.id.bottomBar);
        bottomBar2 = findViewById(R.id.bottomBar2);
        fadeIn = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_out);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentMain);
        NavController navController = navHostFragment.getNavController();

        cartBottom.setOnClickListener(view -> {
            navController.navigate(R.id.cartFragment);
            bottomBar.startAnimation(fadeOut);
            bottomBar2.startAnimation(fadeIn);
            bottomBar.setVisibility(View.GONE);
            bottomBar2.setVisibility(View.VISIBLE);
        });
        nextButton.setOnClickListener(view -> {
            Double totalValue = sharedViewModel.calculateTotalValue();
            int cartSize = sharedViewModel.cartSize();
            navController.navigate(R.id.cartFragment);
            bottomBar.startAnimation(fadeOut);
            bottomBar2.startAnimation(fadeIn);
            bottomBar.setVisibility(View.GONE);
            bottomBar2.setVisibility(View.VISIBLE);
            totalBottom2.setText(String.format("R$ %.2f", totalValue));
            itemCount.setText(String.format("%d items", cartSize));
        });

        nextButton2.setOnClickListener(view -> {
            navController.navigate(R.id.paymentFragment);
            bottomBar2.startAnimation(fadeOut);
            bottomBar2.setVisibility(View.GONE);
        });


    }
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.getPrimaryNavigationFragment();

        if (currentFragment instanceof NavHostFragment) {
            NavController navController = ((NavHostFragment) currentFragment).getNavController();
            int currentDestinationId = navController.getCurrentDestination().getId();

            if (currentDestinationId == R.id.cartFragment) {
                bottomBar2.startAnimation(fadeOut);
                bottomBar.startAnimation(fadeIn);
                bottomBar2.setVisibility(View.GONE);
                bottomBar.setVisibility(View.VISIBLE);
                navController.navigate(R.id.homeFragment);
            } else if (currentDestinationId == R.id.paymentFragment) {
                bottomBar2.startAnimation(fadeIn);
                bottomBar2.setVisibility(View.VISIBLE);
                navController.navigate(R.id.cartFragment);
            } else {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }



}