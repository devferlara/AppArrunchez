package arrunchez.baumsoft.con.lafamiliaarrunchez.gendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.Last_score_teeth;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "LAST_SCORE_TEETH".
*/
public class Last_score_teethDao extends AbstractDao<Last_score_teeth, Long> {

    public static final String TABLENAME = "LAST_SCORE_TEETH";

    /**
     * Properties of entity Last_score_teeth.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Score = new Property(1, String.class, "score", false, "SCORE");
    };


    public Last_score_teethDao(DaoConfig config) {
        super(config);
    }
    
    public Last_score_teethDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"LAST_SCORE_TEETH\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"SCORE\" TEXT);"); // 1: score
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"LAST_SCORE_TEETH\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Last_score_teeth entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String score = entity.getScore();
        if (score != null) {
            stmt.bindString(2, score);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Last_score_teeth readEntity(Cursor cursor, int offset) {
        Last_score_teeth entity = new Last_score_teeth( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1) // score
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Last_score_teeth entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setScore(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Last_score_teeth entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Last_score_teeth entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
