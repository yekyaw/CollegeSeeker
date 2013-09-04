package com.elencticsoft.collegeseeker;

public class CollegeContract {
    public CollegeContract() {}
    
    // College Table Columns names
    public static class CollegeEntry {
        public static final String ID = "_id";
        public static final String NAME = "name";
        public static final String ICON = "icon";
        public static final String FIRST_YR_ENROLLMENT = "first_yr_enrollment";
        public static final String APPLICANTS = "applicants";
        public static final String ADMITS = "admits";
        public static final String APPLICATION_FEE = "app_fee";
        public static final String ROOM_AND_BOARD = "room_board";
        public static final String VER25 = "ver25";
        public static final String VER75 = "ver75";
        public static final String MAT25 = "mat25";
        public static final String MAT75 = "mat75";
        public static final String WRI25 = "wri25";
        public static final String WRI75 = "wri75";
        public static final String SAT25 = "sat25";
        public static final String SAT75 = "sat75";
        public static final String ACT25 = "act25";
        public static final String ACT75 = "act75";
        public static final String TYPE = "type";
        public static final String CLASSIFICATION = "classification";
        public static final String STATE = "state";
        public static final String STATE_ABBR = "state_abbr";
        public static final String LOCALE = "locale";
        public static final String GRAD_COHORT = "grad_cohort";
        public static final String FOUR_YR_GRAD = "four_yr_grad";
        public static final String SIX_YR_GRAD = "six_yr_grad";
        public static final String FOUR_YR_GRAD_RATE = "four_yr_grad_rate";
        public static final String SIX_YR_GRAD_RATE = "six_yr_grad_rate";
        public static final String RETENTION_RATE = "retention_rate";
        public static final String FACULTY_RATIO = "faculty_ratio";
        public static final String TOTAL_ENROLLED = "total_enrolled";
        public static final String PERCENT_FIN_AID = "fin_aid_per";
        public static final String PERCENT_GRANT_AID = "grant_aid_per";
        public static final String NET_COST = "net_cost";
        public static final String NET_COST_1 = "net_cost_1";
        public static final String NET_COST_2 = "net_cost_2";
        public static final String NET_COST_3 = "net_cost_3";
        public static final String NET_COST_4 = "net_cost_4";
        public static final String NET_COST_5 = "net_cost_5";
        public static final String TUITION_IN_STATE = "tuition_in_state";
        public static final String TUITION_OUT_STATE = "tuition_out_state";
        public static final String TOTAL_IN_STATE = "total_in_state";
        public static final String TOTAL_OUT_STATE = "total_out_state";
        public static final String MALE_RATIO = "male_ratio";
        public static final String FEMALE_RATIO = "female_ratio";
        public static final String CITY = "city";
        public static final String WEB_URL = "web_url";
        public static final String ADM_URL = "adm_url";
        public static final String FIN_URL = "fin_url";
        public static final String APP_URL = "app_url";
        public static final String CALC_URL = "calc_url";
        public static final String RESOURCES_SPENT = "resources_spent";
        public static final String ENDOWMENT = "endowment";
        
        public static final String NAME_INDEX = "name_idx";
        public static final String STATE_INDEX = "state_idx";
    }
    
    public static class SavedCollegeEntry {
        public static final String ID = "_id";
        public static final String COLLEGE_ID = "college_id";
    }
    
    public static class MajorEntry {
        public static final String ID = "_id";
        public static final String COLLEGE_ID = "college_id";
        public static final String MAJOR = "major";
        public static final String COLLEGE_INDEX = "college_idx";
        public static final String MAJOR_INDEX = "major_idx";
    }
    
    public static class MajorNameEntry {
        public static final String ID = "_id";
        public static final String NAME = "major_name";
    }
}
