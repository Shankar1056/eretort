package com.apextechies.eretort.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apextechies.eretort.R;
import com.apextechies.eretort.allinterface.OnClickListener;
import com.apextechies.eretort.model.EventsModel;

import java.util.List;

/**
 * Created by Shankar on 12/17/2017.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder> {

    private List<EventsModel> noticeBoardModels;
    private Context context;
    private int noticeboard_row;
    private OnClickListener onClickListener;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textHeading, textTitle, textShortDesc;

        public MyViewHolder(View view) {
            super(view);
            textHeading = (TextView) view.findViewById(R.id.textHeading);
            textTitle = (TextView) view.findViewById(R.id.textTitle);
            textShortDesc = (TextView) view.findViewById(R.id.textShortDesc);
        }
    }


    public EventsAdapter(Context context, List<EventsModel> noticeBoardModels, OnClickListener onClickListener) {
        this.context = context;
        this.noticeBoardModels = noticeBoardModels;
        this.onClickListener = onClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.events_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final EventsModel sa = noticeBoardModels.get(position);
        // holder.CatTextView.setTypeface(Utilz.font(context, "bold"));

        holder.textHeading.setText(sa.getEventsTitle());
        holder.textTitle.setText(sa.getEventsShortDesc());
        holder.textShortDesc.setText("[" + sa.getEvents_date() + "] ");
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


