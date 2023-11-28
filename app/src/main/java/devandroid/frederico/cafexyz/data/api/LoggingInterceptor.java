package devandroid.frederico.cafexyz.data.api;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class LoggingInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        okhttp3.Request request = chain.request();
        long t1 = System.nanoTime();
        Log.d("OkHttp", String.format("Enviando request em %s  %s%n%s",
                request.url(), chain.connection(), request.headers()));

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();
        Log.d("OkHttp", String.format("Resposta recebida em %s %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));

        return response;
    }

}
