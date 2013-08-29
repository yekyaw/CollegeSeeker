package com.elencticsoft.collegeseeker;

import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;
import android.database.Cursor;

import com.elencticsoft.collegeseeker.College.Classification;
import com.elencticsoft.collegeseeker.College.Locale;
import com.elencticsoft.collegeseeker.College.Type;
import com.elencticsoft.collegeseeker.CollegeContract.CollegeEntry;
import com.elencticsoft.collegeseeker.CollegeContract.SavedCollegeEntry;

public class CollegeHelper {
    private static Map<String, Integer> columnIndexes;
    public static final String ALL_STATES = "All States";
    
    public static String getIcon(String name) {
        if (name.equals("Harvard University")) return "harvard";
        else if (name.equals("Princeton University")) return "princeton";
        else if (name.equals("Yale University")) return "yale";
        else if (name.equals("Stanford University")) return "stanford";
        else if (name.equals("Massachusetts Institute of Technology")) return "mit";
        else if (name.equals("California Institute of Technology")) return "caltech";
        else if (name.equals("Dartmouth College")) return "dartmouth";
        else if (name.equals("Columbia University in the City of New York")) return "columbia";
        else if (name.equals("University of Pennsylvania")) return "upenn";
        else if (name.equals("Brown University")) return "brown";
        else if (name.equals("Cornell University")) return "cornell";
        else if (name.equals("Duke University")) return "duke";
        else if (name.equals("University of Chicago")) return "uchicago";
        else if (name.equals("Northwestern University")) return "northwestern";
        else if (name.equals("Johns Hopkins University")) return "jhu";
        else if (name.equals("University of Notre Dame")) return "notre_dame";
        else if (name.equals("Rice University")) return "rice";
        else if (name.equals("Emory University")) return "emory";
        else if (name.equals("Vanderbilt University")) return "vanderbilt";
        else if (name.equals("Georgetown University")) return "georgetown";
        else if (name.equals("Carnegie Mellon University")) return "cmu";
        else if (name.equals("University of California-Berkeley")) return "berkeley";
        else if (name.equals("University of California-Los Angeles")) return "ucla";
        else if (name.equals("University of Virginia-Main Campus")) return "uva";
        else if (name.equals("University of Michigan-Ann Arbor")) return "umich";
        else if (name.equals("University of North Carolina at Chapel Hill")) return "unc";
        else if (name.equals("Washington University in St Louis")) return "wustl";
        
        else if (name.equals("Williams College")) return "williams";
        else if (name.equals("Amherst College")) return "amherst";
        else if (name.equals("Swarthmore College")) return "swarthmore";
        else if (name.equals("Pomona College")) return "pomona";
        else if (name.equals("Middlebury College")) return "middlebury";
        else if (name.equals("Bowdoin College")) return "bowdoin";
        else if (name.equals("Wellesley College")) return "wellesley";
        else if (name.equals("Carleton College")) return "carleton";
        else if (name.equals("Haverford College")) return "haverford";
        else if (name.equals("Claremont McKenna College")) return "cmc";
        else if (name.equals("Vassar College")) return "vassar";
        else if (name.equals("Harvey Mudd College")) return "hmc";
        else if (name.equals("Davidson College")) return "davidson";
        else if (name.equals("Wesleyan University")) return "wesleyan";
        else if (name.equals("Grinnell College")) return "grinnell";
        return null;
    }
    
    public static Classification getClassification(int classificationCode) {
        Classification classification;
        switch (classificationCode) {
        case 15: classification = Classification.RESEARCH_UNI;
        break;
        case 16: classification = Classification.RESEARCH_UNI;
        break;
        case 17: classification = Classification.RESEARCH_UNI;
        break;
        case 18: classification = Classification.MASTERS;
        break;
        case 19: classification = Classification.MASTERS;
        break;
        case 20: classification = Classification.MASTERS;
        break;
        case 21: classification = Classification.LIBERAL_ARTS;
        break;
        case 22: classification = Classification.BAC_COLLEGE;
        break;
        case 23: classification = Classification.BAC_COLLEGE;
        break;
        case 27: classification = Classification.ENGINEERING;
        break;
        default: classification = null;
        break;
        }
        return classification;
    }
    
