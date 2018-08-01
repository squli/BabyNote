package ru.squel.babynote.shortAction.sleeping;


import android.content.ContentValues;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import java.util.HashMap;

import ru.squel.babynote.R;
import ru.squel.babynote.RecyclerItemClickListenerCallback;
import ru.squel.babynote.data.Sleep;
import ru.squel.babynote.database.providers.SleepProvider;
import ru.squel.babynote.dialogs.EditEntryDialog;
import ru.squel.babynote.dialogs.EditEntryDialogCallback;
import ru.squel.babynote.shortAction.ShortActionRecyclerViewFragment;
import ru.squel.babynote.utils.Injector;

/**
 * A simple {@link Fragment} subclass.
 * Фрагмент отображения списка сна
 */
public class SleepRecyclerViewFragment extends ShortActionRecyclerViewFragment
        implements  RecyclerItemClickListenerCallback,
                    EditEntryDialogCallback {

    @Override
    public void editButtonCallback(int position) {
        int id = adapter.getPosition();
        EditEntryDialog editEntryDialog = new EditEntryDialog();
        editEntryDialog.setCurrentEntry(adapter.getItem(id));
        editEntryDialog.setCallback(this);
        editEntryDialog.show(getActivity().getFragmentManager(), "Edit entry: ");
    }

    @Override
    public void deleteButtonCallback(int position) {
        int id = adapter.getPosition();

        Injector.instance().getAppContext().getContentResolver().delete(
                SleepProvider.URI,
                SleepProvider.Columns.ID + " == ?",
                new String[] {String.valueOf(id)});

        refreshRecyclerView();
    }

    @Override
    public void okButtonHandler(HashMap<String, String> val) {
        // нажатие кнопки ок в диалоге редактирования элемента списка
        Sleep sleeps = new Sleep();
        sleeps.setParams(val);

        ContentValues values = new ContentValues();
        values.put(SleepProvider.Columns.BEGIN, sleeps.getDateTimeBegin());
        values.put(SleepProvider.Columns.END, sleeps.getDateTimeEnd());
        values.put(SleepProvider.Columns.TYPE, sleeps.getType());

        int res = Injector.instance().getAppContext().getContentResolver().update(SleepProvider.URI, values, SleepProvider.Columns.ID + "=?", new String[]{String.valueOf(sleeps.getId())});
        refreshRecyclerView();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = -1;
        try {
            position = adapter.getPosition();
        } catch (Exception e) {
            return super.onContextItemSelected(item);
        }
        switch (item.getItemId()) {
            case R.id.edit_data_recycler_view:
                // do your stuff
                editButtonCallback(position);
                break;
            case R.id.delete_data_recycler_view:
                // do your stuff
                deleteButtonCallback(position);
                break;
        }
        return super.onContextItemSelected(item);
    }
}
