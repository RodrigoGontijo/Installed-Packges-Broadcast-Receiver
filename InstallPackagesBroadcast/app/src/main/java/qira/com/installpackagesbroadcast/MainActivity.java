package qira.com.installpackagesbroadcast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        callApp();
    }

    @Override
    public void onResume() {
        super.onResume();
        callApp();
    }


    public void callApp() {
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.qira.biometria");
        if (launchIntent != null) {
            startActivity(launchIntent);
        }
    }
}
