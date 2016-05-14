package arrunchez.baumsoft.con.lafamiliaarrunchez.helpers;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;

/**
 * Created by dayessi on 3/05/16.
 */
public class edientes_utils {

    public static long getCalendarId(Context activity) {
        String[] projection = new String[] {
                CalendarContract.Calendars._ID,
                CalendarContract.Calendars.NAME };
        String selection =
                CalendarContract.Calendars.ACCOUNT_NAME +
                        " = ? AND " +
                        CalendarContract.Calendars.ACCOUNT_TYPE +
                        " = ? ";
        String[] selArgs =new String[]{
                "AppArrunchez",
                CalendarContract.ACCOUNT_TYPE_LOCAL };

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_DENIED) {
            Cursor cursor =
                    activity.getContentResolver().
                            query(
                                    CalendarContract.Calendars.CONTENT_URI,
                                    projection,
                                    selection,
                                    selArgs,
                                    null);

            if (cursor.moveToFirst()) {
                return cursor.getLong(0);
            } else {
                ContentValues values = new ContentValues();
                values.put( CalendarContract.Calendars.ACCOUNT_NAME, "AppArrunchez" );
                values.put( CalendarContract.Calendars.ACCOUNT_TYPE, CalendarContract.ACCOUNT_TYPE_LOCAL );
                values.put( CalendarContract.Calendars.NAME, "Arrunchez Calendar");
                values.put( CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, "Arrunchez Calendar");
                values.put( CalendarContract.Calendars.CALENDAR_COLOR, 0xffff0000);
                values.put( CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, CalendarContract.Calendars.CAL_ACCESS_OWNER);
                values.put( CalendarContract.Calendars.CALENDAR_TIME_ZONE, "America/Bogota");
                values.put( CalendarContract.Calendars.SYNC_EVENTS, 1);

                Uri.Builder builder =
                        CalendarContract.Calendars.CONTENT_URI.buildUpon();

                builder.appendQueryParameter( CalendarContract.Calendars.ACCOUNT_NAME, "com.arrunchez");
                builder.appendQueryParameter( CalendarContract.Calendars.ACCOUNT_TYPE, CalendarContract.ACCOUNT_TYPE_LOCAL);
                builder.appendQueryParameter( CalendarContract.CALLER_IS_SYNCADAPTER, "true");
                Uri uri =
                        activity.getContentResolver().insert(builder.build(), values);
                return new Long(uri.getLastPathSegment());
            }
        }
        return -1;
    }

}
