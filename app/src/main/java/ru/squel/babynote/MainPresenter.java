package ru.squel.babynote;

import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import ru.squel.babynote.data.Eat;
import ru.squel.babynote.data.Pampers;
import ru.squel.babynote.data.Sleep;
import ru.squel.babynote.data.Swim;
import ru.squel.babynote.data.Walk;
import ru.squel.babynote.data.baseData.DataBaseEntry;
import ru.squel.babynote.database.providers.EatProvider;
import ru.squel.babynote.database.providers.PampersProvider;
import ru.squel.babynote.database.providers.SleepProvider;
import ru.squel.babynote.database.providers.SwimProvider;
import ru.squel.babynote.database.providers.WalkProvider;
import ru.squel.babynote.layout.EatingButtonsFragmentPresenter;
import ru.squel.babynote.layout.PampersButtonsFragmentPresenter;
import ru.squel.babynote.utils.Injector;

/**
 * Created by sq on 26.08.2017.
 */
public class MainPresenter implements MainViewPresenterContract.Presenter,
        EatingButtonsFragmentPresenter,
        PampersButtonsFragmentPresenter
         {

    private MainViewPresenterContract.View view = null;

    public MainPresenter(MainViewPresenterContract.View view) {
        this.view = view;
    }

    private void displayEating(String name) {
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        view.showEatingActivity(bundle);
    }

    @Override
    public void buttonLeftHandlerCallback() { displayEating("milk from left side");  }

    @Override
    public void buttonRightHandlerCallback() {
        displayEating("milk from right side");
    }

    @Override
    public void buttonBottleHandlerCallback(int amount) {
        Bundle bundle = new Bundle();
        bundle.putString("name", "from bottle");
        bundle.putInt("amount", amount);
        view.showEatingActivity(bundle);
    }

    @Override
    public void buttonSmallHandlerCallback() {

    }

    @Override
    public void buttonLargeHandlerCallback() {

    }

    @Override
    public void buttonSmallAndLargeHandlerCallback() {

    }

             /**
              * Метод получения списка всех данных для одного дня
              * @param date - дата на которую интересют события
              * @return
              */
    public ArrayList<DataBaseEntry> formAllDayProceduresList(final Date date) {

        ArrayList<DataBaseEntry> list = new ArrayList<>();

        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);

        // select all values by date
        Cursor cursor = Injector.instance().getAppContext().getContentResolver().query(EatProvider.URI,
                null,
                EatProvider.Columns.BEGIN + ">=? and " +  EatProvider.Columns.BEGIN + " <= ?",
                new String[]{String.valueOf(date.getTime()), String.valueOf(date.getTime() + 1000*60*60*24)},
                EatProvider.Columns.ID);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            long id = cursor.getInt(cursor.getColumnIndex(EatProvider.Columns.ID));
            int type = cursor.getInt(cursor.getColumnIndex(EatProvider.Columns.TYPE));
            int amount = cursor.getInt(cursor.getColumnIndex(EatProvider.Columns.AMOUNT));
            long beginDate = cursor.getLong(cursor.getColumnIndex(EatProvider.Columns.BEGIN));
            long endDate = cursor.getLong(cursor.getColumnIndex(EatProvider.Columns.END));

            Eat item = new Eat(type, amount);
            item.setDateTimeBegin(new Date(beginDate));
            item.setDateTimeEnd(new Date(endDate));
            item.setId(id);

            list.add(item);
            cursor.moveToNext();
        }
        cursor.close();

        // select all values by date
        cursor = Injector.instance().getAppContext().getContentResolver().query(PampersProvider.URI,
                null,
                PampersProvider.Columns.TIME + ">=? and " +  PampersProvider.Columns.TIME + " <= ?",
                new String[]{String.valueOf(date.getTime()), String.valueOf(date.getTime() + 1000*60*60*24)},
                PampersProvider.Columns.ID);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            long id = cursor.getInt(cursor.getColumnIndex(PampersProvider.Columns.ID));
            int type = cursor.getInt(cursor.getColumnIndex(PampersProvider.Columns.TYPE));
            long beginDate = cursor.getLong(cursor.getColumnIndex(PampersProvider.Columns.TIME));
            Pampers item = new Pampers(type, beginDate);
            item.setId(id);
            list.add(item);
            cursor.moveToNext();
        }
        cursor.close();

        // select all values by date
        cursor = Injector.instance().getAppContext().getContentResolver().query(SleepProvider.URI,
                null,
                SleepProvider.Columns.BEGIN + ">=? and " +  SleepProvider.Columns.BEGIN + " <= ?",
                new String[]{String.valueOf(date.getTime()), String.valueOf(date.getTime() + 1000*60*60*24)},
                SleepProvider.Columns.ID);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            long id = cursor.getInt(cursor.getColumnIndex(SleepProvider.Columns.ID));
            int type = cursor.getInt(cursor.getColumnIndex(SleepProvider.Columns.TYPE));
            long beginDate = cursor.getLong(cursor.getColumnIndex(SleepProvider.Columns.BEGIN));
            long endDate = cursor.getLong(cursor.getColumnIndex(SleepProvider.Columns.END));

            Sleep item = new Sleep(beginDate, endDate, type);
            item.setId(id);
            list.add(item);
            cursor.moveToNext();
        }
        cursor.close();

        // select all values by date
        cursor = Injector.instance().getAppContext().getContentResolver().query(SwimProvider.URI,
                null,
                SwimProvider.Columns.BEGIN + ">=? and " +  SwimProvider.Columns.BEGIN + " <= ?",
                new String[]{String.valueOf(date.getTime()), String.valueOf(date.getTime() + 1000*60*60*24)},
                SwimProvider.Columns.ID);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            long id = cursor.getInt(cursor.getColumnIndex(SwimProvider.Columns.ID));
            int type = cursor.getInt(cursor.getColumnIndex(SwimProvider.Columns.TYPE));
            long beginDate = cursor.getLong(cursor.getColumnIndex(SwimProvider.Columns.BEGIN));
            long endDate = cursor.getLong(cursor.getColumnIndex(SwimProvider.Columns.END));

            Swim item = new Swim(beginDate, endDate, type);
            item.setId(id);
            list.add(item);
            cursor.moveToNext();
        }
        cursor.close();

        // select all values by date
        cursor = Injector.instance().getAppContext().getContentResolver().query(WalkProvider.URI,
                null,
                WalkProvider.Columns.BEGIN + ">=? and " +  WalkProvider.Columns.BEGIN + " <= ?",
                new String[]{String.valueOf(date.getTime()), String.valueOf(date.getTime() + 1000*60*60*24)},
                WalkProvider.Columns.ID);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            long id = cursor.getInt(cursor.getColumnIndex(WalkProvider.Columns.ID));
            int type = cursor.getInt(cursor.getColumnIndex(WalkProvider.Columns.TYPE));
            long beginDate = cursor.getLong(cursor.getColumnIndex(WalkProvider.Columns.BEGIN));
            long endDate = cursor.getLong(cursor.getColumnIndex(WalkProvider.Columns.END));

            Walk item = new Walk(beginDate, endDate, type);
            item.setId(id);
            list.add(item);
            cursor.moveToNext();
        }
        cursor.close();

        Collections.sort(list, new Comparator<DataBaseEntry>(){
            public int compare(DataBaseEntry o1, DataBaseEntry o2){
                if(o1.getDateTimeBegin() == o2.getDateTimeBegin())
                    return 0;
                return o1.getDateTimeBegin() < o2.getDateTimeBegin() ? -1 : 1;
            }
        });

        return list;
    }
}
