package devandroid.frederico.cafexyz.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

import devandroid.frederico.cafexyz.ui.home.adapter.CardAdapter;
import devandroid.frederico.cafexyz.data.ProductModel;
import devandroid.frederico.cafexyz.R;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView productRecycle = findViewById(R.id.product_recycle);

        ArrayList<ProductModel> productModelArrayList = new ArrayList<ProductModel>();
        productModelArrayList.add(new ProductModel("Ovos com bacon", "R$ 25,00", R.drawable.ovos));
        productModelArrayList.add(new ProductModel("Kit café com waffle", "R$ 30,00", R.drawable.waffles));

        CardAdapter courseAdapter = new CardAdapter(this, productModelArrayList);

        productRecycle.setAdapter(courseAdapter);

    }
}