package ru.squel.babynote.shortAction.walking;

import android.content.ContentValues;
import android.view.MenuItem;

import java.util.HashMap;

import ru.squel.babynote.R;
import ru.squel.babynote.RecyclerItemClickListenerCallback;
import ru.squel.babynote.data.Walk;
import ru.squel.babynote.database.providers.WalkProvider;
import ru.squel.babynote.dialogs.EditEntryDialog;
import ru.squel.babynote.dialogs.EditEntryDialogCallback;
import ru.squel.babynote.shortAction.ShortActionRecyclerViewFragment;
import ru.squel.babynote.utils.Injector;

/**
 * Created by sq on 01.11.2017.
 */

public class WalkRecyclerViewFragment extends ShortActionRecyclerViewFragment
        implements RecyclerItemClickListenerCallback,
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
                WalkProvider.URI,
                WalkProvider.Columns.ID + " == ?",
                new String[] {String.valueOf(id)});

        refreshRecyclerView();
    }

    @Override
    public void okButtonHandler(HashMap<String, String> val) {
        // нажатие кнопки ок в диалоге редактирования элемента списка
        Walk walks = new Walk();
        walks.setParams(val);

        ContentValues values = new ContentValues();
        values.put(WalkProvider.Columns.BEGIN, walks.getDateTimeBegin());
        values.put(WalkProvider.Columns.END, walks.getDateTimeEnd());
        values.put(WalkProvider.Columns.TYPE, walks.getType());

        int res = Injector.instance().getAppContext().getContentResolver().update(WalkProvider.URI, values, WalkProvider.Columns.ID + "=?", new String[]{String.valueOf(walks.getId())});
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
                editButtonCallback(position);
                break;
            case R.id.delete_data_recycler_view:
                deleteButtonCallback(position);
                break;
        }
        return super.onContextItemSelected(item);
    }
}
