package arrunchez.baumsoft.con.lafamiliaarrunchez.fragmentos;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.TextViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.db.chart.Tools;
import com.db.chart.model.LineSet;
import com.db.chart.model.Point;
import com.db.chart.view.LineChartView;
import com.db.chart.view.XController;
import com.db.chart.view.YController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import arrunchez.baumsoft.con.lafamiliaarrunchez.R;
import arrunchez.baumsoft.con.lafamiliaarrunchez.adapters.calificacion_alimentos_adapter;
import arrunchez.baumsoft.con.lafamiliaarrunchez.adapters.puntuacion_diaria_adapter;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.Calidientes;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.CalidientesDao;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.Calificaciones;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.CalificacionesDao;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.Calimentos;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.CalimentosDao;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.DaoMaster;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.DaoSession;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.Participantes;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.ParticipantesDao;
import arrunchez.baumsoft.con.lafamiliaarrunchez.models.model_alimentos;
import arrunchez.baumsoft.con.lafamiliaarrunchez.models.model_calificacion_individual;
import arrunchez.baumsoft.con.lafamiliaarrunchez.tabbed.cuestionario;
import de.greenrobot.dao.query.Join;
import de.greenrobot.dao.query.QueryBuilder;


public class puntuacion extends Fragment {

    private SQLiteDatabase db;
    private CalimentosDao cmedao;
    private ListView lista;
    private int total_familia;
    private String fecha_inicial;
    private Boolean bandera_fecha;
    private CalidientesDao cdidao;
    public ArrayList<model_calificacion_individual> array_modelos = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_puntuacion, container, false);

        total_familia = 0;
        bandera_fecha = false;
        fecha_inicial = "";

        // conexion con la base de datos
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getActivity(), "arrunchez-db", null);
        db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();

        array_modelos.clear();


        //Loop personas
        ParticipantesDao participantes_ = daoSession.getParticipantesDao();
        QueryBuilder<Participantes> parti = participantes_.queryBuilder();
        for (Participantes participante : parti.list()) {


            /* Seccion Alimentacion */
            cmedao = daoSession.getCalimentosDao();
            QueryBuilder<Calimentos> qbcm = cmedao.queryBuilder();
            Join cmjoin = qbcm.join(CalimentosDao.Properties.Calificacion_id, Calificaciones.class);
            cmjoin.where(CalificacionesDao.Properties.Participante_id.eq(participante.getId()));

            int total_logrado_ = 0;
            Boolean bandera_inicio = true;
            String fecha_actual = "";
            List<Integer> resultados = new ArrayList<Integer>();

            for (Calimentos cmentos : qbcm.list()) {

                if (bandera_inicio) {
                    fecha_actual = cmentos.getCalificaciones().getDate();
                    bandera_inicio = false;
                }

                if (!fecha_actual.equals(cmentos.getCalificaciones().getDate())) {
                    fecha_actual = cmentos.getCalificaciones().getDate();
                }

                if (cmentos.getEstado() == true) {
                    total_logrado_++;
                    total_familia++;
                }

                if (cmentos.getAlimentos().getId() == 6) {
                    resultados.add(total_logrado_);
                    total_logrado_ = 0;
                }

                if (!bandera_fecha) {
                    fecha_inicial = cmentos.getCalificaciones().getDate();
                    bandera_fecha = true;
                }

            }
            /* Seccion alimentacion */



            if (resultados.size() != 0) {
                array_modelos.add(new model_calificacion_individual(participante.getId().intValue(), resultados, 0));
            }


        }

        TextView imagen_none = (TextView) view.findViewById(R.id.texto_puntuacion);
        lista = (ListView) view.findViewById(R.id.lista);
        Log.d("Size array", "- " + array_modelos.size());
        if (array_modelos.size() != 0) {
            lista.setAdapter(null);
            lista.setAdapter(new puntuacion_diaria_adapter(getActivity(), array_modelos));
            lista.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    lista.getParent().requestDisallowInterceptTouchEvent(
                            true);

                    int action = event.getActionMasked();

                    switch (action) {
                        case MotionEvent.ACTION_UP:
                            lista.getParent()
                                    .requestDisallowInterceptTouchEvent(false);
                            break;
                    }

                    return false;
                }
            });
            lista.setVisibility(View.VISIBLE);
            imagen_none.setVisibility(View.GONE);
        } else {
            lista.setVisibility(View.GONE);
            imagen_none.setVisibility(View.VISIBLE);
        }

        //db.close();

        Button btn_info = (Button) view.findViewById(R.id.btn_info);
        btn_info.setText("Total: " + total_familia);

        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        String inputString1 = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
        String inputString2 = "";
        if (fecha_inicial.equals("")){
            inputString2 = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
        } else {
            inputString2 = fecha_inicial;
        }

        try {
            Date date1 = myFormat.parse(inputString1);
            Date date2 = myFormat.parse(inputString2);
            long diff = date2.getTime() - date1.getTime();
            int dias = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

            ImageView dia1 = (ImageView) view.findViewById(R.id.dia1);
            ImageView dia2 = (ImageView) view.findViewById(R.id.dia2);
            ImageView dia3 = (ImageView) view.findViewById(R.id.dia3);
            ImageView dia4 = (ImageView) view.findViewById(R.id.dia4);
            ImageView dia5 = (ImageView) view.findViewById(R.id.dia5);
            ImageView dia6 = (ImageView) view.findViewById(R.id.dia6);

            //System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
            Log.d("Dias pasados", " - " + dias);

            if(dias == 0){
                chequear(dia1);
            }

            if(dias == -1){
                chequear(dia1);
                chequear(dia2);
            }

            if(dias == -2){
                chequear(dia1);
                chequear(dia2);
                chequear(dia3);
            }

            if(dias == -3){
                chequear(dia1);
                chequear(dia2);
                chequear(dia3);
                chequear(dia4);
            }

            if(dias == -4){
                chequear(dia1);
                chequear(dia2);
                chequear(dia3);
                chequear(dia4);
                chequear(dia5);
            }

            if(dias <= -5){
                chequear(dia1);
                chequear(dia2);
                chequear(dia3);
                chequear(dia4);
                chequear(dia5);
                chequear(dia6);
            }




        } catch (ParseException e) {
            e.printStackTrace();
        }

        return view;

    }

    private void chequear(ImageView elemento){
        elemento.setImageResource(R.drawable.checked);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (this.isVisible()) {
            if (isVisibleToUser) {
                ((cuestionario) getActivity()).colorToolbar(new ColorDrawable(0xFFFF8C09));
            }
        }
    }

}