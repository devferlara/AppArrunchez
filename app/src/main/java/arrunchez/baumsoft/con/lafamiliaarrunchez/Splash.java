package arrunchez.baumsoft.con.lafamiliaarrunchez;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import arrunchez.baumsoft.con.lafamiliaarrunchez.tabbed.cuestionario;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Intent mainIntent = new Intent(Splash.this, bluetooth.class);
                Intent mainIntent = new Intent(Splash.this, Inicio.class);
                startActivity(mainIntent);
                finish();
            }
        }, 1000);


    }

}
