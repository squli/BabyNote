package ru.squel.babynote.timer;

/**
 * Created by sq on 26.08.2017.
 */
public interface TimerButtonsCallback {

    void startButtonHandler();
    void stopButtonHandler(String valueSeconds);

}
