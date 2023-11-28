package devandroid.frederico.cafexyz.data.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    final static String base_url = "http://demo8004616.mockable.io";
    static Retrofit retrofit = null;

    public static Retrofit getClient(){
        if (retrofit == null){
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new LoggingInterceptor()) // Add the OkHttp interceptor
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();
        }
        return retrofit;
    }

}
