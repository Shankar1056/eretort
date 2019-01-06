package com.apextechies.eretort.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apextechies.eretort.R;
import com.apextechies.eretort.allinterface.OnClickListener;
import com.apextechies.eretort.model.ReplyModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Shankar on 1/1/2018.
 */

public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.MyViewHolder> {

    private List<ReplyModel> noticeBoardModels;
    private Context context;
    private int replylist_row;
    private OnClickListener onClickListener;

   public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView quesText,date, time;
        public ImageView quesImage,profile_image;
        public LinearLayout layout;

        public MyViewHolder(View view) {
            super(view);
            quesText = (TextView) view.findViewById(R.id.quesText);
            profile_image = (ImageView) view.findViewById(R.id.profile_image);
            quesImage = (ImageView) view.findViewById(R.id.quesImage);
            date = (TextView) view.findViewById(R.id.date);
            time = (TextView) view.findViewById(R.id.time);
        }
    }


    public ReplyAdapter(Context context, List<ReplyModel> noticeBoardModels, int replylist_row, OnClickListener onClickListener) {
        this.context = context;
        this.noticeBoardModels = noticeBoardModels;
        this.onClickListener = onClickListener;
        this.replylist_row = replylist_row;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.questionlist_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ReplyModel sa = noticeBoardModels.get(position);
        holder.quesText.setText(sa.getReplyText());
        holder.date.setText("Date: "+sa.getDate());
        holder.time.setText("Time: "+sa.getTime());
        if (sa.getReplyImage().equals(""))
        {
            holder.quesImage.setVisibility(View.GONE);
        }
        else {
            holder.quesImage.setVisibility(View.VISIBLE);
            Picasso.with(context).load(sa.getReplyImage()).into(holder.quesImage);
        }
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




