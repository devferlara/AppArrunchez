package arrunchez.baumsoft.con.lafamiliaarrunchez.helpers;

import android.content.Context;

import arrunchez.baumsoft.con.lafamiliaarrunchez.DaoAPP;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.Last_score_foods;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.Last_score_tale;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.Last_score_teeth;

/**
 * Created by baumsoft on 3/05/16.
 */
public class katana {

    String TAG = "Desde katana ";


    /*
    *   Save Score
    *   Permite grabar las puntuaciones recientes.
    *   1 -> Alimentos
    *   2 -> Dientes
    *   3 -> Cuento
    */

    public void saveScore(Context contexto, int type, String new_value) {

        if (type == 1) {
            Last_score_foods item = new Last_score_foods();
            item.setScore(new_value);
            DaoAPP.daoSession.getLast_score_foodsDao().insert(item);
        }

        if (type == 2) {
            Last_score_teeth item = new Last_score_teeth();
            item.setScore(new_value);
            DaoAPP.daoSession.getLast_score_teethDao().insert(item);
        }

        if (type == 3) {
            Last_score_tale item = new Last_score_tale();
            item.setScore(new_value);
            DaoAPP.daoSession.getLast_score_taleDao().insert(item);
        }

    }


}
