package ru.squel.babynote.database.providers;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import ru.squel.babynote.database.SQLiteTableProvider;

/**
 * Created by sq on 17.10.2017.
 */

public class DrugProvider extends SQLiteTableProvider {

    public static final String TABLE_NAME = "drugsTable";

    public static final Uri URI = Uri.parse("content://ru.squel.babynote.provider/" + TABLE_NAME);

    public DrugProvider() {
        super(TABLE_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public Uri getBaseUri() {
        return URI;
    }
}
