package devandroid.frederico.cafexyz.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import devandroid.frederico.cafexyz.data.api.ApiService;
import devandroid.frederico.cafexyz.data.database.AppDB;
import devandroid.frederico.cafexyz.data.database.TransactionEntity;
import devandroid.frederico.cafexyz.databinding.ActivityMainBinding;
import devandroid.frederico.cafexyz.ui.cart.SharedViewModel;
import devandroid.frederico.cafexyz.R;
import devandroid.frederico.cafexyz.ui.home.adapter.HomeFragment;
import devandroid.frederico.cafexyz.ui.payment.PaymentFragmentListener;

public class MainActivity extends AppCompatActivity implements HomeFragment.BottomBarVisibilityListener, SharedViewModel.CartListener, PaymentFragmentListener {
    private SharedViewModel sharedViewModel;
    List<TransactionEntity> roomDataList = new ArrayList<>();
    AppDB database;
    private ActivityMainBinding binding;
    private Animation fadeIn;
    private Animation fadeOut;
    @Override
    public void setBottomBarVisibility(int visibility) {
        if (binding.bottomBar.getVisibility() != View.VISIBLE && visibility == View.VISIBLE) {
            binding.bottomBar.startAnimation(fadeIn);
        }
        binding.bottomBar.setVisibility(visibility);
    }



    @Override
    public void onCartUpdated(double totalValue) {
        binding.totalBottom.setText(String.format("R$ %.2f", totalValue));
        int cartSize = sharedViewModel.cartSize();
        binding.itemTotal.setText(String.format("%d items", cartSize));
    }

