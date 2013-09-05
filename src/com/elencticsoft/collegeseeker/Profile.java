package com.elencticsoft.collegeseeker;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.profile, container, false);
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
        
        final EditText verView, matView, wriView, actView;
        final CheckBox college_small, college_medium, college_large,
        city, suburb, town, rural,
        public_i, private_fp, private_nfp, private_rel;
        final Spinner stateSpinner, sortSpinner, majorSpinner;

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
        
        Button b = (Button) view.findViewById(R.id.button_send);
        b.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int ver = parseInt(verView.getText().toString());
                int mat = parseInt(matView.getText().toString());
                int wri = parseInt(wriView.getText().toString());
                int act = parseInt(actView.getText().toString());
                ((MainActivity) getSherlockActivity()).updateMatchList(
                        stateSpinner.getSelectedItem().toString(), sortSpinner.getSelectedItem().toString(),
                        college_small.isChecked(), college_medium.isChecked(), college_large.isChecked(),
                        city.isChecked(), suburb.isChecked(), town.isChecked(), rural.isChecked(),
                        public_i.isChecked(), private_fp.isChecked(), private_nfp.isChecked(), private_rel.isChecked(),
                        majorSpinner.getSelectedItem().toString(), ver, mat, wri, act);
            }
        });

        //        verView.setFilters(new InputFilter[] { new InputFilter.LengthFilter(3) });
        //        matView.setFilters(new InputFilter[] { new InputFilter.LengthFilter(3) });
        //        wriView.setFilters(new InputFilter[] { new InputFilter.LengthFilter(3) });
        //        setEditTextListener(wriView);
    }
}
