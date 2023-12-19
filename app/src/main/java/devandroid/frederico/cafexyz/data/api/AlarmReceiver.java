package devandroid.frederico.cafexyz.data.api;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {

            Intent serviceIntent = new Intent(context, ApiService.class);
            context.startForegroundService(serviceIntent);

        }
    }

}
