package arrunchez.baumsoft.con.lafamiliaarrunchez;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import arrunchez.baumsoft.con.lafamiliaarrunchez.adapters.calificacion_alimentos_adapter;
import arrunchez.baumsoft.con.lafamiliaarrunchez.adapters.ladientes_adapter;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.CalidientesDao;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.Calificaciones;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.CalificacionesDao;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.Calimentos;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.CalimentosDao;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.DaoMaster;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.DaoSession;
import arrunchez.baumsoft.con.lafamiliaarrunchez.models.ladientes;
import arrunchez.baumsoft.con.lafamiliaarrunchez.models.model_alimentos;
import de.greenrobot.dao.query.Join;
import de.greenrobot.dao.query.QueryBuilder;

public class puntuacion_dia_alimentacion extends AppCompatActivity {

    private SQLiteDatabase db;
    private CalidientesDao cdidao;
    private CalimentosDao cmedao;
    private ListView lista;
    public ArrayList<model_alimentos> lds = new ArrayList<>();
    private int total_final, total_logrado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntuacion_dia_alimentacion);
        total_final = 0;
        total_logrado = 0;

        // conexion con la base de datos
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(puntuacion_dia_alimentacion.this, "arrunchez-db", null);
        db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        lista = (ListView) findViewById(R.id.lista);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        cmedao = daoSession.getCalimentosDao();
        QueryBuilder<Calimentos> qbcm = cmedao.queryBuilder();
        Join cmjoin = qbcm.join(CalimentosDao.Properties.Calificacion_id, Calificaciones.class);
        cmjoin.where(CalificacionesDao.Properties.Date.eq(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));

        setTitle("Resultados participantes");
        Log.d("Counter", " " + daoSession.getParticipantesDao().queryBuilder().count());

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

            lista.setAdapter(new calificacion_alimentos_adapter(puntuacion_dia_alimentacion.this, lds));
            Button btn_result = (Button) findViewById(R.id.btn_info);

            float porcentaje = (total_logrado * 100) / total_final;

            if(porcentaje < 80){
                ImageView imagenresultado = (ImageView) findViewById(R.id.imagen_resultado);
                imagenresultado.setImageResource(R.drawable.ohohtexto);
                btn_result.setBackgroundResource(R.drawable.botonohoh);
            }

            btn_result.setText("Total: " + total_logrado + "/" + total_final);

        } else {
            // no estan todos
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void toast(int position, model_alimentos modelo, String cadena, String nombre){

        if(!cadena.equals("none")){
            AlertDialog alertDialog = new AlertDialog.Builder(puntuacion_dia_alimentacion.this).create();
            alertDialog.setTitle(nombre + ".");
            alertDialog.setMessage(cadena);
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }

}
