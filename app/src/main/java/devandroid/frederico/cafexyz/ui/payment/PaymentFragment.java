package devandroid.frederico.cafexyz.ui.payment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import devandroid.frederico.cafexyz.R;
import devandroid.frederico.cafexyz.ui.cart.CartViewModel;


public class PaymentFragment extends Fragment {

    private CartViewModel cartViewModel;

    public PaymentFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cartViewModel = new ViewModelProvider(requireActivity()).get(CartViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.payment_fragment, container, false);
        TextView totalValueTextView = view.findViewById(R.id.total_value);
        double totalValue = cartViewModel.calculateTotalValue();
        totalValueTextView.setText(String.format("R$ %.2f", totalValue));
        return view;
    }
}