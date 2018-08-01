package ru.squel.babynote.dialogs;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ru.squel.babynote.R;
import ru.squel.babynote.data.Baby;

/**
 * Created by sq on 02.09.2017.
 */
public class EditBabyDialog extends DialogFragment {

    public static final String TAG = EditBabyDialog.class.getSimpleName();

    private EditBabyDialogCallback callback = null;
    private Baby baby = null;

    private TextView birthDay = null;
    private EditText babyName = null;
    private RadioGroup sexRadioButton = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setCallback(EditBabyDialogCallback callback) {
        this.callback = callback;
    }

    public void setBaby(Baby baby) {
        this.baby = baby;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.baby_dialog, null);
        builder.setView(view);

        babyName = (EditText) view.findViewById(R.id.baby_name_dialog);
        sexRadioButton = (RadioGroup) view.findViewById(R.id.baby_sex_radiobutton_dialog);
        birthDay = (TextView) view.findViewById(R.id.baby_birthday_dialog);
        birthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker();
            }
        });

        if (baby != null) {
            babyName.setText(baby.getName());
            if (baby.getBabySex() == 1)
                sexRadioButton.check(R.id.radioButton_female);
            else
                sexRadioButton.check(R.id.radioButton_male);

            birthDay.setText(baby.getBabyBirthday());
        }

        builder.setMessage("Edit baby: ")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (baby == null)
                            baby = new Baby();

                        baby.setBabyName(babyName.getText().toString());
                        if (sexRadioButton.getCheckedRadioButtonId() == R.id.radioButton_female)
                            baby.setBabySex(1);
                        else if (sexRadioButton.getCheckedRadioButtonId() == R.id.radioButton_male)
                            baby.setBabySex(0);
                        callback.okButtonHandler(baby);
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

    private void datePicker(){
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {

                        String date_time = dayOfMonth + "." + (monthOfYear + 1) + "." + year;
                        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
                        try {
                            Date date = formatter.parse(date_time);
                            baby.setBabyBirthday(date);
                            birthDay.setText(formatter.format(date).toString());
                        }
                        catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

}
