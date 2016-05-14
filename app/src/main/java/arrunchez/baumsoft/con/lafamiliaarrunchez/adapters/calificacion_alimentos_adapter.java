package arrunchez.baumsoft.con.lafamiliaarrunchez.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.media.Image;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.easing.linear.Linear;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import arrunchez.baumsoft.con.lafamiliaarrunchez.R;
import arrunchez.baumsoft.con.lafamiliaarrunchez.models.ladientes;
import arrunchez.baumsoft.con.lafamiliaarrunchez.models.model_alimentos;
import arrunchez.baumsoft.con.lafamiliaarrunchez.puntuacion_dia_alimentacion;


/**
 * Created by baumsoft on 22/04/16.
 */
public class calificacion_alimentos_adapter extends ArrayAdapter<model_alimentos> {

    private Activity activity;
    private ArrayList<model_alimentos> lds = new ArrayList<>();
    private List<Integer> map;

    public calificacion_alimentos_adapter(Activity activity, ArrayList<model_alimentos> ldi) {
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
            convertView = inflator.inflate(R.layout.list_alimento_calificacion, null);


            final model_alimentos modelo = (model_alimentos) getItem(position);
            ImageView imagen_participante = (ImageView) convertView.findViewById(R.id.foto_personaje);
            imagen_participante.setImageResource(map.get(modelo.getId()));

            TextView nombre = (TextView) convertView.findViewById(R.id.nombre_personaje);
            TextView recomendaciones = (TextView) convertView.findViewById(R.id.recomendaciones);

            int logrados = 0;
            int fallados = 0;

            StringBuilder alerta = new StringBuilder("Recuerda incluir lo siguiente en tu alimentación diaria: ");

            if(modelo.get_a1() == true){ logrados++; } else { fallados++; alerta.append("Agua "); }
            if(modelo.get_a2() == true){ logrados++; } else { fallados++; alerta.append(" - Frutas y verduras "); }
            if(modelo.get_a3() == true){ logrados++; } else { fallados++; alerta.append("- Alimentos ricos en calcio "); }
            if(modelo.get_a4() == true){ logrados++; } else { fallados++; alerta.append("- Proteínas "); }
            if(modelo.get_a5() == true){ logrados++; } else { fallados++; alerta.append("- Cereales "); }
            if(modelo.get_a6() == true){ logrados++; } else { fallados++; }

            alerta.append(".");

            LinearLayout lleno = (LinearLayout) convertView.findViewById(R.id.lleno);
            LinearLayout vacio = (LinearLayout) convertView.findViewById(R.id.vacio);
            LinearLayout sombra = (LinearLayout) convertView.findViewById(R.id.sombra_small);


            LinearLayout.LayoutParams layout_logrados = new LinearLayout.LayoutParams(0, 5, logrados);
            lleno.setLayoutParams(layout_logrados);

            LinearLayout.LayoutParams layout_fallados = new LinearLayout.LayoutParams(0, 5, fallados);
            vacio.setLayoutParams(layout_fallados);




            /*  */
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




            if(logrados <= 3){
                lleno.setBackgroundColor(Color.parseColor("#FF0000"));
                sombra.setBackgroundResource(R.drawable.sombra_smallred);
                nombre.setTextColor(activity.getResources().getColor(R.color.blanco));
            }

            if(logrados != 6){
                nombre.setText(modelo.getNombre() + " - Recomendaciones");
            } else {
                nombre.setText(modelo.getNombre() + " - Felicitaciones");
                alerta = new StringBuilder("Felicitaciones, lo hiciste bien.");
            }
            recomendaciones.setText("" + logrados);

            final int resultado_logrados = logrados;
            final String cadena_alerta = alerta.toString();
            sombra.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(resultado_logrados != 6){
                        ((puntuacion_dia_alimentacion) getContext()).toast(position, modelo, cadena_alerta, modelo.getNombre());
                    }
                }
            });

        }
        return convertView;
    }




}
