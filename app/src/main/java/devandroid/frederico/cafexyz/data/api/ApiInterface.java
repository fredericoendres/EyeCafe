package devandroid.frederico.cafexyz.data.api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("b02488f8-78ef-4cfd-9793-4764fd7dd603")
    Call<ApiResponse> getProductList();

}
