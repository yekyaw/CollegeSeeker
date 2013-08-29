package com.elencticsoft.collegeseeker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockListFragment;

public class CollegeMatchList extends SherlockListFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.match_college_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getListView().setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                College college = (College) parent.getItemAtPosition(position);
                CollegeDialogBox.newInstance(college.getId()).show(getFragmentManager(), "dialog");
            }
        });
    }
    
    public void filterList(String query) {
        CollegeMatchAdapter adapter = (CollegeMatchAdapter) getListAdapter();
        if (adapter != null) adapter.getFilter().filter(query);
    }
}
