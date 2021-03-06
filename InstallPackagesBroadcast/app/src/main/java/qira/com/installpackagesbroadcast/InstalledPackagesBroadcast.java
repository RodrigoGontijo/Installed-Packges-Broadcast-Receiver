package qira.com.installpackagesbroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class InstalledPackagesBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Uri data = intent.getData();
        if (data.toString().equals("package:" + "com.qira.biometria")) {
            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage("com.qira.biometria");
            context.startActivity(launchIntent);
            Toast toast = Toast.makeText(context, "Qira Atulizado com Sucesso!", Toast.LENGTH_SHORT);
            toast.show();
            Log.d("QIRA", "Qira Atualizou");
        }
    }
}
