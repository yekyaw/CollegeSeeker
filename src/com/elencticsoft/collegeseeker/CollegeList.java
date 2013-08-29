package com.elencticsoft.collegeseeker;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.Spinner;

import com.actionbarsherlock.app.SherlockFragment;

public class CollegeList extends SherlockFragment {
    private ListView listView;
    private Spinner stateSpinner, sortSpinner;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.college_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        listView = (ListView) view.findViewById(R.id.college_list);
        stateSpinner = (Spinner) view.findViewById(R.id.statesSpinner);
        sortSpinner = (Spinner) view.findViewById(R.id.sortSpinner);
        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                final College college = CollegeHelper.convertToCollege(cursor);
                CollegeDialogBox.newInstance(college.getId()).show(getFragmentManager(), "dialog");
            }
        });
        listView.setAdapter(createAdapter());
        
        stateSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                    int position, long id) {
                updateAdapter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) { }
        });
        
        sortSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                    int position, long id) {
                updateAdapter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) { }
        });
    }
    
    private void updateAdapter() {
        String state = stateSpinner.getSelectedItem().toString();
        String sort = sortSpinner.getSelectedItem().toString();
        DatabaseHandler handler = DatabaseHandler.getInstance(getSherlockActivity());
        Cursor cursor = handler.getCollegesByState(state, sort);
        CollegeAdapter adapter = (CollegeAdapter)listView.getAdapter();
        adapter.changeCursor(cursor);
    }
    
    private CollegeAdapter createAdapter() {
        final DatabaseHandler handler = DatabaseHandler.getInstance(getSherlockActivity());
        Cursor cursor = handler.getCollegesByState(stateSpinner.getSelectedItem().toString(),
                sortSpinner.getSelectedItem().toString());
        
        CollegeAdapter adapter = new CollegeAdapter(getSherlockActivity(), cursor);
        adapter.setFilterQueryProvider(new FilterQueryProvider() {     
            @Override
            public Cursor runQuery(CharSequence constraint) {
                return handler.getMatchColleges(constraint.toString(), 
                        stateSpinner.getSelectedItem().toString(), sortSpinner.getSelectedItem().toString());
            }
        });
        return adapter;
    }

    public void filterList(String query) {
        CollegeAdapter adapter = (CollegeAdapter) listView.getAdapter();
        adapter.getFilter().filter(query);
    }
}
