package ru.squel.babynote.shortAction.walking;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Date;

import ru.squel.babynote.data.Walk;
import ru.squel.babynote.data.baseData.DataBaseEntry;
import ru.squel.babynote.database.providers.WalkProvider;
import ru.squel.babynote.shortAction.ShortActionPresenter;
import ru.squel.babynote.timer.TimerButtonsCallback;
import ru.squel.babynote.utils.Injector;

/**
 * Created by sq on 31.10.2017.
 */

public class WalkPresenter extends ShortActionPresenter
        implements TimerButtonsCallback {

    private boolean addedFlag = false;
    private Walk currentWalking = new Walk();

    @Override
    public void startButtonHandler() {
        currentWalking.setDateTimeBegin(new Date().getTime());
        addedFlag = false;
    }

    @Override
    public void stopButtonHandler(String valueSeconds) {
        if (!addedFlag) {
            if (super.getSpinnerValue() >= 0) {
                currentWalking.setType(super.getSpinnerValue());
            }
            insertWalk();
            view.updateRecyclerView();
            addedFlag = true;
        }
    }

    @Override
    public ArrayList<DataBaseEntry> getList() {
        // select all values by date
        Cursor cursor = Injector.instance().getAppContext().getContentResolver().query(WalkProvider.URI,
                null,
                WalkProvider.Columns.BEGIN + ">=? and " +  WalkProvider.Columns.BEGIN + " <= ?",
                new String[]{String.valueOf(getDateForRecyclerViewBegin()), String.valueOf(getDateForRecyclerViewBegin() + 1000*60*60*24)},
                WalkProvider.Columns.ID);

        // form recyclerView
        ArrayList<DataBaseEntry> items = new ArrayList<DataBaseEntry>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            long id = cursor.getInt(cursor.getColumnIndex(WalkProvider.Columns.ID));
            int type = cursor.getInt(cursor.getColumnIndex(WalkProvider.Columns.TYPE));
            long beginDate = cursor.getLong(cursor.getColumnIndex(WalkProvider.Columns.BEGIN));
            long endDate = cursor.getLong(cursor.getColumnIndex(WalkProvider.Columns.END));

            Walk item = new Walk(beginDate,endDate, type);
            item.setId(id);

            items.add(item);
            cursor.moveToNext();
        }

        cursor.close();
        return items;
    }

    public void insertWalk() {
        currentWalking.setDateTimeEnd(new Date());
        ContentValues values = new ContentValues();
        values.put(WalkProvider.Columns.BEGIN, currentWalking.getDateTimeBegin());
        values.put(WalkProvider.Columns.END, currentWalking.getDateTimeEnd());
        values.put(WalkProvider.Columns.TYPE, currentWalking.getType());
        Uri newUri = Injector.instance().getAppContext().getContentResolver().insert(WalkProvider.URI, values);
    }

}
