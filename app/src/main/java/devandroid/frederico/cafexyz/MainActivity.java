package devandroid.frederico.cafexyz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String API_URL = BuildConfig.MIDTRANS_API;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView productRecycle = findViewById(R.id.product_recycle);

        ArrayList<ProductModel> productModelArrayList = new ArrayList<ProductModel>();
        productModelArrayList.add(new ProductModel("Ovos com bacon", "R$ 25,00", R.drawable.ovos));
        productModelArrayList.add(new ProductModel("Kit café com waffle", "R$ 30,00", R.drawable.waffles));

        CardAdapter courseAdapter = new CardAdapter(this, productModelArrayList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        productRecycle.setLayoutManager(linearLayoutManager);
        productRecycle.setAdapter(courseAdapter);

    }
}