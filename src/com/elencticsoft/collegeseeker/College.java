package com.elencticsoft.collegeseeker;

import java.util.ArrayList;
import java.util.List;

public class College implements Comparable<College> {
    private int id;
    private String name;
    private String icon;
    private int firstYearEnrollment;
    private int applicants;
    private int admits;

    private int ver25;
    private int ver75;
    private int mat25;
    private int mat75;
    private int wri25;
    private int wri75;
    private int sat25;
    private int sat75;
    private int act25;
    private int act75;
    private Type type;
    private Classification classification;
    private String state;
    private Locale locale;
    private int gradCohort;
    private int fourYrGrad;
    private int sixYrGrad;
    private int fourYrGradRate;
    private int sixYrGradRate;
    private int retentionRate;
    private int facultyRatio;
    private int totalEnrolled;
    
    private int percentFinAid;
    private int percentGrantAid;
    private int netCost;
    private int netCost1;
    private int netCost2;
    private int netCost3;
    private int netCost4;
    private int netCost5;
    private int applicationFee;
    private int roomAndBoard;
    private int tuitionInState;
    private int tuitionOutState;
    private int totalInState;
    private int totalOutState;
    
    private int maleRatio;
    private int femaleRatio;
    private String city;
    private String webUrl;
    private String admUrl;
    private String finUrl;
    private String appUrl;
    private String calcUrl;
    private double resourcesSpent;
    private double endowment;
    private String stateAbbr;
    private List<String> majors;
    
    public College() { }
    
    public College(String name) {
        this.name = name;
    }
   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getFirstYearEnrollment() {
        return firstYearEnrollment;
    }

