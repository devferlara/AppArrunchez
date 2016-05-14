package arrunchez.baumsoft.con.lafamiliaarrunchez.receivers;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import arrunchez.baumsoft.con.lafamiliaarrunchez.R;
import arrunchez.baumsoft.con.lafamiliaarrunchez.Splash;
import arrunchez.baumsoft.con.lafamiliaarrunchez.helpers.edientes_utils;
import arrunchez.baumsoft.con.lafamiliaarrunchez.models.model_cdientes;

/**
 * Created by dayessi on 3/05/16.
 */
public class edientesReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equalsIgnoreCase(CalendarContract.ACTION_EVENT_REMINDER)) {


            Uri uri = intent.getData();
            String alertTime = uri.getLastPathSegment();

            String selection = CalendarContract.CalendarAlerts.ALARM_TIME + "=?";

            Cursor cursor = context.getContentResolver().query(
                    CalendarContract.CalendarAlerts.CONTENT_URI_BY_INSTANCE,
                    new String[]{CalendarContract.CalendarAlerts.EVENT_ID},
                    selection,
                    new String[]{alertTime},
                    null);


            if (cursor.moveToFirst()) {

                String[] proj = new String[] {
                        CalendarContract.Events._ID,
                        CalendarContract.Events.TITLE,
                        CalendarContract.Events.DTSTART };
                String sel =
                        CalendarContract.Events.CALENDAR_ID +
                                " = ? AND " +
                                CalendarContract.Events._ID +
                                " = ?";
                String[] selArgs = { String.valueOf(edientes_utils.getCalendarId(context)), String.valueOf(cursor.getLong(0)) };
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
                    Cursor c =

                            context.getContentResolver().
                                    query(
                                            CalendarContract.Events.CONTENT_URI,
                                            proj,
                                            sel,
                                            selArgs,
                                            null);
                    if (c.moveToFirst()) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(c.getLong(2));

                        mostrarNotification(c.getString(1), context);

                        /*
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                        Toast.makeText(context, c.getString(1), Toast.LENGTH_LONG).show();

                        Notification notification = builder.setContentTitle(c.getString(1))
                                .setContentText(calendar.getTime().toString())
                                .setSmallIcon(R.mipmap.ic_launcher).build();

                        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.notify(0, notification);



                        Intent notificationIntent = new Intent(context, Splash.class);
                        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        PendingIntent intento = PendingIntent.getActivity(context, 0,
                                notificationIntent, 0);
                        notification.flags |= Notification.FLAG_AUTO_CANCEL;
                        notificationManager.notify(0, notification);
                        */

                    }
                }
            }
        }
    }



    private void mostrarNotification(String msg, Context contexto) {

        Intent intent = new Intent(contexto, Splash.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(contexto, (int) System.currentTimeMillis(), intent, 0);

        Notification n = new Notification.Builder(contexto)
                .setContentTitle("Recordatorio")
                .setContentText(msg)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .setAutoCancel(true).build();

        n.defaults |= Notification.DEFAULT_SOUND;
        n.defaults |= Notification.DEFAULT_VIBRATE;
        n.defaults |= Notification.DEFAULT_LIGHTS;

        NotificationManager notificationManager =
                (NotificationManager) contexto.getSystemService(contexto.NOTIFICATION_SERVICE);

        notificationManager.notify(0, n);

    }


}
