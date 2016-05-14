package arrunchez.baumsoft.con.lafamiliaarrunchez;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.DaoMaster;
import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.DaoSession;

/**
 * Created by Desarrollador on 8/11/15.
 */
public class DaoAPP extends Application {

    public static DaoSession daoSession;

    @Override
    public void onCreate(){
        super.onCreate();

        try{
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getApplicationContext(), "arrunchez-db", null);
            SQLiteDatabase db = helper.getWritableDatabase();
            DaoMaster daoMaster = new DaoMaster(db);
            daoSession = daoMaster.newSession();
        } catch (Exception e){
            Log.d("Exeption", e.toString());
        }


    }

}