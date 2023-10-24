package devandroid.frederico.cafexyz.ui.payment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import devandroid.frederico.cafexyz.R;
import devandroid.frederico.cafexyz.ui.cart.SharedViewModel;


public class PaymentFragment extends Fragment {

    private SharedViewModel sharedViewModel;

    public PaymentFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.payment_fragment, container, false);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        TextView totalValueTextView = view.findViewById(R.id.total_value);
        double totalValue = sharedViewModel.calculateTotalValue();
        totalValueTextView.setText(String.format("R$ %.2f", totalValue));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageButton buttonAdd = view.findViewById(R.id.qnt_add0);
        ImageButton buttonRemove = view.findViewById(R.id.qnt_remove0);
        ConstraintLayout constraintLayout2 = view.findViewById(R.id.constraintLayout2);
        TextView valueTextView = view.findViewById(R.id.value_text);
        View midBar0 = view.findViewById(R.id.mid_bar0);
        Button btnDiscount = view.findViewById(R.id.btn_discount);

        btnDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DiscountFragment dialogFragment = new DiscountFragment();
                dialogFragment.show(getParentFragmentManager(), "CustomDialog");
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAdd.setVisibility(View.GONE);
                buttonRemove.setVisibility(View.VISIBLE);
                constraintLayout2.getLayoutParams().height = convertDpToPixel(133, requireContext());
                valueTextView.setVisibility(View.VISIBLE);
                valueTextView.setText(String.format("R$ %.2f", sharedViewModel.calculateTotalValue()));
                midBar0.setVisibility(View.VISIBLE);
            }
        });

        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAdd.setVisibility(View.VISIBLE);
                buttonRemove.setVisibility(View.GONE);
                constraintLayout2.getLayoutParams().height = convertDpToPixel(77, requireContext());
                valueTextView.setVisibility(View.GONE);
                midBar0.setVisibility(View.GONE);
            }
        });
    }
    private int convertDpToPixel(float dp, Context context) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }
}