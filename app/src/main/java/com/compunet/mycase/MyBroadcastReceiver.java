package com.compunet.mycase;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.widget.Button;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {

    Button start, clear;
    Notification noti;
    NotificationManager nmgr;
    public static final int NOTIFICATION_ID = 0;


    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "You have set a reminder for a case at this time..",
                Toast.LENGTH_LONG).show();

        /*Intent intent1=new Intent(this,Notify.class);*/

        // Vibrate the mobile phone
        Vibrator vibrator = (Vibrator) context
                .getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(2000);
    }

}

