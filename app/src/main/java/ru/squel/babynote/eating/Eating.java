package ru.squel.babynote.eating;

import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ru.squel.babynote.BaseActivity;
import ru.squel.babynote.R;
import ru.squel.babynote.layout.EatingButtons;
import ru.squel.babynote.timer.TimerButtonsCallback;
import ru.squel.babynote.timer.TimerFragment;

public class Eating extends BaseActivity implements TimerButtonsCallback,
                                                         EatingViewPresenterContract.View {

    private TextView eatingHeader = null;

    private EatingPresenter presenter = new EatingPresenter(this);

    EatItemFragment eatRecyclerViewFragment = new EatItemFragment();

    private boolean addedFlag = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eating);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(R.string.EatingActivityTitle);

        String name = null;
        int amount = 0;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Date selected", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                datePicker();
            }
        });

        EatingButtons eatingButtons = (EatingButtons) getSupportFragmentManager().findFragmentById(R.id.fragment_eating_buttons);
        eatingButtons.setEatingButtonsFragmentPresenter(this.presenter);

        TimerFragment timerFragment = (TimerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_timer);
        timerFragment.setPresenter(this);

        //установил данные для отображения
        eatRecyclerViewFragment.setEatItemFragmentCallback(this.presenter);
        // новая транзакция
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        // замена фрейма на созданный фрагмент
        transaction.replace(R.id.fragment_container_recyclerview, eatRecyclerViewFragment);
        // запуск транзакции без названия
        transaction.addToBackStack(null);
        // настройка анимации
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        // отправка фрагмента на исполнение
        transaction.commit();

        eatingHeader = (TextView) findViewById(R.id.eatingSourse);

        Bundle getBundle = this.getIntent().getExtras();
        if (getBundle != null) {
            name = getBundle.getString("name");
            amount = getBundle.getInt("amount", 0);
            presenter.parseBundle(name, amount);
        }

        displayEatingListHelpMessage();
    }

    @Override
    public void startButtonHandler() {
        presenter.setBeginTimeCurrentEating();
        addedFlag = false;
    }

    @Override
    public void stopButtonHandler(String stopTimestamp) {
        if (!addedFlag) {
            presenter.insertEating();
            eatRecyclerViewFragment.refreshRecyclerView();
            addedFlag = true;
        }
    }

    public void displayEatingHeaderMilk(String name) {
        eatingHeader.setText(getResources().getString(R.string.eating_milk_header_message, name));
    }

    public void displayEatingHeaderMilk(int amount) {
        if (amount == 0) {
            eatingHeader.setText(getResources().getString(R.string.eating_bottle_header_message));
        }
        else {
            eatingHeader.setText(getResources().getString(R.string.eating_bottle_with_amount_header_message, amount));
        }
    }


    private void datePicker(){
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {

                        String date_time = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                        try {
                            presenter.setDateToDisplayRecyclerView( (Date)formatter.parse(date_time) );
                        }
                        catch (ParseException e) {
                            e.printStackTrace();
                        }
                        eatRecyclerViewFragment.refreshRecyclerView();
                        displayEatingListHelpMessage();
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void displayEatingListHelpMessage() {
        DateFormat formatter = new SimpleDateFormat("dd:MM:yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(presenter.getDateToDisplayRecyclerView());
        TextView listHeader = (TextView) findViewById(R.id.eatingListHelpMessage);
        listHeader.setText(getResources().getString(R.string.eating_list_help_message, formatter.format(calendar.getTime())));
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }
}
