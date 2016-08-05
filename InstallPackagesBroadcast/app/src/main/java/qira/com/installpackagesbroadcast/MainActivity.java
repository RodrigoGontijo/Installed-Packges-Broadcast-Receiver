package qira.com.installpackagesbroadcast;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TimerTask myTask;
    private Timer timer;
    private TextView textView;
    private PackageInfo pInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.version);
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            textView.setText("InstallPackages: " + "V" + pInfo.versionName.toString());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        timer = new Timer();
        myTask = new MyTimerTask();
        timer.schedule(myTask, 10000);

    }


    public void callApp() {
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.qira.biometria");
        if (launchIntent != null) {
            startActivity(launchIntent);
        }
    }


    class MyTimerTask extends TimerTask {
        public void run() {
            callApp();
        }
    }
}
