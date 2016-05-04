package arrunchez.baumsoft.con.lafamiliaarrunchez.fragmentos;

import android.Manifest;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TimePicker;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import arrunchez.baumsoft.con.lafamiliaarrunchez.Inicio;
import arrunchez.baumsoft.con.lafamiliaarrunchez.R;
import arrunchez.baumsoft.con.lafamiliaarrunchez.adapters.cdientes_adapter;
import arrunchez.baumsoft.con.lafamiliaarrunchez.helpers.edientes_utils;
import arrunchez.baumsoft.con.lafamiliaarrunchez.models.model_cdientes;


public class calendario extends Fragment {
    private MaterialCalendarView calendarView;

    Calendar dateAndTime = Calendar.getInstance();

    TimePickerDialog.OnTimeSetListener t;

    AlertDialog.Builder builder;

    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_calendario, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);

        ((Inicio) getActivity()).getSupportActionBar().setTitle("Calendario");


        builder = new AlertDialog.Builder(getActivity());
        LayoutInflater i = (LayoutInflater) getActivity().getLayoutInflater();
        final View v = (View) i.inflate(R.layout.form_calendar, null);
        builder.setView(v);
        // Add the buttons
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                EditText nombres = (EditText) v.findViewById(R.id.nombre);
                EditText direccion = (EditText) v.findViewById(R.id.direccion);
                EditText comentarios = (EditText) v.findViewById(R.id.comentarios);

                long calId = edientes_utils.getCalendarId(getActivity());

                if (calId != -1) {
                    ContentValues values = new ContentValues();
                    values.put(CalendarContract.Events.DTSTART, dateAndTime.getTimeInMillis());
                    values.put(CalendarContract.Events.DTEND, dateAndTime.getTimeInMillis());

                    values.put(CalendarContract.Events.TITLE, nombres.getText().toString());
                    values.put(CalendarContract.Events.CALENDAR_ID, calId);
                    values.put(CalendarContract.Events.EVENT_TIMEZONE, dateAndTime.getTimeZone().getDisplayName());
                    values.put(CalendarContract.Events.DESCRIPTION, comentarios.getText().toString());
                    values.put(CalendarContract.Events.EVENT_LOCATION, direccion.getText().toString());

                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_DENIED) {
                        Uri uri =
                                getActivity().getContentResolver().
                                        insert(CalendarContract.Events.CONTENT_URI, values);
                        long eventId = new Long(uri.getLastPathSegment());

                        setReminder(getActivity().getContentResolver(), eventId, 60);
                    }
                }
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // pass
            }
        });


        t = new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker timePicker, int h, int m) {
                if (timePicker.isShown()) {
                    dateAndTime.set(Calendar.HOUR_OF_DAY, h);
                    dateAndTime.set(Calendar.MINUTE, m);

                    builder.create().show();
                }
            }
        };

        calendarView = (MaterialCalendarView) view.findViewById(R.id.calendarView);
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                dateAndTime.set(Calendar.YEAR, date.getYear());
                dateAndTime.set(Calendar.MONTH, date.getMonth());
                dateAndTime.set(Calendar.DAY_OF_MONTH, date.getDay());

                new TimePickerDialog(getContext(), t,
                        dateAndTime.get(Calendar.HOUR_OF_DAY),
                        dateAndTime.get(Calendar.MINUTE),
                        true).show();
            }
        });

        String[] proj = new String[]{
                CalendarContract.Events._ID,
                CalendarContract.Events.DTSTART,
                CalendarContract.Events.TITLE};
        String sel =
                CalendarContract.Events.CALENDAR_ID +
                        " = ? ";
        String[] selArgs = {String.valueOf(edientes_utils.getCalendarId(getActivity()))};
        Cursor cursor =
                getActivity().getContentResolver().
                        query(
                                CalendarContract.Events.CONTENT_URI,
                                proj,
                                sel,
                                selArgs,
                                null);
        ArrayList<model_cdientes> acd = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(cursor.getLong(1));

                acd.add(new model_cdientes(cursor.getLong(0), calendar.getTime().toString(), cursor.getString(2)));
            } while (cursor.moveToNext());
        }
        viewPager.setAdapter(new cdientes_adapter(getActivity(), acd));


        return view;
    }

    public void setReminder(ContentResolver cr, long eventID, int timeBefore) {
        try {
            ContentValues values = new ContentValues();
            values.put(CalendarContract.Reminders.MINUTES, timeBefore);
            values.put(CalendarContract.Reminders.EVENT_ID, eventID);
            values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
                Uri uri = cr.insert(CalendarContract.Reminders.CONTENT_URI, values);
                Cursor c = CalendarContract.Reminders.query(cr, eventID,
                        new String[]{CalendarContract.Reminders.MINUTES});
                if (c.moveToFirst()) {
                    System.out.println("calendar"
                            + c.getInt(c.getColumnIndex(CalendarContract.Reminders.MINUTES)));
                }
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
