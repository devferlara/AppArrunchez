package arrunchez.baumsoft.con.lafamiliaarrunchez;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import arrunchez.baumsoft.con.lafamiliaarrunchez.fragmentos.*;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.Seeds;
import arrunchez.baumsoft.con.lafamiliaarrunchez.tabbed.cuestionario;

public class Inicio extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        setFragment(0);

        prefs = getSharedPreferences("arrunchez.baumsoft.con.lafamiliaarrunchez", MODE_PRIVATE);


    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            new AlertDialog.Builder(this)
                    .setTitle("Información")
                    .setMessage("¿Deseas salir de la aplicación?")
                    .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing

                        }
                    })
                    .setIcon(R.drawable.personaje1_)
                    .show();
        }


    }


    public void ayuda(){
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.inicio) {
            setFragment(0);
        } else if (id == R.id.tesoro) {
            setFragment(1);
        } else if (id == R.id.calendario) {
            setFragment(7);
        } else if (id == R.id.alimentacion) {
            setFragment(4);
        } else if (id == R.id.caries) {
            setFragment(6);
        } else if (id == R.id.instrucciones) {
            setFragment(3);
        } else if (id == R.id.como_lavar) {
            setFragment(5);
        } else if (id == R.id.reciclar) {
            setFragment(8);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setFragment(int position) {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        switch (position) {
            case 0:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                agregar_participantes inboxFragment = new agregar_participantes();
                fragmentTransaction.replace(R.id.fragment, inboxFragment);
                fragmentTransaction.commit();
                break;
            case 1:
                if (prefs.getBoolean("firstrun", true)) {
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    crear_participante crearFragment = new crear_participante();
                    fragmentTransaction.replace(R.id.fragment, crearFragment);
                    fragmentTransaction.commit();
                } else {
                    Intent mainIntent = new Intent(Inicio.this, cuestionario.class);
                    startActivity(mainIntent);
                }
                break;
            case 4:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                alimentacion crearAlimentacion = new alimentacion();
                fragmentTransaction.replace(R.id.fragment, crearAlimentacion);
                fragmentTransaction.commit();
                break;
            case 5:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                lavado_info crearlavado_info = new lavado_info();
                fragmentTransaction.replace(R.id.fragment, crearlavado_info);
                fragmentTransaction.commit();
                break;
            case 6:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                caries crearCaries = new caries();
                fragmentTransaction.replace(R.id.fragment, crearCaries);
                fragmentTransaction.commit();
                break;
            case 7:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                calendario crearCalendario = new calendario();
                fragmentTransaction.replace(R.id.fragment, crearCalendario);
                fragmentTransaction.commit();
                break;
            case 3:
                Intent mainIntent = new Intent(Inicio.this, instrucciones_activity.class);
                startActivity(mainIntent);
                break;
            case 8:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                reciclar crearCiclo = new reciclar();
                fragmentTransaction.replace(R.id.fragment, crearCiclo);
                fragmentTransaction.commit();
                break;

        }
    }

    public void iniciarDesdeFragment(int numero) {
        setFragment(numero);
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
            Intent intent = new Intent(Inicio.this, instrucciones_activity.class);
            startActivityForResult(intent, 0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
