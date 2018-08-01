package ru.squel.babynote.shortAction.playing;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Date;

import ru.squel.babynote.data.Play;
import ru.squel.babynote.data.baseData.DataBaseEntry;
import ru.squel.babynote.database.providers.PlayProvider;
import ru.squel.babynote.shortAction.ShortActionPresenter;
import ru.squel.babynote.timer.TimerButtonsCallback;
import ru.squel.babynote.utils.Injector;

/**
 * Created by Саша on 02.11.2017.
 */

public class PlayPresenter extends ShortActionPresenter implements TimerButtonsCallback {

    private boolean addedFlag = false;
    private Play currentPlaying = new Play();

    @Override
    public ArrayList<DataBaseEntry> getList() {
        // select all values by date
        Cursor cursor = Injector.instance().getAppContext().getContentResolver().query(PlayProvider.URI,
                null,
                PlayProvider.Columns.BEGIN + ">=? and " +  PlayProvider.Columns.BEGIN + " <= ?",
                new String[]{String.valueOf(getDateForRecyclerViewBegin()), String.valueOf(getDateForRecyclerViewBegin() + 1000*60*60*24)},
                PlayProvider.Columns.ID);

        // form recyclerView
        ArrayList<DataBaseEntry> items = new ArrayList<DataBaseEntry>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            long id = cursor.getInt(cursor.getColumnIndex(PlayProvider.Columns.ID));
            int type = cursor.getInt(cursor.getColumnIndex(PlayProvider.Columns.TYPE));
            long beginDate = cursor.getLong(cursor.getColumnIndex(PlayProvider.Columns.BEGIN));
            long endDate = cursor.getLong(cursor.getColumnIndex(PlayProvider.Columns.END));

            Play item = new Play(beginDate,endDate, type);
            item.setId(id);

            items.add(item);
            cursor.moveToNext();
        }

        cursor.close();
        return items;
    }

    @Override
    public void startButtonHandler() {
        currentPlaying.setDateTimeBegin(new Date().getTime());
        addedFlag = false;
    }

    @Override
    public void stopButtonHandler(String valueSeconds) {
        if (!addedFlag) {
            if (super.getSpinnerValue() >= 0) {
                currentPlaying.setType(super.getSpinnerValue());
            }
            insertPlay();
            view.updateRecyclerView();
            addedFlag = true;
        }
    }

    public void insertPlay() {
        currentPlaying.setDateTimeEnd(new Date());

        ContentValues values = new ContentValues();
        values.put(PlayProvider.Columns.BEGIN, currentPlaying.getDateTimeBegin());
        values.put(PlayProvider.Columns.END, currentPlaying.getDateTimeEnd());
        values.put(PlayProvider.Columns.TYPE, currentPlaying.getType());

        Uri newUri = Injector.instance().getAppContext().getContentResolver().insert(PlayProvider.URI, values);
    }
}
