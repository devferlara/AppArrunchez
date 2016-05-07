package arrunchez.baumsoft.con.lafamiliaarrunchez.adapters;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import arrunchez.baumsoft.con.lafamiliaarrunchez.Inicio;
import arrunchez.baumsoft.con.lafamiliaarrunchez.R;
import arrunchez.baumsoft.con.lafamiliaarrunchez.fragmentos.calendario;
import arrunchez.baumsoft.con.lafamiliaarrunchez.helpers.edientes_utils;
import arrunchez.baumsoft.con.lafamiliaarrunchez.models.model_cdientes;

/**
 * Created by dayessi on 3/05/16.
 */
public class cdientes_adapter extends PagerAdapter {

    private Activity activity;
    private ArrayList<model_cdientes> acd;

    public cdientes_adapter(Activity activity, ArrayList<model_cdientes> acd) {
        this.activity = activity;
        this.acd = acd;
    }

    @Override
    public int getCount() {
        return this.acd.size();
    }

    @Override
    public View instantiateItem(ViewGroup container, final int position) {
        LayoutInflater inflater = (LayoutInflater) activity.getLayoutInflater();

        View view = (View) inflater.inflate(R.layout.list_item_ecalendar, container, false);
        TextView titulo_armado_ = (TextView) view.findViewById(R.id.hora_evento);
        TextView nombre_ = (TextView) view.findViewById(R.id.nombre_evento);
        TextView descripcion_ = (TextView) view.findViewById(R.id.descripcion_evento);

        model_cdientes cd = this.acd.get(position);

        titulo_armado_.setText(cd.getTitulo_armado());
        nombre_.setText(cd.getTitulo());
        descripcion_.setText(cd.getDescripcion());

        final String final_nombre = cd.getTitulo();
        final String final_direccion = cd.getDescripcion();
        final String final_comentarios = cd.getComentarios();

        LinearLayout controlar = (LinearLayout) view.findViewById(R.id.controlar_click);
        controlar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                LayoutInflater i = (LayoutInflater) activity.getLayoutInflater();
                final View g = (View) i.inflate(R.layout.form_calendar_show, null);
                TextView nombre_info = (TextView) g.findViewById(R.id.nombre_info);
                TextView direccion_info = (TextView) g.findViewById(R.id.direccion_info);
                TextView comentarios_info = (TextView) g.findViewById(R.id.comentarios_info);

                nombre_info.setText(final_nombre);
                direccion_info.setText(final_direccion);
                comentarios_info.setText(final_comentarios);
                builder.setView(g);
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                builder.create().show();

            }
        });

        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