    public static Locale getLocale(int localeCode) {
        Locale locale;
        switch (localeCode) {
        case 11: locale = Locale.CITY;
        break;
        case 12: locale = Locale.CITY;
        break;
        case 13: locale = Locale.CITY;
        break;
        case 21: locale = Locale.SUBURB;
        break;
        case 22: locale = Locale.SUBURB;
        break;
        case 23: locale = Locale.SUBURB;
        break;
        case 31: locale = Locale.TOWN;
        break;
        case 32: locale = Locale.TOWN;
        break;
        case 33: locale = Locale.TOWN;
        break;
        case 41: locale = Locale.RURAL;
        break;
        case 42: locale = Locale.RURAL;
        break;
        case 43: locale = Locale.RURAL;
        break;
        default: locale = null;
        break;
        }
        return locale;
    }
    
    public static Type getType(int typeCode) {
        Type type;
        switch (typeCode) {
        case 1: type = Type.PUBLIC;
        break;
        case 2: type = Type.PRIVATE_FP;
        break;
        case 3: type = Type.PRIVATE_NFP;
        break;
        case 4: type = Type.PRIVATE_REL;
        break;
        default:type = null;
        break;
        }
        return type;
    }
    
    public static boolean isGoodMatch(boolean small, boolean medium, boolean large,
            boolean city, boolean suburb, boolean town, boolean rural,
            boolean public_i, boolean private_fp, boolean private_nfp, boolean private_rel,
            Integer ver, Integer mat, Integer wri, Integer act, College college) {
        
        if (!small && (college.getTotalEnrolled() < 5000)) return false;
        if (!medium && ((college.getTotalEnrolled() >= 5000) && (college.getTotalEnrolled() <= 10000))) return false;
        if (!large && (college.getTotalEnrolled() > 10000)) return false;
        
        switch (college.getLocale()) {
        case CITY: if (!city) return false;
        break;
        case RURAL: if (!rural) return false;
        break;
        case SUBURB: if (!suburb) return false;
        break;
        case TOWN: if (!town) return false;
        break;
        default:
            break;
        }
        
        switch (college.getType()) {
        case PRIVATE_FP: if (!private_fp) return false;
        break;
        case PRIVATE_NFP:if (!private_nfp) return false;
        break;
        case PRIVATE_REL:if (!private_rel) return false;
        break;
        case PUBLIC: if (!public_i) return false;
        break;
        default:
            break;     
        }
   
        if ((ver == null) && (mat == null) && (wri == null) && (act == null)) return true;
        if ((ver == null) || (mat == null) || (wri == null)) {
            if (act != null) return isACTMatch(act, college);
            return false;
        }
        if (act == null) return isSATMatch(ver, mat, wri, college);
        return isSATACTMatch(ver, mat, wri, act, college);
    }
    
    private static boolean isSATMatch(int ver, int mat, int wri, College college) { 
        int avg, cAvg25, cAvg75;    
        if (college.getWri75() == 0) {
            cAvg25 = (college.getVer25() + college.getMat25()) / 2;
            cAvg75 = (college.getVer75() + college.getMat75()) / 2;
            avg = (ver + mat) / 2;
        }
        else {
            cAvg25 = (college.getVer25() + college.getMat25() + college.getWri25()) / 3;
            cAvg75 = (college.getVer75() + college.getMat75() + college.getWri75()) / 3;
            avg = (ver + mat + wri) / 3;
        }     
        if ((avg >= cAvg25 - 30) && (avg <= cAvg75 + 30)) return true;
        return false;
    }
    
    private static boolean isACTMatch(int act, College college) {
        if ((act >= college.getAct25() - 1) && (act <= college.getAct75() + 1)) return true;
        return false;
    }
    
