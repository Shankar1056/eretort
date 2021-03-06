package com.apextechies.eretort.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apextechies.eretort.R;
import com.apextechies.eretort.activity.TakeAttendenceActivity;
import com.apextechies.eretort.model.StudentListDataModel;
import com.apextechies.eretort.utilz.Utilz;

import java.util.List;


public class AttendenceAdapter extends RecyclerView.Adapter<AttendenceAdapter.ViewHolder> {

    private List<StudentListDataModel> mStudentsList;
    private Activity mActivity;
    private int mIntTotalStudentCount;
    private int mIntAbsentStudentCount;

    public AttendenceAdapter(Activity mActivity, List<StudentListDataModel> studentListDataModels) {
        this.mStudentsList = studentListDataModels;
        this.mActivity = mActivity;
        mIntTotalStudentCount = 0;
        if (studentListDataModels != null && studentListDataModels.size() > 0) {
            mIntAbsentStudentCount = mIntTotalStudentCount = mStudentsList.size();
        }
        ((TakeAttendenceActivity) mActivity).manageAbsentPresentCount(mIntTotalStudentCount, mIntAbsentStudentCount);
    }

    // Create new views
    @Override
    public AttendenceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_itemview, parent, false);

        // create ViewHolder
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

        final int pos = position;
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

            viewHolder.presentButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    managePresentAbsent(viewHolder.presentButton, viewHolder.absentButton, true);

                    if (v != null && v instanceof TextView) {
                        TextView cb = (TextView) v;
                        StudentListDataModel contact = (StudentListDataModel) cb.getTag();
                        if (!contact.isSelected()) {
                            mIntAbsentStudentCount = mIntAbsentStudentCount - 1;
                            ((TakeAttendenceActivity) mActivity).manageAbsentPresentCount(mIntTotalStudentCount, mIntAbsentStudentCount);
                        }
                        contact.setSelected(true);
                    }
                    mStudentsList.get(pos).setSelected(true);
                }
            });
            viewHolder.absentButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    managePresentAbsent(viewHolder.presentButton, viewHolder.absentButton, false);
                    if (v != null && v instanceof TextView) {
                        TextView cb = (TextView) v;
                        StudentListDataModel contact = (StudentListDataModel) cb.getTag();

                        if (contact.isSelected()) {
                            mIntAbsentStudentCount = mIntAbsentStudentCount + 1;
                            ((TakeAttendenceActivity) mActivity).manageAbsentPresentCount(mIntTotalStudentCount, mIntAbsentStudentCount);
                        }

                        contact.setSelected(false);
                    }
                    mStudentsList.get(pos).setSelected(false);
                }
            });

            viewHolder.absentButton.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    managePresentAbsent(viewHolder.presentButton, viewHolder.absentButton, false);
                    if (v != null && v instanceof TextView) {
                        TextView cb = (TextView) v;
                        StudentListDataModel contact = (StudentListDataModel) cb.getTag();
                        if (contact != null) {
                            Utilz.showMessageDialog(mActivity, "This feature is coming soon...");
                        }
                    }
                    return false;
                }
            });

            if (mStudentsList.get(position).isSelected()) {
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

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public TextView tvEmailId;
        public TextView absentButton, presentButton;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            tvName = itemLayoutView.findViewById(R.id.tvName);
            tvEmailId = itemLayoutView.findViewById(R.id.tvEmailId);
            absentButton = itemLayoutView.findViewById(R.id.absentButton);
            presentButton = itemLayoutView.findViewById(R.id.presentButton);
        }

    }

}
