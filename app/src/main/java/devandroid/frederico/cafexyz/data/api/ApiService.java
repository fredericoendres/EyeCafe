package devandroid.frederico.cafexyz.data.api;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiService extends Service {
    public static final String ACTION_SHOW_TOAST = "devandroid.frederico.cafexyz.ACTION_SHOW_TOAST";
    public static final String EXTRA_TOAST_MESSAGE = "toast_message";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        makeApiRequest();
        return START_STICKY;
    }

    private void makeApiRequest() {
        ApiClient.getClient().create(ApiInterface.class).getProductList().enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.code() == 200 && response.body() != null) {
                    String message = response.body().getMessage();
                    sendBroadcastToShowToast(message);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                sendBroadcastToShowToast("API Request Failed");
            }
        });
    }

    private void sendBroadcastToShowToast(String message) {
        Intent broadcastIntent = new Intent(ACTION_SHOW_TOAST);
        broadcastIntent.putExtra(EXTRA_TOAST_MESSAGE, message);
        sendBroadcast(broadcastIntent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
