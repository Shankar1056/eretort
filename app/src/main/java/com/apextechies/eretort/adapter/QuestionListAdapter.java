package com.apextechies.eretort.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.apextechies.eretort.R;
import com.apextechies.eretort.allinterface.OnClickListener;
import com.apextechies.eretort.model.AskQuestionModel;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

/**
 * Created by Shankar on 12/31/2017.
 */

public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.MyViewHolder> {

    private List<AskQuestionModel> noticeBoardModels;
    private Context context;
    private int noticeboard_row;
    private OnClickListener onClickListener;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView quesText,date,time;
        public ImageView quesImage,profile_image;
        public LinearLayout layout;
        private ProgressBar progressBar;

        public MyViewHolder(View view) {
            super(view);
            quesText = (TextView) view.findViewById(R.id.quesText);
            profile_image = (ImageView) view.findViewById(R.id.profile_image);
            quesImage = (ImageView) view.findViewById(R.id.quesImage);
            date = (TextView) view.findViewById(R.id.date);
            time = (TextView) view.findViewById(R.id.time);
           // progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        }
    }


    public QuestionListAdapter(Context context, List<AskQuestionModel> noticeBoardModels, OnClickListener onClickListener) {
        this.context = context;
        this.noticeBoardModels = noticeBoardModels;
        this.onClickListener = onClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.questionlist_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final AskQuestionModel sa = noticeBoardModels.get(position);
        holder.quesText.setText("Ques: "+sa.getQuestionText());
        holder.date.setText("Date: "+sa.getDate());
        holder.time.setText("Time: "+sa.getTime());
        if (sa.getQuestionImage().equals(""))
        {
            holder.quesImage.setVisibility(View.GONE);
        }
        else {
            holder.quesImage.setVisibility(View.VISIBLE);
            Picasso.with(context).load(sa.getQuestionImage()).into(holder.quesImage);
           /* Glide.with(context)
                    .load("https://raw.githubusercontent.com/bumptech/glide/master/static/glide_logo.png")
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, com.bumptech.glide.request.target.Target<Drawable> target, boolean isFirstResource) {
                            holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, com.bumptech.glide.request.target.Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                           holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }


                    })
                    .into(holder.quesImage);
*/
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



