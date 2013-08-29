package com.elencticsoft.collegeseeker;

import java.util.List;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.appbrain.AppBrain;

public class MainActivity extends SherlockFragmentActivity implements SearchView.OnQueryTextListener {
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);

        if (savedInstanceState == null) {
            Tab tab = actionBar.newTab()
                    .setText(R.string.title_college_match_list)
                    .setTabListener(new TabListener<Profile>(this, "profile", Profile.class));
            actionBar.addTab(tab);

            tab = actionBar.newTab()
                    .setText(R.string.title_saved_college_list)
                    .setTabListener(new TabListener<SavedCollegeList>(this, "savedList", SavedCollegeList.class));
            actionBar.addTab(tab);

            tab = actionBar.newTab()
                    .setText(R.string.title_activity_college_list)
                    .setTabListener(new TabListener<CollegeList>(this, "collegeList", CollegeList.class));
            actionBar.addTab(tab);
        }

        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DatabaseHandler handler = DatabaseHandler.getInstance(this);
        if (handler != null) handler.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getSupportMenuInflater().inflate(R.menu.main, menu);
        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_bar_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        searchView.setOnQueryTextListener(this);
        searchView.setFocusable(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.action_about:
            new AboutDialog().show(getSupportFragmentManager(), "aboutDialog");
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        filterList(newText);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        filterList(query);
        return true;
    }

    public void updateDetailedView(int collegeId) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        CollegeList collegeList = (CollegeList) fm.findFragmentByTag("collegeList");
        CollegeMatchList matchList = (CollegeMatchList) fm.findFragmentByTag("matchList");
        SavedCollegeList savedList = (SavedCollegeList) fm.findFragmentByTag("savedList");
        if ((collegeList != null) && (collegeList.isVisible())) ft.detach(collegeList);
        else if ((matchList != null) && (matchList.isVisible())) ft.detach(matchList);
        else if ((savedList != null) && (savedList.isVisible())) ft.detach(savedList);
        ft.add(R.id.fragment, DetailedView.newInstance(collegeId), "detailedView");
        ft.addToBackStack(null);
        ft.commit();
    }

    public void updateSavedList() {
        DatabaseHandler handler = DatabaseHandler.getInstance(this);
        SavedCollegeList savedList = (SavedCollegeList) getSupportFragmentManager().findFragmentByTag("savedList");
        ((CollegeAdapter)savedList.getListAdapter()).changeCursor(handler.getSavedColleges());
    }

    public void updateMatchList(List<College> colleges) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Profile profile = (Profile) getSupportFragmentManager().findFragmentByTag("profile");
        if ((profile != null) && (profile.isVisible())) ft.detach(profile);
        CollegeMatchList matchList = new CollegeMatchList();
        CollegeMatchAdapter adapter = new CollegeMatchAdapter(this, R.layout.college_list_item, colleges);
        matchList.setListAdapter(adapter);
        ft.add(R.id.fragment, matchList, "matchList");
        ft.addToBackStack(null);
        ft.commit();
    }

    private void filterList(String query) {
        FragmentManager fm = getSupportFragmentManager();
        CollegeList collegeList = (CollegeList) fm.findFragmentByTag("collegeList");
        CollegeMatchList matchList = (CollegeMatchList) fm.findFragmentByTag("matchList");
        SavedCollegeList savedList = (SavedCollegeList) fm.findFragmentByTag("savedList");
        if ((collegeList != null) && (collegeList.isVisible())) collegeList.filterList(query);
        else if ((matchList != null) && (matchList.isVisible())) matchList.filterList(query);
        else if ((savedList != null) && (savedList.isVisible())) savedList.filterList(query);
    }
    
    @Override
    public void onBackPressed() {
        AppBrain.getAds().maybeShowInterstitial(this);
        super.onBackPressed();
    }
}
