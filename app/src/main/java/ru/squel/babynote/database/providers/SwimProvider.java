package ru.squel.babynote.database.providers;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

import ru.squel.babynote.database.SQLiteTableProvider;

/**
 * Created by sq on 17.10.2017.
 */

public class SwimProvider extends SQLiteTableProvider {

    public static final String TABLE_NAME = "swimsTable";

    public static final Uri URI = Uri.parse("content://ru.squel.babynote.provider/" + TABLE_NAME);

    public interface Columns extends BaseColumns {
        String ID = "_id";
        String BEGIN = "dateTimeBegin";
        String END = "dateTimeEnd";
        String TYPE = "type";
    }

    public SwimProvider() {
        super(TABLE_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_SLEEP = "CREATE TABLE " + TABLE_NAME + "(" +
                SleepProvider.Columns.ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                SleepProvider.Columns.BEGIN +  " INT NOT NULL, " +
                SleepProvider.Columns.END +  " INT NOT NULL, " +
                SleepProvider.Columns.TYPE + " INTEGER NOT NULL" + ")";

        db.execSQL(CREATE_TABLE_SLEEP);
    }

    @Override
    public Uri getBaseUri() {
        return URI;
    }
}
