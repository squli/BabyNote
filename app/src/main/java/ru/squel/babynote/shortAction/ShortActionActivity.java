package ru.squel.babynote.shortAction;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ru.squel.babynote.BaseActivity;
import ru.squel.babynote.R;
import ru.squel.babynote.data.Play;
import ru.squel.babynote.data.Sleep;
import ru.squel.babynote.data.Swim;
import ru.squel.babynote.data.Walk;
import ru.squel.babynote.layout.PampersButtons;
import ru.squel.babynote.layout.ShortActionHeaderFragment;
import ru.squel.babynote.shortAction.pampers.PampersPresenter;
import ru.squel.babynote.shortAction.pampers.PampersRecyclerViewFragment;
import ru.squel.babynote.shortAction.playing.PlayPresenter;
import ru.squel.babynote.shortAction.playing.PlayRecyclerViewFragment;
import ru.squel.babynote.shortAction.sleeping.SleepPresenter;
import ru.squel.babynote.shortAction.sleeping.SleepRecyclerViewFragment;
import ru.squel.babynote.shortAction.swiming.SwimPresenter;
import ru.squel.babynote.shortAction.swiming.SwimRecyclerViewFragment;
import ru.squel.babynote.shortAction.walking.WalkPresenter;
import ru.squel.babynote.shortAction.walking.WalkRecyclerViewFragment;
import ru.squel.babynote.timer.TimerFragment;

/**
 * Базовая активность для отображения всех продолжительных действий, за исключением еды.
 */
public class ShortActionActivity extends BaseActivity implements ShortActionViewPresenterContract.View {

    public static final String TAG = ShortActionActivity.class.getSimpleName();

    public static final int shortActionPampers  = 1;
    public static final int shortActionSleep    = 2;
    public static final int shortActionWalk     = 3;
    public static final int shortActionPlay     = 4;
    public static final int shortActionSwim     = 5;
    public static final int shortActionDrugs    = 6;

    public static final String shortActionTypeTitle = "shortActionType";

    private int type = 0;

