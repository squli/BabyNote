package ru.squel.babynote.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Set;

import ru.squel.babynote.data.baseData.DataBaseEntry;

/**
 * Created by sq on 02.09.2017.
 * Класс для отображения диалога редактирования всех записей - о кормлениях, подгудниках,
 * прогулках и т.д.
 */
public class EditEntryDialog extends DialogFragment {

    private EditEntryDialogCallback callback = null;

    private HashMap<String, EditText> editTextStringHashMap = new HashMap<>();
    private Spinner spinnerType = null;

    /**
     * Запись, которая подлежит редактированию, отсюда будут получены ключи названий полей
     * и их значения для редактирования.
     */
    private DataBaseEntry currentEntry = null;

    public void setCallback(EditEntryDialogCallback callback) {
        this.callback = callback;
    }

    public void setCurrentEntry(DataBaseEntry currentEntry) {
        this.currentEntry = currentEntry;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LinearLayout layout = new LinearLayout(getContext());
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(parms);

        layout.setGravity(Gravity.CLIP_VERTICAL);
        layout.setPadding(8, 8, 8, 8);

        Set<String> keySet = currentEntry.getParams().keySet();
        for (String key : keySet) {

            LinearLayout layoutHorizontal = new LinearLayout(getContext());
            layoutHorizontal.setOrientation(LinearLayout.HORIZONTAL);
            layoutHorizontal.setPadding(4, 4, 4, 4);
            layoutHorizontal.setLayoutParams(parms);

            LinearLayout.LayoutParams tv1Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            TextView tv = new TextView(getContext());
            int id = getResources().getIdentifier(key, "string", "ru.squel.babynote");
            tv.setText(getResources().getString(id));
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(16);
            layoutHorizontal.addView(tv, tv1Params);

            if (!key.equals("type")) {
                EditText et = new EditText(getContext());
                et.setText(currentEntry.getParams().get(key));
                layoutHorizontal.addView(et, tv1Params);

                editTextStringHashMap.put(key, et);
            } else {
                spinnerType = new Spinner(getContext());
                ArrayAdapter<String> adapter =new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, currentEntry.getTypesMap());
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerType.setAdapter(adapter);

                int i = 0;
                for (i = 0; i < currentEntry.getTypesMap().length; i++) {
                    if (currentEntry.getParams().get(key).equals(currentEntry.getTypesMap()[i]));
                        break;
                }
                spinnerType.setSelection(i);
                layoutHorizontal.addView(spinnerType, tv1Params);
            }
            layout.addView(layoutHorizontal);
        }

        builder.setView(layout);

        builder.setMessage("Edit entry: ")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        HashMap<String, String> params = new HashMap<String, String>();
                        for (String key : currentEntry.getParams().keySet()) {
                            if (!key.equals("type")) {
                                params.put(key, editTextStringHashMap.get(key).getText().toString());
                            } else {
                                params.put("type", String.valueOf(spinnerType.getSelectedItemPosition()));
                            }
                        }
                        params.put("_id", String.valueOf(currentEntry.getId()));
                        callback.okButtonHandler(params);
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


}