    private static boolean isSATACTMatch(int ver, int mat, int wri, int act, College college) {
        int avg, cAvg25, cAvg75;    
        if (college.getWri75() == 0) {
            cAvg25 = (college.getVer25() + college.getMat25()) / 2;
            cAvg75 = (college.getVer75() + college.getMat75()) / 2;
            avg = (ver + mat) / 2;
        }
        else {
            cAvg25 = (college.getVer25() + college.getMat25() + college.getWri25()) / 3;
            cAvg75 = (college.getVer75() + college.getMat75() + college.getWri75()) / 3;
            avg = (ver + mat + wri) / 3;
        }     
        if ((avg > cAvg75 + 30) || (act > college.getAct75() + 1)) return false;
        if ((avg < cAvg25 - 30) && (act < college.getAct25() - 1)) return false;
        return true;
    }
    
    private static Map<String, Integer> getColumnIndexes(Cursor cursor) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put(CollegeEntry.ACT25, cursor.getColumnIndex(CollegeEntry.ACT25));
        map.put(CollegeEntry.ACT75, cursor.getColumnIndex(CollegeEntry.ACT75));
        map.put(CollegeEntry.ADMITS, cursor.getColumnIndex(CollegeEntry.ADMITS));
        map.put(CollegeEntry.APPLICANTS, cursor.getColumnIndex(CollegeEntry.APPLICANTS));
        map.put(CollegeEntry.APPLICATION_FEE, cursor.getColumnIndex(CollegeEntry.APPLICATION_FEE));
        map.put(CollegeEntry.CLASSIFICATION, cursor.getColumnIndex(CollegeEntry.CLASSIFICATION));
        map.put(CollegeEntry.FACULTY_RATIO, cursor.getColumnIndex(CollegeEntry.FACULTY_RATIO));
        map.put(CollegeEntry.FIRST_YR_ENROLLMENT, cursor.getColumnIndex(CollegeEntry.FIRST_YR_ENROLLMENT));
        map.put(CollegeEntry.FOUR_YR_GRAD, cursor.getColumnIndex(CollegeEntry.FOUR_YR_GRAD));
        map.put(CollegeEntry.FOUR_YR_GRAD_RATE, cursor.getColumnIndex(CollegeEntry.FOUR_YR_GRAD_RATE));
        map.put(CollegeEntry.GRAD_COHORT, cursor.getColumnIndex(CollegeEntry.GRAD_COHORT));
        map.put(CollegeEntry.ICON, cursor.getColumnIndex(CollegeEntry.ICON));
        map.put(CollegeEntry.ID, cursor.getColumnIndex(CollegeEntry.ID));
        map.put(CollegeEntry.LOCALE, cursor.getColumnIndex(CollegeEntry.LOCALE));
        map.put(CollegeEntry.MAT25, cursor.getColumnIndex(CollegeEntry.MAT25));
        map.put(CollegeEntry.MAT75, cursor.getColumnIndex(CollegeEntry.MAT75));
        map.put(CollegeEntry.NAME, cursor.getColumnIndex(CollegeEntry.NAME));
        map.put(CollegeEntry.RETENTION_RATE, cursor.getColumnIndex(CollegeEntry.RETENTION_RATE));
        map.put(CollegeEntry.ROOM_AND_BOARD, cursor.getColumnIndex(CollegeEntry.ROOM_AND_BOARD));
        map.put(CollegeEntry.SIX_YR_GRAD, cursor.getColumnIndex(CollegeEntry.SIX_YR_GRAD));
        map.put(CollegeEntry.SIX_YR_GRAD_RATE, cursor.getColumnIndex(CollegeEntry.SIX_YR_GRAD_RATE));
        map.put(CollegeEntry.STATE, cursor.getColumnIndex(CollegeEntry.STATE));
        map.put(CollegeEntry.STATE_ABBR, cursor.getColumnIndex(CollegeEntry.STATE_ABBR));
        map.put(CollegeEntry.TYPE, cursor.getColumnIndex(CollegeEntry.TYPE));
        map.put(CollegeEntry.VER25, cursor.getColumnIndex(CollegeEntry.VER25));
        map.put(CollegeEntry.VER75, cursor.getColumnIndex(CollegeEntry.VER75));
        map.put(CollegeEntry.WRI25, cursor.getColumnIndex(CollegeEntry.WRI25));
        map.put(CollegeEntry.WRI75, cursor.getColumnIndex(CollegeEntry.WRI75));
        map.put(CollegeEntry.SAT25, cursor.getColumnIndex(CollegeEntry.SAT25));
        map.put(CollegeEntry.SAT75, cursor.getColumnIndex(CollegeEntry.SAT75));
        map.put(CollegeEntry.TOTAL_ENROLLED, cursor.getColumnIndex(CollegeEntry.TOTAL_ENROLLED));
        map.put(CollegeEntry.PERCENT_FIN_AID, cursor.getColumnIndex(CollegeEntry.PERCENT_FIN_AID));
        map.put(CollegeEntry.PERCENT_GRANT_AID, cursor.getColumnIndex(CollegeEntry.PERCENT_GRANT_AID));
        map.put(CollegeEntry.NET_COST, cursor.getColumnIndex(CollegeEntry.NET_COST));
        map.put(CollegeEntry.NET_COST_1, cursor.getColumnIndex(CollegeEntry.NET_COST_1));
        map.put(CollegeEntry.NET_COST_2, cursor.getColumnIndex(CollegeEntry.NET_COST_2));
        map.put(CollegeEntry.NET_COST_3, cursor.getColumnIndex(CollegeEntry.NET_COST_3));
        map.put(CollegeEntry.NET_COST_4, cursor.getColumnIndex(CollegeEntry.NET_COST_4));
        map.put(CollegeEntry.NET_COST_5, cursor.getColumnIndex(CollegeEntry.NET_COST_5));
        map.put(CollegeEntry.TUITION_IN_STATE, cursor.getColumnIndex(CollegeEntry.TUITION_IN_STATE));
        map.put(CollegeEntry.TUITION_OUT_STATE, cursor.getColumnIndex(CollegeEntry.TUITION_OUT_STATE));
        map.put(CollegeEntry.TOTAL_IN_STATE, cursor.getColumnIndex(CollegeEntry.TOTAL_IN_STATE));
        map.put(CollegeEntry.TOTAL_OUT_STATE, cursor.getColumnIndex(CollegeEntry.TOTAL_OUT_STATE));
        map.put(CollegeEntry.MALE_RATIO, cursor.getColumnIndex(CollegeEntry.MALE_RATIO));
        map.put(CollegeEntry.FEMALE_RATIO, cursor.getColumnIndex(CollegeEntry.FEMALE_RATIO));
        map.put(CollegeEntry.CITY, cursor.getColumnIndex(CollegeEntry.CITY));
        map.put(CollegeEntry.WEB_URL, cursor.getColumnIndex(CollegeEntry.WEB_URL));
        map.put(CollegeEntry.ADM_URL, cursor.getColumnIndex(CollegeEntry.ADM_URL));
        map.put(CollegeEntry.FIN_URL, cursor.getColumnIndex(CollegeEntry.FIN_URL));
        map.put(CollegeEntry.APP_URL, cursor.getColumnIndex(CollegeEntry.APP_URL));
        map.put(CollegeEntry.CALC_URL, cursor.getColumnIndex(CollegeEntry.CALC_URL));
        map.put(CollegeEntry.RESOURCES_SPENT, cursor.getColumnIndex(CollegeEntry.RESOURCES_SPENT));
        map.put(CollegeEntry.ENDOWMENT, cursor.getColumnIndex(CollegeEntry.ENDOWMENT));
        
