package ru.squel.babynote.database.providers;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

import ru.squel.babynote.database.SQLiteTableProvider;

/**
 * Created by sq on 16.10.2017.
 */

public class PampersProvider extends SQLiteTableProvider {


    public static final String TABLE_NAME = "pampersTable";

    public static final Uri URI = Uri.parse("content://ru.squel.babynote.provider/" + TABLE_NAME);

    public PampersProvider() {
        super(TABLE_NAME);
    }

    public interface Columns extends BaseColumns {
        String ID = "_id";
        String TYPE = "type";
        String TIME = "dateTime";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_PAMPERS = "CREATE TABLE " + TABLE_NAME + "(" +
                PampersProvider.Columns.ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                PampersProvider.Columns.TIME +  " INTEGER NOT NULL, " +
                PampersProvider.Columns.TYPE + " INTEGER NOT NULL";

        db.execSQL(CREATE_TABLE_PAMPERS);
    }

    @Override
    public Uri getBaseUri() {
        return URI;
    }
}
