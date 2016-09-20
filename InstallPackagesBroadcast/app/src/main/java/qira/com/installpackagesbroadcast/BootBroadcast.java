package qira.com.installpackagesbroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BootBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage("com.qira.biometria");
        if (launchIntent != null) {
            context.startActivity(launchIntent);
            Toast toast = Toast.makeText(context, "Boot finalizado com sucesso!", Toast.LENGTH_SHORT);
            toast.show();
            Log.d("QIRA", "Boot Complete");
        }
    }
}