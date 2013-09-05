package com.elencticsoft.collegeseeker;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    public static final String firstTab = "profile";
    public static final String secondTab = "savedList";
    public static final String thirdTab = "collegeList";
    
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);

        Tab tab = actionBar.newTab()
                .setText(R.string.title_college_match_list)
                .setTabListener(new TabListener<Profile>(this, firstTab, Profile.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                .setText(R.string.title_saved_college_list)
                .setTabListener(new TabListener<SavedCollegeList>(this, secondTab, SavedCollegeList.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                .setText(R.string.title_activity_college_list)
                .setTabListener(new TabListener<CollegeList>(this, thirdTab, CollegeList.class));
        actionBar.addTab(tab);

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

    public void updateDetailedView(int collegeId, String tag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment, DetailedView.newInstance(collegeId), tag);
        ft.addToBackStack(null);
        ft.commit();
    }

    public void updateSavedList() {
        DatabaseHandler handler = DatabaseHandler.getInstance(this);
        SavedCollegeList savedList = (SavedCollegeList) getSupportFragmentManager().findFragmentByTag(secondTab);
        ((CollegeAdapter)savedList.getListAdapter()).changeCursor(handler.getSavedColleges());
    }

    public void updateMatchList(String state, String sort, boolean small, boolean medium, boolean large,
            boolean city, boolean suburb, boolean town, boolean rural,
            boolean public_i, boolean private_fp, boolean private_nfp, boolean private_rel,
            String major, int ver, int mat, int wri, int act) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        CollegeMatchList matchList = CollegeMatchList.getInstance(state, sort, small, medium, large, city, suburb, 
                town, rural, public_i, private_fp, private_nfp, private_rel, major, ver, mat, wri, act);
        ft.replace(R.id.fragment, matchList, firstTab);
        ft.addToBackStack(null);
        ft.commit();
    }

    private void filterList(String query) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment);
        if (fragment instanceof CollegeMatchList) {
            CollegeMatchList matchList = (CollegeMatchList) fragment;
            matchList.filterList(query);
        }
        else if (fragment instanceof SavedCollegeList) {
            SavedCollegeList savedList = (SavedCollegeList) fragment;
            savedList.filterList(query);
        }
        else if (fragment instanceof CollegeList) {
            CollegeList collegeList = (CollegeList) fragment;
            collegeList.filterList(query);
        }
    }
    
    @Override
    public void onBackPressed() {
        AppBrain.getAds().maybeShowInterstitial(this);
        super.onBackPressed();
    }
}
