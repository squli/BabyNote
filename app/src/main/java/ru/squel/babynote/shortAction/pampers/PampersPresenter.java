package ru.squel.babynote.shortAction.pampers;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Date;

import ru.squel.babynote.data.Pampers;
import ru.squel.babynote.data.baseData.DataBaseEntry;
import ru.squel.babynote.database.providers.EatProvider;
import ru.squel.babynote.database.providers.PampersProvider;
import ru.squel.babynote.layout.PampersButtonsFragmentPresenter;
import ru.squel.babynote.shortAction.ShortActionCallback;
import ru.squel.babynote.shortAction.ShortActionPresenter;
import ru.squel.babynote.utils.Injector;

/**
 * Created by sq on 17.10.2017.
 */

public class PampersPresenter extends ShortActionPresenter implements ShortActionCallback, PampersButtonsFragmentPresenter {

    private Pampers currentPampers;

    @Override
    public ArrayList<DataBaseEntry> getList() {
        // select all values by date
        Cursor cursor = Injector.instance().getAppContext().getContentResolver().query(PampersProvider.URI,
                null,
                PampersProvider.Columns.TIME + ">=? and " +  PampersProvider.Columns.TIME + " <= ?",
                new String[]{String.valueOf(getDateForRecyclerViewBegin()), String.valueOf(getDateForRecyclerViewBegin() + 1000*60*60*24)},
                EatProvider.Columns.ID);

        // form recyclerView
        ArrayList<DataBaseEntry> items = new ArrayList<DataBaseEntry>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            long id = cursor.getInt(cursor.getColumnIndex(PampersProvider.Columns.ID));
            int type = cursor.getInt(cursor.getColumnIndex(PampersProvider.Columns.TYPE));
            long beginDate = cursor.getLong(cursor.getColumnIndex(PampersProvider.Columns.TIME));

            Pampers item = new Pampers(type, beginDate);
            item.setId(id);

            items.add(item);
            cursor.moveToNext();
        }

        cursor.close();
        return items;
    }

    public void insertPampers() {
        if (currentPampers != null) {
            ContentValues values = new ContentValues();
            values.put(PampersProvider.Columns.TIME, currentPampers.getDateTimeBegin());
            values.put(PampersProvider.Columns.TYPE, currentPampers.getType());

            Uri newUri = Injector.instance().getAppContext().getContentResolver().insert(PampersProvider.URI, values);
        }
    }

    @Override
    public void buttonSmallHandlerCallback() {
        currentPampers = new Pampers(Pampers.pampersTypes.get(Pampers.pampersSmallType), new Date().getTime());
        insertPampers();
        view.updateRecyclerView();
    }

    @Override
    public void buttonLargeHandlerCallback() {
        currentPampers = new Pampers(Pampers.pampersTypes.get(Pampers.pampersLargeType), new Date().getTime());
        insertPampers();
        view.updateRecyclerView();
    }

    @Override
    public void buttonSmallAndLargeHandlerCallback() {
        currentPampers = new Pampers(Pampers.pampersTypes.get(Pampers.pampersSmallAndLargeType), new Date().getTime());
        insertPampers();
        view.updateRecyclerView();
    }

    @Override
    public void startButtonHandler() {
        //не требуется, т.к. процесс без таймера
    }

    @Override
    public void stopButtonHandler(String valueSeconds) {
        //не требуется, т.к. процесс без таймера
    }
}
