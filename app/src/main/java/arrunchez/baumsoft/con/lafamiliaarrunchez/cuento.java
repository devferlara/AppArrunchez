package arrunchez.baumsoft.con.lafamiliaarrunchez;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.daimajia.easing.linear.Linear;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import arrunchez.baumsoft.con.lafamiliaarrunchez.helpers.katana;

public class cuento extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuento);


        Bundle extras = getIntent().getExtras();
        String fecha_inicial = extras.getString("fecha");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        SharedPreferences prefs = getSharedPreferences("arrunchez.baumsoft.con.lafamiliaarrunchez", MODE_PRIVATE);

        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        String inputString1 = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
        String inputString2 = prefs.getString("fechainicial", "none");
        
        try {

            Date date1 = myFormat.parse(inputString1);
            Date date2 = myFormat.parse(inputString2);
            long diff = date2.getTime() - date1.getTime();
            int dias = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

            final Intent intento = new Intent(cuento.this, cuentouno.class);

            if (dias == 0) {
                intento.putExtra("cuento", "0");
            }

            if (dias == -1) {
                intento.putExtra("cuento", "1");
            }

            if (dias == -2) {
                intento.putExtra("cuento", "2");
            }

            if (dias == -3) {
                intento.putExtra("cuento", "3");
            }

            if (dias == -4) {
                intento.putExtra("cuento", "4");
            }

            if (dias <= -5) {
                intento.putExtra("cuento", "5");
            }

            LinearLayout leercuento = (LinearLayout) findViewById(R.id.leercuento);
            leercuento.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(intento, 1);
                }
            });


        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("Request code", " " + requestCode);

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("estado");
                katana kata = new katana();
                Intent resultIntent = new Intent();

                if (result.equals("si")) {
                    resultIntent.putExtra("estado", "si");
                    kata.saveScore(cuento.this, 3, "3");
                } else {
                    resultIntent.putExtra("estado", "no");
                    kata.saveScore(cuento.this, 3, "6");
                }
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
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

}
