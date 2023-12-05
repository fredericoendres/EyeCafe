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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;

import devandroid.frederico.cafexyz.R;
import devandroid.frederico.cafexyz.ui.cart.SharedViewModel;
import devandroid.frederico.cafexyz.ui.home.adapter.DiscountClickListener;


public class PaymentFragment extends Fragment implements DiscountClickListener {

    private SharedViewModel sharedViewModel;
    double totalValue;
    private TextView totalValueTextView;
    private String payType;

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
        totalValueTextView = view.findViewById(R.id.total_value);
        double totalValue = sharedViewModel.calculateTotalValue();
        totalValueTextView.setText(String.format("R$ %.2f", totalValue));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageButton buttonAdd = view.findViewById(R.id.qnt_add0);
        ImageButton buttonAdd1 = view.findViewById(R.id.qnt_add1);
        ImageButton buttonAdd2 = view.findViewById(R.id.qnt_add2);
        ImageButton buttonRemove = view.findViewById(R.id.qnt_remove0);
        ImageButton buttonRemove1 = view.findViewById(R.id.qnt_remove1);
        ImageButton buttonRemove2 = view.findViewById(R.id.qnt_remove2);
        ConstraintLayout constraintLayout2 = view.findViewById(R.id.constraintLayout2);
        ConstraintLayout constraintLayout4 = view.findViewById(R.id.constraintLayout4);
        ConstraintLayout constraintLayout5 = view.findViewById(R.id.constraintLayout5);
        TextView valueTextView = view.findViewById(R.id.value_text);
        TextView valueTextView1 = view.findViewById(R.id.value_text1);
        TextView valueTextView2 = view.findViewById(R.id.value_text2);
        View midBar0 = view.findViewById(R.id.mid_bar0);
        View midBar1 = view.findViewById(R.id.mid_bar1);
        View midBar2 = view.findViewById(R.id.mid_bar2);
        Button btnDiscount = view.findViewById(R.id.btn_discount);
        Button btnFinalizar = view.findViewById(R.id.btn_finalizar);




        btnDiscount.setOnClickListener(view12 -> {
            DiscountFragment discountFragment = new DiscountFragment();
            discountFragment.setCallback(this);
            discountFragment.show(getParentFragmentManager(), "DiscountDialog");
        });

        btnFinalizar.setOnClickListener(view1 -> {
            if (payType != null) {
                sharedViewModel.setPaymentType(payType);
                sharedViewModel.finalizarVenda(requireContext());
                NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentMain);
                navController.navigate(R.id.homeFragment);
            }
            else {
                Snackbar.make(requireView(), "Selecione uma forma de pagamento para prosseguir", Snackbar.LENGTH_LONG).show();
            }
        });

        buttonAdd.setOnClickListener(v -> {
            if(payType == null) {
                buttonAdd.setVisibility(View.GONE);
                buttonRemove.setVisibility(View.VISIBLE);
                constraintLayout2.getLayoutParams().height = convertDpToPixel(133, requireContext());
                valueTextView.setVisibility(View.VISIBLE);
                valueTextView.setText(String.format("R$ %.2f", sharedViewModel.calculateTotalValue()));
                if (sharedViewModel.calculateDiscountedTotalValue() > 0) {
                    valueTextView.setText(String.format("R$ %.2f", sharedViewModel.calculateDiscountedTotalValue()));
                }
                midBar0.setVisibility(View.VISIBLE);
                payType = "Dinheiro";
            }
            else {
                Snackbar.make(requireView(), "Você já possui uma forma de pagamento selecionada", Snackbar.LENGTH_LONG).show();
            }
        });

        buttonAdd1.setOnClickListener(v -> {
            if(payType == null) {
                buttonAdd1.setVisibility(View.GONE);
                buttonRemove1.setVisibility(View.VISIBLE);
                constraintLayout4.getLayoutParams().height = convertDpToPixel(133, requireContext());
                valueTextView1.setVisibility(View.VISIBLE);
                valueTextView1.setText(String.format("R$ %.2f", sharedViewModel.calculateTotalValue()));
                if (sharedViewModel.calculateDiscountedTotalValue() > 0) {
                    valueTextView1.setText(String.format("R$ %.2f", sharedViewModel.calculateDiscountedTotalValue()));
                }
                midBar1.setVisibility(View.VISIBLE);
                payType = "Crédito";
            }
            else {
                Snackbar.make(requireView(), "Você já possui uma forma de pagamento selecionada", Snackbar.LENGTH_LONG).show();
            }
        });

        buttonAdd2.setOnClickListener(v -> {
            if(payType == null) {
                buttonAdd2.setVisibility(View.GONE);
                buttonRemove2.setVisibility(View.VISIBLE);
                constraintLayout5.getLayoutParams().height = convertDpToPixel(133, requireContext());
                valueTextView2.setVisibility(View.VISIBLE);
                valueTextView2.setText(String.format("R$ %.2f", sharedViewModel.calculateTotalValue()));
                if (sharedViewModel.calculateDiscountedTotalValue() > 0) {
                    valueTextView2.setText(String.format("R$ %.2f", sharedViewModel.calculateDiscountedTotalValue()));
                }
                midBar2.setVisibility(View.VISIBLE);
                payType = "Débito";
            }
            else {
                Snackbar.make(requireView(), "Você já possui uma forma de pagamento selecionada", Snackbar.LENGTH_LONG).show();
            }
        });

        buttonRemove.setOnClickListener(v -> {
            buttonAdd.setVisibility(View.VISIBLE);
            buttonRemove.setVisibility(View.GONE);
            constraintLayout2.getLayoutParams().height = convertDpToPixel(77, requireContext());
            valueTextView.setVisibility(View.GONE);
            midBar0.setVisibility(View.GONE);
            payType = null;
        });

        buttonRemove1.setOnClickListener(v -> {
            buttonAdd1.setVisibility(View.VISIBLE);
            buttonRemove1.setVisibility(View.GONE);
            constraintLayout4.getLayoutParams().height = convertDpToPixel(77, requireContext());
            valueTextView1.setVisibility(View.GONE);
            midBar1.setVisibility(View.GONE);
            payType = null;
        });

        buttonRemove2.setOnClickListener(v -> {
            buttonAdd2.setVisibility(View.VISIBLE);
            buttonRemove2.setVisibility(View.GONE);
            constraintLayout5.getLayoutParams().height = convertDpToPixel(77, requireContext());
            valueTextView2.setVisibility(View.GONE);
            midBar2.setVisibility(View.GONE);
            payType = null;
        });
    }

    private int convertDpToPixel(float dp, Context context) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

    @Override
    public void discountClick() {
        if (totalValueTextView != null) {
            double totalValue = sharedViewModel.calculateTotalValue();
            if (sharedViewModel.calculateDiscountedTotalValue() > 0) {
                totalValueTextView.setText(String.format("R$ %.2f", sharedViewModel.calculateDiscountedTotalValue()));
            } else {
                totalValueTextView.setText(String.format("R$ %.2f", totalValue));
            }
        }
    }
}