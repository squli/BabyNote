package ru.squel.babynote.layout;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import ru.squel.babynote.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShortActionHeaderFragment extends Fragment {

    private TextView header;
    private Spinner spinner;

    private String headerTitle;
    private ArrayAdapter<String> adapter;

    public ShortActionHeaderFragment() {
        // Required empty public constructor
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {

        this.headerTitle = headerTitle;
    }

    public void setSpinnerValues(Context context, final String[] values) {
        this.adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, values);
    }

    public int getSpinnerIndex() {
        if (spinner != null) {
            return spinner.getSelectedItemPosition();
        }
        else
            return -1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_short_action_header, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        header = (TextView) getView().findViewById(R.id.fragment_short_action_header_title);
        spinner = (Spinner) getView().findViewById(R.id.fragment_short_action_spinner);

        if (adapter != null) {
            spinner.setAdapter(adapter);
            spinner.setSelection(1);
            spinner.setVisibility(View.VISIBLE);
        }

        if (getHeaderTitle() != null) {
            header.setText(headerTitle);
        }
    }

}
