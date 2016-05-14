package arrunchez.baumsoft.con.lafamiliaarrunchez.fragmentos;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import arrunchez.baumsoft.con.lafamiliaarrunchez.R;
import arrunchez.baumsoft.con.lafamiliaarrunchez.adapters.calificacion_dientes_adapter;
import arrunchez.baumsoft.con.lafamiliaarrunchez.adapters.ladientes_adapter;
import arrunchez.baumsoft.con.lafamiliaarrunchez.cuento;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.Calidientes;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.CalidientesDao;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.Calificaciones;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.CalificacionesDao;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.Calimentos;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.CalimentosDao;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.DaoMaster;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.DaoSession;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.Participantes;
import arrunchez.baumsoft.con.lafamiliaarrunchez.helpers.katana;
import arrunchez.baumsoft.con.lafamiliaarrunchez.models.ladientes;
import arrunchez.baumsoft.con.lafamiliaarrunchez.models.model_dientes;
import arrunchez.baumsoft.con.lafamiliaarrunchez.puntuacion_dia_alimentacion;
import arrunchez.baumsoft.con.lafamiliaarrunchez.puntuacion_dia_cepillado;
import arrunchez.baumsoft.con.lafamiliaarrunchez.tabbed.cuestionario;
import de.greenrobot.dao.query.Join;
import de.greenrobot.dao.query.QueryBuilder;


public class lavado_dientes extends Fragment {

