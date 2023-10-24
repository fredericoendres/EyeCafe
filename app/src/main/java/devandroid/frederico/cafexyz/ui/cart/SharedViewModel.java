package devandroid.frederico.cafexyz.ui.cart;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import devandroid.frederico.cafexyz.data.ProductModel;

public class SharedViewModel extends ViewModel {

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    private int clickCount = 0;

    private ArrayList<ProductModel> cartItems = new ArrayList<>();

    public void setCartItems(ArrayList<ProductModel> cartItems) {
        this.cartItems = cartItems;
    }

    public ArrayList<ProductModel> getCartItems() {
        return cartItems;
    }

    private CartListener cartListener;

    public void setCartListener(CartListener listener) {
        this.cartListener = listener;
    }

    public void finalizarVenda() {
        cartItems.clear();
        notifyCartUpdate();
    }

    public void notifyCartUpdate() {
        if (cartListener != null) {
            cartListener.onCartUpdated(calculateTotalValue());
        }
    }

    public interface CartListener {
        void onCartUpdated(double totalValue);
    }



    public void addToCart(ProductModel productModel) {
        cartItems.add(productModel);
    }

    public double calculateTotalValue() {
        double totalValue = 0;
        for (ProductModel product : cartItems) {
            try {
                Double price = product.getProductPrice();
                totalValue += price;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return totalValue;
    }

}