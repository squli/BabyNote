package ru.squel.babynote.layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ru.squel.babynote.R;
import ru.squel.babynote.dialogs.BottleDialog;
import ru.squel.babynote.dialogs.BottleDialogCallback;

/**
 *
 */
public class EatingButtons extends Fragment implements BottleDialogCallback {

    private EatingButtonsFragmentPresenter eatingButtonsFragmentPresenter = null;

    private Button leftButton;
    private Button rightButton;
    private Button bottleButton;

    public void setEatingButtonsFragmentPresenter(EatingButtonsFragmentPresenter eatingButtonsFragmentPresenter) {
        this.eatingButtonsFragmentPresenter = eatingButtonsFragmentPresenter;
    }

    public EatingButtons() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_eating_buttons, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        leftButton = (Button) getView().findViewById(R.id.eatLeftButton);
        rightButton = (Button) getView().findViewById(R.id.eatRightButton);
        bottleButton = (Button) getView().findViewById(R.id.eatBottle);

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eatingButtonsFragmentPresenter != null)
                    eatingButtonsFragmentPresenter.buttonLeftHandlerCallback();
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eatingButtonsFragmentPresenter != null)
                    eatingButtonsFragmentPresenter.buttonRightHandlerCallback();
            }
        });

        bottleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottleDialog dialog = new BottleDialog();
                dialog.setBottleCallback(EatingButtons.this);
                dialog.show(getActivity().getFragmentManager(), "Bottle");
            }
        });
    }

    @Override
    public void okButtonHandler(int amount) {
        if (eatingButtonsFragmentPresenter != null)
            eatingButtonsFragmentPresenter.buttonBottleHandlerCallback(amount);
    }
}
