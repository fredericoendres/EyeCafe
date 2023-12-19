package devandroid.frederico.cafexyz.ui.cart;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import devandroid.frederico.cafexyz.R;

public class EditFragment extends DialogFragment {

    private SharedViewModel sharedViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_layout, container, false);
        return view;
    }

    public static EditFragment newInstance(String productTitle, double productPrice, String productImage, String observation) {
        EditFragment fragment = new EditFragment();
        Bundle args = new Bundle();
        args.putString("productTitle", productTitle);
        args.putDouble("productPrice", productPrice);
        args.putString("productImage", productImage);
        args.putString("observation", observation);
        fragment.setArguments(args);
        return fragment;
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
        super.onCreate(savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String productTitle = getArguments().getString("productTitle", "");
        double productPrice = getArguments().getDouble("productPrice", 0.0);
        String productImage = getArguments().getString("productImage", "");
        String observation = getArguments().getString("observation", "");
        EditText observationText = view.findViewById(R.id.observationText);
        TextView titleTextView = view.findViewById(R.id.product_title);
        TextView priceTextView = view.findViewById(R.id.product_price);
        ShapeableImageView imageView = view.findViewById(R.id.longPressImage);
        Button btnCancel = view.findViewById(R.id.btn_cancelar);
        Button btnOk = view.findViewById(R.id.btn_ok);
        titleTextView.setText(productTitle);
        observationText.setText(observation);
        priceTextView.setText(String.format("R$ %.2f", productPrice));
        Glide.with(requireContext())
                .load(productImage)
                .into(imageView);

        btnCancel.setOnClickListener(v -> dismiss());

        btnOk.setOnClickListener(v -> dismiss());
    }
}