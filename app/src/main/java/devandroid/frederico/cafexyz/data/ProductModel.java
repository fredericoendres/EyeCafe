package devandroid.frederico.cafexyz.data;

import java.math.BigDecimal;

public class ProductModel {

    private String productTitle;
    private Double productPrice;
    private int productImage;

    public ProductModel(String productTitle, Double productPrice, int productImage) {
        this.productTitle = productTitle;
        this.productPrice = productPrice;
        this.productImage = productImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }


}