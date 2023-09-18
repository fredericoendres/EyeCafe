package devandroid.frederico.cafexyz;

public class ProductModel {

    private String productTitle;
    private String productPrice;
    private int productImage;

    public ProductModel(String productTitle, String productPrice, int productImage) {
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

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }
}
