package com.elencticsoft.collegeseeker;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.actionbarsherlock.app.SherlockFragment;

public class Profile extends SherlockFragment {
    private EditText verView, matView, wriView, actView;
    private CheckBox college_small, college_medium, college_large,
    city, suburb, town, rural,
    public_i, private_fp, private_nfp, private_rel;
    Spinner stateSpinner, sortSpinner, majorSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.profile, container, false);
        Button b = (Button) view.findViewById(R.id.button_send);
        b.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler handler = DatabaseHandler.getInstance(getSherlockActivity());
                List<College> colleges = new ArrayList<College>();
                Cursor cursor = handler.getCollegesByState(stateSpinner.getSelectedItem().toString(),
                        sortSpinner.getSelectedItem().toString());
                int ver = parseInt(verView.getText().toString());
                int mat = parseInt(matView.getText().toString());
                int wri = parseInt(wriView.getText().toString());
                int act = parseInt(actView.getText().toString());
                while (cursor.moveToNext()) {
                    College college = CollegeHelper.convertToCollege(cursor);
                    college.setMajors(DatabaseHandler.getInstance(getSherlockActivity()).getMajors(college.getId()));
                    if (CollegeHelper.isGoodMatch(college_small.isChecked(), college_medium.isChecked(), college_large.isChecked(),
                            city.isChecked(), suburb.isChecked(), town.isChecked(), rural.isChecked(),
                            public_i.isChecked(), private_fp.isChecked(), private_nfp.isChecked(), private_rel.isChecked(),
                            majorSpinner.getSelectedItem().toString(), ver, mat, wri, act, college)) colleges.add(college);
                }
                cursor.close();
                ((MainActivity) getSherlockActivity()).updateMatchList(colleges);
            }
        });

        return view;
    }

    private static int parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        verView = (EditText) view.findViewById(R.id.sat_ver);
        matView = (EditText) view.findViewById(R.id.sat_mat);
        wriView = (EditText) view.findViewById(R.id.sat_wri);
        actView = (EditText) view.findViewById(R.id.act_input);
        college_small = (CheckBox) view.findViewById(R.id.checkbox_small);
        college_medium = (CheckBox) view.findViewById(R.id.checkbox_medium);
        college_large = (CheckBox) view.findViewById(R.id.checkbox_large);
        city = (CheckBox) view.findViewById(R.id.checkbox_city);
        suburb = (CheckBox) view.findViewById(R.id.checkbox_suburb);
        town = (CheckBox) view.findViewById(R.id.checkbox_town);
        rural = (CheckBox) view.findViewById(R.id.checkbox_rural);
        public_i = (CheckBox) view.findViewById(R.id.checkbox_public);
        private_fp = (CheckBox) view.findViewById(R.id.checkbox_private_fp);
        private_nfp = (CheckBox) view.findViewById(R.id.checkbox_private_nfp);
        private_rel = (CheckBox) view.findViewById(R.id.checkbox_private_rel);
        stateSpinner = (Spinner) view.findViewById(R.id.statesSpinner2);
        sortSpinner = (Spinner) view.findViewById(R.id.sortSpinner2);
        majorSpinner = (Spinner) view.findViewById(R.id.majorSpinner);

        //        verView.setFilters(new InputFilter[] { new InputFilter.LengthFilter(3) });
        //        matView.setFilters(new InputFilter[] { new InputFilter.LengthFilter(3) });
        //        wriView.setFilters(new InputFilter[] { new InputFilter.LengthFilter(3) });
        //        setEditTextListener(wriView);
    }
}
