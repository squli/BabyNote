package ru.squel.babynote.timer;

/**
 * Created by sq on 26.08.2017.
 */
public class SimpleTimer {

    // число секунд для отображения
    private int seconds = 0;

    private int stepSize = 1000;

    // флаг работы таймера
    private boolean isRunnging = false;

    // второй фалг, отображает был ли запущен таймер в момент переключения фокуса
    private boolean wasRunning = false;

    public void setTime(int t) {seconds = t;}
    public void setState(boolean state) {
        isRunnging = state;
    }
    public void clearValue() {seconds = 0;}
    public int getSeconds() {return seconds;}
    public boolean getState() {return isRunnging;}
    public boolean getWasRunning() {return wasRunning;}
    public void setWasRunning(boolean wr) {wasRunning = wr;}
    public int getStepSize() {return stepSize;}

    // Получить отформатированный текст для отображения
    public String getFormattedText() {
        String s = new String();
        int hours = seconds / 3600_000;
        int minutes = (seconds % 3600_000) / 60_000;
        int seconds = (this.seconds % 60_000) / 1000;

        s = String.format("%01d:%02d:%03d", hours, minutes, seconds);
        if (isRunnging)
            this.seconds += stepSize;
        return s;
    }
}
