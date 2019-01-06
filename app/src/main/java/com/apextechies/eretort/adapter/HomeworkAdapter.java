package com.apextechies.eretort.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apextechies.eretort.R;
import com.apextechies.eretort.allinterface.OnClickListener;
import com.apextechies.eretort.model.HomeworkModel;

import java.util.List;

/**
 * Created by Shankar on 3/5/2018.
 */

public class HomeworkAdapter extends RecyclerView.Adapter<HomeworkAdapter.MyViewHolder> {

    private List<HomeworkModel> noticeBoardModels;
    private Context context;
    private int noticeboard_row;
    private OnClickListener onClickListener;
    private int timetablerow;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_timetable, tv_subject, tv_teac_name,tv_status;

        public MyViewHolder(View view) {
            super(view);
            tv_timetable = (TextView) view.findViewById(R.id.tv_timetable);
            tv_subject = (TextView) view.findViewById(R.id.tv_subject);
            tv_teac_name = (TextView) view.findViewById(R.id.tv_teac_name);
            tv_status = (TextView) view.findViewById(R.id.tv_status);
        }
    }


    public HomeworkAdapter(Context context, List<HomeworkModel> noticeBoardModels, int timetablerow, OnClickListener onClickListener) {
        this.context = context;
        this.noticeBoardModels = noticeBoardModels;
        this.timetablerow = timetablerow;
        this.onClickListener = onClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(timetablerow, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final HomeworkModel sa = noticeBoardModels.get(position);
        // holder.CatTextView.setTypeface(Utilz.font(context, "bold"));

        holder.tv_timetable.setText("Time "+sa.getFrom_time()+" to "+sa.getTo_time());
        holder.tv_subject.setText(sa.getSubject());
        holder.tv_teac_name.setText("Faculty - " + sa.getTeacher_name());
        holder.tv_status.setText("Status - " + sa.getStatus());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onclick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return noticeBoardModels.size();
    }

}



