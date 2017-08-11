package cn.ml.saddhu.bihudaily.engine.domain;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SECTION".
*/
public class SectionDao extends AbstractDao<Section, Long> {

    public static final String TABLENAME = "SECTION";

    /**
     * Properties of entity Section.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Description = new Property(0, String.class, "description", false, "DESCRIPTION");
        public final static Property Id = new Property(1, Long.class, "id", true, "_id");
        public final static Property Name = new Property(2, String.class, "name", false, "NAME");
        public final static Property ThumbnailUrl = new Property(3, String.class, "thumbnailUrl", false, "THUMBNAIL_URL");
        public final static Property Timestamp = new Property(4, int.class, "timestamp", false, "TIMESTAMP");
    }

    private DaoSession daoSession;


    public SectionDao(DaoConfig config) {
        super(config);
    }
    
    public SectionDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SECTION\" (" + //
                "\"DESCRIPTION\" TEXT," + // 0: description
                "\"_id\" INTEGER PRIMARY KEY ," + // 1: id
                "\"NAME\" TEXT," + // 2: name
                "\"THUMBNAIL_URL\" TEXT," + // 3: thumbnailUrl
                "\"TIMESTAMP\" INTEGER NOT NULL );"); // 4: timestamp
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SECTION\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Section entity) {
        stmt.clearBindings();
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(1, description);
        }
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(2, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(3, name);
        }
 
        String thumbnailUrl = entity.getThumbnailUrl();
        if (thumbnailUrl != null) {
            stmt.bindString(4, thumbnailUrl);
        }
        stmt.bindLong(5, entity.getTimestamp());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Section entity) {
        stmt.clearBindings();
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(1, description);
        }
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(2, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(3, name);
        }
 
        String thumbnailUrl = entity.getThumbnailUrl();
        if (thumbnailUrl != null) {
            stmt.bindString(4, thumbnailUrl);
        }
        stmt.bindLong(5, entity.getTimestamp());
    }

    @Override
    protected final void attachEntity(Section entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1);
    }    

    @Override
    public Section readEntity(Cursor cursor, int offset) {
        Section entity = new Section( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // description
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // id
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // name
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // thumbnailUrl
            cursor.getInt(offset + 4) // timestamp
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Section entity, int offset) {
        entity.setDescription(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setThumbnailUrl(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setTimestamp(cursor.getInt(offset + 4));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Section entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Section entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Section entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
