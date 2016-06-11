package arrunchez.baumsoft.con.lafamiliaarrunchez;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import arrunchez.baumsoft.con.lafamiliaarrunchez.fragmentos.*;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.DaoMaster;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.Mac_bluetooth;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.Seeds;
import arrunchez.baumsoft.con.lafamiliaarrunchez.tabbed.cuestionario;

public class Inicio extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    private manejador_arduino manejador;
    SharedPreferences prefs = null;
    ProgressDialog progress;
    private String temp_bluetooth;
    private boolean bandera_bluetooth;
    private ArrayList<String> mDeviceList = new ArrayList<String>();
    private final static int REQUEST_ENABLE_BT=3;
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

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

        bandera_bluetooth = false;

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        setFragment(0);

        prefs = getSharedPreferences("arrunchez.baumsoft.con.lafamiliaarrunchez", MODE_PRIVATE);

        if(!mBluetoothAdapter.isEnabled())
        {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            empezar_integracion();
        }

    }

    public void empezar_integracion(){
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        List<Mac_bluetooth> lista = DaoAPP.daoSession.getMac_bluetoothDao().loadAll();
        boolean bandera_bluetooth_inpaired_por_persona = false;
        if (pairedDevices.size() != 0){

            for (BluetoothDevice device : pairedDevices) {

                if (device.getName() != null) {

                    if(device.getName().equals("HC-05")){
                        bandera_bluetooth_inpaired_por_persona = true;
                    }

                    if(lista.size() == 0){
                        unpairDevice(device);
                    }
                }

            }

            if(!bandera_bluetooth_inpaired_por_persona){
                DaoAPP.daoSession.getMac_bluetoothDao().deleteAll();
                continuar_integracion();
            }


        } else {
            DaoAPP.daoSession.getMac_bluetoothDao().deleteAll();
            continuar_integracion();
        }
    }

    public void continuar_integracion(){
        IntentFilter intent = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(mPairReceiver, intent);

        List<Mac_bluetooth> lista_mac = DaoAPP.daoSession.getMac_bluetoothDao().loadAll();
        if (lista_mac.size() == 0) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(Inicio.this);
            alertDialog.setTitle("Información");
            alertDialog.setMessage("Aún no has vinculado ningún dispositivo con el App. ¿Deseas buscar uno en este momento?");
            alertDialog.setCancelable(false);
            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    if (mBluetoothAdapter.isEnabled()) {
                        pairbluetooth();
                        progress = ProgressDialog.show(Inicio.this, "Información",
                                "Buscando dispositivos, por favor espere.", true);

                        pairbluetooth();
                    } else {
                        Intent intentBtEnabled = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(intentBtEnabled, REQUEST_ENABLE_BT);
                    }

                }
            });

            // Setting Negative "NO" Button
            alertDialog.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            // Showing Alert Message
            alertDialog.show();
        }
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);

                if (state == BluetoothAdapter.STATE_ON) {
                    Toast.makeText(Inicio.this, "El Bluetooth ha sido activado.", Toast.LENGTH_LONG).show();
                }

                if (state == BluetoothAdapter.STATE_OFF) {
                    Toast.makeText(Inicio.this, "El Bluetooth ha sido desactivado.", Toast.LENGTH_LONG).show();
                }

            }

            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                //discovery starts, we can show progress dialog or perform other tasks
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //discovery finishes, dismis progress dialog
                if (!bandera_bluetooth) {
                    if(progress != null){
                        if (progress.isShowing())
                            progress.dismiss();
                        Toast.makeText(Inicio.this, "No hemos encontrado dispositivos.", Toast.LENGTH_LONG).show();
                    }
                }
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                if (device.getName() != null) {
                    Log.d("mac", device.getName());
                    if (device.getName().equals("HC-05")) {
                        if(progress != null){
                            if (progress.isShowing())
                                progress.dismiss();
                            bandera_bluetooth = true;
                            Toast.makeText(Inicio.this, "Introduce el código: 1234", Toast.LENGTH_LONG).show();
                            pairDevice(device);
                        }

                    }
                }

            }
        }
    };

    private void pairDevice(BluetoothDevice device) {
        try {
            Method method_ = device.getClass().getMethod("createBond", (Class[]) null);
            method_.invoke(device, (Object[]) null);
            temp_bluetooth = device.getAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void unpairDevice(BluetoothDevice device) {
        try {
            Method method = device.getClass().getMethod("removeBond", (Class[]) null);
            method.invoke(device, (Object[]) null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private final BroadcastReceiver mPairReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                final int state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR);
                final int prevState = intent.getIntExtra(BluetoothDevice.EXTRA_PREVIOUS_BOND_STATE, BluetoothDevice.ERROR);
                View view = Inicio.this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                if (state == BluetoothDevice.BOND_BONDED) {
                    Mac_bluetooth nuevo = new Mac_bluetooth();
                    nuevo.setMac(temp_bluetooth);
                    DaoAPP.daoSession.getMac_bluetoothDao().insert(nuevo);
                    Toast.makeText(Inicio.this, "Fantástico ha sido vinculado exitosamente.", Toast.LENGTH_LONG).show();
                    manejador = new manejador_arduino();
                    manejador.conectar(Inicio.this, temp_bluetooth);
                    manejador.alumbrar("1");
                    manejador.alumbrar("A");

                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    manejador.alumbrar("1");
                                    manejador.alumbrar("A");

                                    new android.os.Handler().postDelayed(
                                            new Runnable() {
                                                public void run() {
                                                    manejador.alumbrar("1");
                                                    manejador.alumbrar("A");

                                                    new android.os.Handler().postDelayed(
                                                            new Runnable() {
                                                                public void run() {
                                                                    manejador.alumbrar("1");
                                                                    manejador.alumbrar("A");
                                                                    try {
                                                                        manejador.desconectar();
                                                                    } catch (IOException e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                }
                                                            },
                                                            400);
                                                }
                                            },
                                            400);

                                }
                            },
                            400);



                } else if (state == BluetoothDevice.BOND_NONE && prevState == BluetoothDevice.BOND_BONDED) {

                }

            }
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 3) {
            if (resultCode == RESULT_OK) {
                empezar_integracion();
                Toast.makeText(Inicio.this, "Bluetooth activado correctamente.", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(Inicio.this, "El Bluetooth no ha sido activado.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void pairbluetooth() {

        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        IntentFilter filter = new IntentFilter();

        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

        registerReceiver(mReceiver, filter);
        adapter.startDiscovery();

    }

    @Override
    public void onResume() {
        super.onResume();
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

    public void ayuda() {

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
        } else if (id == R.id.creditos) {
            setFragment(9);
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
            case 9:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                creditos crearcredito = new creditos();
                fragmentTransaction.replace(R.id.fragment, crearcredito);
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
