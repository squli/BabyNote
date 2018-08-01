package ru.squel.babynote;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Date;

import ru.squel.babynote.data.baseData.DataBaseEntry;

/**
 * Created by sq on 26.08.2017.
 */
public class MainViewPresenterContract {

    public interface View {
        void showEatingActivity(Bundle bundle);
        //void updateNavigationHeaderBaby(Baby baby);
    }

    public interface Presenter {
        ArrayList<DataBaseEntry> formAllDayProceduresList(final Date date);
    }
}
