package ru.squel.babynote.shortAction.swiming;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Date;

import ru.squel.babynote.data.Swim;
import ru.squel.babynote.data.baseData.DataBaseEntry;
import ru.squel.babynote.database.providers.SwimProvider;
import ru.squel.babynote.shortAction.ShortActionPresenter;
import ru.squel.babynote.timer.TimerButtonsCallback;
import ru.squel.babynote.utils.Injector;

/**
 * Created by sq on 02.11.2017.
 */

public class SwimPresenter extends ShortActionPresenter implements TimerButtonsCallback {

    private boolean addedFlag = false;
    private Swim currentSwiming = new Swim();

    @Override
    public ArrayList<DataBaseEntry> getList() {
        // select all values by date
        Cursor cursor = Injector.instance().getAppContext().getContentResolver().query(SwimProvider.URI,
                null,
                SwimProvider.Columns.BEGIN + ">=? and " +  SwimProvider.Columns.BEGIN + " <= ?",
                new String[]{String.valueOf(getDateForRecyclerViewBegin()), String.valueOf(getDateForRecyclerViewBegin() + 1000*60*60*24)},
                SwimProvider.Columns.ID);

        // form recyclerView
        ArrayList<DataBaseEntry> items = new ArrayList<DataBaseEntry>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            long id = cursor.getInt(cursor.getColumnIndex(SwimProvider.Columns.ID));
            int type = cursor.getInt(cursor.getColumnIndex(SwimProvider.Columns.TYPE));
            long beginDate = cursor.getLong(cursor.getColumnIndex(SwimProvider.Columns.BEGIN));
            long endDate = cursor.getLong(cursor.getColumnIndex(SwimProvider.Columns.END));

            Swim item = new Swim(beginDate,endDate, type);
            item.setId(id);

            items.add(item);
            cursor.moveToNext();
        }

        cursor.close();
        return items;
    }

    @Override
    public void startButtonHandler() {
        currentSwiming.setDateTimeBegin(new Date().getTime());
        addedFlag = false;
    }

    @Override
    public void stopButtonHandler(String valueSeconds) {
        if (!addedFlag) {
            if (super.getSpinnerValue() >= 0) {
                currentSwiming.setType(super.getSpinnerValue());
            }
            insertSwim();
            view.updateRecyclerView();
            addedFlag = true;
        }
    }

    public void insertSwim() {
        currentSwiming.setDateTimeEnd(new Date());

        ContentValues values = new ContentValues();
        values.put(SwimProvider.Columns.BEGIN, currentSwiming.getDateTimeBegin());
        values.put(SwimProvider.Columns.END, currentSwiming.getDateTimeEnd());
        values.put(SwimProvider.Columns.TYPE, currentSwiming.getType());

        Uri newUri = Injector.instance().getAppContext().getContentResolver().insert(SwimProvider.URI, values);
    }
}
