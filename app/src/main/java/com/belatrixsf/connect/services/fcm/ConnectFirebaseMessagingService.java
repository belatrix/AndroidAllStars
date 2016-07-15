package com.belatrixsf.connect.services.fcm;

import android.app.LauncherActivity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.belatrixsf.connect.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by PedroCarrillo on 6/2/16.
 */

public class ConnectFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Map<String, String> notificationData = remoteMessage.getData();
        if (notificationData != null) {
            Log.d(TAG, "title: " + notificationData.get("title")+"");
            Log.d(TAG, "detail "+ remoteMessage.getData().get("detail"));

            sendNotification(notificationData.get("title"), remoteMessage.getData().get("detail"));
        }
        com.google.firebase.messaging.RemoteMessage.Notification notification = remoteMessage.getNotification();
        //Calling method to generate notification
        if (notification != null) {

            //Displaying data in log
            //It is optional
            Log.d(TAG, "title: " + notification.getTitle()+"");
            Log.d(TAG, "getBody: " + notification.getBody()+"");
            Log.d(TAG, "getBodyLocalizationKey: " + notification.getBodyLocalizationKey()+"");
            Log.d(TAG, "getClickAction: " + notification.getClickAction()+"");
            Log.d(TAG, "getColor: " + notification.getColor()+"");
            Log.d(TAG, "getSound: " + notification.getSound()+"");
            Log.d(TAG, "getBodyLocalizationArgs: " + notification.getBodyLocalizationArgs()+"");
            Log.d(TAG, "getTitleLocalizationArgs: " + notification.getTitleLocalizationArgs()+"");

            sendNotification(notification.getTitle(), notification.getBody());
        }
    }

    //This method is only generating push notification
    //It is same as we did in earlier posts
    private void sendNotification(String messageTitle, String messageBody) {
        Intent intent = new Intent(this, LauncherActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle(messageTitle);
        bigTextStyle.bigText(messageBody);

        Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                R.mipmap.ic_launcher);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(icon)
                .setStyle(bigTextStyle)
                .setContentText(messageBody)
                .setContentTitle(messageTitle)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }

}
