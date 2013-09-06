package com.elencticsoft.collegeseeker;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import au.com.bytecode.opencsv.CSVReader;

import com.elencticsoft.collegeseeker.CollegeContract.CollegeEntry;
import com.elencticsoft.collegeseeker.CollegeContract.MajorEntry;
import com.elencticsoft.collegeseeker.CollegeContract.MajorNameEntry;
import com.elencticsoft.collegeseeker.CollegeContract.SavedCollegeEntry;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
 
public class DatabaseHandler extends SQLiteAssetHelper {
    private static DatabaseHandler instance;
 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 2;
 
    // Database Name
    private static final String DATABASE_NAME = "collegesManager";
 
    private static final String TABLE_COLLEGES = "colleges";
    private static final String TABLE_SAVED_COLLEGES = "saved_colleges";
    private static final String TABLE_MAJORS = "majors";
    private static final String TABLE_MAJOR_NAMES = "major_names";
    private static final String TEXT_TYPE = " TEXT";
    private static final String TEXT_TYPE_COMMA = " TEXT,";
    private static final String INT_TYPE = " INTEGER";
    private static final String INT_TYPE_COMMA = " INTEGER,";
    private static final String DEC_TYPE = " DECIMAL";
    private static final String DEC_TYPE_COMMA = " DECIMAL,";
    private static final String COMMA_SEP = ",";
    
    private SQLiteDatabase db;
    private Context context;
    
