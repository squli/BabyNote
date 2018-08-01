package ru.squel.babynote.shortAction.sleeping;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Date;

import ru.squel.babynote.data.Sleep;
import ru.squel.babynote.data.baseData.DataBaseEntry;
import ru.squel.babynote.database.providers.SleepProvider;
import ru.squel.babynote.shortAction.ShortActionPresenter;
import ru.squel.babynote.timer.TimerButtonsCallback;
import ru.squel.babynote.utils.Injector;

/**
 * Created by sq on 21.10.2017.
 */

public class SleepPresenter extends ShortActionPresenter implements TimerButtonsCallback {

    private boolean addedFlag = false;
    private Sleep currentSleeping = new Sleep();

    @Override
    public ArrayList<DataBaseEntry> getList() {
        // select all values by date
        Cursor cursor = Injector.instance().getAppContext().getContentResolver().query(SleepProvider.URI,
                null,
                SleepProvider.Columns.BEGIN + ">=? and " +  SleepProvider.Columns.BEGIN + " <= ?",
                new String[]{String.valueOf(getDateForRecyclerViewBegin()), String.valueOf(getDateForRecyclerViewBegin() + 1000*60*60*24)},
                SleepProvider.Columns.ID);

        // form recyclerView
        ArrayList<DataBaseEntry> items = new ArrayList<DataBaseEntry>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            long id = cursor.getInt(cursor.getColumnIndex(SleepProvider.Columns.ID));
            int type = cursor.getInt(cursor.getColumnIndex(SleepProvider.Columns.TYPE));
            long beginDate = cursor.getLong(cursor.getColumnIndex(SleepProvider.Columns.BEGIN));
            long endDate = cursor.getLong(cursor.getColumnIndex(SleepProvider.Columns.END));

            Sleep item = new Sleep(beginDate,endDate, type);
            item.setId(id);

            items.add(item);
            cursor.moveToNext();
        }

        cursor.close();
        return items;
    }

    @Override
    public void startButtonHandler() {
        currentSleeping.setDateTimeBegin(new Date().getTime());
        addedFlag = false;
    }

    @Override
    public void stopButtonHandler(String valueSeconds) {
        if (!addedFlag) {
            if (super.getSpinnerValue() >= 0) {
                currentSleeping.setType(super.getSpinnerValue());
            }
            insertSleep();
            view.updateRecyclerView();
            addedFlag = true;
        }
    }

    public void insertSleep() {
        currentSleeping.setDateTimeEnd(new Date());

        ContentValues values = new ContentValues();
        values.put(SleepProvider.Columns.BEGIN, currentSleeping.getDateTimeBegin());
        values.put(SleepProvider.Columns.END, currentSleeping.getDateTimeEnd());
        values.put(SleepProvider.Columns.TYPE, currentSleeping.getType());

        Uri newUri = Injector.instance().getAppContext().getContentResolver().insert(SleepProvider.URI, values);
    }
}
