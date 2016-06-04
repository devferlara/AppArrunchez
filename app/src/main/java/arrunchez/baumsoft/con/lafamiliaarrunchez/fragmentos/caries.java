package arrunchez.baumsoft.con.lafamiliaarrunchez.fragmentos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import arrunchez.baumsoft.con.lafamiliaarrunchez.Inicio;
import arrunchez.baumsoft.con.lafamiliaarrunchez.R;


public class caries extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_caries, container, false);
        ((Inicio) getActivity()).getSupportActionBar().setTitle("Caries y enfermedad de encías");
        return view;
    }
}
