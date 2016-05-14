package arrunchez.baumsoft.con.lafamiliaarrunchez;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.easing.linear.Linear;

public class cuentouno extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        String cuento = extras.getString("cuento");

        switch (cuento){

            case "0":
                setContentView(R.layout.cuento_uno);
                break;

            case "1":
                setContentView(R.layout.cuento_dos);
                break;

            case "2":
                setContentView(R.layout.cuento_tres);
                break;

            case "3":
                setContentView(R.layout.cuento_cuatro);
                break;

            case "4":
                setContentView(R.layout.cuento_cinco);
                break;

            case "5":
                setContentView(R.layout.cuento_seis);
                break;

        }



        //TextView texto = (TextView) findViewById(R.id.cuento_texto);
        //TextView texto2 = (TextView) findViewById(R.id.cuento_texto2);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        LinearLayout cuento_terminado = (LinearLayout) findViewById(R.id.cuento_terminado);
        cuento_terminado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("estado", "si");
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });

        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/Futura.ttf");
        //texto.setTypeface(myTypeface);
        //texto2.setTypeface(myTypeface);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("estado", "no");
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
