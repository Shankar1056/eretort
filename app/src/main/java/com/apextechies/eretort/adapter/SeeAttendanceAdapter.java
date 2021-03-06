package com.apextechies.eretort.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apextechies.eretort.R;
import com.apextechies.eretort.common.AppConstants;
import com.apextechies.eretort.model.StudentListDataModel;

import java.util.List;


public class SeeAttendanceAdapter extends RecyclerView.Adapter<SeeAttendanceAdapter.MyViewHolder> {

    private List<StudentListDataModel> mStudentsList;
    private Activity mActivity;

    public SeeAttendanceAdapter(Activity mActivity, List<StudentListDataModel> studentListDataModels) {
        this.mStudentsList = studentListDataModels;
        this.mActivity = mActivity;
    }

    // Create new views
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_itemview, parent, false);

        // create ViewHolder
        return new MyViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, int position) {
        if (mStudentsList != null && mStudentsList.size() > 0) {
            viewHolder.tvName.setText(mStudentsList.get(position).getStudentName());
            if (mStudentsList.get(position).getRollNo() > 0) {
                viewHolder.tvEmailId.setText(String.format(mActivity.getResources().getString(R.string.roll_no_with_value),
                        mStudentsList.get(position).getRollNo() + ""));
            } else {
                viewHolder.tvEmailId.setText(String.format(mActivity.getResources().getString(R.string.roll_no_with_value), "N/A"));
            }
            viewHolder.presentButton.setTag(mStudentsList.get(position));
            viewHolder.absentButton.setTag(mStudentsList.get(position));
            if (mStudentsList.get(position).getStatus().equalsIgnoreCase(AppConstants.PRESENT)) {
                managePresentAbsent(viewHolder.presentButton, viewHolder.absentButton, true);
            } else {
                managePresentAbsent(viewHolder.presentButton, viewHolder.absentButton, false);
            }
        }
    }

    @SuppressLint("NewApi")
    private void managePresentAbsent(TextView presentBtn, TextView absentBtn, boolean isPresent) {
        if (presentBtn != null && absentBtn != null) {
            if (isPresent) {
                presentBtn.setTextColor(mActivity.getResources().getColor(R.color.white));
                presentBtn.setBackground(mActivity.getResources().getDrawable(R.drawable.present_button_bg));
                absentBtn.setTextColor(mActivity.getResources().getColor(R.color.textColor));
                absentBtn.setBackground(mActivity.getResources().getDrawable(R.drawable.border));
            } else {
                presentBtn.setTextColor(mActivity.getResources().getColor(R.color.textColor));
                presentBtn.setBackground(mActivity.getResources().getDrawable(R.drawable.border));
                absentBtn.setTextColor(mActivity.getResources().getColor(R.color.white));
                absentBtn.setBackground(mActivity.getResources().getDrawable(R.drawable.absent_button_bg));
            }
        }
    }

    // Return the size arraylist
    @Override
    public int getItemCount() {
        return mStudentsList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public TextView tvEmailId;
        public TextView absentButton, presentButton;

        public MyViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            tvName = itemLayoutView.findViewById(R.id.tvName);
            tvEmailId = itemLayoutView.findViewById(R.id.tvEmailId);
            absentButton = itemLayoutView.findViewById(R.id.absentButton);
            presentButton = itemLayoutView.findViewById(R.id.presentButton);
        }
    }
}
