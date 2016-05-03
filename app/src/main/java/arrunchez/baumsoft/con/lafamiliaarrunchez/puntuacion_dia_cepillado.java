package arrunchez.baumsoft.con.lafamiliaarrunchez;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import arrunchez.baumsoft.con.lafamiliaarrunchez.adapters.calificacion_dientes_adapter;
import arrunchez.baumsoft.con.lafamiliaarrunchez.adapters.ladientes_adapter;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.Calidientes;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.CalidientesDao;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.Calificaciones;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.CalificacionesDao;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.DaoMaster;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.DaoSession;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.Participantes;
import arrunchez.baumsoft.con.lafamiliaarrunchez.models.ladientes;
import arrunchez.baumsoft.con.lafamiliaarrunchez.models.model_alimentos;
import arrunchez.baumsoft.con.lafamiliaarrunchez.models.model_dientes;
import de.greenrobot.dao.query.Join;
import de.greenrobot.dao.query.QueryBuilder;

public class puntuacion_dia_cepillado extends AppCompatActivity {

    private SQLiteDatabase db;
    public ArrayList<model_dientes> lds = new ArrayList<>();
    private CalidientesDao cdidao;
    private int total_final, total_logrado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntuacion_dia_cepillado);

        total_final = 0;
        total_logrado = 0;

        setTitle("Participantes");

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(puntuacion_dia_cepillado.this, "arrunchez-db", null);
        db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        ListView lista = (ListView) findViewById(R.id.lista);

        cdidao = daoSession.getCalidientesDao();
        QueryBuilder<Calidientes> qbcd = cdidao.queryBuilder();
        Join cjoin = qbcd.join(CalidientesDao.Properties.Calificacion_id, Calificaciones.class);
        cjoin.where(CalificacionesDao.Properties.Date.eq(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF397BA2));

        if(qbcd.count() > 0) {
            for(Calidientes cd : qbcd.list()) {
                total_final ++;
                //Log.d("Participante", cd.getCalificaciones().getParticipantes().getParticipante());
                //Log.d("Calificacion", String.valueOf(cd.getEstado()));
                lds.add(new model_dientes(cd.getCalificaciones().getParticipantes().getParticipante(), cd.getEstado(), (int) cd.getCalificaciones().getParticipantes().getAvatar_id()));
                if(cd.getEstado() == true){
                    total_logrado++;
                }
            }
        }
        lista.setAdapter(new calificacion_dientes_adapter(puntuacion_dia_cepillado.this, lds));
        Button btn_result = (Button) findViewById(R.id.btn_info);
        float porcentaje = (total_logrado * 100) / total_final;
        if(porcentaje < 80){
            ImageView imagenresultado = (ImageView) findViewById(R.id.imagen_resultado);
            imagenresultado.setImageResource(R.drawable.ohohtexto);
            btn_result.setBackgroundResource(R.drawable.botonohoh);
        }

        btn_result.setText("Total: " + total_logrado + "/" + total_final);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
