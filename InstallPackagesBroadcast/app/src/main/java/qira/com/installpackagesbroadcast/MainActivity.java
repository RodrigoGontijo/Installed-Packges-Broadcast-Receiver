package qira.com.installpackagesbroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


    private static final int DELAY = 660000; //milliseconds

    private TimerTask myTask;
    private Timer timer;
    private TextView textView;
    private PackageInfo pInfo;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private String android_id;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private Handler h = new Handler();
    private boolean initializationFail = true;


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
        android_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(android_id.toUpperCase());
        sharedPref = getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();


        BroadcastReceiver broadcast_receiver_finish_activity = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("initialization_ok")) {
                    initializationFail = false;
                    editor.putInt("initialization_ok", sharedPref.getInt(("initialization_ok"), 0) + 1);
                    editor.commit();
                    myRef.child("Contador Inicialização OK :").setValue(sharedPref.getInt(("initialization_ok"), 0));
                }
            }
        };
        registerReceiver(broadcast_receiver_finish_activity, new IntentFilter("initialization_ok"));


        h.postDelayed(new Runnable() {
            public void run() {
                if (initializationFail) {
                    PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
                    pm.reboot("");
                }
            }
        }, DELAY);

        broadcastSensorOk();
        broadcastSensorFail();
        broadcastConnectionOk();
        broadcastConnectionFail();





    }

    @Override
    public void onResume() {
        super.onResume();
        timer = new Timer();
        myTask = new MyTimerTask();
        timer.schedule(myTask, 15000);
    }


    /**
     * -------------------- Broadcast Connection FAIL --------------------
     */

    private void broadcastConnectionFail() {
        BroadcastReceiver broadcast_receiver_finish_activity = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("connection_fail")) {
                    editor.putInt("connection_fail", sharedPref.getInt(("connection_fail"), 0) + 1);
                    editor.commit();
                    myRef.child("Contador Conexão OFF :").setValue(sharedPref.getInt(("connection_fail"), 0));
                }
            }
        };
        registerReceiver(broadcast_receiver_finish_activity, new IntentFilter("connection_fail"));
    }


    /**
     * -------------------- Broadcast Connection OK --------------------
     */

    private void broadcastConnectionOk() {
        BroadcastReceiver broadcast_receiver_finish_activity = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("connection_ok")) {
                    editor.putInt("connection_ok", sharedPref.getInt(("connection_ok"), 0) + 1);
                    editor.commit();
                    myRef.child("Contador Conexão OK :").setValue(sharedPref.getInt(("connection_ok"), 0));
                }
            }
        };
        registerReceiver(broadcast_receiver_finish_activity, new IntentFilter("connection_ok"));
    }


    /**
     * -------------------- Broadcast Sensor FAIL --------------------
     */


    private void broadcastSensorFail() {
        BroadcastReceiver broadcast_receiver_finish_activity = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("sensor_fail")) {
                    editor.putInt("sensor_fail", sharedPref.getInt(("sensor_fail"), 0) + 1);
                    editor.commit();
                    myRef.child("Contador Sensor FAIL :").setValue(sharedPref.getInt(("sensor_fail"), 0));
                }
            }
        };
        registerReceiver(broadcast_receiver_finish_activity, new IntentFilter("sensor_fail"));
    }


    /**
     * -------------------- Broadcast Sensor OK --------------------
     */


    private void broadcastSensorOk() {
        BroadcastReceiver broadcast_receiver_finish_activity = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("sensor_ok")) {
                    editor.putInt("sensor_ok", sharedPref.getInt(("sensor_ok"), 0) + 1);
                    editor.commit();
                    myRef.child("Contador Sensor OK :").setValue(sharedPref.getInt(("sensor_ok"), 0));
                    myRef.push();

                }
            }
        };
        registerReceiver(broadcast_receiver_finish_activity, new IntentFilter("sensor_ok"));
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
