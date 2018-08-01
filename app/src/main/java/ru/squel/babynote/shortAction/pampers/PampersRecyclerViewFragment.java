package ru.squel.babynote.shortAction.pampers;

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
import ru.squel.babynote.data.Pampers;
import ru.squel.babynote.database.providers.PampersProvider;
import ru.squel.babynote.dialogs.EditEntryDialog;
import ru.squel.babynote.dialogs.EditEntryDialogCallback;
import ru.squel.babynote.shortAction.ShortActionRecyclerViewFragment;
import ru.squel.babynote.utils.Injector;

/**
 * Created by sq on 17.10.2017.
 */

public class PampersRecyclerViewFragment extends ShortActionRecyclerViewFragment
        implements  RecyclerItemClickListenerCallback,
        EditEntryDialogCallback {

    private PampersItemRecyclerViewAdapter adapter = null;

    private int mColumnCount = 1;

    public PampersRecyclerViewFragment() {
        // Required empty public constructor
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

            adapter = new PampersItemRecyclerViewAdapter(fragmentCallback.getList());
            recyclerView.setAdapter(adapter);

            registerForContextMenu(recyclerView);

            refreshRecyclerView();
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
        editEntryDialog.show(getActivity().getFragmentManager(), "Edit entry: ");

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
        adapter.setmValues(fragmentCallback.getList());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void okButtonHandler(HashMap<String, String> val) {
        Pampers pampers = new Pampers();
        pampers.setParams(val);

        ContentValues values = new ContentValues();
        values.put(PampersProvider.Columns.TIME, pampers.getDateTimeBegin());
        values.put(PampersProvider.Columns.TYPE, pampers.getType());

        int res = Injector.instance().getAppContext().getContentResolver().update(PampersProvider.URI, values, PampersProvider.Columns.ID + "=?", new String[]{String.valueOf(pampers.getId())});
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
