package ru.squel.babynote.shortAction.playing;

import android.content.ContentValues;
import android.view.MenuItem;

import java.util.HashMap;

import ru.squel.babynote.R;
import ru.squel.babynote.RecyclerItemClickListenerCallback;
import ru.squel.babynote.data.Play;
import ru.squel.babynote.database.providers.PlayProvider;
import ru.squel.babynote.dialogs.EditEntryDialog;
import ru.squel.babynote.dialogs.EditEntryDialogCallback;
import ru.squel.babynote.shortAction.ShortActionRecyclerViewFragment;
import ru.squel.babynote.utils.Injector;

/**
 * Created by sq on 02.11.2017.
 */

public class PlayRecyclerViewFragment extends ShortActionRecyclerViewFragment
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
                PlayProvider.URI,
                PlayProvider.Columns.ID + " == ?",
                new String[] {String.valueOf(id)});

        refreshRecyclerView();
    }

    @Override
    public void okButtonHandler(HashMap<String, String> val) {
        // нажатие кнопки ок в диалоге редактирования элемента списка
        Play plays = new Play();
        plays.setParams(val);

        ContentValues values = new ContentValues();
        values.put(PlayProvider.Columns.BEGIN, plays.getDateTimeBegin());
        values.put(PlayProvider.Columns.END, plays.getDateTimeEnd());
        values.put(PlayProvider.Columns.TYPE, plays.getType());

        int res = Injector.instance().getAppContext().getContentResolver().update(PlayProvider.URI, values, PlayProvider.Columns.ID + "=?", new String[]{String.valueOf(plays.getId())});
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
