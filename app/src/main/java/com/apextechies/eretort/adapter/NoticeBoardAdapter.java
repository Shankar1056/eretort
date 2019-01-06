package com.apextechies.eretort.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apextechies.eretort.R;
import com.apextechies.eretort.allinterface.OnClickListener;
import com.apextechies.eretort.model.NoticeBoardModel;

import java.util.List;

/**
 * Created by shankar on 12/12/17.
 */

public class NoticeBoardAdapter extends RecyclerView.Adapter<NoticeBoardAdapter.MyViewHolder> {

    private List<NoticeBoardModel> noticeBoardModels;
    private Context context;
    private int noticeboard_row;
    private OnClickListener onClickListener;
    private String fromclas;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textHeading,textTitle,textShortDesc;

        public MyViewHolder(View view) {
            super(view);
            textHeading = (TextView) view.findViewById(R.id.textHeading);
            textTitle = (TextView) view.findViewById(R.id.textTitle);
            textShortDesc = (TextView) view.findViewById(R.id.textShortDesc);
        }
    }


    public NoticeBoardAdapter(Context context, List<NoticeBoardModel> noticeBoardModels, int noticeboard_row,String fromclas, OnClickListener onClickListener) {
        this.context = context;
        this.noticeBoardModels = noticeBoardModels;
        this.onClickListener = onClickListener;
        this.noticeboard_row = noticeboard_row;
        this.fromclas = fromclas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(noticeboard_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final NoticeBoardModel sa = noticeBoardModels.get(position);
        // holder.CatTextView.setTypeface(Utilz.font(context, "bold"));

        holder.textHeading.setText(sa.getHeading());
        holder.textTitle.setText(sa.getTitle());
        if (fromclas.equals("noticeboard"))
        {
            holder.textShortDesc.setText("[" + sa.getPostedDate() + "] " + sa.getLondesc());
        }
        else if (fromclas.equals("notification"))
        {
            holder.textShortDesc.setText("[" + sa.getPostedDate() + "] ");
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

