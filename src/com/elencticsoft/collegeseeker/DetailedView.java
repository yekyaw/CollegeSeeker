package com.elencticsoft.collegeseeker;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

public class DetailedView extends SherlockFragment {
    
    public static DetailedView newInstance(int collegeId) {
        DetailedView detailedView = new DetailedView();
        Bundle bundle = new Bundle();
        bundle.putInt("college", collegeId);
        detailedView.setArguments(bundle);
        return detailedView;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.college_detailed, container, false);
    }
    
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        int collegeId = getArguments().getInt("college");
        College college = DatabaseHandler.getInstance(getSherlockActivity()).getCollege(collegeId);
        ImageView imageView = (ImageView) view.findViewById(R.id.college_logo);
        TextView collegeView = (TextView) view.findViewById(R.id.college);
        TextView enrollmentView = (TextView) view.findViewById(R.id.enrollment);
        TextView acceptanceView = (TextView) view.findViewById(R.id.acceptance);
        TextView typeView = (TextView) view.findViewById(R.id.type);
        TextView sat25View = (TextView) view.findViewById(R.id.sat25);
        TextView sat75View = (TextView) view.findViewById(R.id.sat75);
        TextView actView = (TextView) view.findViewById(R.id.act);
        TextView gradView = (TextView) view.findViewById(R.id.grad);
        TextView retentionView = (TextView) view.findViewById(R.id.retention);
        TextView costView = (TextView) view.findViewById(R.id.cost);
        TextView facultyView = (TextView) view.findViewById(R.id.faculty_ratio);
        TextView tuitionView = (TextView) view.findViewById(R.id.tuition);
        TextView roomBoardView = (TextView) view.findViewById(R.id.room_board);
        TextView percentAidView = (TextView) view.findViewById(R.id.percent_aid);
        TextView percentGrantAidView = (TextView) view.findViewById(R.id.percent_grant_aid);
        TextView netCostView = (TextView) view.findViewById(R.id.net_cost);
        TextView netCost1View = (TextView) view.findViewById(R.id.net_cost1);
        TextView netCost2View = (TextView) view.findViewById(R.id.net_cost2);
        TextView netCost3View = (TextView) view.findViewById(R.id.net_cost3);
        TextView netCost4View = (TextView) view.findViewById(R.id.net_cost4);
        TextView netCost5View = (TextView) view.findViewById(R.id.net_cost5);
        TextView maleFemaleView = (TextView) view.findViewById(R.id.male_female_ratio);
        TextView resourcesView = (TextView) view.findViewById(R.id.resources);
        TextView endowmentView = (TextView) view.findViewById(R.id.endowment);
        TextView appFeeView = (TextView) view.findViewById(R.id.app_fee);
        TextView webUrlView = (TextView) view.findViewById(R.id.web_url);
        TextView admUrlView = (TextView) view.findViewById(R.id.adm_url);
        TextView finUrlView = (TextView) view.findViewById(R.id.fin_url);
        TextView appUrlView = (TextView) view.findViewById(R.id.app_url);
        TextView calcUrlView = (TextView) view.findViewById(R.id.calc_url);
  
        collegeView.setText(String.format("%s, %s, %s", college.getName(), college.getCity(), 
                college.getStateAbbr()));
        appFeeView.setText(String.format("$%d", college.getApplicationFee()));
        webUrlView.setText(college.getWebUrl());
        admUrlView.setText(college.getAdmUrl());
        finUrlView.setText(college.getFinUrl());
        appUrlView.setText(college.getAppUrl());
        calcUrlView.setText(college.getCalcUrl());
        maleFemaleView.setText(String.format("%d%%/%d%%", college.getMaleRatio(), college.getFemaleRatio()));
        enrollmentView.setText(String.valueOf(college.getTotalEnrolled()));
        double rate = 0;
        if (college.getApplicants() != 0) rate = (double) college.getAdmits() / college.getApplicants() * 100;
        acceptanceView.setText(String.format("%2.1f%%", rate));
        typeView.setText(String.format("%s/%s", college.getType(), college.getClassification()));
        sat25View.setText(String.format("%d/%d/%d ", college.getVer25(), college.getMat25(), college.getWri25()));
        sat75View.setText(String.format("%d/%d/%d", college.getVer75(), college.getMat75(), college.getWri75()));
        actView.setText(String.format("%d-%d", college.getAct25(), college.getAct75()));
        if (college.getIcon() != null) {
            Drawable d = getResources().getDrawable(getResources().getIdentifier(college.getIcon(), "drawable", 
                    getSherlockActivity().getPackageName()));
            // set image to ImageView
            imageView.setImageDrawable(d);
        }
        else imageView.setImageDrawable(null);
        gradView.setText(String.format("%d%%/%d%% ", college.getFourYrGradRate(), college.getSixYrGradRate()));
        retentionView.setText(String.format("%d%%", college.getRetentionRate()));
        costView.setText(String.format("$%d/$%d", college.getTotalInState(), college.getTotalOutState()));
        facultyView.setText(String.valueOf(college.getFacultyRatio()));
        
        tuitionView.setText(String.format("$%d/$%d", college.getTuitionInState(), college.getTuitionOutState()));
        roomBoardView.setText(String.format("$%d", college.getRoomAndBoard()));
        percentAidView.setText(String.format("%d%%", college.getPercentFinAid()));
        percentGrantAidView.setText(String.format("%d%%", college.getPercentGrantAid()));
        netCostView.setText(String.format("$%d", college.getNetCost()));
        netCost1View.setText(String.format("$%d", college.getNetCost1()));
        netCost2View.setText(String.format("$%d", college.getNetCost2()));
        netCost3View.setText(String.format("$%d", college.getNetCost3()));
        netCost4View.setText(String.format("$%d", college.getNetCost4()));
        netCost5View.setText(String.format("$%d", college.getNetCost5()));
        resourcesView.setText(String.format("$%.0f", college.getResourcesSpent()));
        endowmentView.setText(String.format("$%.0f", college.getEndowment()));
    }
}
