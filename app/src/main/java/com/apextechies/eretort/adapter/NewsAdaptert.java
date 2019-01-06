package com.apextechies.eretort.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apextechies.eretort.R;
import com.apextechies.eretort.allinterface.OnClickListener;
import com.apextechies.eretort.model.NewsModel;

import java.util.List;

/**
 * Created by Shankar on 12/17/2017.
 */

public class NewsAdaptert extends RecyclerView.Adapter<NewsAdaptert.MyViewHolder> {

    private List<NewsModel> noticeBoardModels;
    private Context context;
    private int noticeboard_row;
    private OnClickListener onClickListener;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textHeading,textTitle,textShortDesc;

        public MyViewHolder(View view) {
            super(view);
            textHeading = (TextView) view.findViewById(R.id.textHeading);
            textTitle = (TextView) view.findViewById(R.id.textTitle);
            textShortDesc = (TextView) view.findViewById(R.id.textShortDesc);
        }
    }


    public NewsAdaptert(Context context, List<NewsModel> noticeBoardModels, OnClickListener onClickListener) {
        this.context = context;
        this.noticeBoardModels = noticeBoardModels;
        this.onClickListener = onClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final NewsModel sa = noticeBoardModels.get(position);
        // holder.CatTextView.setTypeface(Utilz.font(context, "bold"));

        holder.textHeading.setText(sa.getNewsTitle());
        holder.textTitle.setText(sa.getNewsShortDesc());
            holder.textShortDesc.setText("[" + sa.getNews_date() + "] " );


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

