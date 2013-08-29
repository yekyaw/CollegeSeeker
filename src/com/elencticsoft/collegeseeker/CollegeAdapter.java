package com.elencticsoft.collegeseeker;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class CollegeAdapter extends CursorAdapter {   
    public CollegeAdapter(Context context, Cursor cursor) {
        super(context, cursor, FLAG_REGISTER_CONTENT_OBSERVER);
    }

    static class ViewHolder {
        ImageView imageView;
        TextView collegeView;
        TextView enrollmentView;
        TextView acceptanceView;
        TextView typeView;
        TextView sat25View;
        TextView sat75View;
        TextView actView;
        TextView gradView;
        TextView costView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();

        College college = CollegeHelper.convertToCollege(cursor);
        holder.collegeView.setText(String.format("%s, %s, %s", college.getName(), college.getCity(), 
                college.getStateAbbr()));
        holder.enrollmentView.setText(String.format("%d", college.getTotalEnrolled()));
        double rate = 0;
        if (college.getApplicants() != 0) rate = (double) college.getAdmits() / college.getApplicants() * 100;
        holder.acceptanceView.setText(String.format("%2.1f%%", rate));
        holder.typeView.setText(String.format("%s/%s", college.getType(), college.getClassification()));
        holder.sat25View.setText(String.format("%d/%d/%d ", college.getVer25(), college.getMat25(), college.getWri25()));
        holder.sat75View.setText(String.format("%d/%d/%d", college.getVer75(), college.getMat75(), college.getWri75()));
        holder.actView.setText(String.format("%d-%d", college.getAct25(), college.getAct75()));
        if (college.getIcon() != null) {
            Drawable d = context.getResources().getDrawable(context.getResources().getIdentifier(college.getIcon(), "drawable", 
                    context.getPackageName()));
            // set image to ImageView
            holder.imageView.setImageDrawable(d);
        }
        else holder.imageView.setImageDrawable(null);
        holder.gradView.setText(String.format("%d%%/%d%% ", college.getFourYrGradRate(), college.getSixYrGradRate()));
        holder.costView.setText(String.format("$%d/$%d", college.getTotalInState(), college.getTotalOutState()));
        //            gradView.setText(String.format("%d %d %d", college.getFourYrGrad(), college.getSixYrGrad(), college.getGradCohort()));
        //            retentionView.setText(String.format("%d %d", college.getTransfers(), college.getNoLongerEnrolled()));
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.college_list_item, parent, false);
        ViewHolder holder = new ViewHolder();
        holder.imageView = (ImageView) view.findViewById(R.id.college_logo);
        holder.collegeView = (TextView) view.findViewById(R.id.college);
        holder.enrollmentView = (TextView) view.findViewById(R.id.enrollment);
        holder.acceptanceView = (TextView) view.findViewById(R.id.acceptance);
        holder.typeView = (TextView) view.findViewById(R.id.type);
        holder.sat25View = (TextView) view.findViewById(R.id.sat25);
        holder.sat75View = (TextView) view.findViewById(R.id.sat75);
        holder.actView = (TextView) view.findViewById(R.id.act);
        holder.gradView = (TextView) view.findViewById(R.id.grad);
        holder.costView = (TextView) view.findViewById(R.id.cost);
        view.setTag(holder);

        return view;
    }
}