package ru.squel.babynote.utils;

import android.app.Application;

/**
 * Created by sq on 29.08.2017.
 */
public class BabyNoteApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Injector.instance().init(this);
    }
}
