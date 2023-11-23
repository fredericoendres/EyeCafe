package devandroid.frederico.cafexyz.data.api;

import java.util.ArrayList;

public class ApiResponse {

    private boolean status;
    private String message;
    private ArrayList<ProductModel> data;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<ProductModel> getData() {
        return data;
    }
}
