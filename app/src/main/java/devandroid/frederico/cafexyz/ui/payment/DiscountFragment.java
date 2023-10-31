package devandroid.frederico.cafexyz.ui.payment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import devandroid.frederico.cafexyz.R;
import devandroid.frederico.cafexyz.ui.cart.SharedViewModel;
import devandroid.frederico.cafexyz.ui.home.adapter.DiscountClickListener;

public class DiscountFragment extends DialogFragment implements View.OnClickListener {

    private StringBuilder discountAmount = new StringBuilder();
    private TextView discountValueTextView;

    private SharedViewModel sharedViewModel;
    private DiscountClickListener discountClickListener;


    public void setCallback (DiscountClickListener discountClickListener) {
        this.discountClickListener = discountClickListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.discount_layout, container, false);

        discountValueTextView = view.findViewById(R.id.discount_value);

        view.findViewById(R.id.qnt_sub0).setOnClickListener(this);
        view.findViewById(R.id.qnt_sub1).setOnClickListener(this);
        view.findViewById(R.id.qnt_sub2).setOnClickListener(this);
        view.findViewById(R.id.qnt_sub3).setOnClickListener(this);
        view.findViewById(R.id.qnt_sub4).setOnClickListener(this);
        view.findViewById(R.id.qnt_sub5).setOnClickListener(this);
        view.findViewById(R.id.qnt_sub6).setOnClickListener(this);
        view.findViewById(R.id.qnt_sub7).setOnClickListener(this);
        view.findViewById(R.id.qnt_sub8).setOnClickListener(this);
        view.findViewById(R.id.qnt_sub9).setOnClickListener(this);
        view.findViewById(R.id.qnt_sub_delete).setOnClickListener(this);
        view.findViewById(R.id.qnt_sub_limpar).setOnClickListener(this);
        view.findViewById(R.id.btn_cancelar).setOnClickListener(this);
        view.findViewById(R.id.btn_ok).setOnClickListener(this);

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
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.qnt_sub_delete) {
            if (discountAmount.length() > 0) {
                discountAmount.deleteCharAt(discountAmount.length() - 1);
            }
        } else if (id == R.id.qnt_sub_limpar) {
            discountAmount.setLength(0);
        } else if (id == R.id.btn_cancelar) {
            dismiss();
        } else if (id == R.id.btn_ok) {
                if (discountAmount.length() > 0) {
                    sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
                    double discount = Double.parseDouble(discountAmount.toString());
                    sharedViewModel.applyDiscount(discount);
                    discountClickListener.discountClick();
                }
            dismiss();
        }else {
            if (discountAmount.length() < 2) {
                discountAmount.append(((Button) view).getText());
            } else if (discountAmount.length() == 2) {
                discountAmount = new StringBuilder("100");
            }
        }
        updateDiscountValue(discountAmount.toString());
    }

    private void updateDiscountValue(String discount) {
        discountValueTextView.setText(discount + "%");
    }
}

