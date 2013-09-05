package com.elencticsoft.collegeseeker;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockListFragment;

public class CollegeMatchList extends SherlockListFragment {
    private DbTask dbTask;
    
    public static CollegeMatchList getInstance(String state, String sort, boolean small, boolean medium, 
            boolean large, boolean city, boolean suburb, boolean town, boolean rural,
            boolean public_i, boolean private_fp, boolean private_nfp, boolean private_rel,
            String major, int ver, int mat, int wri, int act) {
        CollegeMatchList matchList = new CollegeMatchList();
        Bundle args = new Bundle();
        args.putString("state", state);
        args.putString("sort", sort);
        args.putBoolean("small", small);
        args.putBoolean("medium", medium);
        args.putBoolean("large", large);
        args.putBoolean("city", city);
        args.putBoolean("suburb", suburb);
        args.putBoolean("town", town);
        args.putBoolean("rural", rural);
        args.putBoolean("public", public_i);
        args.putBoolean("private_fp", private_fp);
        args.putBoolean("private_nfp", private_nfp);
        args.putBoolean("private_rel", private_rel);
        args.putString("major", major);
        args.putInt("ver", ver);
        args.putInt("mat", mat);
        args.putInt("wri", wri);
        args.putInt("act", act);
        matchList.setArguments(args);
        return matchList;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.match_college_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Bundle args = getArguments();
        dbTask = new DbTask();
        dbTask.execute(
                args.getBoolean("small"),
                args.getBoolean("medium"),
                args.getBoolean("large"),
                args.getBoolean("city"),
                args.getBoolean("suburb"),
                args.getBoolean("town"),
                args.getBoolean("rural"),
                args.getBoolean("public"),
                args.getBoolean("private_fp"),
                args.getBoolean("private_nfp"),
                args.getBoolean("private_rel"),
                args.getString("major"),
                args.getInt("ver"),
                args.getInt("mat"),
                args.getInt("wri"),
                args.getInt("act"),
                args.getString("state"), 
                args.getString("sort"));
        getListView().setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                College college = (College) parent.getItemAtPosition(position);
                CollegeDialogBox.newInstance(college.getId(), MainActivity.firstTab).show(getFragmentManager(), "dialog");
            }
        });
    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (dbTask != null) dbTask.cancel(true);
    }
    
    public void filterList(String query) {
        CollegeMatchAdapter adapter = (CollegeMatchAdapter) getListAdapter();
        if (adapter != null) adapter.getFilter().filter(query);
    }
    
    private class DbTask extends AsyncTask<Object, Void, List<College>> {
        @Override
        protected List<College> doInBackground(Object... params) {
            DatabaseHandler handler = DatabaseHandler.getInstance(getSherlockActivity());
            Cursor cursor = handler.getCollegesByState((String)params[16], (String)params[17]);
            List<College> colleges = new ArrayList<College>();
            while (cursor.moveToNext()) {
                College college = CollegeHelper.convertToCollege(cursor);
                college.setMajors(DatabaseHandler.getInstance(getSherlockActivity()).getMajors(college.getId()));
                if (CollegeHelper.isGoodMatch((Boolean)params[0], (Boolean)params[1], (Boolean)params[2], (Boolean)params[3], 
                        (Boolean)params[4], (Boolean)params[5], (Boolean)params[6], (Boolean)params[7], (Boolean)params[8], (Boolean)params[9],
                        (Boolean)params[10], (String)params[11], (Integer)params[12], (Integer)params[13], (Integer)params[14], (Integer)params[15], 
                        college)) 
                    colleges.add(college);
            }
            cursor.close();
            return colleges;
        }
        @Override
        protected void onPostExecute(List<College> colleges) {
            CollegeMatchAdapter adapter = new CollegeMatchAdapter(getSherlockActivity(), 
                    R.layout.college_list_item, colleges);
            setListAdapter(adapter);
        }
    }
}
