package arrunchez.baumsoft.con.lafamiliaarrunchez.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import arrunchez.baumsoft.con.lafamiliaarrunchez.R;
import arrunchez.baumsoft.con.lafamiliaarrunchez.adapters.calificacion_alimentos_adapter;
import arrunchez.baumsoft.con.lafamiliaarrunchez.enviar_bluetooth;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.CalidientesDao;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.Calificaciones;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.CalificacionesDao;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.Calimentos;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.CalimentosDao;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.DaoMaster;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.DaoSession;
import arrunchez.baumsoft.con.lafamiliaarrunchez.models.model_alimentos;
import de.greenrobot.dao.query.Join;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by baumsoft on 28/04/16.
 */
public class calcular_alimentacion {

    private SQLiteDatabase db;
    private CalidientesDao cdidao;
    private CalimentosDao cmedao;
    private ListView lista;
    public ArrayList<model_alimentos> lds = new ArrayList<>();
    private int total_final, total_logrado;

    public void iniciar(Context contexto){
        total_final = 0;
        total_logrado = 0;

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(contexto, "arrunchez-db", null);
        db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();

        cmedao = daoSession.getCalimentosDao();
        QueryBuilder<Calimentos> qbcm = cmedao.queryBuilder();
        Join cmjoin = qbcm.join(CalimentosDao.Properties.Calificacion_id, Calificaciones.class);
        cmjoin.where(CalificacionesDao.Properties.Date.eq(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));


        if((daoSession.getParticipantesDao().queryBuilder().count() * 6) == qbcm.count()) {

            int bandera_inicio_true = 0;
            String actual = "";
            Boolean c_1, c_2, c_3, c_4, c_5, c_6;
            c_1 = c_2 = c_3 = c_4 = c_5 = c_6 = false;


            for(Calimentos cmentos : qbcm.list()) {
                total_final ++;
                if(bandera_inicio_true == 0){
                    actual = cmentos.getCalificaciones().getParticipantes().getParticipante();
                }

                if(!actual.equals(cmentos.getCalificaciones().getParticipantes().getParticipante())){
                    actual = cmentos.getCalificaciones().getParticipantes().getParticipante();
                    Log.d("Info", actual);
                }

                if(cmentos.getAlimentos().getId() == 1){c_1 = cmentos.getEstado();}
                if(cmentos.getAlimentos().getId() == 2){c_2 = cmentos.getEstado();}
                if(cmentos.getAlimentos().getId() == 3){c_3 = cmentos.getEstado();}
                if(cmentos.getAlimentos().getId() == 4){c_4 = cmentos.getEstado();}
                if(cmentos.getAlimentos().getId() == 5){c_5 = cmentos.getEstado();}
                if(cmentos.getAlimentos().getId() == 6){
                    c_6 = cmentos.getEstado();
                    lds.add(new model_alimentos(c_1, c_2, c_3, c_4, c_5, c_6, (int) cmentos.getCalificaciones().getParticipantes().getAvatar_id(), cmentos.getCalificaciones().getParticipantes().getParticipante()));
                }

                if(cmentos.getEstado() == true){
                    total_logrado++;
                }

                bandera_inicio_true = 1;
            }


            float porcentaje = (total_logrado * 100) / total_final;


            enviar_bluetooth blue = new enviar_bluetooth();

            if(porcentaje <= 30){
                blue.enviar("7");
                Log.d("Info", "boton 7");
            }

            if(porcentaje > 30 && porcentaje < 80){
                blue.enviar("4");
                Log.d("Info", "boton 4");
            }

            if(porcentaje >= 80 ){
                blue.enviar("1");
                Log.d("Info", "boton 1");
            }

        }

    }

}
