package com.example.projekt;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("AlarmManager", "Wyswietlam powiadomienie");
        // Wywołuje się, kiedy alarm jest uruchamiany
        // Tutaj możesz wyświetlić powiadomienie
        showNotification(context);
    }

    private void showNotification(Context context) {
        // Utwórz kanał powiadomień (wymagane w wersji Android Oreo i nowszych)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("your_channel_id", "Your Channel Name", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        // Utwórz intent dla powiadomienia
        Intent notificationIntent = new Intent(context, MenuActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Utwórz powiadomienie
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "your_channel_id")
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("Nauka słówek")
                .setContentText("Nie zapomnij sprawdzić dzisiejszego słówka dnia")
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        // Wyślij powiadomienie
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(123, builder.build());
    }
}


