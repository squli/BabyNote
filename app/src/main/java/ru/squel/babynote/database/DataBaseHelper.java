package ru.squel.babynote.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ru.squel.babynote.database.providers.BabyProvider;
import ru.squel.babynote.database.providers.EatProvider;
import ru.squel.babynote.database.providers.PampersProvider;
import ru.squel.babynote.database.providers.PlayProvider;
import ru.squel.babynote.database.providers.SleepProvider;
import ru.squel.babynote.database.providers.SwimProvider;
import ru.squel.babynote.database.providers.WalkProvider;

/**
 * Created by sq on 24.08.2017.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = DataBaseConstants.DB_NAME;
    private static final int DATABASE_VERSION = DataBaseConstants.DB_VERSION;

    private static final String TABLE_BABYES = BabyProvider.TABLE_NAME;
    private static final String TABLE_BABYES_ID = "_id";
    private static final String TABLE_BABYES_NAME = "name";
    private static final String TABLE_BABYES_BITHDAY = "birthday";
    private static final String TABLE_BABYES_SEX = "sex";

    private static volatile DataBaseHelper mInstance = null;

    /**
     * Get access to db-object as singletone
     * @return
     */
    public static DataBaseHelper getInstance(Context context) {
        if (mInstance == null) {
            // если указатель нулевой, то нужна блокировка
            synchronized (DataBaseHelper.class) {
                if (mInstance == null)
                    mInstance = new DataBaseHelper(context.getApplicationContext());
            }
        }
        return mInstance;
    }

    private DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_BABYES = "CREATE TABLE " + TABLE_BABYES + "(" +
                TABLE_BABYES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                TABLE_BABYES_NAME + " TEXT NOT NULL UNIQUE, " +
                TABLE_BABYES_BITHDAY + " TEXT NOT NULL, " +
                TABLE_BABYES_SEX + " INTEGER NOT NULL" + ")";

        String CREATE_TABLE_EATINGS = "CREATE TABLE " + EatProvider.TABLE_NAME + "(" +
                EatProvider.Columns.ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                EatProvider.Columns.BEGIN +  " INTEGER NOT NULL, " +
                EatProvider.Columns.END +  " INTEGER NOT NULL, " +
                EatProvider.Columns.TYPE + " INTEGER NOT NULL, " +
                EatProvider.Columns.SUBTYPE + " TEXT, " +
                EatProvider.Columns.AMOUNT + " INTEGER" + ")";

        String CREATE_TABLE_PAMPERS = "CREATE TABLE " + PampersProvider.TABLE_NAME + "(" +
                PampersProvider.Columns.ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                PampersProvider.Columns.TIME +  " INTEGER NOT NULL, " +
                PampersProvider.Columns.TYPE + " INTEGER NOT NULL" + ")";

        String CREATE_TABLE_SLEEP = "CREATE TABLE " + SleepProvider.TABLE_NAME + "(" +
                SleepProvider.Columns.ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                SleepProvider.Columns.BEGIN +  " INT NOT NULL, " +
                SleepProvider.Columns.END +  " INT NOT NULL, " +
                SleepProvider.Columns.TYPE + " INTEGER NOT NULL" + ")";

        String CREATE_TABLE_PLAY = "CREATE TABLE " + PlayProvider.TABLE_NAME + "(" +
                SleepProvider.Columns.ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                SleepProvider.Columns.BEGIN +  " INT NOT NULL, " +
                SleepProvider.Columns.END +  " INT NOT NULL, " +
                SleepProvider.Columns.TYPE + " INTEGER NOT NULL" + ")";

        String CREATE_TABLE_SWIM = "CREATE TABLE " + SwimProvider.TABLE_NAME + "(" +
                SleepProvider.Columns.ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                SleepProvider.Columns.BEGIN +  " INT NOT NULL, " +
                SleepProvider.Columns.END +  " INT NOT NULL, " +
                SleepProvider.Columns.TYPE + " INTEGER NOT NULL" + ")";

        String CREATE_TABLE_WALK = "CREATE TABLE " + WalkProvider.TABLE_NAME + "(" +
                SleepProvider.Columns.ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                SleepProvider.Columns.BEGIN +  " INT NOT NULL, " +
                SleepProvider.Columns.END +  " INT NOT NULL, " +
                SleepProvider.Columns.TYPE + " INTEGER NOT NULL" + ")";

        db.execSQL(CREATE_TABLE_BABYES);
        db.execSQL(CREATE_TABLE_EATINGS);
        db.execSQL(CREATE_TABLE_PAMPERS);
        db.execSQL(CREATE_TABLE_SLEEP);
        db.execSQL(CREATE_TABLE_PLAY);
        db.execSQL(CREATE_TABLE_SWIM);
        db.execSQL(CREATE_TABLE_WALK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BABYES);
        db.execSQL("DROP TABLE IF EXISTS " + EatProvider.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PampersProvider.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SleepProvider.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PlayProvider.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SwimProvider.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + WalkProvider.TABLE_NAME);
        onCreate(db);
    }
}
