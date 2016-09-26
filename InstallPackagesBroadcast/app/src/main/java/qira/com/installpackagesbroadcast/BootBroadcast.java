package qira.com.installpackagesbroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BootBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Intent i = new Intent(context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Toast toast = Toast.makeText(context, "Gestor Inicializado com sucesso!", Toast.LENGTH_SHORT);
            toast.show();
            Log.d("QIRA", "Boot Complete");
            context.startActivity(i);

        }
    }
}