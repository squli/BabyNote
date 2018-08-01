package ru.squel.babynote.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import ru.squel.babynote.R;

/**
 * Created by sq on 26.08.2017.
 */
public class BottleDialog extends DialogFragment {

    private BottleDialogCallback callback = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.bottle_dialog, null);
        builder.setView(view);

        final EditText amount = (EditText) view.findViewById(R.id.bottleAmount);

        builder.setMessage("Eating from Bottle ")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        callback.okButtonHandler(Integer.parseInt(amount.getText().toString()));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        // do nothing
                    }
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }


    public void setBottleCallback(BottleDialogCallback callback) {
        this.callback = callback;
    }
}
