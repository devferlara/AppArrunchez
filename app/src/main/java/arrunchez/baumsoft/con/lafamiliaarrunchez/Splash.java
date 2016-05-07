package arrunchez.baumsoft.con.lafamiliaarrunchez;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;

import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.DaoMaster;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.Seeds;
import arrunchez.baumsoft.con.lafamiliaarrunchez.tabbed.cuestionario;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(Splash.this, "arrunchez-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        new Seeds().seedsAllTables(db, false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(Splash.this, Inicio.class);
                startActivity(mainIntent);
                finish();
            }
        }, 1000);



    }

}
