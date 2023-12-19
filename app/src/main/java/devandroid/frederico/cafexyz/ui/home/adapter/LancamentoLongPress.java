package devandroid.frederico.cafexyz.ui.home.adapter;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import devandroid.frederico.cafexyz.R;
import devandroid.frederico.cafexyz.data.api.ProductModel;
import devandroid.frederico.cafexyz.ui.cart.SharedViewModel;

public class LancamentoLongPress extends DialogFragment {

    public LancamentoLongPress() {
    }

    public interface OnDismissListener {
        void onDismiss();
    }

    public void setOnDismissListener(OnDismissListener listener) {
        this.onDismissListener = listener;
    }

    private OnDismissListener onDismissListener;
    private SharedViewModel sharedViewModel;
    private String title;
    private String price;
    private String imageUrl;
    private int counter = 0;


    public void setData(String title, String price, String imageUrl) {
        this.title = title;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lancamento_longpress, container, false);


        return view;
    }



    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null) {
                window.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.color.transparent));
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setStyle(STYLE_NORMAL, R.style.DialogFragmentTheme);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView titleTextView = view.findViewById(R.id.product_title);
        TextView priceTextView = view.findViewById(R.id.product_price);
        ImageView imageView = view.findViewById(R.id.longPressImage);

        titleTextView.setText(title);
        priceTextView.setText(price);
        Glide.with(requireContext()).load(imageUrl).into(imageView);

        EditText observationText = view.findViewById(R.id.observationText);
        Button btnCancel = view.findViewById(R.id.btn_cancelar);
        Button btnOk = view.findViewById(R.id.btn_ok);
        Button qntAdd = view.findViewById(R.id.qnt_add);
        Button qntSub = view.findViewById(R.id.qnt_sub_limpar);
        TextView qntCounter = view.findViewById(R.id.qnt_counter);
        TextView totalText = view.findViewById(R.id.total_text);

        qntAdd.setOnClickListener(v -> {
            counter++;
            updateCounter(qntCounter, totalText);
        });

        qntSub.setOnClickListener(v -> {
            if (counter > 0) {
                counter--;
                updateCounter(qntCounter, totalText);
            }
        });

        btnCancel.setOnClickListener(v -> dismiss());

        btnOk.setOnClickListener(v -> {
            ProductModel productModel = new ProductModel(title, Double.parseDouble(price.replace("R$ ", "").replace(",", ".")), imageUrl, observationText.getText().toString());
            sharedViewModel.addToCart(productModel, counter);
            dismiss();
            if (onDismissListener != null) {
                onDismissListener.onDismiss();
            }
        });

    }

    private void updateCounter(TextView qntCounter, TextView totalText) {
        qntCounter.setText(String.valueOf(counter));
        double productPrice = Double.parseDouble(price.replace("R$ ", "").replace(",", "."));
        double totalAmount = counter * productPrice;
        String formattedTotal = String.format("Total: R$ %.2f", totalAmount);
        totalText.setText(formattedTotal);
    }

}