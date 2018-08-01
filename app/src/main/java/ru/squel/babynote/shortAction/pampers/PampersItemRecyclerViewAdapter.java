package ru.squel.babynote.shortAction.pampers;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import ru.squel.babynote.R;
import ru.squel.babynote.data.baseData.DataBaseEntry;
import ru.squel.babynote.data.Pampers;

/**
 * Created by sq on 17.10.2017.
 */

public class PampersItemRecyclerViewAdapter extends RecyclerView.Adapter<PampersItemRecyclerViewAdapter.ViewHolder> {


    private ArrayList<Pampers> mValues;

    public PampersItemRecyclerViewAdapter(ArrayList<DataBaseEntry> items) {
        mValues = new ArrayList<Pampers>();
        for (DataBaseEntry d : items) {
            if (d instanceof Pampers) {
                mValues.add((Pampers) d);
            }
            else {
                mValues.add(new Pampers(d));
            }
        }
    }

    public void setmValues(ArrayList<DataBaseEntry> items) {
        mValues = new ArrayList<Pampers>();
        for (DataBaseEntry d : items) {
            if (d instanceof Pampers) {
                mValues.add((Pampers) d);
            }
            else {
                mValues.add(new Pampers(d));
            }
        }
    }

    @Override
    public PampersItemRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_pampersitem, parent, false);
        return new PampersItemRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PampersItemRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        DateFormat formatter = new SimpleDateFormat("hh:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(holder.mItem.getDateTimeBegin());
        String stringTime = formatter.format(calendar.getTime());

        holder.mIdView.setText(String.valueOf(position));
        holder.mDateView.setText(stringTime);

        if (holder.mItem.getType() == Pampers.pampersTypes.get(Pampers.pampersSmallType)) {
            holder.mDescriptionView.setText(R.string.pampers_small_recycler_description);
        } else if (holder.mItem.getType() == Pampers.pampersTypes.get(Pampers.pampersLargeType)) {
            holder.mDescriptionView.setText(R.string.pampers_large_recycler_description);
        } else if (holder.mItem.getType() == Pampers.pampersTypes.get(Pampers.pampersSmallAndLargeType)) {
            holder.mDescriptionView.setText(R.string.pampers_small_and_large_recycler_description);
        }

        /*holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    //mListener.onListFragmentInteraction(holder.mItem);
                }
                return true;
            }
        });*/

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(holder.getPosition());
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        public final View mView;
        public final TextView mIdView;
        public final TextView mDateView;
        public final TextView mDescriptionView;

        public Pampers mItem;

        public ViewHolder(View view) {
            super(view);

            mView = view;
            mIdView = (TextView) view.findViewById(R.id.pampers_item_id);
            mDateView = (TextView) view.findViewById(R.id.pampers_item_time);
            mDescriptionView = (TextView) view.findViewById(R.id.pampers_item_description);

            view.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            //menuInfo is null
            menu.add(Menu.NONE, R.id.edit_data_recycler_view,
                    Menu.NONE, "Edit");
            menu.add(Menu.NONE, R.id.delete_data_recycler_view,
                    Menu.NONE, "Delete");
        }
    }

    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Pampers getItem(int position) {
        return mValues.get(position);
    }
}
