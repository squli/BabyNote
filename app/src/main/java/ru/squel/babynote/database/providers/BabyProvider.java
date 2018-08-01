package ru.squel.babynote.database.providers;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

import ru.squel.babynote.database.SQLiteTableProvider;

/**
 * Created by sq on 29.08.2017.
 */
public class BabyProvider extends SQLiteTableProvider {

    public static final String TABLE_NAME = "babyesTable";

    public static final Uri URI = Uri.parse("content://ru.squel.babynote.provider/" + TABLE_NAME);

    public interface Columns extends BaseColumns {
        String ID = "_id";
        String NAME = "name";
        String BITHDAY = "birthday";
        String SEX = "sex";
    }

    public BabyProvider() {
        super(TABLE_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_BABYES = "CREATE TABLE " + TABLE_NAME + "(" +
                Columns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                Columns.NAME + " TEXT NOT NULL UNIQUE, " +
                Columns.BITHDAY + " TEXT NOT NULL, " +
                Columns.SEX + " INTEGER NOT NULL" + ")";

        db.execSQL(CREATE_TABLE_BABYES);
    }

    @Override
    public Uri getBaseUri() {
        return URI;
    }
}
