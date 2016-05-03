package arrunchez.baumsoft.con.lafamiliaarrunchez.gendao;


import android.database.sqlite.SQLiteDatabase;



/**
 * Created by dayessi on 7/04/16.
 */
public class Seeds {

    public void seedsAllTables(SQLiteDatabase db, boolean ifNotExists) {
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();

        seedsAvatar(daoSession);
        seedsAlimentos(daoSession);
    }

    public static void seedsAvatar(DaoSession daoSession) {
        AvatarsDao ad = daoSession.getAvatarsDao();
        ad.insert(new Avatars(null, "Papa"));
        ad.insert(new Avatars(null, "mama"));
        ad.insert(new Avatars(null, "niño"));
        ad.insert(new Avatars(null, "abuela"));
        ad.insert(new Avatars(null, "hada"));
    }


    public static void seedsAlimentos(DaoSession daoSession) {
        AlimentosDao ad = daoSession.getAlimentosDao();
        ad.insert(new Alimentos(null, "Agua"));
        ad.insert(new Alimentos(null, "Frutas y verduras"));
        ad.insert(new Alimentos(null, "Alimentos ricos en Calcio o Lácteos"));
        ad.insert(new Alimentos(null, "Proteínas"));
        ad.insert(new Alimentos(null, "Cereales"));
        ad.insert(new Alimentos(null, "Postres"));
    }
}