    public void setFirstYearEnrollment(int enrollment) {
        this.firstYearEnrollment = enrollment;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getApplicants() {
        return applicants;
    }

    public void setApplicants(int applicants) {
        this.applicants = applicants;
    }

    public int getAdmits() {
        return admits;
    }

    public void setAdmits(int admits) {
        this.admits = admits;
    }

    public int getVer25() {
        return ver25;
    }

    public void setVer25(int ver25) {
        this.ver25 = ver25;
    }

    public int getVer75() {
        return ver75;
    }

    public void setVer75(int ver75) {
        this.ver75 = ver75;
    }

    public int getMat25() {
        return mat25;
    }

    public void setMat25(int mat25) {
        this.mat25 = mat25;
    }

    public int getMat75() {
        return mat75;
    }

    public void setMat75(int mat75) {
        this.mat75 = mat75;
    }

    public int getWri25() {
        return wri25;
    }

    public void setWri25(int wri25) {
        this.wri25 = wri25;
    }

    public int getWri75() {
        return wri75;
    }

    public void setWri75(int wri75) {
        this.wri75 = wri75;
    }

    public int getAct25() {
        return act25;
    }

    public void setAct25(int act25) {
        this.act25 = act25;
    }

    public int getAct75() {
        return act75;
    }

    public void setAct75(int act75) {
        this.act75 = act75;
    }
    
    public Classification getClassification() {
        return classification;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public int getApplicationFee() {
        return applicationFee;
    }

    public void setApplicationFee(int applicationFee) {
        this.applicationFee = applicationFee;
    }

    public int getRoomAndBoard() {
        return roomAndBoard;
    }

    public void setRoomAndBoard(int roomAndBoard) {
        this.roomAndBoard = roomAndBoard;
    }

    public int getGradCohort() {
        return gradCohort;
    }

    public void setGradCohort(int gradCohort) {
        this.gradCohort = gradCohort;
    }

    public int getFourYrGrad() {
        return fourYrGrad;
    }

    public void setFourYrGrad(int fourYrGrad) {
        this.fourYrGrad = fourYrGrad;
    }

    public int getSixYrGrad() {
        return sixYrGrad;
    }

    public void setSixYrGrad(int sixYrGrad) {
        this.sixYrGrad = sixYrGrad;
    }

    public int getTotalEnrolled() {
        return totalEnrolled;
    }

    public void setTotalEnrolled(int totalEnrolled) {
        this.totalEnrolled = totalEnrolled;
    }

    public int getPercentFinAid() {
        return percentFinAid;
    }

    public void setPercentFinAid(int percentFinAid) {
        this.percentFinAid = percentFinAid;
    }

    public int getPercentGrantAid() {
        return percentGrantAid;
    }

    public void setPercentGrantAid(int percentGrantAid) {
        this.percentGrantAid = percentGrantAid;
    }

    public int getNetCost() {
        return netCost;
    }

    public void setNetCost(int netCost) {
        this.netCost = netCost;
    }

    public int getNetCost1() {
        return netCost1;
    }

    public void setNetCost1(int netCost1) {
        this.netCost1 = netCost1;
    }

    public int getNetCost2() {
        return netCost2;
    }

    public void setNetCost2(int netCost2) {
        this.netCost2 = netCost2;
    }

    public int getNetCost3() {
        return netCost3;
    }

    public void setNetCost3(int netCost3) {
        this.netCost3 = netCost3;
    }

    public int getNetCost4() {
        return netCost4;
    }

    public void setNetCost4(int netCost4) {
        this.netCost4 = netCost4;
    }

    public int getNetCost5() {
        return netCost5;
    }

    public void setNetCost5(int netCost5) {
        this.netCost5 = netCost5;
    }

    public int getRetentionRate() {
        return retentionRate;
    }

    public void setRetentionRate(int retentionRate) {
        this.retentionRate = retentionRate;
    }

    public int getFacultyRatio() {
        return facultyRatio;
    }

    public void setFacultyRatio(int facultyRatio) {
        this.facultyRatio = facultyRatio;
    }

    public int getTuitionInState() {
        return tuitionInState;
    }

    public void setTuitionInState(int tuitionInState) {
        this.tuitionInState = tuitionInState;
    }

    public int getTuitionOutState() {
        return tuitionOutState;
    }

    public void setTuitionOutState(int tuitionOutState) {
        this.tuitionOutState = tuitionOutState;
    }

    public int getTotalInState() {
        return totalInState;
    }

    public void setTotalInState(int totalInState) {
        this.totalInState = totalInState;
    }

    public int getTotalOutState() {
        return totalOutState;
    }

    public void setTotalOutState(int totalOutState) {
        this.totalOutState = totalOutState;
    }

    public int getMaleRatio() {
        return maleRatio;
    }

    public void setMaleRatio(int maleRatio) {
        this.maleRatio = maleRatio;
    }

    public int getFemaleRatio() {
        return femaleRatio;
    }

    public void setFemaleRatio(int femaleRatio) {
        this.femaleRatio = femaleRatio;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getAdmUrl() {
        return admUrl;
    }

    public void setAdmUrl(String adminUrl) {
        this.admUrl = adminUrl;
    }

    public String getFinUrl() {
        return finUrl;
    }

    public void setFinUrl(String finUrl) {
        this.finUrl = finUrl;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public double getResourcesSpent() {
        return resourcesSpent;
    }

    public void setResourcesSpent(double resourcesSpent) {
        this.resourcesSpent = resourcesSpent;
    }

    public double getEndowment() {
        return endowment;
    }

    public void setEndowment(double endowment) {
        this.endowment = endowment;
    }

    public String getStateAbbr() {
        return stateAbbr;
    }

    public void setStateAbbr(String stateAbbr) {
        this.stateAbbr = stateAbbr;
    }

    public int getSat25() {
        return sat25;
    }

    public void setSat25(int sat25) {
        this.sat25 = sat25;
    }

    public int getSat75() {
        return sat75;
    }

    public void setSat75(int sat75) {
        this.sat75 = sat75;
    }

    public int getFourYrGradRate() {
        return fourYrGradRate;
    }

    public void setFourYrGradRate(int fourYrGradRate) {
        this.fourYrGradRate = fourYrGradRate;
    }

    public int getSixYrGradRate() {
        return sixYrGradRate;
    }

    public void setSixYrGradRate(int sixYrGradRate) {
        this.sixYrGradRate = sixYrGradRate;
    }

    public String getCalcUrl() {
        return calcUrl;
    }

    public void setCalcUrl(String calcUrl) {
        this.calcUrl = calcUrl;
    }

    public List<String> getMajors() {
        return majors;
    }

    public void setMajors(List<String> majors) {
        this.majors = majors;
    }
    
    public void addMajor(String major) {
        if (majors == null) majors = new ArrayList<String>();
        majors.add(major);
    }

    public enum Type {
        PUBLIC("Public"), PRIVATE_FP("Private for-profit"), 
        PRIVATE_NFP("Private not-for-profit"), PRIVATE_REL("Private religious");
        
        private final String name;
        private Type(String name) {
            this.name = name;
        }
        
        @Override
        public String toString() {
            return name;
        }
    }
    
    public enum Classification {
        RESEARCH_UNI("Research University"), LIBERAL_ARTS("Liberal Arts College"), MASTERS("Master's University"), BAC_COLLEGE("Baccalaureate College"),
        ENGINEERING("School of Engineering");
        
        private final String name;
        private Classification(String name) {
            this.name = name;
        }
        
        @Override
        public String toString() {
            return name;
        }
    }
    
    public enum Locale {
        CITY("City"), SUBURB("Suburb"), TOWN("Town"), RURAL("Rural");
        
        private final String name;
        private Locale(String name) {
            this.name = name;
        }
        
        @Override
        public String toString() {
            return name;
        }
    }

    @Override
    public int compareTo(College another) {
        return this.name.compareTo(another.name);
    }
    
    @Override
    public String toString() {
        return name;
    }
}
