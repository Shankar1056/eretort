package com.apextechies.eretort.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apextechies.eretort.R;
import com.apextechies.eretort.allinterface.OnClickListener;
import com.apextechies.eretort.model.LeaveRequestModel;

import java.util.ArrayList;

/**
 * Created by Shankar on 3/17/2018.
 */

public class LeaveRequestListAdapter extends RecyclerView.Adapter<LeaveRequestListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<LeaveRequestModel> imageList;
    private OnClickListener clickPosition;

    public LeaveRequestListAdapter(Context context, ArrayList<LeaveRequestModel> imageList, OnClickListener clickPosition) {
        this.context = context;
        this.imageList = imageList;
        this.clickPosition = clickPosition;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaverequest_item,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.studName.setText("Student Name: "+imageList.get(position).getName());
        holder.status.setText("Application Status: "+imageList.get(position).getStatus());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickPosition.onclick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView studName, status;

        public ViewHolder(View itemView) {
            super(itemView);
            studName = (TextView)itemView.findViewById(R.id.studName);
            status = (TextView)itemView.findViewById(R.id.status);

        }
    }
}
