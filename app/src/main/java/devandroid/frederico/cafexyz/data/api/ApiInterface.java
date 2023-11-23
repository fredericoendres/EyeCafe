package devandroid.frederico.cafexyz.data.api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("listaeye")
    Call<ApiResponse> getProductList();

}
