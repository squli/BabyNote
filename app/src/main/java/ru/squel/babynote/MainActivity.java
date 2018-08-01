package ru.squel.babynote;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Date;

import ru.squel.babynote.data.baseData.DataBaseEntry;
import ru.squel.babynote.layout.EatingButtons;
import ru.squel.babynote.layout.EatingButtonsFragmentPresenter;
import ru.squel.babynote.statistics.StatisticsItemFragment;

public class MainActivity extends BaseActivity
        implements MainViewPresenterContract.View,
                   StatisticsItemFragment.OnListFragmentInteractionListener {

    StatisticsItemFragment statisticsItemFragment = new StatisticsItemFragment();
    private MainViewPresenterContract.Presenter presenter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.app_bar_main_portrait);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Main Activity Title");

        if (presenter == null)
            presenter = new MainPresenter(this);

        /// фрагмент с кнопками управления едой
        EatingButtons eatingButtons = (EatingButtons) getSupportFragmentManager().findFragmentById(R.id.fragment_eating_buttons);
        eatingButtons.setEatingButtonsFragmentPresenter((EatingButtonsFragmentPresenter) presenter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        updateDayStatistic();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }

    public void updateDayStatistic() {
        ArrayList<DataBaseEntry> todayList = presenter.formAllDayProceduresList(new Date());
        statisticsItemFragment.setAdapter(todayList, getResources());

        // новая транзакция
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // замена фрейма на созданный фрагмент
        transaction.replace(R.id.fragment_today_statistics_container, statisticsItemFragment);
        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        transaction.commit();
    }

    @Override
    public void onListFragmentInteraction(DataBaseEntry item) {

    }
}
