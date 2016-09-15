package qira.com.installpackagesbroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TimerTask myTask;
    private Timer timer;
    private TextView textView;
    private PackageInfo pInfo;
    private FirebaseDatabase database;
    private DatabaseReference myRef;


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

         database = FirebaseDatabase.getInstance();
         myRef = database.getReference("message");
        myRef.child("teste");


    }

    @Override
    public void onResume() {
        super.onResume();
        timer = new Timer();
        myTask = new MyTimerTask();
        timer.schedule(myTask, 10000);
        broadcastInitialization();
        broadcastSensorOk();
        broadcastSensorFail();
        broadcastConnectionOk();
        broadcastConnectionFail();

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

                }
            }
        };
        registerReceiver(broadcast_receiver_finish_activity, new IntentFilter("sensor_ok"));
    }


    /**
     * -------------------- Broadcast Initialization OK --------------------
     */


    private void broadcastInitialization() {
        BroadcastReceiver broadcast_receiver_finish_activity = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("initialization_ok")) {

                }
            }
        };
        registerReceiver(broadcast_receiver_finish_activity, new IntentFilter("initialization_ok"));
    }




    public void callApp() {
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.qira.biometria.generica");
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
