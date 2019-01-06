package com.apextechies.eretort.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.apextechies.eretort.BuildConfig;
import com.apextechies.eretort.R;
import com.apextechies.eretort.allinterface.OnClickListener;
import com.apextechies.eretort.model.TeacherListDataModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;


public class TeacherListAdapter extends RecyclerView.Adapter<TeacherListAdapter.MyViewHolder> {

    private List<TeacherListDataModel> images = new ArrayList<>();
    private Context mContext;
    private OnClickListener onClickListener;

    public TeacherListAdapter(Context context, List<TeacherListDataModel> images, OnClickListener onClickListener) {
        mContext = context;
        this.images = images;
        this.onClickListener = onClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.teacherlist_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        TeacherListDataModel teacherListDataModel = images.get(position);

        if (teacherListDataModel != null && teacherListDataModel.getStatus().equalsIgnoreCase("1")) {
            holder.itemView.setVisibility(View.VISIBLE);
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.user);
            requestOptions.error(R.drawable.user);
            requestOptions.fitCenter();
            /*String imageUrl = BuildConfig.WEBSITE_URL + teacherListDataModel.getImage();
            Glide.with(mContext)
                    .load(imageUrl)
                    .apply(requestOptions)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.progressBar.setVisibility(View.GONE);

                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(holder.teacherProfilePicIv);*/
            if (!TextUtils.isEmpty(teacherListDataModel.getQualification())) {
                holder.qualificationTv.setVisibility(View.VISIBLE);
                holder.qualificationTv.setText("Qual.-" + teacherListDataModel.getQualification());
            } else {
                holder.qualificationTv.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(teacherListDataModel.getWhat_teach())) {
                holder.subjectTv.setVisibility(View.VISIBLE);
                if (teacherListDataModel.getWhat_teach() != null &&
                        teacherListDataModel.getWhat_teach().toLowerCase().contains("driver") &&
                        teacherListDataModel.getWhat_teach().toLowerCase().contains("guard") &&
                        teacherListDataModel.getWhat_teach().toLowerCase().contains("security") &&
                        teacherListDataModel.getWhat_teach().toLowerCase().contains("accountant") &&
                        teacherListDataModel.getWhat_teach().toLowerCase().contains("librarian")) {
                    holder.subjectTv.setText("Role-" + teacherListDataModel.getWhat_teach());
                } else {
                    holder.subjectTv.setText("Teaching-" + teacherListDataModel.getWhat_teach());
                }
            } else {
                holder.subjectTv.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(teacherListDataModel.getName())) {
                holder.teacherName.setVisibility(View.VISIBLE);
                holder.teacherName.setText(teacherListDataModel.getName());
            } else {
                holder.teacherName.setVisibility(View.GONE);
            }
        } else {
            holder.itemView.setVisibility(View.GONE);
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
        return images.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView teacherProfilePicIv;
        public TextView qualificationTv;
        public TextView subjectTv;
        public TextView teacherName;
        private ProgressBar progressBar;
        private LinearLayout mainLl;

        public MyViewHolder(View view) {
            super(view);
            teacherProfilePicIv = (ImageView) view.findViewById(R.id.teacherProfilePicIv);
            qualificationTv = (TextView) view.findViewById(R.id.qualificationTv);
            subjectTv = (TextView) view.findViewById(R.id.subjectTv);
            teacherName = (TextView) view.findViewById(R.id.teacherName);
            progressBar = (ProgressBar) view.findViewById(R.id.progress);
            mainLl = (LinearLayout) view.findViewById(R.id.mainLl);
            teacherProfilePicIv = view.findViewById(R.id.teacherProfilePicIv);
            qualificationTv = view.findViewById(R.id.qualificationTv);
            subjectTv = view.findViewById(R.id.subjectTv);
            teacherName = view.findViewById(R.id.teacherName);
            //  progressBar = (ProgressBar)view.findViewById(R.id.progress);
        }
    }


}