    public boolean foregroundServiceRunning(){
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service : activityManager.getRunningServices(Integer.MAX_VALUE)){
            if(ApiService.class.getName().equals(service.service.getClassName())){
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        sharedViewModel.setCartListener(this);
        database = AppDB.getInstance(this);

        Intent serviceIntent = new Intent(this, ApiService.class);
        startForegroundService(serviceIntent);
        foregroundServiceRunning();
        fadeIn = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_out);
        sharedViewModel.getTotalValueLiveData().observe(this, totalValue -> {
            binding.totalBottom2.setText(String.format("R$ %.2f", totalValue));
        });

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentMain);
        NavController navController = navHostFragment.getNavController();
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            int destinationId = destination.getId();
            if (sharedViewModel.cartSize() > 0) {
                if (destinationId == R.id.homeFragment) {
                    binding.totalBottom.setText(String.format("R$ %.2f", sharedViewModel.calculateTotalValue()));
                    binding.itemTotal.setText(String.format("%d items", sharedViewModel.cartSize()));
                    binding.bottomBar.setVisibility(View.VISIBLE);
                } else if (destinationId == R.id.cartFragment) {
                    binding.totalBottom2.setText(String.format("R$ %.2f", sharedViewModel.calculateTotalValue()));
                    binding.bottomBar.setVisibility(View.GONE);
                    binding.bottomBar2.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.cartBottom.setOnClickListener(view -> {
            Double totalValue = sharedViewModel.calculateTotalValue();
            int cartSize = sharedViewModel.cartSize();
            binding.topBar.startAnimation(fadeOut);
            binding.topBar.setVisibility(View.GONE);
            binding.topBarCart.startAnimation(fadeIn);
            binding.topBarCart.setVisibility(View.VISIBLE);
            navController.navigate(R.id.cartFragment);
            binding.bottomBar.startAnimation(fadeOut);
            binding.bottomBar2.startAnimation(fadeIn);
            binding.bottomBar.setVisibility(View.GONE);
            binding.bottomBar2.setVisibility(View.VISIBLE);
            binding.totalBottom2.setText(String.format("R$ %.2f", totalValue));
            binding.itemTotal.setText(String.format("%d items", cartSize));
        });
        binding.arrowBottom.setOnClickListener(view -> {
            int cartSize = sharedViewModel.cartSize();
            binding.topBar.setVisibility(View.GONE);
            binding.topBarCart.setVisibility(View.VISIBLE);
            navController.navigate(R.id.cartFragment);
            binding.bottomBar.startAnimation(fadeOut);
            binding.bottomBar2.startAnimation(fadeIn);
            binding.bottomBar.setVisibility(View.GONE);
            binding.bottomBar2.setVisibility(View.VISIBLE);
            binding.totalBottom2.setText(String.format("R$ %.2f", sharedViewModel.calculateTotalValue()));
            binding.itemTotal.setText(String.format("%d items", cartSize));
        });

        binding.arrowBottom2.setOnClickListener(view -> {
            navController.navigate(R.id.paymentFragment);
            binding.topBarCart.setVisibility(View.GONE);
            binding.topBarPayment.setVisibility(View.VISIBLE);
            binding.bottomBar2.startAnimation(fadeOut);
            binding.bottomBar2.setVisibility(View.GONE);
        });

        binding.imageLimpar.setOnClickListener(view -> {
            sharedViewModel.limparCart();
            binding.bottomBar2.setVisibility(View.GONE);
            binding.topBarCart.setVisibility(View.GONE);
            binding.topBar.setVisibility(View.VISIBLE);
            navController.navigate(R.id.homeFragment);
        });

        binding.imageLimparPayment.setOnClickListener(view -> {
            sharedViewModel.limparCart();
            binding.topBarPayment.setVisibility(View.GONE);
            binding.topBar.setVisibility(View.VISIBLE);
            navController.navigate(R.id.homeFragment);
        });

        binding.returnButton.setOnClickListener(view -> {
            onCartUpdated(sharedViewModel.calculateTotalValue());
            binding.bottomBar2.startAnimation(fadeOut);
            binding.bottomBar2.setVisibility(View.GONE);
            binding.topBar.setVisibility(View.VISIBLE);
            binding.topBarCart.setVisibility(View.GONE);
            binding.topBar.setVisibility(View.VISIBLE);
            binding.topBarCart.setVisibility(View.GONE);
            if (sharedViewModel.cartSize() > 0) {
                binding.bottomBar.startAnimation(fadeIn);
                binding.bottomBar.setVisibility(View.VISIBLE);
            }
            navController.navigate(R.id.homeFragment);
        });

        binding.returnButtonPayment.setOnClickListener(view -> {
            onCartUpdated(sharedViewModel.calculateDiscountedTotalValue());
            binding.bottomBar2.startAnimation(fadeIn);
            binding.bottomBar2.setVisibility(View.VISIBLE);
            binding.topBarCart.setVisibility(View.VISIBLE);
            binding.topBarPayment.setVisibility(View.GONE);
            navController.navigate(R.id.cartFragment);
            binding.topBarPayment.setVisibility(View.GONE);
            binding.topBarCart.setVisibility(View.VISIBLE);
        });
    }

    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.getPrimaryNavigationFragment();

        if (currentFragment instanceof NavHostFragment) {
            NavController navController = ((NavHostFragment) currentFragment).getNavController();
            int currentDestinationId = navController.getCurrentDestination().getId();

            if (currentDestinationId == R.id.cartFragment) {
                onCartUpdated(sharedViewModel.calculateTotalValue());
                binding.bottomBar2.startAnimation(fadeOut);
                binding.bottomBar2.setVisibility(View.GONE);
                binding.topBar.setVisibility(View.VISIBLE);
                binding.topBarCart.setVisibility(View.GONE);
                if (sharedViewModel.cartSize() > 0) {
                    binding.bottomBar.startAnimation(fadeIn);
                    binding.bottomBar.setVisibility(View.VISIBLE);
                }
                navController.navigate(R.id.homeFragment);
            } else if (currentDestinationId == R.id.paymentFragment) {
                onCartUpdated(sharedViewModel.calculateDiscountedTotalValue());
                binding.bottomBar2.startAnimation(fadeIn);
                binding.bottomBar2.setVisibility(View.VISIBLE);
                binding.topBarCart.setVisibility(View.VISIBLE);
                binding.topBarPayment.setVisibility(View.GONE);
                navController.navigate(R.id.cartFragment);
            } else {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPaymentFragmentFinish() {
        binding.topBarPayment.setVisibility(View.GONE);
        binding.topBar.setVisibility(View.VISIBLE);
    }
}