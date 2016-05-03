package arrunchez.baumsoft.con.lafamiliaarrunchez.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import arrunchez.baumsoft.con.lafamiliaarrunchez.R;
import arrunchez.baumsoft.con.lafamiliaarrunchez.models.ladientes;
import arrunchez.baumsoft.con.lafamiliaarrunchez.models.model_alimentos;
import arrunchez.baumsoft.con.lafamiliaarrunchez.models.model_dientes;
import arrunchez.baumsoft.con.lafamiliaarrunchez.puntuacion_dia_alimentacion;

/**
 * Created by baumsoft on 25/04/16.
 */
public class calificacion_dientes_adapter extends ArrayAdapter<model_dientes> {

    private Activity activity;
    private ArrayList<model_dientes> lds = new ArrayList<>();
    private List<Integer> map;

    public calificacion_dientes_adapter(Activity activity, ArrayList<model_dientes> ldi) {
        super(activity, 0, ldi);
        this.activity = activity;
        this.lds = ldi;

        map = new ArrayList<>();
        map.add(0, R.drawable.personaje1);
        map.add(1, R.drawable.personaje1);
        map.add(2, R.drawable.personaje2);
        map.add(3, R.drawable.personaje3);
        map.add(4, R.drawable.personaje4);
        map.add(5, R.drawable.personaje5);
    }

    @Override
    public int getCount() {
        return this.lds.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflator = activity.getLayoutInflater();
            convertView = inflator.inflate(R.layout.list_dientes_calificacion, null);


            final model_dientes modelo = (model_dientes) getItem(position);
            ImageView imagen_participante = (ImageView) convertView.findViewById(R.id.foto_personaje);
            imagen_participante.setImageResource(map.get(modelo.getId()));

            TextView nombre = (TextView) convertView.findViewById(R.id.nombre_personaje);
            TextView recomendaciones = (TextView) convertView.findViewById(R.id.recomendaciones);

            int logrados = 0;
            int fallados = 0;

            if(modelo.getEstado() == true){ logrados++; } else { fallados++; }

            LinearLayout lleno = (LinearLayout) convertView.findViewById(R.id.lleno);
            LinearLayout vacio = (LinearLayout) convertView.findViewById(R.id.vacio);
            LinearLayout sombra = (LinearLayout) convertView.findViewById(R.id.sombra_small);

            LinearLayout.LayoutParams layout_logrados = new LinearLayout.LayoutParams(0, 5, logrados);
            lleno.setLayoutParams(layout_logrados);

            LinearLayout.LayoutParams layout_fallados = new LinearLayout.LayoutParams(0, 5, fallados);
            vacio.setLayoutParams(layout_fallados);








            LinearLayout lleno_ = (LinearLayout) convertView.findViewById(R.id.lleno_);
            LinearLayout vacio_ = (LinearLayout) convertView.findViewById(R.id.vacio_);

            LinearLayout.LayoutParams layout_logrados_ = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, logrados);
            LinearLayout.LayoutParams layout_fallados_ = new LinearLayout.LayoutParams(0, 25, fallados);

            if(logrados == 0){
                layout_logrados_ = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                lleno_.setGravity(Gravity.LEFT);
                layout_fallados_ = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 5);
            }

            if (logrados == 6){
                layout_logrados_ = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 6);
                layout_fallados_ = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0);
            }

            lleno_.setLayoutParams(layout_logrados_);
            vacio_.setLayoutParams(layout_fallados_);


            if(logrados == 0){
                lleno.setBackgroundColor(Color.parseColor("#FF0000"));
                sombra.setBackgroundResource(R.drawable.sombra_smallred);
                nombre.setTextColor(activity.getResources().getColor(R.color.blanco));
            }

            nombre.setText(modelo.getNombre());
            recomendaciones.setText("" + logrados);

        }
        return convertView;
    }


}