    public static synchronized DatabaseHandler getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHandler(context);
        }
        try {
            instance.open();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return instance;
    }
 
    private DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
    
    public void open() {
        if (db == null) db = getWritableDatabase();
    }
    
    @Override
    public void close() {
        super.close();
        instance = null;
    }
 
    // Creating Tables
    public void createDatabase(SQLiteDatabase db) {
        createCollegeTable(db);
        createSavedTable(db);
        createMajorTable(db);
//        populateCollegeTable(db);
        populateMajorTable(db);
    }

    private void createCollegeTable(SQLiteDatabase db) {
        String CREATE_COLLEGES_TABLE = "CREATE TABLE " + TABLE_COLLEGES + "("
                + CollegeEntry.ID + INT_TYPE + " PRIMARY KEY" + COMMA_SEP + CollegeEntry.ACT25 + 
                INT_TYPE_COMMA + CollegeEntry.ACT75 + INT_TYPE_COMMA + CollegeEntry.ADMITS + INT_TYPE_COMMA +
                CollegeEntry.APPLICANTS + INT_TYPE_COMMA + CollegeEntry.APPLICATION_FEE + INT_TYPE_COMMA +
                CollegeEntry.CLASSIFICATION + INT_TYPE_COMMA + CollegeEntry.FIRST_YR_ENROLLMENT + INT_TYPE_COMMA +
                CollegeEntry.FOUR_YR_GRAD + INT_TYPE_COMMA + CollegeEntry.GRAD_COHORT + INT_TYPE_COMMA +
                CollegeEntry.ICON + TEXT_TYPE_COMMA + CollegeEntry.LOCALE + INT_TYPE_COMMA + CollegeEntry.MAT25 +
                INT_TYPE_COMMA + CollegeEntry.MAT75 + INT_TYPE_COMMA + CollegeEntry.NAME + TEXT_TYPE_COMMA +
                CollegeEntry.RETENTION_RATE + INT_TYPE_COMMA + CollegeEntry.FACULTY_RATIO + INT_TYPE_COMMA + 
                CollegeEntry.ROOM_AND_BOARD + INT_TYPE_COMMA + CollegeEntry.SIX_YR_GRAD + INT_TYPE_COMMA + 
                CollegeEntry.STATE + TEXT_TYPE_COMMA + CollegeEntry.STATE_ABBR + TEXT_TYPE_COMMA +
                CollegeEntry.TYPE + INT_TYPE_COMMA + CollegeEntry.VER25 + 
                INT_TYPE_COMMA + CollegeEntry.VER75 + INT_TYPE_COMMA + CollegeEntry.WRI25 + INT_TYPE_COMMA + 
                CollegeEntry.WRI75 + INT_TYPE_COMMA + CollegeEntry.MALE_RATIO + INT_TYPE_COMMA + CollegeEntry.FEMALE_RATIO +
                INT_TYPE_COMMA + CollegeEntry.TUITION_IN_STATE + INT_TYPE_COMMA + CollegeEntry.TUITION_OUT_STATE +
                INT_TYPE_COMMA + CollegeEntry.TOTAL_IN_STATE + INT_TYPE_COMMA + CollegeEntry.TOTAL_OUT_STATE + INT_TYPE_COMMA +
                CollegeEntry.TOTAL_ENROLLED + INT_TYPE_COMMA + CollegeEntry.PERCENT_FIN_AID + INT_TYPE_COMMA +
                CollegeEntry.PERCENT_GRANT_AID + INT_TYPE_COMMA + CollegeEntry.NET_COST + INT_TYPE_COMMA +
                CollegeEntry.NET_COST_1 + INT_TYPE_COMMA + CollegeEntry.NET_COST_2 + INT_TYPE_COMMA +
                CollegeEntry.NET_COST_3 + INT_TYPE_COMMA + CollegeEntry.NET_COST_4 + INT_TYPE_COMMA +
                CollegeEntry.NET_COST_5 + INT_TYPE_COMMA + CollegeEntry.CITY + TEXT_TYPE_COMMA + CollegeEntry.WEB_URL +
                TEXT_TYPE_COMMA + CollegeEntry.ADM_URL + TEXT_TYPE_COMMA + CollegeEntry.FIN_URL + TEXT_TYPE_COMMA +
                CollegeEntry.APP_URL + TEXT_TYPE_COMMA + CollegeEntry.RESOURCES_SPENT + DEC_TYPE_COMMA +
                CollegeEntry.CALC_URL + TEXT_TYPE_COMMA + CollegeEntry.SAT25 + INT_TYPE_COMMA + CollegeEntry.SAT75 +
                INT_TYPE_COMMA + CollegeEntry.FOUR_YR_GRAD_RATE + INT_TYPE_COMMA + CollegeEntry.SIX_YR_GRAD_RATE + INT_TYPE_COMMA +
                CollegeEntry.ENDOWMENT + DEC_TYPE + ")";
        db.execSQL(CREATE_COLLEGES_TABLE);
        String index = "CREATE INDEX " + CollegeEntry.NAME_INDEX + " ON " + TABLE_COLLEGES +
                "(" + CollegeEntry.NAME + ")";
        db.execSQL(index);
        index = "CREATE INDEX " + CollegeEntry.STATE_INDEX + " ON " + TABLE_COLLEGES +
                "(" + CollegeEntry.STATE + ")";
        db.execSQL(index);
    }

    private void createMajorTable(SQLiteDatabase db) {
        String CREATE_MAJOR_NAME_TABLE = "CREATE TABLE " + TABLE_MAJOR_NAMES + "(" +
                MajorNameEntry.ID + INT_TYPE + " PRIMARY KEY" + COMMA_SEP + MajorNameEntry.NAME + TEXT_TYPE + ")";
        db.execSQL(CREATE_MAJOR_NAME_TABLE);
        String CREATE_MAJORS_TABLE = "CREATE TABLE " + TABLE_MAJORS + "(" +
                MajorEntry.ID + INT_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP + MajorEntry.COLLEGE_ID +
                INT_TYPE_COMMA + MajorEntry.MAJOR + INT_TYPE_COMMA + 
                "FOREIGN KEY(" + MajorEntry.MAJOR + ") REFERENCES " + TABLE_MAJOR_NAMES + "(" + MajorNameEntry.ID + ")" + COMMA_SEP
                + "FOREIGN KEY(" + MajorEntry.COLLEGE_ID + ") REFERENCES " + TABLE_COLLEGES + "(" + CollegeEntry.ID + "))";
        db.execSQL(CREATE_MAJORS_TABLE);
        String index = "CREATE INDEX " + MajorEntry.COLLEGE_INDEX + " ON " + TABLE_MAJORS +
                "(" + MajorEntry.COLLEGE_ID + ")";
        db.execSQL(index);
        index = "CREATE INDEX " + MajorEntry.MAJOR_INDEX + " ON " + TABLE_MAJORS +
                "(" + MajorEntry.MAJOR + ")";
        db.execSQL(index);
    }

    private void createSavedTable(SQLiteDatabase db) {
        String CREATE_SAVED_COLLEGES_TABLE = "CREATE TABLE " + TABLE_SAVED_COLLEGES + "(" +
                SavedCollegeEntry.ID + INT_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP + SavedCollegeEntry.COLLEGE_ID +
                INT_TYPE + " UNIQUE NOT NULL," + "FOREIGN KEY(" + SavedCollegeEntry.COLLEGE_ID + ") REFERENCES " +
                TABLE_COLLEGES + "(" + CollegeEntry.ID + "))";
        db.execSQL(CREATE_SAVED_COLLEGES_TABLE);
    }

