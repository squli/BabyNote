package ru.squel.babynote.database;

import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ru.squel.babynote.database.providers.BabyProvider;
import ru.squel.babynote.database.providers.DrugProvider;
import ru.squel.babynote.database.providers.EatProvider;
import ru.squel.babynote.database.providers.PampersProvider;
import ru.squel.babynote.database.providers.PlayProvider;
import ru.squel.babynote.database.providers.SleepProvider;
import ru.squel.babynote.database.providers.SwimProvider;
import ru.squel.babynote.database.providers.WalkProvider;

/**
 * Created by sq on 24.08.2017.
 */
public class BabyDataContentProvider extends ContentProvider {

    private static final String DATABASE_NAME = DataBaseConstants.DB_NAME;

    private static final int DATABASE_VERSION = DataBaseConstants.DB_VERSION;

    private static final Map<String, SQLiteTableProvider> SCHEMA = new ConcurrentHashMap<>();

    static {
        SCHEMA.put(EatProvider.TABLE_NAME, new EatProvider());
        SCHEMA.put(BabyProvider.TABLE_NAME, new BabyProvider());

        SCHEMA.put(PampersProvider.TABLE_NAME, new PampersProvider());

        SCHEMA.put(SleepProvider.TABLE_NAME, new SleepProvider());
        SCHEMA.put(PlayProvider.TABLE_NAME, new PlayProvider());
        SCHEMA.put(SwimProvider.TABLE_NAME, new SwimProvider());
        SCHEMA.put(WalkProvider.TABLE_NAME, new WalkProvider());

        SCHEMA.put(DrugProvider.TABLE_NAME, new DrugProvider());
    }

    private final SQLiteUriMatcher mUriMatcher = new SQLiteUriMatcher();

    private DataBaseHelper mHelper;

    /**
     * Получение информации о поставщике контента
     * @param context
     * @param provider
     * @param flags
     * @return
     * @throws PackageManager.NameNotFoundException
     */
    private static ProviderInfo getProviderInfo(Context context, Class<? extends ContentProvider> provider, int flags)
            throws PackageManager.NameNotFoundException {
        return context.getPackageManager()
                .getProviderInfo(new ComponentName(context.getPackageName(), provider.getName()), flags);
    }

    /**
     * Получение названия таблицы из URI
     * @param uri
     * @return
     */
    private static String getTableName(Uri uri) {
        return uri.getPathSegments().get(0);
    }

    /**
     * При воздании поставщика контента в mUriMatcher вносятся authority из его параметров
     * @return
     */
    @Override
    public boolean onCreate() {
        try {
            final ProviderInfo pi = getProviderInfo(getContext(), getClass(), 0);
            final String[] authorities = TextUtils.split(pi.authority, ";");
            for (final String authority : authorities) {
                mUriMatcher.addAuthority(authority);
            }
            mHelper = DataBaseHelper.getInstance(getContext());
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            throw new SQLiteException(e.getMessage());
        }
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final int matchResult = mUriMatcher.match(uri);
        if (matchResult == SQLiteUriMatcher.NO_MATCH) {
            throw new SQLiteException("Unknown uri " + uri);
        }

        final String tableName = getTableName(uri);
        final SQLiteTableProvider tableProvider = SCHEMA.get(tableName);

        if (tableProvider == null) {
            throw new SQLiteException("No such table " + tableName);
        }

        if (matchResult == SQLiteUriMatcher.MATCH_ALL) { //TODO заменить BabyProvider.Columns.ID на sortOrder
            Cursor res = tableProvider.query(mHelper.getReadableDatabase(), projection, selection, selectionArgs, BabyProvider.Columns.ID);
            return res;
        }
        else if (matchResult == SQLiteUriMatcher.MATCH_ID) {

        }

        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    /**
     * Вставка данных через поставщик контента.
     * @param uri - путь к данным, должен быть путем до таблицы, в которую вставляются данные
     * @param values - значения для вставки
     * @return
     */
    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final int matchResult = mUriMatcher.match(uri);

        if (matchResult == SQLiteUriMatcher.NO_MATCH) {
            throw new SQLiteException("Unknown uri " + uri);
        }

        final String tableName = getTableName(uri);
        final SQLiteTableProvider tableProvider = SCHEMA.get(tableName);

        if (tableProvider == null) {
            throw new SQLiteException("No such table " + tableName);
        }

        if (matchResult == SQLiteUriMatcher.MATCH_ALL) {
            final long lastId = tableProvider.insert(mHelper.getWritableDatabase(), values);
            getContext().getContentResolver().notifyChange(tableProvider.getBaseUri(), null);

            //final Bundle extras = new Bundle();
            //extras.putLong(SQLiteOperation.KEY_LAST_ID, lastId);
            //tableProvider.onContentChanged(getContext(), SQLiteOperation.INSERT, extras);
            return uri;
        }

        return null;
    }

    /**
     *
     * @param uri
     * @param selection
     * @param selectionArgs
     * @return
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final int matchResult = mUriMatcher.match(uri);

        if (matchResult == SQLiteUriMatcher.NO_MATCH) {
            throw new SQLiteException("Unknown uri " + uri);
        }

        final String tableName = getTableName(uri);
        final SQLiteTableProvider tableProvider = SCHEMA.get(tableName);

        if (tableProvider == null) {
            throw new SQLiteException("No such table " + tableName);
        }

        if (matchResult == SQLiteUriMatcher.MATCH_ALL) {
            return tableProvider.delete(mHelper.getWritableDatabase(), selection, selectionArgs);
        }
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final int matchResult = mUriMatcher.match(uri);

        if (matchResult == SQLiteUriMatcher.NO_MATCH) {
            throw new SQLiteException("Unknown uri " + uri);
        }

        final String tableName = getTableName(uri);
        final SQLiteTableProvider tableProvider = SCHEMA.get(tableName);

        if (tableProvider == null) {
            throw new SQLiteException("No such table " + tableName);
        }

        if (matchResult == SQLiteUriMatcher.MATCH_ALL) {
            return tableProvider.update(mHelper.getWritableDatabase(), values, selection, selectionArgs);
        }
        return 0;
    }
}
