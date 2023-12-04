package devandroid.frederico.cafexyz.ui.cart;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import devandroid.frederico.cafexyz.data.api.ProductModel;
import devandroid.frederico.cafexyz.data.database.TransactionRepository;

public class SharedViewModel extends ViewModel {

    private TransactionRepository roomRepository;

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    private int clickCount = 0;

    private double totalValue = 0;
    private double discountAmount = 0;

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

    public void finalizarVenda(Context context) {
        notifyCartUpdate();
        roomRepository = new TransactionRepository(context);
        roomRepository.insertTransaction(calculateDiscountedTotalValue());
        cartItems.clear();
    }

    public void applyDiscount(double discount) {
        this.discountAmount = discount;
        notifyTotalValueUpdate();
    }

    public void notifyTotalValueUpdate() {
        if (cartListener != null) {
            cartListener.onCartUpdated(calculateDiscountedTotalValue());
        }
    }

    public double calculateDiscountedTotalValue() {
        totalValue = calculateTotalValue();
        double discountedValue = totalValue - (totalValue * discountAmount / 100);
        return discountedValue;
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


    public int productCount(String productTitle) {
        int count = 0;
        for (ProductModel product : cartItems) {
            if (product.getProductTitle().equals(productTitle)) {
                count++;
            }
        }
        return count;
    }

    public int cartSize() {
        return cartItems.size();
    }


    public void deleteItem(ProductModel productModel) {
        cartItems.remove(productModel);
        notifyCartUpdate();
    }

}