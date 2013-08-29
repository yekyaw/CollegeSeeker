package com.elencticsoft.collegeseeker;

import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import au.com.bytecode.opencsv.CSVReader;

import com.elencticsoft.collegeseeker.CollegeContract.CollegeEntry;
import com.elencticsoft.collegeseeker.CollegeContract.SavedCollegeEntry;
 
public class DatabaseHandler extends SQLiteOpenHelper {
    private static DatabaseHandler instance;
 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "collegesManager";
 
    // Contacts table name
    private static final String TABLE_COLLEGES = "colleges";
    private static final String TABLE_SAVED_COLLEGES = "saved_colleges";
    private static final String TEXT_TYPE_COMMA = " TEXT,";
    private static final String INT_TYPE = " INTEGER";
    private static final String INT_TYPE_COMMA = " INTEGER,";
    private static final String DEC_TYPE = " DECIMAL";
    private static final String DEC_TYPE_COMMA = " DECIMAL,";
    private static final String COMMA_SEP = ",";
    
    private SQLiteDatabase db;
    private Context context;
    
    public static DatabaseHandler getInstance(Context context) {
        if (instance == null) instance = new DatabaseHandler(context);
        return instance;
    }
 
    private DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        if (db == null) db = getWritableDatabase();
    }
    
    @Override
    public void close() {
        super.close();
        instance = null;
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        createCollegeTable(db);
        String CREATE_SAVED_COLLEGES_TABLE = "CREATE TABLE " + TABLE_SAVED_COLLEGES + "(" +
        SavedCollegeEntry.ID + INT_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP + SavedCollegeEntry.COLLEGE_ID +
        INT_TYPE + " UNIQUE NOT NULL," + "FOREIGN KEY(" + SavedCollegeEntry.COLLEGE_ID + ") REFERENCES " +
        TABLE_COLLEGES + "(" + CollegeEntry.ID + "))";
        db.execSQL(CREATE_SAVED_COLLEGES_TABLE);
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
        readFromCsv(db);
    }
    
    private void readFromCsv(SQLiteDatabase db) {
        CSVReader csv = new CSVReader(new InputStreamReader(context.getResources().openRawResource(R.raw.database)));

        String[] v;
        try {
            while ((v = csv.readNext()) != null) {
                db.insert(TABLE_COLLEGES, null, CollegeHelper.createCollegeValues(CollegeHelper.parseCollege(v)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COLLEGES);
 
        // Create tables again
        createCollegeTable(db);
    }
    
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COLLEGES);
 
        // Create tables again
        createCollegeTable(db);
    }
 
    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
 
    // Adding new college
    public void addCollege(College college) {
        // Inserting Row
        db.insert(TABLE_COLLEGES, null, CollegeHelper.createCollegeValues(college));
    }
    
    public void saveCollege(int collegeId) {
        db.insertOrThrow(TABLE_SAVED_COLLEGES, null, CollegeHelper.createSavedCollegeValues(collegeId));
    }
    
    public Cursor getSavedColleges() {
        String query = "SELECT * FROM " + TABLE_COLLEGES + " a INNER JOIN " + TABLE_SAVED_COLLEGES +
                " b ON a." + CollegeEntry.ID + "=b." + SavedCollegeEntry.COLLEGE_ID;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }
    
    public Cursor getSavedColleges(String name) {
        String query = "SELECT * FROM " + TABLE_COLLEGES + " a INNER JOIN " + TABLE_SAVED_COLLEGES +
                " b ON a." + CollegeEntry.ID + "=b." + SavedCollegeEntry.COLLEGE_ID + " WHERE a." +
                CollegeEntry.NAME + " like ?";
        Cursor cursor = db.rawQuery(query, new String[] { "%" + name + "%"});
        return cursor;
    }
 
    // Getting single college
    public College getCollege(int id) {
        Cursor cursor = db.query(TABLE_COLLEGES, null, CollegeEntry.ID + "=?",
                new String[] { Integer.toString(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        
        College college = CollegeHelper.convertToCollege(cursor);
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
    public void deleteCollege(int id) {
        db.delete(TABLE_COLLEGES, CollegeEntry.ID + "=?",
                new String[] { Integer.toString(id) });
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
 
}