//    private void populateCollegeTable(SQLiteDatabase db) {
//        CSVReader csv;
//        String[] v;
//
//        try {
//            csv = new CSVReader(new InputStreamReader(context.getResources().openRawResource(R.raw.database)));
//            while ((v = csv.readNext()) != null) {
//                College college = CollegeHelper.parseCollege(v);
//                addCollege(db, college);
//            }
//            csv.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private void populateMajorTable(SQLiteDatabase db) {
        CSVReader csv;
        String[] v;
        try {
            csv = new CSVReader(new InputStreamReader(context.getResources().openRawResource(R.raw.major_list)));
            while ((v = csv.readNext()) != null) {
                addMajorName(db, Integer.parseInt(v[0]), v[1]);
            }
            csv.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            csv = new CSVReader(new InputStreamReader(context.getResources().openRawResource(R.raw.college_majors)));
            while ((v = csv.readNext()) != null) {
                int collegeId = Integer.parseInt(v[0]);
                String[] majors = v[1].split(";");
                for (String major: majors)
                    addMajor(db, collegeId, Integer.parseInt(major));
            }
            csv.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        if ((oldVersion == 1) && (newVersion == 2)) {
            populateMajorTable(db);
        }
    }
 
    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
    
    public void addCollege(SQLiteDatabase db, College college) {
        // Inserting Row
        db.insert(TABLE_COLLEGES, null, CollegeHelper.createCollegeValues(college));
    }
    
    public void addMajor(SQLiteDatabase db, int collegeId, int major) {
        // Inserting Row
        db.insert(TABLE_MAJORS, null, CollegeHelper.createMajorValues(collegeId,
                major));
    }
    
    public void addMajorName(SQLiteDatabase db, int id, String name) {
        // Inserting Row
        db.insert(TABLE_MAJOR_NAMES, null, CollegeHelper.createMajorNameValues(id,
                name));
    }
    
    public void saveCollege(int collegeId) {
        db.insertOrThrow(TABLE_SAVED_COLLEGES, null, CollegeHelper.createSavedCollegeValues(collegeId));
    }
    
    public Cursor getSavedColleges() {
        String query = "SELECT * FROM " + TABLE_SAVED_COLLEGES + " b INNER JOIN " + TABLE_COLLEGES +
                " a ON a." + CollegeEntry.ID + "=b." + SavedCollegeEntry.COLLEGE_ID;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }
    
    public Cursor getSavedColleges(String name) {
        String query = "SELECT * FROM " + TABLE_SAVED_COLLEGES + " b INNER JOIN " + TABLE_COLLEGES +
                " a ON a." + CollegeEntry.ID + "=b." + SavedCollegeEntry.COLLEGE_ID + " WHERE a." +
                CollegeEntry.NAME + " like ?";
        Cursor cursor = db.rawQuery(query, new String[] { "%" + name + "%"});
        return cursor;
    }
 
    // Getting single college
    public College getCollege(int id) {
        Cursor cursor = db.query(TABLE_COLLEGES, null, CollegeEntry.ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        
        College college = CollegeHelper.convertToCollege(cursor);
        college.setMajors(getMajors(id));
        cursor.close();
        return college;
    }
     
    // Getting All Colleges
    public Cursor getAllColleges(String sort) {
        Cursor cursor = db.query(TABLE_COLLEGES, null, null, null, null, null, CollegeHelper.getSortClause(sort), null);
        return cursor;
    }
    
    // Getting match colleges
    public Cursor getMatchColleges(String match, String state, String sort) {
        Cursor cursor;
        if (state.equals(CollegeHelper.ALL_STATES)) {
            cursor = db.query(TABLE_COLLEGES, null, CollegeEntry.NAME + " like ?",
                new String[] { "%" + match + "%" }, null, null, CollegeHelper.getSortClause(sort), null);
        }
        else {
            cursor = db.query(TABLE_COLLEGES, null, CollegeEntry.STATE + "=? AND " + 
               CollegeEntry.NAME + " like ?", 
               new String[] { state, "%" + match + "%" }, null, null, CollegeHelper.getSortClause(sort), null);
        }
        
        return cursor;
    }
 
    // Updating single college
    public int updateCollege(College college) {
        // updating row
        return db.update(TABLE_COLLEGES, CollegeHelper.createCollegeValues(college), CollegeEntry.NAME + " = ?",
                new String[] { college.getName() });
    }
 
    // Deleting single college
    public int deleteCollege(int id) {
        return db.delete(TABLE_SAVED_COLLEGES, SavedCollegeEntry.COLLEGE_ID + "=?",
                new String[] { String.valueOf(id) });
    }
 
    // Getting colleges Count
    public int getCollegesCount() {
        Cursor cursor = db.query(TABLE_COLLEGES, null, null, null, null, null, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }

    public Cursor getCollegesByState(String state, String sort) {
        if (state.equals(CollegeHelper.ALL_STATES)) return getAllColleges(sort);
        Cursor cursor = db.query(TABLE_COLLEGES, null, CollegeEntry.STATE + "=?",
                new String[] { state }, null, null, CollegeHelper.getSortClause(sort), null);
        
        return cursor;
    }

    public List<String> getMajors(int id) {
        List<String> majors = new ArrayList<String>();
        String query = "SELECT b." + MajorNameEntry.NAME + " FROM " + TABLE_MAJORS + " a INNER JOIN " +
                TABLE_MAJOR_NAMES + " b ON a." + MajorEntry.MAJOR + "=b." + MajorNameEntry.ID +
                " WHERE a." + MajorEntry.COLLEGE_ID + "=?";
        Cursor cursor = db.rawQuery(query, new String[] { String.valueOf(id) });
        while (cursor.moveToNext()) {
            majors.add(cursor.getString(0));
        }
        cursor.close();
        return majors;
    }
 
}
