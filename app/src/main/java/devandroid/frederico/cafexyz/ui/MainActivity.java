package devandroid.frederico.cafexyz.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import devandroid.frederico.cafexyz.ui.cart.SharedViewModel;
import devandroid.frederico.cafexyz.R;
import devandroid.frederico.cafexyz.ui.home.adapter.HomeFragment;

public class MainActivity extends AppCompatActivity implements HomeFragment.BottomBarVisibilityListener, SharedViewModel.CartListener {
    private SharedViewModel sharedViewModel;
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
        TextView itemCount2 = findViewById(R.id.itemTotal2);
        bottomBar = findViewById(R.id.bottomBar);
        bottomBar2 = findViewById(R.id.bottomBar2);
        fadeIn = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_out);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentMain);
        NavController navController = navHostFragment.getNavController();


        cartBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.cartFragment);
                bottomBar.startAnimation(fadeOut);
                bottomBar2.startAnimation(fadeIn);
                bottomBar.setVisibility(View.GONE);
                bottomBar2.setVisibility(View.VISIBLE);
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double totalValue = sharedViewModel.calculateTotalValue();
                int cartSize = sharedViewModel.cartSize();
                navController.navigate(R.id.cartFragment);
                bottomBar.startAnimation(fadeOut);
                bottomBar2.startAnimation(fadeIn);
                bottomBar.setVisibility(View.GONE);
                bottomBar2.setVisibility(View.VISIBLE);
                totalBottom2.setText(String.format("R$ %.2f", totalValue));
                itemCount2.setText(String.format("itens", cartSize));

            }
        });

        nextButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.paymentFragment);
                bottomBar2.startAnimation(fadeOut);
                bottomBar2.setVisibility(View.GONE);
            }
        });


    }


}