package ru.squel.babynote.shortAction;

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
import ru.squel.babynote.data.Play;
import ru.squel.babynote.data.Sleep;
import ru.squel.babynote.data.Swim;
import ru.squel.babynote.data.Walk;
import ru.squel.babynote.data.baseData.DataBaseEntry;
import ru.squel.babynote.data.baseData.ShortActionData;

/**
 * Created by sq on 21.10.2017.
 * Адаптер для отображения в списках всех потомков ShortActionData
 */

public class ShortActionItemRecyclerViewAdapter extends RecyclerView.Adapter<ShortActionItemRecyclerViewAdapter.ViewHolder> {

    private ArrayList<ShortActionData> mValues;

    public ShortActionItemRecyclerViewAdapter(ArrayList<DataBaseEntry> items) {
        mValues = new ArrayList<ShortActionData>();
        if (items != null) {
            for (DataBaseEntry d : items) {
                if (d instanceof Sleep) {
                    mValues.add((Sleep) d);
                } else if (d instanceof Swim) {
                    mValues.add((Swim) d);
                } else if (d instanceof Play) {
                    mValues.add((Play) d);
                } else if (d instanceof Walk) {
                    mValues.add((Walk) d);
                } else {

                }
            }
        }
    }

    public void setmValues(ArrayList<DataBaseEntry> items) {
        mValues = new ArrayList<ShortActionData>();
        if (items != null) {
            for (DataBaseEntry d : items) {
                if (d instanceof Sleep) {
                    mValues.add((Sleep) d);
                } else if (d instanceof Swim) {
                    mValues.add((Swim) d);
                } else if (d instanceof Play) {
                    mValues.add((Play) d);
                } else if (d instanceof Walk) {
                    mValues.add((Walk) d);
                } else {

                }
            }
        }
    }

    @Override
    public ShortActionItemRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_sleepitem, parent, false);
        return new ShortActionItemRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ShortActionItemRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        DateFormat formatter = new SimpleDateFormat("hh:mm:ss");
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(holder.mItem.getDateTimeBegin());
        String stringTimeBegin = formatter.format(calendar.getTime());

        calendar.setTimeInMillis(holder.mItem.getDateTimeEnd());
        String stringTimeEnd = formatter.format(calendar.getTime());

        holder.mIdView.setText(String.valueOf(position));
        holder.mDateViewBegin.setText(stringTimeBegin);
        holder.mDateViewEnd.setText(stringTimeEnd);

        if (holder.mItem instanceof Sleep) {
            if (holder.mItem.getType() == Sleep.sleepTypes.get(Sleep.lefSide)) {
                holder.mDescriptionView.setText(R.string.sleep_left_side_recycler_description);
            } else if (holder.mItem.getType() == Sleep.sleepTypes.get(Sleep.rightSide)) {
                holder.mDescriptionView.setText(R.string.sleep_right_side_recycler_description);
            } else if (holder.mItem.getType() == Sleep.sleepTypes.get(Sleep.deepSleep)) {
                holder.mDescriptionView.setText(R.string.sleep_deep_sleep_recycler_description);
            } else {

            }
        } else if (holder.mItem instanceof Walk) {
            if (holder.mItem.getType() == Walk.walkTypes.get(Walk.onBalcony)) {
                holder.mDescriptionView.setText(R.string.walk_on_balcony_recycler_description);
            } else if (holder.mItem.getType() == Walk.walkTypes.get(Walk.onStreet)) {
                holder.mDescriptionView.setText(R.string.walk_on_street_recycler_description);
            }  else {

            }
        } else if (holder.mItem instanceof Play) {
            if (holder.mItem.getType() == Play.playTypes.get(Play.inAbdominal)) {
                holder.mDescriptionView.setText(R.string.play_inAbdominal_recycler_description);
            } else if (holder.mItem.getType() == Play.playTypes.get(Play.inArena)) {
                holder.mDescriptionView.setText(R.string.play_inArena_recycler_description);
            } else if (holder.mItem.getType() == Play.playTypes.get(Play.inBad)) {
                holder.mDescriptionView.setText(R.string.play_inBad_recycler_description);
            } else {

            }
        } else if (holder.mItem instanceof Swim) {
            if (holder.mItem.getType() == Swim.swimTypes.get(Swim.withSalt)) {
                holder.mDescriptionView.setText(R.string.swim_with_salt_recycler_description);
            } else if (holder.mItem.getType() == Swim.swimTypes.get(Swim.withSoap)) {
                holder.mDescriptionView.setText(R.string.swim_with_soap_recycler_description);
            }  else {

            }
        } else {

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
        public final TextView mDateViewBegin;
        public final TextView mDateViewEnd;
        public final TextView mDescriptionView;

        public ShortActionData mItem;

        public ViewHolder(View view) {
            super(view);

            mView = view;
            mIdView = (TextView) view.findViewById(R.id.sleep_item_id);
            mDateViewBegin = (TextView) view.findViewById(R.id.sleep_item_time_begin);
            mDateViewEnd = (TextView) view.findViewById(R.id.sleep_item_time_end);
            mDescriptionView = (TextView) view.findViewById(R.id.sleep_item_description);

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

    public ShortActionData getItem(int position) {
        return mValues.get(position);
    }
}
