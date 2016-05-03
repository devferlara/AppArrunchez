package arrunchez.baumsoft.con.lafamiliaarrunchez.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import arrunchez.baumsoft.con.lafamiliaarrunchez.R;
import arrunchez.baumsoft.con.lafamiliaarrunchez.models.ladientes;

/**
 * Created by dayessi on 9/04/16.
 */
public class ladientes_adapter extends BaseAdapter {

    private Activity activity;

    private ArrayList<ladientes> lds = new ArrayList<>();

    private Map<String, Integer> map = new HashMap<String, Integer>();


    public ladientes_adapter(Activity activity, ArrayList<ladientes> ldi) {
        this.activity = activity;
        this.lds = ldi;

        map.put("Papa", R.drawable.personaje1);
        map.put("mama", R.drawable.personaje2);
        map.put("niño", R.drawable.personaje3);
        map.put("abuela", R.drawable.personaje4);
        map.put("hada", R.drawable.personaje5);
    }

    @Override
    public int getCount() {
        return this.lds.size();
    }

    @Override
    public Object getItem(int position) {
        return this.lds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflator = activity.getLayoutInflater();
            convertView = inflator.inflate(R.layout.lavaste_dientes_item, null);

            final CheckBox check_parti = (CheckBox) convertView.findViewById(R.id.check_parti);
            TextView nombres = (TextView) convertView.findViewById(R.id.nombres);
            TextView avatar = (TextView) convertView.findViewById(R.id.avatar);
            ImageView personaje = (ImageView) convertView.findViewById(R.id.personaje);

            final ladientes ld = (ladientes) getItem(position);
            nombres.setText(ld.getNombres());
            avatar.setText(ld.getAvatar());
            personaje.setImageResource(map.get(ld.getAvatar()));

            check_parti.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ld.cdientes.setEstado(check_parti.isChecked());
                }
            });
        }
        return convertView;
    }
}
