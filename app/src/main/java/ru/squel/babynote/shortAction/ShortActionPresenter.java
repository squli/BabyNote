package ru.squel.babynote.shortAction;

import java.util.Date;

import ru.squel.babynote.layout.ShortActionHeaderFragment;
import ru.squel.babynote.timer.TimerButtonsCallback;

/**
 * Created by sq on 17.10.2017.
 * Базовый класс презентера для всех продолжительных действий
 */

public abstract class ShortActionPresenter implements TimerButtonsCallback, ShortActionCallback {

    public ShortActionViewPresenterContract.View view;
    private Date dateToDisplayRecyclerView = new Date();

    private ShortActionHeaderFragment headerFragment;

    public void setView(ShortActionViewPresenterContract.View view) {
        this.view = view;
    }

    public long getDateForRecyclerViewBegin() {
        Date d = dateToDisplayRecyclerView;
        d.setHours(0);
        d.setMinutes(0);
        d.setSeconds(0);
        return d.getTime();
    }

    public void setDateToDisplayRecyclerView(Date date) {
        dateToDisplayRecyclerView = date;
    }


    public void setHeaderFragment(final ShortActionHeaderFragment headerFragment) {
        this.headerFragment = headerFragment;
    }

    public int getSpinnerValue() {
        if (headerFragment != null) {
            return headerFragment.getSpinnerIndex();
        } else
            return -2;
    }
}
