package ru.squel.babynote.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by sq on 27.08.2017.
 * Абстрактный класс для реализации основных операций с таблицей из базы.
 * Для использования нужно реализовать метод получения URI конкретного класса-таблицы и
 * метод создания таблицы с нуля
 */
public abstract class SQLiteTableProvider implements SQLiteOperation {

    private final String tableName;

    public SQLiteTableProvider(String tableName) {
        this.tableName = tableName;
    }

    public abstract void onCreate(SQLiteDatabase db);

    public abstract Uri getBaseUri();

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + tableName + ";");
        onCreate(db);
    }

    public Cursor query(SQLiteDatabase db, String[] columns, String where, String[] whereArgs, String orderBy) {
        return db.query(tableName, columns, where, whereArgs, null, null, orderBy);
    }

    public long insert(SQLiteDatabase db, ContentValues values) {
        return db.insert(tableName, BaseColumns._ID, values);
    }

    public int delete(SQLiteDatabase db, String where, String[] whereArgs) {
        return db.delete(tableName, where, whereArgs);
    }

    public int update(SQLiteDatabase db, ContentValues values, String where, String[] whereArgs) {
        return db.update(tableName, values, where, whereArgs);
    }

}
