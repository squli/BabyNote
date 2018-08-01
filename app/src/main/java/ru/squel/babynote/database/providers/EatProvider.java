package ru.squel.babynote.database.providers;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

import ru.squel.babynote.database.SQLiteTableProvider;

/**
 * Created by sq on 27.08.2017.
 * Класс для таблицы с кормлениями
 *
 */
public class EatProvider extends SQLiteTableProvider {

    public static final String TABLE_NAME = "eatingsTable";

    public static final Uri URI = Uri.parse("content://ru.squel.babynote.provider/" + TABLE_NAME);

    public interface Columns extends BaseColumns {
        String ID = "_id";
        String BEGIN = "dateTimeBegin";
        String END = "dateTimeEnd";
        String TYPE = "eatType";
        String SUBTYPE = "eatSubType";
        String AMOUNT = "amount";
    }

    public EatProvider() {
        super(TABLE_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_EATINGS = "CREATE TABLE " + TABLE_NAME + "(" +
                Columns.ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                Columns.BEGIN +  " INT NOT NULL, " +
                Columns.END +  " INT NOT NULL, " +
                Columns.TYPE + " INTEGER NOT NULL, " +
                Columns.SUBTYPE + " TEXT, " +
                Columns.AMOUNT + " INTEGER" + ")";

        db.execSQL(CREATE_TABLE_EATINGS);
    }

    @Override
    public Uri getBaseUri() {
        return URI;
    }
}
