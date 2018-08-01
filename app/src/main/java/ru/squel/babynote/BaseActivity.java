package ru.squel.babynote;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ru.squel.babynote.data.Baby;
import ru.squel.babynote.database.providers.BabyProvider;
import ru.squel.babynote.dialogs.EditBabyDialog;
import ru.squel.babynote.dialogs.EditBabyDialogCallback;
import ru.squel.babynote.eating.Eating;
import ru.squel.babynote.shortAction.ShortActionActivity;
import ru.squel.babynote.utils.Injector;

/**
 * Created by sq on 22.10.2017.
 */

public abstract class BaseActivity extends AppCompatActivity implements EditBabyDialogCallback, NavigationView.OnNavigationItemSelectedListener{

    private LinearLayout babyHeader;

    private TextView leftMenuMainTextView;
    private TextView leftMenuEatingTextView;
    private TextView leftMenuPampersTextView;
    private TextView leftMenuSleepTextView;
    private TextView leftMenuWalkTextView;
    private TextView leftMenuPlayTextView;
    private TextView leftMenuSwimTextView;
    private TextView leftMenuDrugsTextView;
    private TextView leftMenuStatsTextView;

    private Baby currentBaby = new Baby();

    DrawerLayout drawer;

    @Override
    public void setContentView(int layoutResID)
    {
        drawer = (DrawerLayout) getLayoutInflater().inflate(R.layout.nav_drawler, null);
        FrameLayout activityContainer = (FrameLayout) drawer.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(drawer);

        /**
         * Установка текущего ребенка
         */
        Cursor cursor = Injector.instance().getAppContext().getContentResolver().query(BabyProvider.URI, null, null, null, null);
        if (cursor.moveToFirst() && cursor.getCount() > 0) {
            currentBaby.setBabyName(cursor.getString(cursor.getColumnIndex(BabyProvider.Columns.NAME)));
            currentBaby.setBabySex(cursor.getInt(cursor.getColumnIndex(BabyProvider.Columns.SEX)));
            DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            Date date = new Date();
            try {
                date = formatter.parse(cursor.getString(cursor.getColumnIndex(BabyProvider.Columns.BITHDAY)));
                currentBaby.setBabyBirthday(date);
            } catch (ParseException e) {
                e.printStackTrace();
            } finally {
                currentBaby.setBabyBirthday(date);
            }
        }

        babyHeader = (LinearLayout) drawer.findViewById(R.id.nav_view).findViewById(R.id.baby_header);
        final Baby baby = getCurrentBaby();
        if (baby != null) {
            updateNavigationHeaderBaby(baby);
        }
        babyHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                babyHeaderClickCallback().show(BaseActivity.this.getFragmentManager(), "Baby");
            }
        });
        ///кнопка главной страницы в левом меню
        leftMenuMainTextView = (TextView) drawer.findViewById(R.id.left_menu_main_page);
        leftMenuMainTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMainActivity(null);
            }
        });
        ///кнопка еды в левом меню
        leftMenuEatingTextView = (TextView) drawer.findViewById(R.id.left_menu_eating);
        leftMenuEatingTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEatingActivity(null);
            }
        });
        ///кнопка памперсов в левом меню
        leftMenuPampersTextView = (TextView) drawer.findViewById(R.id.left_menu_pampers);
        leftMenuPampersTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(ShortActionActivity.shortActionTypeTitle, ShortActionActivity.shortActionPampers);
                showShortActionActivity(bundle);
            }
        });
        ///кнопка сна в левом меню
        leftMenuSleepTextView = (TextView) drawer.findViewById(R.id.left_menu_sleep);
        leftMenuSleepTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(ShortActionActivity.shortActionTypeTitle, ShortActionActivity.shortActionSleep);
                showShortActionActivity(bundle);
            }
        });
        ///кнопка прогулки в левом меню
        leftMenuWalkTextView = (TextView) drawer.findViewById(R.id.left_menu_walk);
        leftMenuWalkTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(ShortActionActivity.shortActionTypeTitle, ShortActionActivity.shortActionWalk);
                showShortActionActivity(bundle);
            }
        });
        ///кнопка бодрствования в левом меню
        leftMenuPlayTextView = (TextView) drawer.findViewById(R.id.left_menu_play);
        leftMenuPlayTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(ShortActionActivity.shortActionTypeTitle, ShortActionActivity.shortActionPlay);
                showShortActionActivity(bundle);
            }
        });
        ///кнопка плавания в левом меню
        leftMenuSwimTextView = (TextView) drawer.findViewById(R.id.left_menu_swim);
        leftMenuSwimTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(ShortActionActivity.shortActionTypeTitle, ShortActionActivity.shortActionSwim);
                showShortActionActivity(bundle);
            }
        });
        ///кнопка лекарств в левом меню
        leftMenuDrugsTextView = (TextView) drawer.findViewById(R.id.left_menu_drugs);
        leftMenuDrugsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(ShortActionActivity.shortActionTypeTitle, ShortActionActivity.shortActionDrugs);
                showShortActionActivity(bundle);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            int currentBabyId = savedInstanceState.getInt("currentBabyId");
            setCurrentBabyId(currentBabyId);
        }
    }

    public Baby getCurrentBaby() {
        return currentBaby;
    }

    public EditBabyDialog babyHeaderClickCallback() {
        //show dialog of creation new baby
        EditBabyDialog dialog = new EditBabyDialog();
        dialog.setBaby(currentBaby);
        dialog.setCallback(this);
        return dialog;
    }

    public void setCurrentBabyId(int id) {
        //read from database parameters of this baby
        //currentBaby =
    }

    /**
     * Колбэк для кнопки ОК в диалоге редактирования ребенка
     * @param baby - отредактированный ребенок
     */
    @Override
    public void okButtonHandler(Baby baby) {
        this.currentBaby = baby;
        updateNavigationHeaderBaby(this.currentBaby);
        ContentValues values = new ContentValues();
        values.put(BabyProvider.Columns.NAME, baby.getName());
        values.put(BabyProvider.Columns.SEX, baby.getBabySex());
        values.put(BabyProvider.Columns.BITHDAY, baby.getBabyBirthday());

        Cursor cursor = Injector.instance().getAppContext().getContentResolver().query(BabyProvider.URI, null, null, null, null);
        if (cursor.moveToFirst() && cursor.getCount() > 0)
            Injector.instance().getAppContext().getContentResolver().update(BabyProvider.URI, values, BabyProvider.Columns.NAME + "=?", new String[]{baby.getName()});
        else
            Injector.instance().getAppContext().getContentResolver().insert(BabyProvider.URI, values);

    }

    public void showEatingActivity(Bundle bundle) {
        Intent intent = new Intent(this, Eating.class);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivity(intent);
    }

    public void showShortActionActivity(Bundle bundle) {
        Intent intent = new Intent(this, ShortActionActivity.class);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivity(intent);
    }

    public void showMainActivity(Bundle bundle) {
        Intent intent = new Intent(this, MainActivity.class);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
    * Обновляет заголовок левого меню
    * @param baby
     * */
    public void updateNavigationHeaderBaby(Baby baby) {
        TextView babyName = (TextView) drawer.findViewById(R.id.baby_name);
        babyName.setText(baby.getName());
        TextView babyBirthDay = (TextView) drawer.findViewById(R.id.baby_birthday);
        babyBirthDay.setText(baby.getBabyBirthday());
    }
}
