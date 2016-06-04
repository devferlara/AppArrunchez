package arrunchez.baumsoft.con.lafamiliaarrunchez.fragmentos;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.List;

import arrunchez.baumsoft.con.lafamiliaarrunchez.DaoAPP;
import arrunchez.baumsoft.con.lafamiliaarrunchez.Inicio;
import arrunchez.baumsoft.con.lafamiliaarrunchez.R;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.DaoMaster;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.DaoSession;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.Participantes;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.ParticipantesDao;
import arrunchez.baumsoft.con.lafamiliaarrunchez.tabbed.cuestionario;

public class crear_participante extends Fragment {

    int personajeElegido;
    ImageView personaje1;
    ImageView personaje2;
    ImageView personaje3;
    ImageView personaje4;
    ImageView personaje5;
    SharedPreferences prefs = null;

    private EditText nombres;

    private Button guardar;

    private SQLiteDatabase db;
    private Context context;
    private ParticipantesDao pdao;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_crear_participante, container, false);

        prefs = getActivity().getSharedPreferences("arrunchez.baumsoft.con.lafamiliaarrunchez", context.MODE_PRIVATE);

        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        nombres = (EditText) view.findViewById(R.id.nombres);
        guardar = (Button) view.findViewById(R.id.guardar_);
        nombres.setInputType(InputType.TYPE_CLASS_TEXT);
        nombres.requestFocus();
        InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.showSoftInput(nombres, InputMethodManager.SHOW_FORCED);
        context = getContext();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle("Información");
        alertDialogBuilder
                .setMessage("Crea los personajes para éste juego que tiene una duración de seis días.")
                .setPositiveButton("Entendido",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                    }
                });

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getContext(), "arrunchez-db", null);
        db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
                                        pdao = daoSession.getParticipantesDao();

        personajeElegido = 0;

        YoYo.with(Techniques.FlipInX)
                .duration(600)
                .playOn(view.findViewById(R.id.personaje1));

        YoYo.with(Techniques.FlipInX)
                .duration(700)
                .playOn(view.findViewById(R.id.personaje2));

        YoYo.with(Techniques.FlipInX)
                .duration(800)
                .playOn(view.findViewById(R.id.personaje3));

        YoYo.with(Techniques.FlipInX)
                .duration(900)
                .playOn(view.findViewById(R.id.personaje4));

        YoYo.with(Techniques.FlipInX)
                .duration(1000)
                .playOn(view.findViewById(R.id.personaje5));

        ((Inicio) getActivity()).getSupportActionBar().setTitle("Participantes");

        personaje1 = (ImageView) view.findViewById(R.id.personaje1);
        personaje2 = (ImageView) view.findViewById(R.id.personaje2);
        personaje3 = (ImageView) view.findViewById(R.id.personaje3);
        personaje4 = (ImageView) view.findViewById(R.id.personaje4);
        personaje5 = (ImageView) view.findViewById(R.id.personaje5);

        personaje1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reiniciarPersonajes(1);
                personaje1.setBackgroundResource(R.drawable.personaje1_);
                YoYo.with(Techniques.FlipInX)
                        .duration(600)
                        .playOn(view.findViewById(R.id.personaje1));
            }
        });

        personaje2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reiniciarPersonajes(2);
                personaje2.setBackgroundResource(R.drawable.personaje2_);
                YoYo.with(Techniques.FlipInX)
                        .duration(600)
                        .playOn(view.findViewById(R.id.personaje2));
            }
        });

        personaje3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reiniciarPersonajes(3);
                personaje3.setBackgroundResource(R.drawable.personaje3_);
                YoYo.with(Techniques.FlipInX)
                        .duration(600)
                        .playOn(view.findViewById(R.id.personaje3));
            }
        });

        personaje4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reiniciarPersonajes(4);
                personaje4.setBackgroundResource(R.drawable.personaje4_);
                YoYo.with(Techniques.FlipInX)
                        .duration(600)
                        .playOn(view.findViewById(R.id.personaje4));
            }
        });

        personaje5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reiniciarPersonajes(5);
                personaje5.setBackgroundResource(R.drawable.personaje5_);
                YoYo.with(Techniques.FlipInX)
                        .duration(600)
                        .playOn(view.findViewById(R.id.personaje5));
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(personajeElegido != 0){

                    if(!nombres.getText().toString().equals("")){
                        pdao.insert(new Participantes(null, nombres.getText().toString(), personajeElegido));
                        List<Participantes> participantes = DaoAPP.daoSession.getParticipantesDao().loadAll();

                        if (participantes.size() == 5){
                            prefs.edit().putBoolean("firstrun", false).commit();
                            Intent mainintent = new Intent(getActivity(), cuestionario.class);
                            mainintent.putExtra("pop", "si");
                            startActivity(mainintent);
                            ((Inicio) getActivity()).setFragment(0);
                        } else {
                            nombres.setText("");
                            reiniciarPersonajes(0);

                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                    context);

                            alertDialogBuilder.setTitle("Personajes");
                            alertDialogBuilder
                                    .setMessage("Has agregado correctamente este personaje. ¿Deseas seguir agregando más personajes?")
                                    .setCancelable(false)
                                    .setPositiveButton("Agregar",new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            personajeElegido = 0;
                                        }
                                    })
                                    .setNegativeButton("Finalizar",new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            prefs.edit().putBoolean("firstrun", false).commit();
                                            Intent mainintent = new Intent(getActivity(), cuestionario.class);
                                            mainintent.putExtra("pop", "si");
                                            startActivity(mainintent);
                                            ((Inicio) getActivity()).setFragment(0);
                                            //getActivity().finish();
                                        }
                                    });

                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }



                    } else {
                        Toast.makeText(getActivity(), "Ingresa el nombre antes de guardar.", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), "Selecciona un personaje antes de guardar.", Toast.LENGTH_SHORT).show();
                }



            }
        });

        return view;

    }

    public static void hideKeyboard(Activity activity, View viewToHide) {
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(viewToHide.getWindowToken(), 0);
    }

    public void reiniciarPersonajes(int personaje){

        personajeElegido = personaje;

        personaje1.setBackgroundResource(R.drawable.personaje1);
        personaje2.setBackgroundResource(R.drawable.personaje2);
        personaje3.setBackgroundResource(R.drawable.personaje3);
        personaje4.setBackgroundResource(R.drawable.personaje4);
        personaje5.setBackgroundResource(R.drawable.personaje5);

    }



}
