package com.elencticsoft.collegeseeker;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FilterQueryProvider;

import com.actionbarsherlock.app.SherlockListFragment;

public class SavedCollegeList extends SherlockListFragment {
    private DbTask dbTask;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.saved_college_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbTask = new DbTask();
        dbTask.execute();
    }
    
    @Override
    public void onPause() {
        super.onPause();
        if (dbTask != null) dbTask.cancel(true);
    }
    
    private CollegeAdapter createAdapter(Cursor cursor) {        
        CollegeAdapter adapter = new CollegeAdapter(getSherlockActivity(), cursor);
        adapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence constraint) {
                final DatabaseHandler handler = DatabaseHandler.getInstance(getSherlockActivity());
                return handler.getSavedColleges(constraint.toString());
            }      
        });
        return adapter;
    }

    public void filterList(String query) {
        CollegeAdapter adapter = (CollegeAdapter) getListAdapter();
        adapter.getFilter().filter(query);
    }
    
    private class DbTask extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            ((MainActivity)getSherlockActivity()).showProgress();
        }
        
        @Override
        protected Cursor doInBackground(Void... params) {
            final DatabaseHandler handler = DatabaseHandler.getInstance(getSherlockActivity());
            Cursor cursor = handler.getSavedColleges();
            return cursor;
        }
        @Override
        protected void onPostExecute(Cursor cursor) {
            setListAdapter(createAdapter(cursor));
            getListView().setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                        int position, long id) {
                    Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                    final College college = CollegeHelper.convertToCollege(cursor);
                    SavedDialogBox.newInstance(college.getId()).show(getFragmentManager(), "savedDialog");
                }
            });
            ((MainActivity)getSherlockActivity()).dismissProgress();
        }
        
        @Override
        protected void onCancelled(Cursor result) {
            if ((result != null) && (!result.isClosed())) result.close();
            ((MainActivity)getSherlockActivity()).dismissProgress();
        }
    }
}
