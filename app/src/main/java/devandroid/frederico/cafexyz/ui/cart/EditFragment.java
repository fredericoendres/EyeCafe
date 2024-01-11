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
import devandroid.frederico.cafexyz.databinding.EditLayoutBinding;
import devandroid.frederico.cafexyz.databinding.HomeFragmentBinding;

public class EditFragment extends DialogFragment {

    private SharedViewModel sharedViewModel;
    private EditLayoutBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = EditLayoutBinding.inflate(inflater, container, false);
        return binding.getRoot();
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
        binding.productTitle.setText(productTitle);
        binding.observationText.setText(observation);
        binding.productPrice.setText(String.format("R$ %.2f", productPrice));
        Glide.with(requireContext())
                .load(productImage).circleCrop()
                .into(binding.longPressImage);

        binding.btnCancelar.setOnClickListener(v -> dismiss());

        binding.btnOk.setOnClickListener(v -> dismiss());
    }
}