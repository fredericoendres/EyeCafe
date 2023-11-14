package devandroid.frederico.cafexyz.data;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("listaeye")
    Call<ApiResponse> getProductList();

}
