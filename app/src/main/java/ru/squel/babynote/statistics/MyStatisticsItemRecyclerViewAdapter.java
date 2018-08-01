package ru.squel.babynote.statistics;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import ru.squel.babynote.R;
import ru.squel.babynote.data.Pampers;
import ru.squel.babynote.data.Play;
import ru.squel.babynote.data.Sleep;
import ru.squel.babynote.data.Swim;
import ru.squel.babynote.data.Walk;
import ru.squel.babynote.data.baseData.DataBaseEntry;
import ru.squel.babynote.data.baseData.InstantlyActionData;
import ru.squel.babynote.data.baseData.ShortActionData;
import ru.squel.babynote.statistics.StatisticsItemFragment.OnListFragmentInteractionListener;

/**
 * {@link RecyclerView.Adapter} that can display a and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyStatisticsItemRecyclerViewAdapter extends RecyclerView.Adapter<MyStatisticsItemRecyclerViewAdapter.ViewHolder> {

    private final List<DataBaseEntry> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Resources res;

    public MyStatisticsItemRecyclerViewAdapter(List<DataBaseEntry> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    public void setRes(Resources res) {
        this.res = res;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_statisticsitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        /// проверка класса отображаемой записи:
        boolean isShortActionData = ShortActionData.class.isAssignableFrom((holder.mItem).getClass());

        if (InstantlyActionData.class.isAssignableFrom((holder.mItem).getClass())) {
            HashMap<String, String> paramsItem = holder.mItem.getParams();
            String[] valuesDescriptions = holder.mItem.getTypesMap();
            int type = Integer.parseInt(paramsItem.get(ShortActionData.FieldNames.typeFieldName));

            if (isShortActionData) {
                holder.mTimeEndTextView.setText(paramsItem.get(ShortActionData.FieldNames.dateTimeEndFieldName));

                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                Date dateBegin = new Date();
                Date dateEnd = new Date();
                try {
                    dateBegin = format.parse(paramsItem.get(ShortActionData.FieldNames.dateTimeEndFieldName));
                    dateEnd = format.parse(paramsItem.get(ShortActionData.FieldNames.dateTimeBeginFieldName));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                holder.mDescriptionTextView.setText(getDescriptionString(valuesDescriptions[type],
                        (dateEnd.getTime() - dateBegin.getTime())/60000, -1));
            }
            else {
                if (valuesDescriptions[type].equals(Pampers.pampersSmallType))
                    holder.mDescriptionTextView.setText(res.getString(R.string.pampers_small_recycler_description));
                else if (valuesDescriptions[type].equals(Pampers.pampersLargeType))
                    holder.mDescriptionTextView.setText(res.getString(R.string.pampers_large_recycler_description));
                else if (valuesDescriptions[type].equals(Pampers.pampersSmallAndLargeType))
                    holder.mDescriptionTextView.setText(res.getString(R.string.pampers_small_and_large_recycler_description));
            }
        }

        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        holder.mTimeBeginTextView.setText(formatter.format(
                mValues.get(position).getDateTimeBegin()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        public final TextView mTimeBeginTextView;
        public final TextView mTimeEndTextView;
        public final TextView mDescriptionTextView;

        public DataBaseEntry mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTimeBeginTextView = (TextView) view.findViewById(R.id.daylistat_time_begin);
            mTimeEndTextView = (TextView) view.findViewById(R.id.dailystat_time_end);
            mDescriptionTextView = (TextView) view.findViewById(R.id.daylistat_descr);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }


    private String getDescriptionString(final String parameterName,
                                        final long timeLong,
                                        final int amount) {

        String s = "";
        switch (parameterName) {
            case (Play.inBad):

                break;
            case (Play.inAbdominal):

                break;
            case (Play.inArena):

                break;

            case (Sleep.lefSide):
                s = "На левом боку";
                break;

            case (Sleep.rightSide):
                s = "На правом боку";
                break;

            case (Sleep.deepSleep):
                s = "Глубокий сон";
                break;

            case (Swim.withSalt):
                break;

            case (Swim.withSoap):
                break;

            case (Walk.onBalcony):
                s = String.format(res.getString(R.string.walk_on_balcony_statistics_description), timeLong);
                break;

            case (Walk.onStreet):
                s = String.format(res.getString(R.string.walk_on_street_statistics_description), timeLong);
                break;

            default:break;
        }

        return s;
    }

}
