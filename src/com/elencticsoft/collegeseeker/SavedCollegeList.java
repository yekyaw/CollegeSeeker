package com.elencticsoft.collegeseeker;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FilterQueryProvider;

import com.actionbarsherlock.app.SherlockListFragment;

public class SavedCollegeList extends SherlockListFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.saved_college_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getListView().setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                final College college = CollegeHelper.convertToCollege(cursor);
                SavedDialogBox.newInstance(college.getId()).show(getFragmentManager(), "savedDialog");
            }
        });
        setListAdapter(createAdapter());
    }
    
    private CollegeAdapter createAdapter() {
        final DatabaseHandler handler = DatabaseHandler.getInstance(getSherlockActivity());
        Cursor cursor = handler.getSavedColleges();
        
        CollegeAdapter adapter = new CollegeAdapter(getSherlockActivity(), cursor);
        adapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence constraint) {
                return handler.getSavedColleges(constraint.toString());
            }      
        });
        return adapter;
    }

    public void filterList(String query) {
        CollegeAdapter adapter = (CollegeAdapter) getListAdapter();
        adapter.getFilter().filter(query);
    }
}
