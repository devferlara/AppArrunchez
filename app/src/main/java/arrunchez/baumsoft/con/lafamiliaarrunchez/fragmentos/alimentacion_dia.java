package arrunchez.baumsoft.con.lafamiliaarrunchez.fragmentos;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import arrunchez.baumsoft.con.lafamiliaarrunchez.R;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.Calificaciones;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.CalificacionesDao;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.Calimentos;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.CalimentosDao;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.DaoMaster;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.DaoSession;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.Participantes;
import arrunchez.baumsoft.con.lafamiliaarrunchez.helpers.katana;
import arrunchez.baumsoft.con.lafamiliaarrunchez.puntuacion_dia_alimentacion;
import arrunchez.baumsoft.con.lafamiliaarrunchez.tabbed.cuestionario;
import de.greenrobot.dao.query.Join;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by dayessi on 11/04/16.
 */
public class alimentacion_dia extends Fragment {

    private CheckBox check_agua, check_frutas, check_leche,
            check_proteinas, check_cereales, check_postres;

    private ImageView image_agua, image_frutas, image_leche,
            image_proteinas, image_cereales, image_postres, img_avatar;

    private LinearLayout calificados, calificar, guardar_calificacion;

    private TextView textGuardar, calificandoa;

    private SQLiteDatabase db;
    private ArrayList<Calificaciones> carry = new ArrayList<>();

    private Map<String, Integer> map = new HashMap<String, Integer>();

    private boolean isValid = false;

