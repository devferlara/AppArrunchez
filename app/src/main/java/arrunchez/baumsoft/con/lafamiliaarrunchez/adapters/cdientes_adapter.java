package arrunchez.baumsoft.con.lafamiliaarrunchez.adapters;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import arrunchez.baumsoft.con.lafamiliaarrunchez.R;
import arrunchez.baumsoft.con.lafamiliaarrunchez.models.model_cdientes;

/**
 * Created by dayessi on 3/05/16.
 */
public class cdientes_adapter extends PagerAdapter {

    private Activity activity;

    private ArrayList<model_cdientes> acd;


    public cdientes_adapter(Activity activity, ArrayList<model_cdientes> acd) {
        this.activity = activity;
        this.acd = acd;
    }

    @Override
    public int getCount() {
        return this.acd.size();
    }

    @Override
    public View instantiateItem(ViewGroup container, final int position) {
        LayoutInflater inflater = (LayoutInflater) activity.getLayoutInflater();

        View view = (View) inflater.inflate(R.layout.list_item_ecalendar, container, false);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView descri = (TextView) view.findViewById(R.id.descri);

        model_cdientes cd = this.acd.get(position);

        title.setText(cd.getTitle());
        descri.setText(cd.getDescri());

        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
