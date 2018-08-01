package ru.squel.babynote.timer;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ru.squel.babynote.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class TimerFragment extends Fragment {

    private TimerButtonsCallback presenter = null;

    private Button startButton;
    private Button stopButton;
    private Button pauseButton;
    private TextView timerValue;

    private SimpleTimer simpleTimer = null;
    private Handler mHandler = new Handler();

    public TimerFragment() {
    }

    public void setPresenter(TimerButtonsCallback presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        simpleTimer = new SimpleTimer();

        if (savedInstanceState != null) {
            simpleTimer.setTime(savedInstanceState.getInt("timeInSeconds"));
            simpleTimer.setState(savedInstanceState.getBoolean("timerStatus"));
            simpleTimer.setWasRunning(savedInstanceState.getBoolean("timerRunning"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_timer, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        timerValue = (TextView) getView().findViewById(R.id.TimerTime);

        startButton = (Button) getView().findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.startButtonHandler();
                startTimer();
            }
        });

        stopButton = (Button) getView().findViewById(R.id.StopButton);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.stopButtonHandler(simpleTimer.getFormattedText());
                stopTimer();
            }
        });

        pauseButton = (Button) getView().findViewById(R.id.PauseButton);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
            }
        });
    }

    private void startTimer() {

        simpleTimer.setState(true);

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                timerValue.setText(simpleTimer.getFormattedText());
                mHandler.postDelayed(this, simpleTimer.getStepSize());
            }
        });
    }

    private void stopTimer() {
        simpleTimer.setState(false);
        simpleTimer.clearValue();
    }

    private void pauseTimer() {
        if (simpleTimer.getState() == true)
            simpleTimer.setState(false);
        else
            simpleTimer.setState(true);
    }
}