    private CalimentosDao cadao;
    private CalimentosDao cmedao;
    private DaoSession daoSession;
    private int total_final, total_logrado;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_cuestionario, container, false);

        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText("Alimentación del día");

        carry.clear();

        Button guardar_ = (Button) rootView.findViewById(R.id.guardar_);
        img_avatar = (ImageView) rootView.findViewById(R.id.img_avatar);
        textGuardar = (TextView) rootView.findViewById(R.id.textGuardar);
        calificandoa = (TextView) rootView.findViewById(R.id.section_label);

        calificados = (LinearLayout) rootView.findViewById(R.id.calificados);
        calificar = (LinearLayout) rootView.findViewById(R.id.calificar);
        guardar_calificacion = (LinearLayout) rootView.findViewById(R.id.guardar_calificacion);

        Button ver_puntuacion = (Button) rootView.findViewById(R.id.ver_puntuacion);
        ver_puntuacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), puntuacion_dia_alimentacion.class);
                startActivity(intent);
            }
        });

        total_final = 0;
        total_logrado = 0;

        map.put("Papa", R.drawable.personaje1);
        map.put("mama", R.drawable.personaje2);
        map.put("niño", R.drawable.personaje3);
        map.put("abuela", R.drawable.personaje4);
        map.put("hada", R.drawable.personaje5);

        check_agua = (CheckBox) rootView.findViewById(R.id.check_agua);
        check_frutas = (CheckBox) rootView.findViewById(R.id.check_frutas);
        check_leche = (CheckBox) rootView.findViewById(R.id.check_leche);
        check_proteinas = (CheckBox) rootView.findViewById(R.id.check_proteinas);
        check_cereales = (CheckBox) rootView.findViewById(R.id.check_cereales);
        check_postres = (CheckBox) rootView.findViewById(R.id.check_postres);


        image_agua = (ImageView) rootView.findViewById(R.id.imagen_agua);
        image_frutas = (ImageView) rootView.findViewById(R.id.imagen_frutas);
        image_leche = (ImageView) rootView.findViewById(R.id.imagen_leche);
        image_proteinas = (ImageView) rootView.findViewById(R.id.imagen_huevo);
        image_cereales = (ImageView) rootView.findViewById(R.id.imagen_harinas);
        image_postres = (ImageView) rootView.findViewById(R.id.imagen_postres);


        image_agua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_agua.isChecked()) {
                    check_agua.setChecked(false);
                } else {
                    check_agua.setChecked(true);
                }
                YoYo.with(Techniques.FlipInX)
                        .duration(500)
                        .playOn(image_agua);
            }
        });

        image_frutas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_frutas.isChecked()) {
                    check_frutas.setChecked(false);
                } else {
                    check_frutas.setChecked(true);
                }
                YoYo.with(Techniques.FlipInX)
                        .duration(500)
                        .playOn(image_frutas);
            }
        });

        image_leche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_leche.isChecked()) {
                    check_leche.setChecked(false);
                } else {
                    check_leche.setChecked(true);
                }
                YoYo.with(Techniques.FlipInX)
                        .duration(500)
                        .playOn(image_leche);
            }
        });

        image_proteinas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_proteinas.isChecked()) {
                    check_proteinas.setChecked(false);
                } else {
                    check_proteinas.setChecked(true);
                }
                YoYo.with(Techniques.FlipInX)
                        .duration(500)
                        .playOn(image_proteinas);
            }
        });

        image_cereales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_cereales.isChecked()) {
                    check_cereales.setChecked(false);
                } else {
                    check_cereales.setChecked(true);
                }
                YoYo.with(Techniques.FlipInX)
                        .duration(500)
                        .playOn(image_cereales);
            }
        });

        image_postres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_postres.isChecked()) {
                    check_postres.setChecked(false);
                } else {
                    check_postres.setChecked(true);
                }
                YoYo.with(Techniques.FlipInX)
                        .duration(500)
                        .playOn(image_postres);
            }
        });


        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getContext(), "arrunchez-db", null);
        db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        CalificacionesDao cdao = daoSession.getCalificacionesDao();
                        QueryBuilder<Calificaciones> qbca = cdao.queryBuilder();
                        qbca.join(CalificacionesDao.Properties.Participante_id, Participantes.class);
                        qbca.where(CalificacionesDao.Properties.Date.eq(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));

                        cadao = daoSession.getCalimentosDao();

                        for (Calificaciones c : qbca.list()) {

                            QueryBuilder<Calimentos> qbcl = cadao.queryBuilder();
                            Join j = qbcl.join(CalimentosDao.Properties.Calificacion_id, Calificaciones.class);
                            j.where(CalificacionesDao.Properties.Date.eq(new SimpleDateFormat("yyyy-MM-dd").format(new Date())),
                                    CalificacionesDao.Properties.Participante_id.eq(c.getParticipante_id()));
                            if (qbcl.count() == 0) {
                                carry.add(c);
                            }
                        }

                        loadCuestionario();
                    }
                },
                800);


        guardar_calificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Deseas guardar estas calificaciones")
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    cadao.insert(new Calimentos(null, check_agua.isChecked(), carry.get(0).getId(), 1));
                                    cadao.insert(new Calimentos(null, check_frutas.isChecked(), carry.get(0).getId(), 2));
                                    cadao.insert(new Calimentos(null, check_leche.isChecked(), carry.get(0).getId(), 3));
                                    cadao.insert(new Calimentos(null, check_proteinas.isChecked(), carry.get(0).getId(), 4));
                                    cadao.insert(new Calimentos(null, check_cereales.isChecked(), carry.get(0).getId(), 5));
                                    cadao.insert(new Calimentos(null, check_postres.isChecked(), carry.get(0).getId(), 6));
                                    isValid = false;
                                    carry.remove(0);
                                    loadCuestionario();
                                    resetCuestionario();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User cancelled the dialog
                                }
                            });
                    // Create the AlertDialog object and return it
                    builder.create();
                    builder.show();
                } else {
                    Toast.makeText(getContext(), "Ya terminaste de calificar", Toast.LENGTH_LONG).show();
                }
            }
        });

        LinearLayout pasar2 = (LinearLayout) rootView.findViewById(R.id.pasar2);
        pasar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((cuestionario) getContext()).cambiar(2);
            }
        });

        //db.close();

        return rootView;
    }

    public void resetCuestionario() {
        check_agua.setChecked(false);
        check_frutas.setChecked(false);
        check_leche.setChecked(false);
        check_proteinas.setChecked(false);
        check_cereales.setChecked(false);
        check_postres.setChecked(false);
    }

    public void loadCuestionario() {

        if (carry.size() > 0) {
            isValid = true;
            img_avatar.setImageResource(map.get(carry.get(0).getParticipantes().getAvatars().getAvatar()));
            calificandoa.setText("Calificando a: " + carry.get(0).getParticipantes().getParticipante());
            calificados.setVisibility(View.GONE);
            calificar.setVisibility(View.VISIBLE);
        } else {
            calificar.setVisibility(View.GONE);
            calificados.setVisibility(View.VISIBLE);
            textGuardar.setText("Continuar");
            reportar();
        }
    }


    public void reportar() {
        cmedao = daoSession.getCalimentosDao();
        QueryBuilder<Calimentos> qbcm = cmedao.queryBuilder();
        Join cmjoin = qbcm.join(CalimentosDao.Properties.Calificacion_id, Calificaciones.class);
        cmjoin.where(CalificacionesDao.Properties.Date.eq(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));

        if ((daoSession.getParticipantesDao().queryBuilder().count() * 6) == qbcm.count()) {

            int bandera_inicio_true = 0;
            String actual = "";
            Boolean c_1, c_2, c_3, c_4, c_5, c_6;
            c_1 = c_2 = c_3 = c_4 = c_5 = c_6 = false;

            for (Calimentos cmentos : qbcm.list()) {
                total_final++;
                if (bandera_inicio_true == 0) {
                    actual = cmentos.getCalificaciones().getParticipantes().getParticipante();
                }

                if (!actual.equals(cmentos.getCalificaciones().getParticipantes().getParticipante())) {
                    actual = cmentos.getCalificaciones().getParticipantes().getParticipante();
                }

                if (cmentos.getEstado() == true) {
                    total_logrado++;
                }

                bandera_inicio_true = 1;
            }


            float porcentaje = (total_logrado * 100) / total_final;
            katana kata = new katana();
            if (porcentaje <= 30) {
                ((cuestionario) getContext()).prender("7");
                kata.saveScore(getActivity(), 1, "7");
            }

            if (porcentaje > 30 && porcentaje < 80) {
                ((cuestionario) getContext()).prender("4");
                kata.saveScore(getActivity(), 1, "4");
            }

            if (porcentaje >= 80) {
                ((cuestionario) getContext()).prender("1");
                kata.saveScore(getActivity(), 1, "1");
            }

        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (this.isVisible()) {
            if (isVisibleToUser) {
                ((cuestionario) getActivity()).colorToolbar(new ColorDrawable(0xFF7A41A4));

                SharedPreferences prefs = getActivity().getSharedPreferences("arrunchez.baumsoft.con.lafamiliaarrunchez", getActivity().MODE_PRIVATE);
                if (prefs.getBoolean("mostrarali", true)) {
                    prefs.edit().putBoolean("mostrarali", false).commit();
                    Toast.makeText(getActivity(), "Vas a calificar alimentación de los participantes", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

}