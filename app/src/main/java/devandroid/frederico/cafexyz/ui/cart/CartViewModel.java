package devandroid.frederico.cafexyz.ui.cart;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import devandroid.frederico.cafexyz.data.ProductModel;

public class CartViewModel extends ViewModel {

    private ArrayList<ProductModel> cartItems = new ArrayList<>();

    public void setCartItems(ArrayList<ProductModel> cartItems) {
        this.cartItems = cartItems;
    }

    public ArrayList<ProductModel> getCartItems() {
        return cartItems;
    }

}
