package devandroid.frederico.cafexyz.data.api;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import devandroid.frederico.cafexyz.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            makeApiRequest();
                            try {
                                Thread.sleep(10000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        ).start();

        final String CHANNELID = "Foreground Service ID";
        NotificationChannel channel = new NotificationChannel(
                CHANNELID,
                CHANNELID,
                NotificationManager.IMPORTANCE_LOW
        );

        getSystemService(NotificationManager.class).createNotificationChannel(channel);
        Notification.Builder notification = new Notification.Builder(this, CHANNELID)
                .setContentText("Atividade service rodando")
                .setContentTitle("Eye cafe")
                .setSmallIcon(R.drawable.java_1);

        startForeground(1001, notification.build());
        return super.onStartCommand(intent, flags, startId);
    }

    private void makeApiRequest() {
        ApiClient.getClient().create(ApiInterface.class).getProductList().enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.code() == 200 && response.body() != null) {
                    Log.d("servicoTrue", "Resposta com Sucesso");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.d("servicoTrue", "API nao funcionou mas service sim");
            }
        });
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
