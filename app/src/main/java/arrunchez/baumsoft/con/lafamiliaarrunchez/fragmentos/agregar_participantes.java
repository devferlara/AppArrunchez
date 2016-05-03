package arrunchez.baumsoft.con.lafamiliaarrunchez.fragmentos;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import arrunchez.baumsoft.con.lafamiliaarrunchez.Inicio;
import arrunchez.baumsoft.con.lafamiliaarrunchez.R;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.DaoMaster;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.DaoSession;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.Participantes;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.ParticipantesDao;


public class agregar_participantes extends Fragment {

    private SQLiteDatabase db;
    private ParticipantesDao pdao;

    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVERABLE_BT = 0;
    BluetoothDevice mBluetoothDevice;
    BluetoothAdapter mBluetoothAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_agregar_participantes, container, false);


        SharedPreferences prefs = null;
        prefs = getActivity().getSharedPreferences("arrunchez.baumsoft.con.lafamiliaarrunchez", getActivity().MODE_PRIVATE);


        ((Inicio) getActivity()).getSupportActionBar().setTitle("El tesoro de la familia Arrunchez");

        if (prefs.getBoolean("firstrun", true)) {

            ImageButton crear = (ImageButton) view.findViewById(R.id.add);
            crear.setVisibility(View.VISIBLE);
            crear.setImageResource(R.drawable.add);
            crear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Inicio) getActivity()).iniciarDesdeFragment(1);
                }
            });

            ((Inicio) getActivity()).getSupportActionBar().setTitle("El tesoro de la familia Arrunchez");


        } else {

            ImageView inicio = (ImageView) view.findViewById(R.id.inicio_imagen);
            inicio.setVisibility(View.VISIBLE);

        }

        /*


        */

        return view;
    }



}
