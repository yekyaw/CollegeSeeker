package com.elencticsoft.collegeseeker;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

public class CollegeMatchAdapter extends ArrayAdapter<College> {
        private List<College> colleges;
        private List<College> originalColleges;
        private final int rowResourceId;
        private Filter filter;

        public CollegeMatchAdapter(Context context, int textViewResourceId, List<College> objects) {
            super(context, textViewResourceId, objects);
            this.colleges = objects;
            this.originalColleges = new ArrayList<College>(objects);
            this.rowResourceId = textViewResourceId;
            filter = new NameFilter();
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
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(rowResourceId, parent, false);
                holder = new ViewHolder();
                holder.imageView = (ImageView) convertView.findViewById(R.id.college_logo);
                holder.collegeView = (TextView) convertView.findViewById(R.id.college);
                holder.enrollmentView = (TextView) convertView.findViewById(R.id.enrollment);
                holder.acceptanceView = (TextView) convertView.findViewById(R.id.acceptance);
                holder.typeView = (TextView) convertView.findViewById(R.id.type);
                holder.sat25View = (TextView) convertView.findViewById(R.id.sat25);
                holder.sat75View = (TextView) convertView.findViewById(R.id.sat75);
                holder.actView = (TextView) convertView.findViewById(R.id.act);
                holder.gradView = (TextView) convertView.findViewById(R.id.grad);
                holder.costView = (TextView) convertView.findViewById(R.id.cost);
                convertView.setTag(holder);
            }
            else holder = (ViewHolder) convertView.getTag();

            College college = colleges.get(position);
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
                Drawable d = getContext().getResources().getDrawable(getContext().getResources().getIdentifier(college.getIcon(), "drawable", getContext().getPackageName()));
            // set image to ImageView
                holder.imageView.setImageDrawable(d);
            }
            else holder.imageView.setImageDrawable(null);
            holder.gradView.setText(String.format("%d%%/%d%% ", college.getFourYrGradRate(), college.getSixYrGradRate()));
            holder.costView.setText(String.format("$%d/$%d", college.getTotalInState(), college.getTotalOutState()));
//            gradView.setText(String.format("%d %d %d", college.getFourYrGrad(), college.getSixYrGrad(), college.getGradCohort()));
//            retentionView.setText(String.format("%d %d", college.getTransfers(), college.getNoLongerEnrolled()));
            
            return convertView;
        }
        
        @Override
        public Filter getFilter() {
            return filter;
        }

        private class NameFilter extends Filter {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                constraint = constraint.toString().toLowerCase(Locale.ENGLISH);
                FilterResults result = new FilterResults();
                if ((constraint != null) && (constraint.toString().length() > 0))
                {
                    List<College> filteredItems = new ArrayList<College>();

                    for (College college: originalColleges) {
                        if (college.getName().toLowerCase(Locale.ENGLISH).contains(constraint))
                                filteredItems.add(college);
                    }
                    result.count = filteredItems.size();
                    result.values = filteredItems;
                }
                else
                {
                    result.values = originalColleges;
                    result.count = originalColleges.size();
                }
                return result;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                synchronized(this) {
                    colleges.clear();
                    colleges.addAll((ArrayList<College>) results.values);
                }
                notifyDataSetChanged();
            }
        }
    }