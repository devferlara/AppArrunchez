package arrunchez.baumsoft.con.lafamiliaarrunchez.tabbed;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import arrunchez.baumsoft.con.lafamiliaarrunchez.cuento;
import arrunchez.baumsoft.con.lafamiliaarrunchez.R;
import arrunchez.baumsoft.con.lafamiliaarrunchez.fragmentos.alimentacion_dia;
import arrunchez.baumsoft.con.lafamiliaarrunchez.fragmentos.lavado_dientes;
import arrunchez.baumsoft.con.lafamiliaarrunchez.fragmentos.participantes;
import arrunchez.baumsoft.con.lafamiliaarrunchez.fragmentos.puntuacion;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.DaoMaster;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.DaoSession;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.Participantes;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.ParticipantesDao;
import arrunchez.baumsoft.con.lafamiliaarrunchez.helpers.CustomViewPager;
import arrunchez.baumsoft.con.lafamiliaarrunchez.helpers.katana;
import arrunchez.baumsoft.con.lafamiliaarrunchez.instrucciones_activity;
import arrunchez.baumsoft.con.lafamiliaarrunchez.manejador_arduino;

public class cuestionario extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private CustomViewPager mViewPager;
    private manejador_arduino manejador;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuestionario);

        LayoutInflater inflater = cuestionario.this.getLayoutInflater();
        final View dialoglayout = inflater.inflate(R.layout.form_wait, null);
        final AlertDialog constructor = new AlertDialog.Builder(cuestionario.this).create();
        constructor.setView(dialoglayout);
        constructor.show();
        constructor.setCancelable(false);


        setTitle("El tesoro de la familia Arrunchez");

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        manejador = new manejador_arduino();
                        manejador.conectar(cuestionario.this, cuestionario.this);

                        toolbar = (Toolbar) findViewById(R.id.toolbar);
                        setSupportActionBar(toolbar);

                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        getSupportActionBar().setDisplayShowHomeEnabled(true);

                        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

                        mViewPager = (CustomViewPager) findViewById(R.id.container);
                        mViewPager.setAdapter(mSectionsPagerAdapter);
                        mViewPager.setPagingEnabled(true);


                        tabLayout = (TabLayout) findViewById(R.id.tabs);
                        tabLayout.setupWithViewPager(mViewPager);

                        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                            @Override
                            public void onTabSelected(TabLayout.Tab tab) {
                                mViewPager.setCurrentItem(tab.getPosition());
                                switch (tab.getPosition()) {

                                    case 0:
                                        colorToolbar(new ColorDrawable(0xFF7A41A4));
                                        break;
                                    case 1:
                                        colorToolbar(new ColorDrawable(0xFF7A41A4));
                                        break;
                                    case 2:
                                        colorToolbar(new ColorDrawable(0xFF397BA2));
                                        break;
                                    case 3:
                                        colorToolbar(new ColorDrawable(0xFFFF8C09));
                                        break;

                                }
                            }

                            @Override
                            public void onTabUnselected(TabLayout.Tab tab) {

                            }

                            @Override
                            public void onTabReselected(TabLayout.Tab tab) {

                            }
                        });


                        int[] imagenes = {R.drawable.tabuno, R.drawable.tabdos, R.drawable.tabtres, R.drawable.tabcuatro};
                        String[] nombres = {"Participantes", "Alimentación", "Lavado de dientes", "Puntuación"};

                        for (int i = 0; i < tabLayout.getTabCount(); i++) {
                            View view = getLayoutInflater().inflate(R.layout.tabicon, null);
                            view.findViewById(R.id.icon).setBackgroundResource(imagenes[i]);
                            TextView texto = (TextView) view.findViewById(R.id.texto);
                            texto.setText(nombres[i]);
                            tabLayout.getTabAt(i).setCustomView(view);
                        }

                        constructor.dismiss();

                        katana kata = new katana();
                        String score_tail = kata.getTailScore();
                        String score_teeth = kata.getTeethScore();
                        prender(score_tail);
                        prender(score_teeth);

                    }
                },
                500);


    }

    public void cambiar(int page) {
        mViewPager.setCurrentItem(page);
    }


    public void prender(String cadena) {
        manejador.alumbrar(cadena);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.ayuda) {
            Intent intent = new Intent(cuestionario.this, instrucciones_activity.class);
            startActivityForResult(intent, 0);
            return true;
        }

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void colorToolbar(ColorDrawable color) {

        toolbar.setBackgroundDrawable(color);
        tabLayout.setBackgroundDrawable(color);

    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.

            switch (position) {

                case 0:
                    return new participantes();
                case 1:
                    return new alimentacion_dia();
                case 2:
                    return new lavado_dientes();
                case 3:
                    return new puntuacion();
                default:
                    return new puntuacion();

            }


        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Participantes";
                case 1:
                    return "Alimentación";
                case 2:
                    return "Lavado de dientes";
                case 3:
                    return "Puntuación";
            }
            return null;
        }
    }


    private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("Estado", "Ejecutado");
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    public void onDestroy() {
        super.onDestroy();
        try {
            manejador.desconectar();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("Request code", " " + requestCode);

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {


                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras.containsKey("estado")) {
                        String result = data.getStringExtra("estado");

                        if (result.equals("si")) {
                            manejador.alumbrar("3");
                        } else {
                            manejador.alumbrar("6");
                        }

                        cambiar(4);
                    }
                } else {
                    Log.d("Data", "Viene null");
                }


            }
            if (resultCode == Activity.RESULT_CANCELED) {

            }
        }

    }


}
