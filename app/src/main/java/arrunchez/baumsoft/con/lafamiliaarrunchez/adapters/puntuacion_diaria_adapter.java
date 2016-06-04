package arrunchez.baumsoft.con.lafamiliaarrunchez.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.db.chart.model.LineSet;
import com.db.chart.view.LineChartView;
import com.db.chart.view.XController;
import com.db.chart.view.YController;

import java.util.ArrayList;
import java.util.List;

import arrunchez.baumsoft.con.lafamiliaarrunchez.DaoAPP;
import arrunchez.baumsoft.con.lafamiliaarrunchez.R;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.Participantes;
import arrunchez.baumsoft.con.lafamiliaarrunchez.models.model_alimentos;
import arrunchez.baumsoft.con.lafamiliaarrunchez.models.model_calificacion_individual;
import arrunchez.baumsoft.con.lafamiliaarrunchez.puntuacion_dia_alimentacion;


public class puntuacion_diaria_adapter extends ArrayAdapter<model_calificacion_individual> {

    private Activity activity;
    private ArrayList<model_calificacion_individual> lds = new ArrayList<>();
    private List<Integer> map;

    public puntuacion_diaria_adapter(Activity activity, ArrayList<model_calificacion_individual> ldi) {
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

    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        final model_calificacion_individual modelo = getItem(position);

        LineChartView chart;
        LineSet dataset;

        if (v == null) {
            LayoutInflater vi = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.list_result_item, null);

            chart = (LineChartView) v.findViewById(R.id.linechart);
            dataset = new LineSet();

            for (int i=0; i<modelo.getResultados_alimentos().size(); i++) {
                int suma = modelo.getResultados_alimentos().get(i) + modelo.getResultados_dientes();
                dataset.addPoint(String.valueOf(suma), suma);
            }
            chart.addData(dataset);
            chart.setXAxis(false)
                    .setXLabels(XController.LabelPosition.OUTSIDE)
                    .setYAxis(false)
                    .setYLabels(YController.LabelPosition.NONE);
            dataset.setDotsColor(Color.parseColor("#FEA300"));
            dataset.setDotsStrokeColor(Color.parseColor("#FEA300"));
            dataset.setDotsStrokeThickness(2);
            dataset.setColor(Color.parseColor("#FEA300"));
            chart.setAxisThickness(0);
            chart.setAxisColor(Color.parseColor("#FFFFFF"));
            chart.show();

        }

        ImageView imagen = (ImageView) v.findViewById(R.id.personaje_list);
        Participantes participante = DaoAPP.daoSession.getParticipantesDao().load(Long.valueOf(modelo.getId()));
        imagen.setImageResource(map.get((int) participante.getAvatar_id()));

        Log.d("Id persona", " " + modelo.getId() + " pos " + position);

        return v;
    }


}
