package ru.squel.babynote.eating;

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
import ru.squel.babynote.data.Eat;

/**
 * {@link RecyclerView.Adapter} that can display a Eat and makes a call to the
 */
public class MyeatItemRecyclerViewAdapter extends RecyclerView.Adapter<MyeatItemRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Eat> mValues;

    public MyeatItemRecyclerViewAdapter(ArrayList<Eat> items) {
        mValues = items;
    }

    public void setmValues(ArrayList<Eat> items) {
        mValues = items;
    }

    @Override
    public MyeatItemRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_eatitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        DateFormat formatter = new SimpleDateFormat("hh:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(holder.mItem.getDateTimeBegin());
        String stringTimeBegin = formatter.format(calendar.getTime());
        calendar.setTimeInMillis(holder.mItem.getDateTimeEnd());
        String stringTimeEnd = formatter.format(calendar.getTime());

        holder.mIdView.setText(String.valueOf(position));
        holder.mDateView.setText(stringTimeBegin + " - " + stringTimeEnd);
        holder.mDescriptionView.setText(String.valueOf(holder.mItem.getDescription()));

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

        public Eat mItem;

        public ViewHolder(View view) {
            super(view);

            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mDateView = (TextView) view.findViewById(R.id.eating_date_time);
            mDescriptionView = (TextView) view.findViewById(R.id.eating_description);

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

    public Eat getItem(int position) {
        return mValues.get(position);
    }
}
