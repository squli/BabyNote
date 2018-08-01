package ru.squel.babynote.shortAction;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.squel.babynote.R;

/**
 * Created by sq on 17.10.2017.
 * Базовый класс фрагмента со списком для отображения продолжительных действий
 */

public abstract class ShortActionRecyclerViewFragment extends Fragment {

    private int mColumnCount = 1;

    /// адаптер списка для отображения
    public ShortActionItemRecyclerViewAdapter adapter = null;

    /// колбэк для вызово
    public ShortActionCallback fragmentCallback = null;

    public ShortActionRecyclerViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sleep_recycler_view, container, false);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            adapter = new ShortActionItemRecyclerViewAdapter(fragmentCallback.getList());
            recyclerView.setAdapter(adapter);
            registerForContextMenu(recyclerView);
            refreshRecyclerView();
        }

        return view;
    }

    public void refreshRecyclerView() {
        adapter.setmValues(fragmentCallback.getList());
        adapter.notifyDataSetChanged();
    }

    public void setCallback(ShortActionPresenter presenter) {
        this.fragmentCallback = presenter;
    }

}