        return map;
    }
    
    public static College convertToCollege(Cursor cursor) {
        if (columnIndexes == null) columnIndexes = getColumnIndexes(cursor);
        
        College college = new College();
        college.setAct25(cursor.getInt(columnIndexes.get(CollegeEntry.ACT25)));
        college.setAct75(cursor.getInt(columnIndexes.get(CollegeEntry.ACT75)));
        college.setAdmits(cursor.getInt(columnIndexes.get(CollegeEntry.ADMITS)));
        college.setApplicants(cursor.getInt(columnIndexes.get(CollegeEntry.APPLICANTS)));
        college.setApplicationFee(cursor.getInt(columnIndexes.get(CollegeEntry.APPLICATION_FEE)));
        college.setClassification(Classification.values()[cursor.getInt(columnIndexes.get(CollegeEntry.CLASSIFICATION))]);
        college.setFacultyRatio(cursor.getInt(columnIndexes.get(CollegeEntry.FACULTY_RATIO)));
        college.setFirstYearEnrollment(cursor.getInt(columnIndexes.get(CollegeEntry.FIRST_YR_ENROLLMENT)));
        college.setFourYrGrad(cursor.getInt(columnIndexes.get(CollegeEntry.FOUR_YR_GRAD)));
        college.setFourYrGradRate(cursor.getInt(columnIndexes.get(CollegeEntry.FOUR_YR_GRAD_RATE)));
        college.setGradCohort(cursor.getInt(columnIndexes.get(CollegeEntry.GRAD_COHORT)));
        college.setIcon(cursor.getString(columnIndexes.get(CollegeEntry.ICON)));
        college.setId(cursor.getInt(columnIndexes.get(CollegeEntry.ID)));
        college.setLocale(Locale.values()[cursor.getInt(columnIndexes.get(CollegeEntry.LOCALE))]);
        college.setMat25(cursor.getInt(columnIndexes.get(CollegeEntry.MAT25)));
        college.setMat75(cursor.getInt(columnIndexes.get(CollegeEntry.MAT75)));
        college.setName(cursor.getString(columnIndexes.get(CollegeEntry.NAME)));
        college.setRetentionRate(cursor.getInt(columnIndexes.get(CollegeEntry.RETENTION_RATE)));
        college.setRoomAndBoard(cursor.getInt(columnIndexes.get(CollegeEntry.ROOM_AND_BOARD)));
        college.setSixYrGrad(cursor.getInt(columnIndexes.get(CollegeEntry.SIX_YR_GRAD)));
        college.setSixYrGradRate(cursor.getInt(columnIndexes.get(CollegeEntry.SIX_YR_GRAD_RATE)));
        college.setState(cursor.getString(columnIndexes.get(CollegeEntry.STATE)));
        college.setStateAbbr(cursor.getString(columnIndexes.get(CollegeEntry.STATE_ABBR)));
        college.setType(Type.values()[cursor.getInt(columnIndexes.get(CollegeEntry.TYPE))]);
        college.setVer25(cursor.getInt(columnIndexes.get(CollegeEntry.VER25)));
        college.setVer75(cursor.getInt(columnIndexes.get(CollegeEntry.VER75)));
        college.setWri25(cursor.getInt(columnIndexes.get(CollegeEntry.WRI25)));
        college.setWri75(cursor.getInt(columnIndexes.get(CollegeEntry.WRI75)));
        college.setSat25(cursor.getInt(columnIndexes.get(CollegeEntry.SAT25)));
        college.setSat75(cursor.getInt(columnIndexes.get(CollegeEntry.SAT75)));
        college.setTotalEnrolled(cursor.getInt(columnIndexes.get(CollegeEntry.TOTAL_ENROLLED)));
        college.setPercentFinAid(cursor.getInt(columnIndexes.get(CollegeEntry.PERCENT_FIN_AID)));
        college.setPercentGrantAid(cursor.getInt(columnIndexes.get(CollegeEntry.PERCENT_GRANT_AID)));
        college.setNetCost(cursor.getInt(columnIndexes.get(CollegeEntry.NET_COST)));
        college.setNetCost1(cursor.getInt(columnIndexes.get(CollegeEntry.NET_COST_1)));
        college.setNetCost2(cursor.getInt(columnIndexes.get(CollegeEntry.NET_COST_2)));
        college.setNetCost3(cursor.getInt(columnIndexes.get(CollegeEntry.NET_COST_3)));
        college.setNetCost4(cursor.getInt(columnIndexes.get(CollegeEntry.NET_COST_4)));
        college.setNetCost5(cursor.getInt(columnIndexes.get(CollegeEntry.NET_COST_5)));
        college.setTuitionInState(cursor.getInt(columnIndexes.get(CollegeEntry.TUITION_IN_STATE)));
        college.setTuitionOutState(cursor.getInt(columnIndexes.get(CollegeEntry.TUITION_OUT_STATE)));
        college.setTotalInState(cursor.getInt(columnIndexes.get(CollegeEntry.TOTAL_IN_STATE)));
        college.setTotalOutState(cursor.getInt(columnIndexes.get(CollegeEntry.TOTAL_OUT_STATE)));
        college.setMaleRatio(cursor.getInt(columnIndexes.get(CollegeEntry.MALE_RATIO)));
        college.setFemaleRatio(cursor.getInt(columnIndexes.get(CollegeEntry.FEMALE_RATIO)));
        college.setCity(cursor.getString(columnIndexes.get(CollegeEntry.CITY)));
        college.setWebUrl(cursor.getString(columnIndexes.get(CollegeEntry.WEB_URL)));
        college.setAdmUrl(cursor.getString(columnIndexes.get(CollegeEntry.ADM_URL)));
        college.setFinUrl(cursor.getString(columnIndexes.get(CollegeEntry.FIN_URL)));
        college.setAppUrl(cursor.getString(columnIndexes.get(CollegeEntry.APP_URL)));
        college.setCalcUrl(cursor.getString(columnIndexes.get(CollegeEntry.CALC_URL)));
        college.setResourcesSpent(cursor.getDouble(columnIndexes.get(CollegeEntry.RESOURCES_SPENT)));
        college.setEndowment(cursor.getDouble(columnIndexes.get(CollegeEntry.ENDOWMENT)));

        return college;
    }
    
    public static ContentValues createCollegeValues(College college) {
        ContentValues values = new ContentValues();
        values.put(CollegeEntry.ACT25, college.getAct25());
        values.put(CollegeEntry.ACT75, college.getAct75());
        values.put(CollegeEntry.ADMITS, college.getAdmits());
        values.put(CollegeEntry.APPLICANTS, college.getApplicants());
        values.put(CollegeEntry.APPLICATION_FEE, college.getApplicationFee());
        values.put(CollegeEntry.CLASSIFICATION, college.getClassification().ordinal());
        values.put(CollegeEntry.FACULTY_RATIO, college.getFacultyRatio());
        values.put(CollegeEntry.FIRST_YR_ENROLLMENT, college.getFirstYearEnrollment());
        values.put(CollegeEntry.FOUR_YR_GRAD, college.getFourYrGrad());
        values.put(CollegeEntry.FOUR_YR_GRAD_RATE, college.getFourYrGradRate());
        values.put(CollegeEntry.GRAD_COHORT, college.getGradCohort());
        values.put(CollegeEntry.ICON, college.getIcon());
        values.put(CollegeEntry.ID, college.getId());
        values.put(CollegeEntry.LOCALE, college.getLocale().ordinal());
        values.put(CollegeEntry.MAT25, college.getMat25());
        values.put(CollegeEntry.MAT75, college.getMat75());
        values.put(CollegeEntry.NAME, college.getName());
        values.put(CollegeEntry.RETENTION_RATE, college.getRetentionRate());
        values.put(CollegeEntry.ROOM_AND_BOARD, college.getRoomAndBoard());
        values.put(CollegeEntry.SIX_YR_GRAD, college.getSixYrGrad());
        values.put(CollegeEntry.SIX_YR_GRAD_RATE, college.getSixYrGradRate());
        values.put(CollegeEntry.STATE, college.getState());
        values.put(CollegeEntry.STATE_ABBR, college.getStateAbbr());
        values.put(CollegeEntry.TYPE, college.getType().ordinal());
        values.put(CollegeEntry.VER25, college.getVer25());
        values.put(CollegeEntry.VER75, college.getVer75());
        values.put(CollegeEntry.WRI25, college.getWri25());
        values.put(CollegeEntry.WRI75, college.getWri75());
        values.put(CollegeEntry.SAT25, college.getSat25());
        values.put(CollegeEntry.SAT75, college.getSat75());
        values.put(CollegeEntry.TOTAL_ENROLLED, college.getTotalEnrolled());
        values.put(CollegeEntry.PERCENT_FIN_AID, college.getPercentFinAid());
        values.put(CollegeEntry.PERCENT_GRANT_AID, college.getPercentGrantAid());
        values.put(CollegeEntry.NET_COST, college.getNetCost());
        values.put(CollegeEntry.NET_COST_1, college.getNetCost1());
        values.put(CollegeEntry.NET_COST_2, college.getNetCost2());
        values.put(CollegeEntry.NET_COST_3, college.getNetCost3());
        values.put(CollegeEntry.NET_COST_4, college.getNetCost4());
        values.put(CollegeEntry.NET_COST_5, college.getNetCost5());
        values.put(CollegeEntry.TUITION_IN_STATE, college.getTuitionInState());
        values.put(CollegeEntry.TUITION_OUT_STATE, college.getTuitionOutState());
        values.put(CollegeEntry.TOTAL_IN_STATE, college.getTotalInState());
        values.put(CollegeEntry.TOTAL_OUT_STATE, college.getTotalOutState());
        values.put(CollegeEntry.MALE_RATIO, college.getMaleRatio());
        values.put(CollegeEntry.FEMALE_RATIO, college.getFemaleRatio());
        values.put(CollegeEntry.CITY, college.getCity());
        values.put(CollegeEntry.WEB_URL, college.getWebUrl());
        values.put(CollegeEntry.ADM_URL, college.getAdmUrl());
        values.put(CollegeEntry.FIN_URL, college.getFinUrl());
        values.put(CollegeEntry.APP_URL, college.getAppUrl());
        values.put(CollegeEntry.CALC_URL, college.getCalcUrl());
        values.put(CollegeEntry.RESOURCES_SPENT, college.getResourcesSpent());
        values.put(CollegeEntry.ENDOWMENT, college.getEndowment());
        return values;
    }
    
    public static ContentValues createSavedCollegeValues(int collegeId) {
        ContentValues values = new ContentValues();
        values.put(SavedCollegeEntry.COLLEGE_ID, collegeId);
        return values;
    }
    
    
    public static College parseCollege(String[] v) {
        College college = new College();
        college.setId(Integer.parseInt(v[0]));
        college.setName(v[1]);
        if (v[2].length() > 0) college.setIcon(v[2]);
        college.setFirstYearEnrollment(Integer.parseInt(v[3]));
        college.setApplicants(Integer.parseInt(v[4]));
        college.setAdmits(Integer.parseInt(v[5]));
        college.setApplicationFee(Integer.parseInt(v[6]));
        college.setRoomAndBoard(Integer.parseInt(v[7]));
        college.setVer25(Integer.parseInt(v[8]));
        college.setVer75(Integer.parseInt(v[9]));
        college.setMat25(Integer.parseInt(v[10]));
        college.setMat75(Integer.parseInt(v[11]));
        college.setWri25(Integer.parseInt(v[12]));
        college.setWri75(Integer.parseInt(v[13]));
        college.setAct25(Integer.parseInt(v[14]));
        college.setAct75(Integer.parseInt(v[15]));
        college.setType(Type.values()[Integer.parseInt(v[16])]);
        college.setClassification(Classification.values()[Integer.parseInt(v[17])]);
        college.setState(v[18]);
        college.setLocale(Locale.values()[Integer.parseInt(v[19])]);
        college.setGradCohort(Integer.parseInt(v[20]));
        college.setFourYrGrad(Integer.parseInt(v[21]));
        college.setSixYrGrad(Integer.parseInt(v[22]));
        college.setFacultyRatio(Integer.parseInt(v[23]));
        college.setRetentionRate(Integer.parseInt(v[24]));
        college.setTotalEnrolled(Integer.parseInt(v[25]));
        college.setPercentFinAid(Integer.parseInt(v[26]));
        college.setPercentGrantAid(Integer.parseInt(v[27]));
        college.setNetCost(Integer.parseInt(v[28]));
        college.setNetCost1(Integer.parseInt(v[29]));
        college.setNetCost2(Integer.parseInt(v[30]));
        college.setNetCost3(Integer.parseInt(v[31]));
        college.setNetCost4(Integer.parseInt(v[32]));
        college.setNetCost5(Integer.parseInt(v[33]));
        college.setTuitionInState(Integer.parseInt(v[34]));
        college.setTuitionOutState(Integer.parseInt(v[35]));
        college.setTotalInState(Integer.parseInt(v[36]));
        college.setTotalOutState(Integer.parseInt(v[37]));
        college.setMaleRatio(Integer.parseInt(v[38]));
        college.setFemaleRatio(Integer.parseInt(v[39]));
        college.setCity(v[40]);
        college.setWebUrl(v[41]);
        college.setAdmUrl(v[42]);
        college.setFinUrl(v[43]);
        college.setAppUrl(v[44]);
        college.setResourcesSpent(Double.parseDouble(v[45]));
        college.setEndowment(Double.parseDouble(v[46]));
        college.setStateAbbr(v[47]);
        college.setCalcUrl(v[48]);
        college.setFourYrGradRate(Integer.parseInt(v[49]));
        college.setSixYrGradRate(Integer.parseInt(v[50]));
        college.setSat25(Integer.parseInt(v[51]));
        college.setSat75(Integer.parseInt(v[52]));
        return college;
    }
    
    public static String getSortClause(String selection) {
        if (selection.equals("Sort by Name")) return CollegeEntry.NAME;
        if (selection.equals("4-year Grad Rate")) return CollegeEntry.FOUR_YR_GRAD_RATE + " DESC";
        if (selection.equals("6-year Grad Rate")) return CollegeEntry.SIX_YR_GRAD_RATE + " DESC";
        if (selection.equals("Retention Rate")) return CollegeEntry.RETENTION_RATE + " DESC";
        if (selection.equals("Total Cost (in-state)")) return CollegeEntry.TOTAL_IN_STATE;
        if (selection.equals("Total Cost (out-of-state)")) return CollegeEntry.TOTAL_OUT_STATE;
        if (selection.equals("Average Net Cost")) return CollegeEntry.NET_COST;
        if (selection.equals("SAT 25th percentile")) return CollegeEntry.SAT25 + " DESC";
        if (selection.equals("SAT 75th percentile")) return CollegeEntry.SAT75 + " DESC";    
        if (selection.equals("ACT 25th percentile")) return CollegeEntry.ACT25 + " DESC";
        if (selection.equals("ACT 75th percentile")) return CollegeEntry.ACT75 + " DESC";
        if (selection.equals("SAT 25th percentile")) return CollegeEntry.SAT25 + " DESC";
        if (selection.equals("Student-Faculty Ratio")) return CollegeEntry.FACULTY_RATIO;
        if (selection.equals("Financial Resources/Student")) return CollegeEntry.RESOURCES_SPENT + " DESC";
        if (selection.equals("Endowment")) return CollegeEntry.ENDOWMENT + " DESC";
        return CollegeEntry.NAME;
    }
}