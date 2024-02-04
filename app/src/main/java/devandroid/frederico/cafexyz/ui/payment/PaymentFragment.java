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
import devandroid.frederico.cafexyz.databinding.PaymentFragmentBinding;
import devandroid.frederico.cafexyz.ui.cart.SharedViewModel;
import devandroid.frederico.cafexyz.ui.home.adapter.DiscountClickListener;


public class PaymentFragment extends Fragment implements DiscountClickListener {

    private SharedViewModel sharedViewModel;
    double totalValue;
    private String payType;
    private PaymentFragmentBinding binding;
    private PaymentFragmentListener paymentFragmentListener;


    public PaymentFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            payType = savedInstanceState.getString("payType");
        }
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof PaymentFragmentListener) {
            paymentFragmentListener = (PaymentFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + " falta implementar o PaymentFragmentListener");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("payType", payType);
        super.onSaveInstanceState(outState);
    }
    private void updateUIForPayType() {
        if (payType != null) {
            if (payType == "Dinheiro") {
                binding.qntAdd0.setVisibility(View.GONE);
                binding.qntRemove0.setVisibility(View.VISIBLE);
                binding.constraintLayout2.getLayoutParams().height = convertDpToPixel(133, requireContext());
                binding.valueText.setVisibility(View.VISIBLE);
                binding.valueText.setText(String.format("R$ %.2f", sharedViewModel.calculateTotalValue()));
                if (sharedViewModel.calculateDiscountedTotalValue() > 0) {
                    binding.valueText.setText(String.format("R$ %.2f", sharedViewModel.calculateDiscountedTotalValue()));
                }
                binding.midBar0.setVisibility(View.VISIBLE);
            } else if (payType == "Crédito") {
                binding.qntAdd1.setVisibility(View.GONE);
                binding.qntRemove1.setVisibility(View.VISIBLE);
                binding.constraintLayout4.getLayoutParams().height = convertDpToPixel(133, requireContext());
                binding.valueText1.setVisibility(View.VISIBLE);
                binding.valueText1.setText(String.format("R$ %.2f", sharedViewModel.calculateTotalValue()));
                if (sharedViewModel.calculateDiscountedTotalValue() > 0) {
                    binding.valueText1.setText(String.format("R$ %.2f", sharedViewModel.calculateDiscountedTotalValue()));
                }
                binding.midBar1.setVisibility(View.VISIBLE);
            } else if (payType == "Débito") {
                binding.qntAdd2.setVisibility(View.GONE);
                binding.qntRemove2.setVisibility(View.VISIBLE);
                binding.constraintLayout5.getLayoutParams().height = convertDpToPixel(133, requireContext());
                binding.valueText2.setVisibility(View.VISIBLE);
                binding.valueText2.setText(String.format("R$ %.2f", sharedViewModel.calculateTotalValue()));
                if (sharedViewModel.calculateDiscountedTotalValue() > 0) {
                    binding.valueText2.setText(String.format("R$ %.2f", sharedViewModel.calculateDiscountedTotalValue()));
                }
                binding.midBar2.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = PaymentFragmentBinding.inflate(inflater, container, false);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        double totalValue = sharedViewModel.calculateTotalValue();
        binding.totalValue.setText(String.format("R$ %.2f", totalValue));
        if (sharedViewModel.calculateDiscountedTotalValue() > 0) {
            binding.totalValue.setText(String.format("R$ %.2f", sharedViewModel.calculateDiscountedTotalValue()));
            if(sharedViewModel.calculateDiscountedTotalValue() != sharedViewModel.calculateTotalValue()) {
                binding.btnRemoveDiscount.setVisibility(View.VISIBLE);
                binding.btnDiscount.setVisibility(View.GONE);
                binding.diferencaDesconto.setText(String.format("Desconto: - R$ %.2f", sharedViewModel.calculateDiscountedValue()));
                binding.diferencaDesconto.setVisibility(View.VISIBLE);
            }
        }
        updateUIForPayType();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnDiscount.setOnClickListener(view12 -> {
            DiscountFragment discountFragment = new DiscountFragment();
            discountFragment.setCallback(this);
            discountFragment.show(getParentFragmentManager(), "DiscountDialog");
        });

        binding.btnFinalizar.setOnClickListener(view1 -> {
            if (payType != null) {
                sharedViewModel.setPaymentType(payType);
                sharedViewModel.finalizarVenda(requireContext());
                if (paymentFragmentListener != null) {
                    paymentFragmentListener.onPaymentFragmentFinish();
                }
            } else {
                Snackbar.make(requireView(), "Selecione uma forma de pagamento para prosseguir", Snackbar.LENGTH_LONG).show();
            }
            NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentMain);
            navController.navigate(R.id.homeFragment);
        });

        binding.qntAdd0.setOnClickListener(v -> {
            if(payType == null) {
                binding.qntAdd0.setVisibility(View.GONE);
                binding.qntRemove0.setVisibility(View.VISIBLE);
                binding.constraintLayout2.getLayoutParams().height = convertDpToPixel(133, requireContext());
                binding.valueText.setVisibility(View.VISIBLE);
                binding.valueText.setText(String.format("R$ %.2f", sharedViewModel.calculateTotalValue()));
                if (sharedViewModel.calculateDiscountedTotalValue() > 0) {
                    binding.valueText.setText(String.format("R$ %.2f", sharedViewModel.calculateDiscountedTotalValue()));
                }
                binding.midBar0.setVisibility(View.VISIBLE);
                payType = "Dinheiro";
            }
            else {
                Snackbar.make(requireView(), "Você já possui uma forma de pagamento selecionada", Snackbar.LENGTH_LONG).show();
            }
        });

        binding.qntAdd1.setOnClickListener(v -> {
            if(payType == null) {
                binding.qntAdd1.setVisibility(View.GONE);
                binding.qntRemove1.setVisibility(View.VISIBLE);
                binding.constraintLayout4.getLayoutParams().height = convertDpToPixel(133, requireContext());
                binding.valueText1.setVisibility(View.VISIBLE);
                binding.valueText1.setText(String.format("R$ %.2f", sharedViewModel.calculateTotalValue()));
                if (sharedViewModel.calculateDiscountedTotalValue() > 0) {
                    binding.valueText1.setText(String.format("R$ %.2f", sharedViewModel.calculateDiscountedTotalValue()));
                }
                binding.midBar1.setVisibility(View.VISIBLE);
                payType = "Crédito";
            }
            else {
                Snackbar.make(requireView(), "Você já possui uma forma de pagamento selecionada", Snackbar.LENGTH_LONG).show();
            }
        });

        binding.qntAdd2.setOnClickListener(v -> {
            if(payType == null) {
                binding.qntAdd2.setVisibility(View.GONE);
                binding.qntRemove2.setVisibility(View.VISIBLE);
                binding.constraintLayout5.getLayoutParams().height = convertDpToPixel(133, requireContext());
                binding.valueText2.setVisibility(View.VISIBLE);
                binding.valueText2.setText(String.format("R$ %.2f", sharedViewModel.calculateTotalValue()));
                if (sharedViewModel.calculateDiscountedTotalValue() > 0) {
                    binding.valueText2.setText(String.format("R$ %.2f", sharedViewModel.calculateDiscountedTotalValue()));
                }
                binding.midBar2.setVisibility(View.VISIBLE);
                payType = "Débito";
            }
            else {
                Snackbar.make(requireView(), "Você já possui uma forma de pagamento selecionada", Snackbar.LENGTH_LONG).show();
            }
        });

        binding.qntRemove0.setOnClickListener(v -> {
            binding.qntAdd0.setVisibility(View.VISIBLE);
            binding.qntRemove0.setVisibility(View.GONE);
            binding.constraintLayout2.getLayoutParams().height = convertDpToPixel(77, requireContext());
            binding.valueText.setVisibility(View.GONE);
            binding.midBar0.setVisibility(View.GONE);
            payType = null;
        });

        binding.qntRemove1.setOnClickListener(v -> {
            binding.qntAdd1.setVisibility(View.VISIBLE);
            binding.qntRemove1.setVisibility(View.GONE);
            binding.constraintLayout4.getLayoutParams().height = convertDpToPixel(77, requireContext());
            binding.valueText1.setVisibility(View.GONE);
            binding.midBar1.setVisibility(View.GONE);
            payType = null;
        });

        binding.qntRemove2.setOnClickListener(v -> {
            binding.qntAdd2.setVisibility(View.VISIBLE);
            binding.qntRemove2.setVisibility(View.GONE);
            binding.constraintLayout5.getLayoutParams().height = convertDpToPixel(77, requireContext());
            binding.valueText2.setVisibility(View.GONE);
            binding.midBar2.setVisibility(View.GONE);
            payType = null;
        });

        binding.btnRemoveDiscount.setOnClickListener(view1 -> {
            sharedViewModel.limparDiscount();
            binding.totalValue.setText(String.format("R$ %.2f", sharedViewModel.calculateTotalValue()));
            binding.diferencaDesconto.setVisibility(View.GONE);
            binding.btnRemoveDiscount.setVisibility(View.GONE);
            binding.btnDiscount.setVisibility(View.VISIBLE);
            binding.valueText.setText(String.format("R$ %.2f", sharedViewModel.calculateTotalValue()));
            binding.valueText1.setText(String.format("R$ %.2f", sharedViewModel.calculateTotalValue()));
            binding.valueText2.setText(String.format("R$ %.2f", sharedViewModel.calculateTotalValue()));
        });
    }

    private int convertDpToPixel(float dp, Context context) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

    @Override
    public void discountClick() {
        if (binding.totalValue != null) {
            double totalValue = sharedViewModel.calculateTotalValue();
            if (sharedViewModel.calculateDiscountedTotalValue() > 0) {
                binding.totalValue.setText(String.format("R$ %.2f", sharedViewModel.calculateDiscountedTotalValue()));
                binding.valueText.setText(String.format("R$ %.2f", sharedViewModel.calculateDiscountedTotalValue()));
                binding.valueText1.setText(String.format("R$ %.2f", sharedViewModel.calculateDiscountedTotalValue()));
                binding.valueText2.setText(String.format("R$ %.2f", sharedViewModel.calculateDiscountedTotalValue()));
                binding.diferencaDesconto.setVisibility(View.VISIBLE);
                binding.diferencaDesconto.setText(String.format("Desconto: - R$ %.2f", sharedViewModel.calculateDiscountedValue()));
                binding.btnDiscount.setVisibility(View.GONE);
                binding.btnRemoveDiscount.setVisibility(View.VISIBLE);
            } else {
                binding.totalValue.setText(String.format("R$ %.2f", totalValue));
            }
        }
    }
}