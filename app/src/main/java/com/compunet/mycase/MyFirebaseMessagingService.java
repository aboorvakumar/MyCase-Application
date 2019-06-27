
package com.compunet.mycase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static android.content.ContentValues.TAG;

public class MyFirebaseMessagingService extends FirebaseMessagingService {






     @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
         super.onMessageReceived(remoteMessage);



         sendNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());

        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
    }

    private void sendNotification(String title,String messageBody){
        Intent intent = new Intent(this,Adjournment.class);
        intent.putExtra("Title",title);
        intent.putExtra("Message",messageBody);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);



        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.ic_alarm_settings);
        builder.setContentTitle(title);
        builder.setContentText(messageBody);
        builder.setAutoCancel(true);

        builder.setSound(uri);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,builder.build());


    }


}

