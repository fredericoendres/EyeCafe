package devandroid.frederico.cafexyz.data.api;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class ProductModel {

    private String productTitle;
    private Double productPrice;
    private String observation;
    @SerializedName("productImage")
    private String productImage;

    public ProductModel(String productTitle, Double productPrice, String productImage) {
        this.productTitle = productTitle;
        this.productPrice = productPrice;
        this.productImage = productImage;
    }

    public ProductModel(String productTitle, Double productPrice, String productImage, String observation) {
        this.productTitle = productTitle;
        this.productPrice = productPrice;
        this.productImage = productImage;
        this.observation = observation;
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

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
}