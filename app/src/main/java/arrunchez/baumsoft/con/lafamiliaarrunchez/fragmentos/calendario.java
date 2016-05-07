package arrunchez.baumsoft.con.lafamiliaarrunchez.fragmentos;

import android.Manifest;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.TimeZone;

import arrunchez.baumsoft.con.lafamiliaarrunchez.Inicio;
import arrunchez.baumsoft.con.lafamiliaarrunchez.R;
import arrunchez.baumsoft.con.lafamiliaarrunchez.adapters.cdientes_adapter;
import arrunchez.baumsoft.con.lafamiliaarrunchez.helpers.EventDecorator;
import arrunchez.baumsoft.con.lafamiliaarrunchez.helpers.edientes_utils;
import arrunchez.baumsoft.con.lafamiliaarrunchez.models.model_cdientes;


public class calendario extends Fragment {
    private MaterialCalendarView calendarView;

    Calendar dateAndTime = Calendar.getInstance();

    TimePickerDialog.OnTimeSetListener t;


    private ViewPager viewPager;

    private HashSet<CalendarDay> dates = new HashSet<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_calendario, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);

        ((Inicio) getActivity()).getSupportActionBar().setTitle("Calendario");

        t = new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker timePicker, int h, int m) {
                if (timePicker.isShown()) {
                    dateAndTime.set(Calendar.HOUR_OF_DAY, h);
                    dateAndTime.set(Calendar.MINUTE, m);

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    LayoutInflater i = (LayoutInflater) getActivity().getLayoutInflater();
                    final View v = (View) i.inflate(R.layout.form_calendar, null);
                    builder.setView(v);
                    // Add the buttons
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            EditText nombres = (EditText) v.findViewById(R.id.nombre);
                            EditText direccion = (EditText) v.findViewById(R.id.direccion);
                            EditText comentarios = (EditText) v.findViewById(R.id.comentarios);

                            if(nombres.getText().length() != 0 && direccion.getText().length() != 0 && comentarios.getText().length() != 0){
                                long calId = edientes_utils.getCalendarId(getActivity());
                                if (calId != -1) {
                                    ContentValues values = new ContentValues();
                                    values.put(CalendarContract.Events.DTSTART, dateAndTime.getTimeInMillis());
                                    values.put(CalendarContract.Events.DTEND, dateAndTime.getTimeInMillis());

                                    values.put(CalendarContract.Events.TITLE, nombres.getText().toString());
                                    values.put(CalendarContract.Events.CALENDAR_ID, calId);
                                    values.put(CalendarContract.Events.EVENT_TIMEZONE, dateAndTime.getTimeZone().getDisplayName());
                                    values.put(CalendarContract.Events.DESCRIPTION, direccion.getText().toString());
                                    values.put(CalendarContract.Events.EVENT_LOCATION, comentarios.getText().toString());

                                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_DENIED) {
                                        Uri uri =
                                                getActivity().getContentResolver().
                                                        insert(CalendarContract.Events.CONTENT_URI, values);
                                        long eventId = new Long(uri.getLastPathSegment());

                                        setReminder(getActivity().getContentResolver(), eventId, 60);

                                        Toast.makeText(getActivity(), "Recordatorio agregado correctamente.", Toast.LENGTH_LONG).show();

                                        ((Inicio) getActivity()).iniciarDesdeFragment(7);

                                    }
                                    View view = getActivity().getCurrentFocus();
                                    if (view != null) {
                                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                    }
                                }
                            } else {
                                Toast.makeText(getActivity(), "Completa todos los campos entes de guardar.", Toast.LENGTH_LONG).show();
                                return;
                            }


                        }
                    });
                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // pass
                        }
                    });
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
                CalendarContract.Events.TITLE,
                CalendarContract.Events.DESCRIPTION,
                CalendarContract.Events.EVENT_LOCATION
        };
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

                String weekDay = "";
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

                if (Calendar.MONDAY == dayOfWeek) {
                    weekDay = "Lunes";
                } else if (Calendar.TUESDAY == dayOfWeek) {
                    weekDay = "Martes";
                } else if (Calendar.WEDNESDAY == dayOfWeek) {
                    weekDay = "Miércoles";
                } else if (Calendar.THURSDAY == dayOfWeek) {
                    weekDay = "Jueves";
                } else if (Calendar.FRIDAY == dayOfWeek) {
                    weekDay = "Viernes";
                } else if (Calendar.SATURDAY == dayOfWeek) {
                    weekDay = "Sábado";
                } else if (Calendar.SUNDAY == dayOfWeek) {
                    weekDay = "Domingo";
                }


                int hora_tarde = calendar.get(Calendar.DAY_OF_MONTH);
                if(calendar.get(Calendar.HOUR_OF_DAY) >12){

                    switch (calendar.get(Calendar.HOUR_OF_DAY)){
                        case 13:
                            hora_tarde = 1;
                            break;
                        case 14:
                            hora_tarde = 2;
                            break;
                        case 15:
                            hora_tarde = 3;
                            break;
                        case 16:
                            hora_tarde = 4;
                            break;
                        case 17:
                            hora_tarde = 5;
                            break;
                        case 18:
                            hora_tarde = 6;
                            break;
                        case 19:
                            hora_tarde = 7;
                            break;
                        case 20:
                            hora_tarde = 8;
                            break;
                        case 21:
                            hora_tarde = 9;
                            break;
                        case 22:
                            hora_tarde = 10;
                            break;
                        case 23:
                            hora_tarde = 11;
                            break;

                    }
                }

                String titulo =  weekDay + " " + calendar.get(Calendar.DAY_OF_MONTH) + " a las " + hora_tarde + ":" + calendar.get(Calendar.MINUTE);

                if(calendar.get(Calendar.AM_PM) == 0){
                    titulo = titulo + "AM";
                } else {
                    titulo = titulo + "PM";
                }

                Log.d("Fecha", calendar.getTime().toString());
                acd.add(new model_cdientes(cursor.getLong(0), titulo, cursor.getString(2), cursor.getString(3), cursor.getString(4)));
                dates.add(new CalendarDay(calendar));

            } while (cursor.moveToNext());

        }
        calendarView.addDecorator(new EventDecorator(Color.BLUE, dates));

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

    public void mostrarPopUp(int posicion){

    }


}