    ShortActionRecyclerViewFragment shortActionRecyclerViewFragment = null;
    ShortActionPresenter presenter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_short_action);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle getBundle = this.getIntent().getExtras();
        if (getBundle != null) {
            /// получение типа создваемой активности
            type = getBundle.getInt(shortActionTypeTitle);

            /// создание активности
            switch (type) {
                case (ShortActionActivity.shortActionPampers): {

                    setTitle(R.string.PampersActivityTitle);

                    shortActionRecyclerViewFragment = new PampersRecyclerViewFragment();
                    presenter = new PampersPresenter();
                    presenter.setView(this);
                    PampersButtons pampersButtons = new PampersButtons();
                    pampersButtons.setPresenter(presenter);
                    shortActionRecyclerViewFragment.setCallback(presenter);

                    // новая транзакция
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_short_action_container_buttons, pampersButtons);
                    transaction.replace(R.id.fragment_short_action_container_recyclerview, (PampersRecyclerViewFragment)shortActionRecyclerViewFragment);
                    transaction.addToBackStack(null);
                    transaction.setTransition(FragmentTransaction.TRANSIT_NONE);
                    transaction.commit();

                    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_short_action);
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Snackbar.make(view, "Date selected", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                            datePicker();
                        }
                    });

                     break;
                }

                case (ShortActionActivity.shortActionSleep): {

                    shortActionRecyclerViewFragment = new SleepRecyclerViewFragment();
                    presenter = new SleepPresenter();
                    presenter.setView(this);
                    shortActionRecyclerViewFragment.setCallback(presenter);

                    ShortActionHeaderFragment sleepHeader = new ShortActionHeaderFragment();
                    sleepHeader.setHeaderTitle(getResources().getString(R.string.sleep_header_title));
                    sleepHeader.setSpinnerValues(this, new Sleep().getTypesMap());
                    presenter.setHeaderFragment(sleepHeader);

                    TimerFragment timerFragment = new TimerFragment();
                    timerFragment.setPresenter(presenter);

                    // новая транзакция
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    // замена фрейма на созданный фрагмент
                    transaction.replace(R.id.fragment_short_action_container_buttons, sleepHeader);
                    transaction.replace(R.id.fragment_short_action_container_timerview, timerFragment);
                    transaction.replace(R.id.fragment_short_action_container_recyclerview, (SleepRecyclerViewFragment)shortActionRecyclerViewFragment);
                    transaction.addToBackStack(null);
                    transaction.setTransition(FragmentTransaction.TRANSIT_NONE);
                    transaction.commit();

                    break;
                }
                case (ShortActionActivity.shortActionWalk): {

                    shortActionRecyclerViewFragment = new WalkRecyclerViewFragment();
                    presenter = new WalkPresenter();
                    presenter.setView(this);
                    shortActionRecyclerViewFragment.setCallback(presenter);

                    ShortActionHeaderFragment walkHeader = new ShortActionHeaderFragment();
                    walkHeader.setHeaderTitle(getResources().getString(R.string.walk_header_title));
                    walkHeader.setSpinnerValues(this, new Walk().getTypesMap());
                    presenter.setHeaderFragment(walkHeader);

                    TimerFragment timerFragment = new TimerFragment();
                    timerFragment.setPresenter(presenter);

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    // замена фрейма на созданный фрагмент
                    transaction.replace(R.id.fragment_short_action_container_buttons, walkHeader);
                    transaction.replace(R.id.fragment_short_action_container_timerview, timerFragment);
                    transaction.replace(R.id.fragment_short_action_container_recyclerview, (WalkRecyclerViewFragment)shortActionRecyclerViewFragment);
                    transaction.addToBackStack(null);
                    transaction.setTransition(FragmentTransaction.TRANSIT_NONE);
                    transaction.commit();

                    break;
                }
                case (ShortActionActivity.shortActionPlay): {

                    shortActionRecyclerViewFragment = new PlayRecyclerViewFragment();
                    presenter = new PlayPresenter();
                    presenter.setView(this);
                    shortActionRecyclerViewFragment.setCallback(presenter);

                    ShortActionHeaderFragment playHeader = new ShortActionHeaderFragment();
                    playHeader.setHeaderTitle(getResources().getString(R.string.play_header_title));
                    playHeader.setSpinnerValues(this, new Play().getTypesMap());
                    presenter.setHeaderFragment(playHeader);

                    TimerFragment timerFragment = new TimerFragment();
                    timerFragment.setPresenter(presenter);

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    // замена фрейма на созданный фрагмент
                    transaction.replace(R.id.fragment_short_action_container_buttons, playHeader);
                    transaction.replace(R.id.fragment_short_action_container_timerview, timerFragment);
                    transaction.replace(R.id.fragment_short_action_container_recyclerview, (PlayRecyclerViewFragment)shortActionRecyclerViewFragment);
                    transaction.addToBackStack(null);
                    transaction.setTransition(FragmentTransaction.TRANSIT_NONE);
                    transaction.commit();

                    break;
                }
                case (ShortActionActivity.shortActionSwim): {

                    shortActionRecyclerViewFragment = new SwimRecyclerViewFragment();
                    presenter = new SwimPresenter();
                    presenter.setView(this);
                    shortActionRecyclerViewFragment.setCallback(presenter);

                    ShortActionHeaderFragment swimHeader = new ShortActionHeaderFragment();
                    swimHeader.setHeaderTitle(getResources().getString(R.string.swim_header_title));
                    swimHeader.setSpinnerValues(this, new Swim().getTypesMap());
                    presenter.setHeaderFragment(swimHeader);

                    TimerFragment timerFragment = new TimerFragment();
                    timerFragment.setPresenter(presenter);

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    // замена фрейма на созданный фрагмент
                    transaction.replace(R.id.fragment_short_action_container_buttons, swimHeader);
                    transaction.replace(R.id.fragment_short_action_container_timerview, timerFragment);
                    transaction.replace(R.id.fragment_short_action_container_recyclerview, (SwimRecyclerViewFragment)shortActionRecyclerViewFragment);
                    transaction.addToBackStack(null);
                    transaction.setTransition(FragmentTransaction.TRANSIT_NONE);
                    transaction.commit();

                    break;
                }

                case (ShortActionActivity.shortActionDrugs): {

                    //shortActionRecyclerViewFragment = new DrugsRecyclerViewFragment();

                    break;
                }

                default:
                    break;
            }
        }
    }

    @Override
    public void updateRecyclerView() {
        if (shortActionRecyclerViewFragment != null)
            shortActionRecyclerViewFragment.refreshRecyclerView();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
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
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        String date_time = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                        try {
                            presenter.setDateToDisplayRecyclerView( (Date)formatter.parse(date_time) );
                        }
                        catch (ParseException e) {
                            e.printStackTrace();
                        }
                        shortActionRecyclerViewFragment.refreshRecyclerView();
                        displayEatingListHelpMessage();
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void displayEatingListHelpMessage() {
        DateFormat formatter = new SimpleDateFormat("dd:MM:yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(presenter.getDateForRecyclerViewBegin());

        //TODO Привестив  чувство заголовок списка
        //TextView listHeader = (TextView) findViewById(R.id.eatingListHelpMessage);
        //listHeader.setText(getResources().getString(R.string.eating_list_help_message, formatter.format(calendar.getTime())));
    }
}
