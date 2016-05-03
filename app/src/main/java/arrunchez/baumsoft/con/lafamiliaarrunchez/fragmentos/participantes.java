package arrunchez.baumsoft.con.lafamiliaarrunchez.fragmentos;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import arrunchez.baumsoft.con.lafamiliaarrunchez.Inicio;
import arrunchez.baumsoft.con.lafamiliaarrunchez.R;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.Calificaciones;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.CalificacionesDao;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.Calimentos;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.CalimentosDao;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.DaoMaster;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.DaoSession;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.Participantes;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.ParticipantesDao;
import arrunchez.baumsoft.con.lafamiliaarrunchez.puntuacion_dia_alimentacion;
import arrunchez.baumsoft.con.lafamiliaarrunchez.tabbed.cuestionario;
import de.greenrobot.dao.query.Join;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by dayessi on 11/04/16.
 */
public class participantes extends Fragment {
    SharedPreferences prefs = null;
    private CalimentosDao cadao;
    private ArrayList<Calificaciones> carry = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_participantes, container, false);

        /* Acciones con BD */
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getContext(), "arrunchez-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();

        ParticipantesDao pdao = daoSession.getParticipantesDao();
        CalificacionesDao cd = daoSession.getCalificacionesDao();

        QueryBuilder qb =  cd.queryBuilder();
        qb.where(CalificacionesDao.Properties.Date.eq(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
        long row = qb.count();

        List<Participantes> l = pdao.queryBuilder().list();

        /* Acciones con BD */


        RelativeLayout pasar1 = (RelativeLayout) rootView.findViewById(R.id.pasar1);
        pasar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((cuestionario) getContext()).cambiar(1);
            }
        });

        getActivity().setTitle("El tesoro de la familia Arrunchez");

        prefs = getActivity().getSharedPreferences("arrunchez.baumsoft.con.lafamiliaarrunchez", getActivity().MODE_PRIVATE);

        if (prefs.getBoolean("mostraralert", true)) {
            prefs.edit().putBoolean("mostraralert", false).commit();
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setTitle("Información");
            alertDialogBuilder
                    .setMessage("Felicidades, has creado satisfactoriamente los personajes de este cuento.")
                    .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }


        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("Papa", R.drawable.personaje1);
        map.put("mama", R.drawable.personaje2);
        map.put("niño", R.drawable.personaje3);
        map.put("abuela", R.drawable.personaje4);
        map.put("hada", R.drawable.personaje5);

        int cadena = 0;
            int[] textos = {R.id.text1, R.id.text2, R.id.text3, R.id.text4, R.id.text5};
            int[] personajes = {R.id.per1, R.id.per2, R.id.per3, R.id.per4, R.id.per5};
        for(Participantes  p : l) {

            ImageView imagen = (ImageView) rootView.findViewById(personajes[cadena]);
            imagen.setImageResource(map.get(p.getAvatars().getAvatar()));
            imagen.setVisibility(View.VISIBLE);
            TextView texto = (TextView) rootView.findViewById(textos[cadena]);
            texto.setText(p.getParticipante());
            texto.setVisibility(View.VISIBLE);

            cadena++;

            if(row == 0) {
                cd.insert(new Calificaciones(null, new SimpleDateFormat("yyyy-MM-dd").format(new Date()), p.getId()));
            }


        }

        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (isVisibleToUser) {
                ((cuestionario) getActivity()).colorToolbar(new ColorDrawable(0xFF7A41A4));
            }
        }
    }
}
