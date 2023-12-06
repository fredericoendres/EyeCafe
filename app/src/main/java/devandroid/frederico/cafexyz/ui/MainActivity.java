package devandroid.frederico.cafexyz.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import devandroid.frederico.cafexyz.data.api.AlarmReceiver;
import devandroid.frederico.cafexyz.data.api.ApiService;
import devandroid.frederico.cafexyz.data.api.ApiWorker;
import devandroid.frederico.cafexyz.data.database.AppDB;
import devandroid.frederico.cafexyz.data.database.TransactionEntity;
import devandroid.frederico.cafexyz.ui.cart.SharedViewModel;
import devandroid.frederico.cafexyz.R;
import devandroid.frederico.cafexyz.ui.home.adapter.HomeFragment;

public class MainActivity extends AppCompatActivity implements HomeFragment.BottomBarVisibilityListener, SharedViewModel.CartListener {
    private SharedViewModel sharedViewModel;
    List<TransactionEntity> roomDataList = new ArrayList<>();
    WorkManager workManager = WorkManager.getInstance(MainActivity.this);
    AppDB database;
    private View bottomBar;
    private View bottomBar2;
    private Animation fadeIn;
    private Animation fadeOut;

    private final BroadcastReceiver toastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null && intent.getAction().equals(ApiService.ACTION_SHOW_TOAST)) {
                String message = intent.getStringExtra(ApiService.EXTRA_TOAST_MESSAGE);
                showToast(message);
            }
        }
    };
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
        registerReceiver(toastReceiver, new IntentFilter(ApiService.ACTION_SHOW_TOAST));
        scheduleApiRequests();
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        sharedViewModel.setCartListener(this);
        database = AppDB.getInstance(this);
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

        PeriodicWorkRequest workRequest = new PeriodicWorkRequest.Builder(
                ApiWorker.class, 15, TimeUnit.MINUTES).build();
        workManager.enqueue(workRequest);

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
                onCartUpdated(sharedViewModel.calculateTotalValue());
                bottomBar2.startAnimation(fadeOut);
                bottomBar2.setVisibility(View.GONE);
                if (sharedViewModel.cartSize() > 0) {
                    bottomBar.startAnimation(fadeIn);
                    bottomBar.setVisibility(View.VISIBLE);
                }
                navController.navigate(R.id.homeFragment);
            } else if (currentDestinationId == R.id.paymentFragment) {
                onCartUpdated(sharedViewModel.calculateDiscountedTotalValue());
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

    private void scheduleApiRequests() {
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        if (alarmManager != null) {
            long intervalMillis = 60 * 1000;
            long triggerTime = SystemClock.elapsedRealtime() + intervalMillis;

            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, intervalMillis, pendingIntent);
        }
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(toastReceiver);
    }

}