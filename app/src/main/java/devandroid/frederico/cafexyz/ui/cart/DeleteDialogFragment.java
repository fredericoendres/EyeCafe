package devandroid.frederico.cafexyz.ui.cart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;

import devandroid.frederico.cafexyz.R;
import devandroid.frederico.cafexyz.data.api.ProductModel;

public class DeleteDialogFragment extends PopupWindow {
    private NavController navController;
    private SharedViewModel sharedViewModel;
    private CartAdapter cartAdapter;
    private ProductModel productModel;

    public DeleteDialogFragment(Context context, SharedViewModel sharedViewModel, ProductModel productModel, CartAdapter cartAdapter) {
        super(context);
        this.sharedViewModel = sharedViewModel;
        this.productModel = productModel;
        this.cartAdapter = cartAdapter;
        View view = LayoutInflater.from(context).inflate(R.layout.delete_layout, null);
        setContentView(view);
        int width = context.getResources().getDimensionPixelSize(R.dimen.popup_width);
        int height = context.getResources().getDimensionPixelSize(R.dimen.popup_height);
        setWidth(width);
        setHeight(height);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView editTextView = view.findViewById(R.id.editProduct);
        TextView deleteTextView = view.findViewById(R.id.deleteProduct);
        editTextView.setOnClickListener(v -> {
            EditFragment editFragment = new EditFragment();
            editFragment.show(((FragmentActivity) context).getSupportFragmentManager(), "EditDialogFragment");
            dismiss();
        });

        deleteTextView.setOnClickListener(view1 -> {
            int position = sharedViewModel.getCartItems().indexOf(productModel);
            if (position != -1) {
                sharedViewModel.getCartItems().remove(productModel);
                if (position < cartAdapter.getItemCount()) {
                    cartAdapter.notifyItemRemoved(position);
                } else {
                    cartAdapter.notifyDataSetChanged();
                }
            }
            dismiss();
        });
    }

    public void setNavController(NavController navController) {
        this.navController = navController;
    }

    public void showPopupWindow(View anchor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int[] location = new int[2];
            anchor.getLocationOnScreen(location);
            showAtLocation(anchor, Gravity.NO_GRAVITY, location[0], location[1] - getHeight());
        } else {
            showAsDropDown(anchor);
        }
    }
}