    private SQLiteDatabase db;
    private ListView listLaDientes;
    LinearLayout cuento_abrir;
    private Boolean isValid = false;
    public ArrayList<ladientes> lds = new ArrayList<>();
    private CalidientesDao cdidao;
    private LinearLayout cepillar, cepillados;
    private DaoSession daoSession;
    private CalimentosDao cmedao;
    private int total_final1, total_logrado1, total_final2, total_logrado2;
    private float porcentaje_f_1, porcentaje_f_2;
    private String fecha_inicial;
    private Boolean bandera_fecha, bandera_leer_cuento, bandera_llenar_alimentos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lavado_dientes, container, false);

        listLaDientes = (ListView) view.findViewById(R.id.listLaDientes);
        listLaDientes.setAdapter(null);
        fecha_inicial = "";
        bandera_fecha = false;
        bandera_leer_cuento = false;
        bandera_llenar_alimentos = false;
        cuento_abrir = (LinearLayout) view.findViewById(R.id.abrircuento);

        cepillar = (LinearLayout) view.findViewById(R.id.cepillar);
        cepillados = (LinearLayout) view.findViewById(R.id.cepillados);

        LinearLayout paso2 = (LinearLayout) view.findViewById(R.id.pasar2);

        // conexion con la base de datos
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getContext(), "arrunchez-db", null);
        db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();

        Button ver_puntuacion = (Button) view.findViewById(R.id.ver_puntuacion);
        ver_puntuacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), puntuacion_dia_cepillado.class);
                startActivity(intent);
            }
        });

        // validamos si ya esta calificado
        cdidao = daoSession.getCalidientesDao();
        QueryBuilder<Calidientes> qbcd = cdidao.queryBuilder();
        Join cjoin = qbcd.join(CalidientesDao.Properties.Calificacion_id, Calificaciones.class);
        cjoin.where(CalificacionesDao.Properties.Date.eq(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));


        if (qbcd.count() > 0) {
            cepillados.setVisibility(View.VISIBLE);
            cepillar.setVisibility(View.GONE);
            reportar_();
        } else {

            isValid = true;
            lds.clear();

            CalificacionesDao cdao = daoSession.getCalificacionesDao();
            QueryBuilder<Calificaciones> qbca = cdao.queryBuilder();
            qbca.join(CalificacionesDao.Properties.Participante_id, Participantes.class);
            qbca.where(CalificacionesDao.Properties.Date.eq(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));

            for (Calificaciones c : qbca.list()) {
                lds.add(new ladientes(c.getParticipantes().getParticipante(), c.getParticipantes().getAvatars().getAvatar(),
                        new Calidientes(null, false, c.getId())));
            }

            listLaDientes.setAdapter(new ladientes_adapter(getActivity(), lds));

            cepillar.setVisibility(View.VISIBLE);
            cepillados.setVisibility(View.GONE);

            listLaDientes.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    listLaDientes.getParent().requestDisallowInterceptTouchEvent(
                            true);

                    int action = event.getActionMasked();

                    switch (action) {
                        case MotionEvent.ACTION_UP:
                            listLaDientes.getParent()
                                    .requestDisallowInterceptTouchEvent(false);
                            break;
                    }

                    return false;
                }
            });
        }


        cuento_abrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Deseas guardar estas calificaciones")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                for (ladientes l : lds) {
                                    cdidao.insert(l.cdientes);
                                    Log.d("Avatar", l.getNombres());
                                    Log.d("Estado", String.valueOf(l.cdientes.getEstado()));
                                }
                                cepillados.setVisibility(View.VISIBLE);
                                cepillar.setVisibility(View.GONE);
                                reportar_();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                // Create the AlertDialog object and return it
                builder.create();
                builder.show();
            }
        });

        paso2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bandera_llenar_alimentos){
                    if (bandera_leer_cuento) {
                        Intent intento = new Intent(getActivity(), cuento.class);
                        intento.putExtra("fecha", fecha_inicial);
                        Log.d("fecha enviar", fecha_inicial);
                        getActivity().startActivityForResult(intento, 1);
                    } else {
                        Toast.makeText(getActivity(), "No pasaste, intenta mañana.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Completa el formulario de alimentos.", Toast.LENGTH_SHORT).show();
                }



            }
        });

        //db.close();

        return view;
    }

    public void reportar_() {

        total_final1 = 0;
        total_logrado1 = 0;
        total_final2 = 0;
        total_logrado2 = 0;
        porcentaje_f_1 = 0;
        porcentaje_f_2 = 0;


        cdidao = daoSession.getCalidientesDao();
        QueryBuilder<Calidientes> qbcd = cdidao.queryBuilder();
        Join cjoin = qbcd.join(CalidientesDao.Properties.Calificacion_id, Calificaciones.class);
        cjoin.where(CalificacionesDao.Properties.Date.eq(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));

        if (qbcd.count() > 0) {
            for (Calidientes cd : qbcd.list()) {
                total_final1++;
                if (cd.getEstado() == true) {
                    total_logrado1++;
                }
            }
        }

        cmedao = daoSession.getCalimentosDao();
        QueryBuilder<Calimentos> qbcm = cmedao.queryBuilder();
        Join cmjoin = qbcm.join(CalimentosDao.Properties.Calificacion_id, Calificaciones.class);
        cmjoin.where(CalificacionesDao.Properties.Date.eq(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));

        int bandera_inicio_true = 0;
        String actual = "";
        Boolean c_1, c_2, c_3, c_4, c_5, c_6;
        c_1 = c_2 = c_3 = c_4 = c_5 = c_6 = false;

        for (Calimentos cmentos : qbcm.list()) {
            total_final2++;
            if (bandera_inicio_true == 0) {
                actual = cmentos.getCalificaciones().getParticipantes().getParticipante();
            }

            if (!actual.equals(cmentos.getCalificaciones().getParticipantes().getParticipante())) {
                actual = cmentos.getCalificaciones().getParticipantes().getParticipante();
            }

            if (cmentos.getEstado() == true) {
                total_logrado2++;
            }

            bandera_inicio_true = 1;
        }



        if (total_final1 != 0 && total_final2 != 0) {

            bandera_llenar_alimentos = true;
            katana kata = new katana();
            /* seccion 1 */
            float porcentaje1 = (total_logrado1 * 100) / total_final1;
            porcentaje_f_1 = porcentaje1;
            if (porcentaje1 <= 30) {
                ((cuestionario) getContext()).prender("8 ");
                //kata.saveScore(getContext(), 1, "8");
            }

            if (porcentaje1 > 30 && porcentaje1 < 80) {
                ((cuestionario) getContext()).prender("5");
                //kata.saveScore(getContext(), 1, "8");
            }

            if (porcentaje1 >= 80) {
                ((cuestionario) getContext()).prender("2");
            }
            /* seccion 1 */

            /* seccion 2 */
            float porcentaje2 = (total_logrado2 * 100) / total_final2;
            porcentaje_f_2 = porcentaje2;

            if (porcentaje2 <= 30) {
                ((cuestionario) getContext()).prender("7");
                kata.saveScore(getActivity(), 2, "7");
            }

            if (porcentaje2 > 30 && porcentaje2 < 80) {
                ((cuestionario) getContext()).prender("4");
                kata.saveScore(getActivity(), 2, "4");
            }

            if (porcentaje2 >= 80) {
                ((cuestionario) getContext()).prender("1");
                kata.saveScore(getActivity(), 2, "1");
            }
            /* seccion 2 */

            /* seccion validacion */
            if (porcentaje_f_1 >= 80 && porcentaje_f_2 >= 80) {
                bandera_leer_cuento = true;
            } else {
                bandera_leer_cuento = false;
                katana kati = new katana();
                kati.saveScore(getActivity(), 3, "9");
                ((cuestionario) getActivity()).prender("9");
            }
            /* seccion validacion */


        } else {
            bandera_llenar_alimentos = false;
        }





    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (this.isVisible()) {
            if (isVisibleToUser) {
                ((cuestionario) getActivity()).colorToolbar(new ColorDrawable(0xFF397BA2));

                SharedPreferences prefs = getActivity().getSharedPreferences("arrunchez.baumsoft.con.lafamiliaarrunchez", getActivity().MODE_PRIVATE);
                if (prefs.getBoolean("mostrarcepi", true)) {
                    prefs.edit().putBoolean("mostrarcepi", false).commit();
                    Toast.makeText(getActivity(), "Vas a calificar cepillado de los participantes", Toast.LENGTH_SHORT).show();
                }

                reportar_();
            }
        }
    }


}
