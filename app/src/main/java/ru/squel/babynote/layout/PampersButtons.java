package ru.squel.babynote.layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ru.squel.babynote.R;
import ru.squel.babynote.shortAction.pampers.PampersPresenter;
import ru.squel.babynote.shortAction.ShortActionPresenter;

/**
 * Created by sq on 17.10.2017.
 */

public class PampersButtons extends Fragment {

    private PampersPresenter pampersButtonsFragmentPresenter = null;

    private Button smallButton;
    private Button largeButton;
    private Button smallAndLargeButton;

    public void setPresenter(ShortActionPresenter pampersButtonsFragmentPresenter) {
        this.pampersButtonsFragmentPresenter = (PampersPresenter) pampersButtonsFragmentPresenter;
    }

    public PampersButtons() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pampers_buttons, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        smallButton = (Button) getView().findViewById(R.id.pampersSmallButton);
        largeButton = (Button) getView().findViewById(R.id.pampersLargeButton);
        smallAndLargeButton = (Button) getView().findViewById(R.id.pampersSmallLargeButton);

        smallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pampersButtonsFragmentPresenter != null)
                    pampersButtonsFragmentPresenter.buttonSmallAndLargeHandlerCallback();
            }
        });

        largeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pampersButtonsFragmentPresenter != null)
                    pampersButtonsFragmentPresenter.buttonLargeHandlerCallback();
            }
        });

        smallAndLargeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pampersButtonsFragmentPresenter != null)
                    pampersButtonsFragmentPresenter.buttonSmallAndLargeHandlerCallback();
            }
        });
    }
}
