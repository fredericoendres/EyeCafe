package devandroid.frederico.cafexyz.data.api;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Response;

public class ApiWorker extends Worker {

    private Context context;
    private Handler handler;


    public ApiWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
        this.handler = new Handler(Looper.getMainLooper());
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            ApiResponse response = makeApiRequest();

            if (response != null && response.isStatus()) {
                showUIToast(response.getMessage());
                return Result.success();
            } else {
                showUIToast("API request failed");
                return Result.failure();
            }
        } catch (Exception e) {
            showUIToast("Exception occurred during API request");
            return Result.failure();
        }
    }

    private ApiResponse makeApiRequest() throws IOException {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponse> call = apiInterface.getProductList();
        Response<ApiResponse> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            return null;
        }
    }
    private void showUIToast(String message) {
        handler.post(() -> {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        });
    }

}
