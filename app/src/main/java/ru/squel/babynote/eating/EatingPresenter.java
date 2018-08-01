package ru.squel.babynote.eating;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Date;

import ru.squel.babynote.data.Eat;
import ru.squel.babynote.database.providers.EatProvider;
import ru.squel.babynote.layout.EatingButtonsFragmentPresenter;
import ru.squel.babynote.utils.Injector;

/**
 * Created by sq on 29.08.2017.
 */
public class EatingPresenter implements EatingButtonsFragmentPresenter, EatItemFragmentCallback, EatingViewPresenterContract.Presenter {

    private EatingViewPresenterContract.View myView = null;

    private Date dateToDisplayRecyclerView = new Date();

    private Eat currentEating = new Eat();

    public EatingPresenter(EatingViewPresenterContract.View view) {
        myView = view;
    }

    public long getDateForRecyclerViewBegin() {
        Date d = dateToDisplayRecyclerView;
        d.setHours(0);
        d.setMinutes(0);
        d.setSeconds(0);
        return d.getTime();
    }

    public long getDateToDisplayRecyclerView() {
        return dateToDisplayRecyclerView.getTime();
    }

    public void setDateToDisplayRecyclerView(Date dateToDisplayRecyclerView) {
        this.dateToDisplayRecyclerView = dateToDisplayRecyclerView;
    }

    public void parseBundle(String name, int amount) {
        if (!name.equals("from bottle")) {
            myView.displayEatingHeaderMilk(name);

            if (name.equals("milk from left side")) {
                currentEating.setType(Eat.eatTypes.get("Milk Left"));
            }
            else if (name.equals("milk from right side")) {
                currentEating.setType(Eat.eatTypes.get("Milk Right"));
            }
        }

        if (name.equals("from bottle")) {
            myView.displayEatingHeaderMilk(amount);
            currentEating.setType(Eat.eatTypes.get("Milk Bottle"));
            currentEating.setAmount(amount);
        }
    }

    @Override
    public ArrayList<Eat> getEatList() {
        // select all values by date
        Cursor cursor = Injector.instance().getAppContext().getContentResolver().query(EatProvider.URI,
                null,
                EatProvider.Columns.BEGIN + ">=? and " + EatProvider.Columns.BEGIN + " <= ?",
                new String[]{String.valueOf(getDateForRecyclerViewBegin()), String.valueOf(getDateForRecyclerViewBegin() + 1000*60*60*24)},
                EatProvider.Columns.ID);

        // form recyclerView
        ArrayList<Eat> items = new ArrayList<>();

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

            items.add(item);
            cursor.moveToNext();
        }

        cursor.close();
        return items;
    }

    public void insertEating() {
        currentEating.setDateTimeEnd(new Date());

        ContentValues values = new ContentValues();
        values.put(EatProvider.Columns.BEGIN, currentEating.getDateTimeBegin());
        values.put(EatProvider.Columns.END, currentEating.getDateTimeEnd());
        values.put(EatProvider.Columns.TYPE, currentEating.getType());
        values.put(EatProvider.Columns.AMOUNT, currentEating.getAmount());

        Uri newUri = Injector.instance().getAppContext().getContentResolver().insert(EatProvider.URI, values);
    }

    @Override
    public void buttonLeftHandlerCallback() {
        myView.displayEatingHeaderMilk("milk from left side");
        currentEating = new Eat(Eat.eatTypes.get("Milk Left"));
    }

    @Override
    public void buttonRightHandlerCallback() {
        myView.displayEatingHeaderMilk("milk from right side");
        currentEating = new Eat(Eat.eatTypes.get("Milk Right"));
    }

    @Override
    public void buttonBottleHandlerCallback(int amount) {
        myView.displayEatingHeaderMilk(amount);
        currentEating.setType(Eat.eatTypes.get("Milk Bottle"));
        currentEating.setAmount(amount);
    }

    public void setBeginTimeCurrentEating() {
        currentEating.setDateTimeBegin(new Date());
    }
}
