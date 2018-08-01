package ru.squel.babynote.eating;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

import ru.squel.babynote.R;
import ru.squel.babynote.RecyclerItemClickListenerCallback;
import ru.squel.babynote.data.Eat;
import ru.squel.babynote.database.providers.EatProvider;
import ru.squel.babynote.dialogs.EditEntryDialog;
import ru.squel.babynote.dialogs.EditEntryDialogCallback;
import ru.squel.babynote.utils.Injector;

/**
 * A fragment representing a list of Items.
 * <p>
 * Activities containing this fragment MUST implement the
 * interface.
 */
public class EatItemFragment extends Fragment implements RecyclerItemClickListenerCallback, EditEntryDialogCallback {

    private MyeatItemRecyclerViewAdapter adapter = null;

    private EatItemFragmentCallback eatItemFragmentCallback = null;

    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EatItemFragment() {
    }

    public void setEatItemFragmentCallback(EatItemFragmentCallback eatItemFragmentCallback) {
        this.eatItemFragmentCallback = eatItemFragmentCallback;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eat_recycler_view, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            adapter = new MyeatItemRecyclerViewAdapter(eatItemFragmentCallback.getEatList());
            recyclerView.setAdapter(adapter);

            registerForContextMenu(recyclerView);
        }
        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void editButtonCallback(final int position) {
        int id = adapter.getPosition();

        EditEntryDialog editEntryDialog = new EditEntryDialog();
        editEntryDialog.setCurrentEntry(adapter.getItem(id));
        editEntryDialog.setCallback(this);
        editEntryDialog.show(getFragmentManager(), "Edit entry: ");

    }

    @Override
    public void deleteButtonCallback(final int position) {

        long id = adapter.getPosition();
        /*
        Injector.instance().getAppContext().getContentResolver().delete(
                EatProvider.URI,
                EatProvider.Columns.ID);
                */
    }

    public void refreshRecyclerView() {
        adapter.setmValues(eatItemFragmentCallback.getEatList());
        adapter.notifyDataSetChanged();
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

    @Override
    public void okButtonHandler(HashMap<String, String> val) {

        Eat eat = new Eat();
        eat.setParams(val);

        ContentValues values = new ContentValues();
        values.put(EatProvider.Columns.BEGIN, eat.getDateTimeBegin());
        values.put(EatProvider.Columns.END, eat.getDateTimeEnd());
        values.put(EatProvider.Columns.TYPE, eat.getType());
        values.put(EatProvider.Columns.AMOUNT, eat.getAmount());

        int res = Injector.instance().getAppContext().getContentResolver().update(EatProvider.URI, values, EatProvider.Columns.ID + "=?", new String[]{String.valueOf(eat.getId())});

    }
}
